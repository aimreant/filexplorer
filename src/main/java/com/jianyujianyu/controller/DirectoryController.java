package com.jianyujianyu.controller;

import com.jianyujianyu.model.DirectoryEntity;
import com.jianyujianyu.model.UserEntity;
import com.jianyujianyu.repository.DirectoryRepository;
import com.jianyujianyu.service.DirectoryService;
import com.jianyujianyu.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * Created by aimreant on 12/11/16.
 */
@Controller
public class DirectoryController extends BaseController{

    @Autowired DirectoryService directoryService;
    @Autowired DirectoryRepository directoryRepository;
    @Autowired LogService logService;

    @RequestMapping(value = "/directory", method = RequestMethod.POST)
    public String directoryPost(
            HttpServletRequest request,
            HttpSession httpSession,
            RedirectAttributes attr,
            @RequestParam(value="dir", required=false) Integer dir
    ) {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("filexplorer_o");
        String name = request.getParameter("name").trim();
        DirectoryEntity directoryEntity =
                directoryService.createDirectory(dir, userEntity, name);

        if(directoryEntity == null){
            returnMsg(attr, ERROR, "Database error!");
        }else{
            returnMsg(attr, SUCCESS, "New directory successfully!");
        }

        return "redirect:/files?dir=" + dir;
    }

    @RequestMapping(value = "/directory/{dir_id}", method = RequestMethod.PUT)
    public String directoryRename(
            HttpServletRequest request,
            HttpSession httpSession,
            RedirectAttributes attr,
            @PathVariable("dir_id") Integer dirId,
            @RequestParam(value="dir", required=false) Integer dir,
            @RequestParam(value="pre", required=false) Integer pre,
            @RequestParam(value="deleted", required=false) Integer deleted,
            @RequestParam(value="forever", required=false) Integer forever
    ) {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("filexplorer_o");

        if(dir == null){
            dir = directoryRepository.findOneByNameAndUserByUserId("/", userEntity).getId();
        }

        DirectoryEntity directoryEntity =
                directoryRepository.findOneByIdAndUserByUserId(dirId, userEntity);

        if(directoryEntity == null) {
            // The directory not found
            returnMsg(attr, ERROR, "Invalid directory!");
            return "redirect:/files?dir=" + dir;
        }

        if(pre != null && pre == 1) {
            // Pre-rename
            returnRenameMsg(
                    attr,
                    "Ready to rename directory " + directoryEntity.getName(),
                    "/directory/" + dirId + "?forever=1&dir=" + dir
            );
            return "redirect:/files?dir=" + dir;
        }

        if(forever != null && forever == 1) {
            String originName = directoryEntity.getName();
            String name = request.getParameter("newname").trim();
            directoryEntity.setName(name);
            directoryRepository.saveAndFlush(directoryEntity);
            logService.createLog("Rename Directory " + originName + " to " +directoryEntity.getName(), null, userEntity);
            returnMsg(attr, SUCCESS, "Rename directory successfully!");
            return "redirect:/files?dir=" + dir;
        }

        if(deleted != null && deleted == 1) {
            directoryEntity.setDeletedAt(null);
            directoryRepository.saveAndFlush(directoryEntity);
            returnMsg(attr, SUCCESS, "Restore directory successfully!");
            return "redirect:/files?deleted=1&dir=" + dir;
        }

        return "redirect:/files?dir=" + dir;
    }

    @RequestMapping(value = "/directory/{dir_id}", method = RequestMethod.DELETE)
    public String directoryDelete(
            HttpSession httpSession,
            RedirectAttributes attr,
            @PathVariable("dir_id") Integer dirId,
            @RequestParam(value="forever", required=false) Integer forever,
            @RequestParam(value = "dir", required = false) Integer dir
    ) {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("filexplorer_o");

        if(dir == null){
            dir = directoryRepository.findOneByNameAndUserByUserId("/", userEntity).getId();
        }

        DirectoryEntity directoryEntity =
                directoryRepository.findOneByIdAndUserByUserId(dirId, userEntity);
        if(directoryEntity == null) {
            // The directory not found
            returnMsg(attr, ERROR, "Invalid directory!");
            return "redirect:/files?dir=" + dir;
        }

        if(forever != null && forever == 1) {
            directoryService.deleteDirectory(directoryEntity, userEntity);
            returnMsg(attr, SUCCESS, "Delete directory " + directoryEntity.getName() + " forever succeed!");
            System.out.println(
                    "[DirectoryController]User "+userEntity.getUsername()+" deleted link "+directoryEntity.getName()
            );
            return "redirect:/files?deleted=1&dir=" + dir;
        }

        directoryEntity.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        directoryRepository.saveAndFlush(directoryEntity);
        returnMsg(attr, SUCCESS, "Delete succeed!");
        return "redirect:/files?dir=" + dir;
    }

}
