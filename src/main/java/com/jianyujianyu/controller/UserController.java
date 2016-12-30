package com.jianyujianyu.controller;

import com.jianyujianyu.model.LevelEntity;
import com.jianyujianyu.model.LogEntity;
import com.jianyujianyu.model.UserEntity;
import com.jianyujianyu.repository.LevelRepository;
import com.jianyujianyu.repository.LogRepository;
import com.jianyujianyu.repository.UserRepository;
import com.jianyujianyu.service.LogService;
import com.jianyujianyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;


/**
 * Created by aimreant on 11/10/16.
 */
@Controller
public class UserController extends BaseController {

    @Autowired UserRepository userRepository;
    @Autowired LevelRepository levelRepository;
    @Autowired UserService userService;
    @Autowired LogRepository logRepository;
    @Autowired LogService logService;

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String userGet(
            @RequestParam(value="deleted", required=false) Integer deleted,
            ModelMap modelMap
    ) {

        // Get all the level entities
        List<LevelEntity> levelEntityList = levelRepository.findAll();
        modelMap.addAttribute("levelEntityList", levelEntityList);

        // Get deleted parameter
        if(deleted != null && deleted == 1) {
            // Get all the deleted user entities
            List<UserEntity> userEntityList = userRepository.findByDeletedAtIsNotNull();
            modelMap.addAttribute("deleted", true);
            modelMap.addAttribute("userEntityList", userEntityList);
        }else {
            // Get all the user entities
            List<UserEntity> userEntityList = userRepository.findByDeletedAtIsNull();
            modelMap.addAttribute("deleted", false);
            modelMap.addAttribute("userEntityList", userEntityList);
        }

        return "dashboard/admin/user";
    }

    /**
     * Create a new user, which is an operation of admin
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin/users", method = RequestMethod.POST)
    public String userPost(HttpServletRequest request, RedirectAttributes attr) {

        // Get the new user info from mask form
        String userUsername = request.getParameter("username").trim();
        String userPassword = request.getParameter("password");
        String userLevelStr = request.getParameter("level").trim();
        Integer userLevel = Integer.parseInt((userLevelStr.split("\\("))[0].trim());

        UserEntity userEntity = userRepository.findByUsername(userUsername);
        if(userEntity != null){
            returnMsg(attr, ERROR, "Username exists!");
            return "redirect:/admin/users";
        }else{
            userEntity = userService.createUser(
                userUsername,
                userPassword,
                levelRepository.findOne(userLevel)
            );
        }

        returnMsg(attr, SUCCESS, "Creating user succeed!");

        return "redirect:/admin/users";
    }

    /**
     * delete user, which is an operation of admin
     * @param attr
     * @return
     */
    @RequestMapping(value = "/admin/users/{user_id}", method = RequestMethod.DELETE)
    public String userPut(
            @PathVariable("user_id") Integer userId,
            @RequestParam(value="pre", required=false) Integer pre,
            @RequestParam(value="forever", required=false) Integer forever,
            RedirectAttributes attr) {

        UserEntity userEntity = userRepository.findOne(userId);
        if(userEntity == null){
            returnMsg(attr, ERROR, "User not exists or has been deleted!");
            return "redirect:/admin/users";
        }

        if(pre == null || pre != 1) {
            userEntity.setDeletedAt(new Timestamp(System.currentTimeMillis()));
            userRepository.saveAndFlush(userEntity);
            returnMsg(attr, SUCCESS, "Delete succeed!");
        }else{
            returnConfirmMsg(
                    attr,
                    "Really delete the user " + userEntity.getUsername() + " FOREVER?",
                    "/admin/users/" + userId + "?forever=1"
            );
            return "redirect:/admin/users/" + userId;
        }

        if(forever != null && forever == 1) {
            userService.deleteUser(userEntity);
            System.out.println(
                    "[UserController]Admin deleted link "+userEntity.getUsername()
            );
            returnMsg(attr, SUCCESS, "Delete user forever succeed!");
        }

        return "redirect:/admin/users";
    }

    /**
     * Update user msg, including restore deleted user,
     * which is an operation of admin
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin/users/{user_id}", method = RequestMethod.PUT)
    public String userDelete(
            @PathVariable("user_id") Integer userId,
            @RequestParam(value="deleted", required=false) Integer deleted,
            HttpServletRequest request,
            RedirectAttributes attr
    ) {
        UserEntity userEntity = userRepository.findOne(userId);
        if(userEntity == null){
            returnMsg(attr, ERROR, "User not exists!");

            return "redirect:/admin/users";
        }

        if(deleted == null){
            // Normal update user info

            // Get the user info from mask form
            String userLevelStr = request.getParameter("level").trim();
            Integer userLevel = Integer.parseInt((userLevelStr.split("\\("))[0].trim());
            userEntity.setLevelByLevelId(levelRepository.findOne(userLevel));
            userRepository.saveAndFlush(userEntity);
            returnMsg(attr, SUCCESS, "Update succeed!");
            return "redirect:/admin/users";
        }else if(deleted == 1){
            // Restore deleted user
            userEntity.setDeletedAt(null);
            userRepository.saveAndFlush(userEntity);
            returnMsg(attr, SUCCESS, "Restore succeed!");
            return "redirect:/admin/users";
        }else{
            // Wrong parameter banning
            returnMsg(attr, ERROR, "Parameters invalid!");
            return "redirect:/admin/users";
        }
    }


    @RequestMapping(value = "/admin/users/{user_id}", method = RequestMethod.GET)
    public String userGetDetail(@PathVariable("user_id") Integer userId, ModelMap modelMap) {

        // Get all the level entities
        List<LevelEntity> levelEntityList = levelRepository.findAll();
        modelMap.addAttribute("levelEntityList", levelEntityList);

        // Get the specific user entity
        UserEntity userEntity = userRepository.findOne(userId);
        modelMap.addAttribute("userEntity", userEntity);

        // Get all the logs about user
        List<LogEntity> logEntityList = logRepository.findByUserByUserIdOrderByIdDesc(userEntity);
        modelMap.addAttribute("logEntityList", logEntityList);

        // Get HashMap about logs
        String stat = logService.stat(userEntity);
        modelMap.addAttribute("stat", stat);

        // Check if the user has been deleted
        if(userEntity.getDeletedAt() == null){
            modelMap.addAttribute("deleted", false);
        }else {
            modelMap.addAttribute("deleted", true);
        }

        return "dashboard/admin/user_detail";
    }
}
