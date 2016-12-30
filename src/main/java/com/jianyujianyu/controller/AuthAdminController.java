package com.jianyujianyu.controller;

import com.jianyujianyu.model.AdminEntity;
import com.jianyujianyu.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * Created by aimreant on 11/10/16.
 */
@Controller
public class AuthAdminController extends BaseController {

    @Autowired
    AdminRepository adminRepository;

    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String getAdminLogin() {
        return "dashboard/admin/login";
    }

    /**
     * Post to login, set sessions when login succeed
     * Using default username and password at first time
     * @param request
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public String postAdminLogin(
            HttpServletRequest request,
            HttpSession httpSession,
            RedirectAttributes attr
    ) {

        // Set a key for checking if it can pass
        Boolean passKey = false;

        String username = request.getParameter("username").trim();
        String password = request.getParameter("password");

        List<AdminEntity> adminEntityList = adminRepository.findAll();

        if(adminEntityList.isEmpty()) {
            // When there is no admin in database, use default username
            if (Objects.equals(username, "admin") && Objects.equals(password, "admin")) {
                System.out.println("[AuthAdminController]No admin in database, use default account(admin)");
                // Set sessions to login
                httpSession.setAttribute("filexplorer_s_admin", -1);
                httpSession.setAttribute("filexplorer_b_admin", true);
                httpSession.setAttribute("filexplorer_o_admin", null);
                passKey = true;
            }
        }else{
            // Try to get hash code
            try {
                password = getHashCode(password);
            }catch (Exception e) {
                e.printStackTrace();
            }

            AdminEntity adminEntity = adminRepository.getByUsernameAndPassword(
                    username, password
            );

            if(adminEntity == null){
                attr.addFlashAttribute("loginFail", true);
                passKey = false;
            }else{
                // Set sessions to login
                httpSession.setAttribute("filexplorer_s_admin", adminEntity.getId());
                httpSession.setAttribute("filexplorer_b_admin", true);
                httpSession.setAttribute("filexplorer_o_admin", adminEntity);
                passKey = true;
            }
        }

        if(passKey) {
            return "redirect:/admin";
        }else{
            return "redirect:/admin/login";
        }
    }

}
