package com.jianyujianyu.controller;

import com.jianyujianyu.model.LevelEntity;
import com.jianyujianyu.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by aimreant on 11/29/16.
 */
@Controller
public class LevelController extends BaseController{

    @Autowired
    LevelRepository levelRepository;

    @RequestMapping(value = "/admin/settings/level", method = RequestMethod.GET)
    public String levelGet(ModelMap modelMap){

        // Get all the level entities
        List<LevelEntity> levelEntityList = levelRepository.findAll();
        modelMap.addAttribute("levelEntityList", levelEntityList);

        return "dashboard/admin/level";
    }

    /**
     * Create level entity
     * @return
     */
    @RequestMapping(value = "/admin/settings/level", method = RequestMethod.POST)
    public String levelPost(HttpServletRequest request, RedirectAttributes attr){

        Integer level = Integer.parseInt(request.getParameter("level").trim());
        Long space = Long.parseLong(request.getParameter("space").trim());

        LevelEntity levelEntity = new LevelEntity(level, space);
        levelRepository.saveAndFlush(levelEntity);
        returnMsg(attr, SUCCESS, "Create succeed!");
        return "redirect:/admin/settings/level";
    }

    /**
     * Update level entity
     * @return
     */
    @RequestMapping(value = "/admin/settings/level", method = RequestMethod.PUT)
    public String levelPut(HttpServletRequest request){
        return "dashboard/admin/level";
    }

}
