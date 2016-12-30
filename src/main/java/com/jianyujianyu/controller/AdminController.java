package com.jianyujianyu.controller;

import com.jianyujianyu.model.AdminEntity;
import com.jianyujianyu.model.FileEntity;
import com.jianyujianyu.model.LinkEntity;
import com.jianyujianyu.repository.AdminRepository;
import com.jianyujianyu.repository.FileRepository;
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
import java.util.List;
import java.util.Objects;

/**
 * Created by aimreant on 11/10/16.
 */
@Controller
public class AdminController extends BaseController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    FileRepository fileRepository;

    @RequestMapping(value = "/admin/files", method = RequestMethod.GET)
    public String file(ModelMap modelMap) {

        List<FileEntity> fileEntityList = fileRepository.findAll();
        modelMap.addAttribute("fileEntityList", fileEntityList);
        return "dashboard/admin/file";

    }

    @RequestMapping(value = "/admin/settings", method = RequestMethod.GET)
    public String setting() {

        return "dashboard/admin/index";
    }

    @RequestMapping(value = "/admin/settings/admins", method = RequestMethod.GET)
    public String adminGet(
            @RequestParam(value="deleted", required=false) Integer deleted,
            ModelMap modelMap
    ) {

        // Get deleted parameter
        if(deleted != null && deleted == 1) {
            // Get all the deleted user entities
            List<AdminEntity> adminEntityList = adminRepository.findByDeletedAtIsNotNull();
            modelMap.addAttribute("deleted", true);
            modelMap.addAttribute("adminEntityList", adminEntityList);
        }else {
            // Get all the user entities
            List<AdminEntity> adminEntityList = adminRepository.findByDeletedAtIsNull();
            modelMap.addAttribute("deleted", false);
            modelMap.addAttribute("adminEntityList", adminEntityList);
        }

        return "dashboard/admin/admin";
    }

    /**
     * Create a new admin
     * @param request
     * @param attr
     * @return
     */
    @RequestMapping(value = "/admin/settings/admins", method = RequestMethod.POST)
    public String adminPost(HttpServletRequest request, RedirectAttributes attr) {

        // Get the new admin info from mask form
        String userUsername = request.getParameter("username").trim();
        String userPassword = request.getParameter("password");

        AdminEntity adminEntity = adminRepository.findByUsername(userUsername);
        if(adminEntity != null){
            returnMsg(attr, ERROR, "Username exists!");
            return "redirect:/admin/settings/admins";
        }

        try {
            userPassword = getHashCode(userPassword);
        }catch (Exception e) {
            e.printStackTrace();
        }

        adminEntity = new AdminEntity(
                userUsername, userPassword
        );
        adminRepository.saveAndFlush(adminEntity);
        returnMsg(attr, SUCCESS, "Creating admin succeed!");
        return "redirect:/admin/settings/admins";
    }

    /**
     * Delete a admin
     * @param attr
     * @return
     */
    @RequestMapping(value = "/admin/settings/admins/{admin_id}", method = RequestMethod.DELETE)
    public String adminPost(
            HttpServletRequest request,
            @PathVariable("admin_id") Integer adminId,
            @RequestParam(value="pre", required=false) Integer pre,
            @RequestParam(value="forever", required=false) Integer forever,
            RedirectAttributes attr) {

        // If he delete himself
        Integer filexplorerId = (Integer) request.getSession().getAttribute("filexplorer_s_admin");
        if(Objects.equals(filexplorerId, adminId)){
            returnMsg(attr, ERROR, "You couldn't delete yourself!");
            return "redirect:/admin/settings/admins";
        }

        // Check the admin
        AdminEntity adminEntity = adminRepository.findOne(adminId);
        if(adminEntity == null){
            returnMsg(attr, ERROR, "Admin not exists or has been deleted!");
            return "redirect:/admin/settings/admins";
        }

        if(pre == null || pre != 1) {
            adminEntity.setDeletedAt(new Timestamp(System.currentTimeMillis()));
            adminRepository.saveAndFlush(adminEntity);
            returnMsg(attr, SUCCESS, "Delete succeed!");
        }else{
            returnConfirmMsg(
                    attr,
                    "Really delete the admin " + adminEntity.getUsername() + " FOREVER?",
                    "/admin/settings/admins/" + adminId + "?forever=1"
            );
            return "redirect:/admin/settings/admins";
        }

        if(forever != null && forever == 1) {
            adminRepository.delete(adminId);
            returnMsg(attr, SUCCESS, "Delete forever succeed!");
        }

        return "redirect:/admin/settings/admins";
    }

    /**
     * Delete a admin
     * @param attr
     * @return
     */
    @RequestMapping(value = "/admin/settings/admins/{admin_id}", method = RequestMethod.PUT)
    public String adminPost(
            @PathVariable("admin_id") Integer adminId,
            @RequestParam(value="deleted", required=false) Integer deleted,
            RedirectAttributes attr
    ) {

        AdminEntity adminEntity = adminRepository.findOne(adminId);
        if(adminEntity == null){
            returnMsg(attr, ERROR, "Admin not exists!");
            return "redirect:/admin/settings/admins";
        }

        if(deleted != null && deleted == 1) {
            // Restore deleted admin
            adminEntity.setDeletedAt(null);
            adminRepository.saveAndFlush(adminEntity);
            returnMsg(attr, SUCCESS, "Restore admin succeed!");
            return "redirect:/admin/settings/admins";
        }else{
            // Wrong parameter banning
            returnMsg(attr, ERROR, "Parameters invalid!");
            return "redirect:/admin/settings/admins";
        }
    }
}
