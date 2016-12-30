package com.jianyujianyu.controller;

import com.jianyujianyu.model.LogEntity;
import com.jianyujianyu.model.UserEntity;
import com.jianyujianyu.repository.LogRepository;
import com.jianyujianyu.repository.UserRepository;
import com.jianyujianyu.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * Created by aimreant on 11/10/16.
 */
@Controller
public class MainController {

    @Autowired UserRepository userRepository;
    @Autowired LogRepository logRepository;
    @Autowired LogService logService;

    /**
     * When go straight to dashboard index
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap modelMap, HttpSession httpSession) {

        UserEntity userEntity = (UserEntity) httpSession.getAttribute("filexplorer_o");
        List<LogEntity> logEntityList = logRepository.findByUserByUserIdOrderByIdDesc(userEntity);
        modelMap.addAttribute("logEntityList", logEntityList);

        // Get String about logs
        String stat = logService.stat(userEntity);
        modelMap.addAttribute("stat", stat);

        return "dashboard/index";
    }


    /**
     * When go straight to dashboard index
     * @return
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminIndex(ModelMap modelMap) {

        // Get logs
        List<LogEntity> logEntityList = logService.getLogs();
        modelMap.addAttribute("logEntityList", logEntityList);

        // Get String about logs
        String stat = logService.statAll();
        modelMap.addAttribute("stat", stat);

        return "dashboard/admin/index";
    }
}
