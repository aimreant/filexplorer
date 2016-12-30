package com.jianyujianyu.controller;

import com.jianyujianyu.model.UserEntity;
import com.jianyujianyu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by aimreant on 11/10/16.
 */
@Controller
public class AuthController extends BaseController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin() {
        return "dashboard/login";
    }

    /**
     * Post to login, set sessions when login succeed
     * @param request
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin(
            HttpServletRequest request,
            HttpSession httpSession,
            RedirectAttributes attr
    ) {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password");

        try {
            password = getHashCode(password);
        }catch (Exception e) {
            e.printStackTrace();
        }

        UserEntity userEntity = userRepository.getByUsernameAndPassword(
                username, password
        );

        if(userEntity == null){
            attr.addFlashAttribute("loginFail", true);
            return "redirect:/login";
        }else{
            // Set sessions to login
            httpSession.setAttribute("filexplorer_s", userEntity.getId());
            httpSession.setAttribute("filexplorer_b", true);
            httpSession.setAttribute("filexplorer_o", userEntity);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpSession httpSession, RedirectAttributes attr){
        // Set sessions to logout
        httpSession.setAttribute("filexplorer_s", 0);
        httpSession.setAttribute("filexplorer_b", false);
        httpSession.setAttribute("filexplorer_o", null);

        // Set sessions for admin to logout
        httpSession.setAttribute("filexplorer_s_admin", 0);
        httpSession.setAttribute("filexplorer_b_admin", false);
        httpSession.setAttribute("filexplorer_o_admin", null);

        attr.addFlashAttribute("logoutSuccess", true);

        return "redirect:/login";
    }
}
