package com.jianyujianyu.interceptor;

import com.jianyujianyu.model.AdminEntity;
import com.jianyujianyu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Created by aimreant on 11/28/16.
 */
public class AuthAdminInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    UserRepository userRepository;

    // Before the actual handler will be executed
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {

        if(Objects.equals(request.getRequestURI(), "/login")){
            System.out.println("[AuthAdminInterceptor]Enter login page, true forever.");
            return true;
        }

        if(Objects.equals(request.getRequestURI(), "/admin/login")){
            System.out.println("[AuthAdminInterceptor]Enter admin login page, true forever.");
            return true;
        }

        // Get sessions
        Integer filexplorerId = (Integer) request.getSession().getAttribute("filexplorer_s_admin");
        Boolean filexplorerBoolean = (Boolean) request.getSession().getAttribute("filexplorer_b_admin");

        if(filexplorerBoolean == null){
            System.out.println("[AuthAdminInterceptor]Session filexplorer_b not found.");
            response.sendRedirect("/admin/login");
            return false;
        }

        if(!filexplorerBoolean){
            System.out.println("[AuthAdminInterceptor]Session filexplorer_b not found.");
            response.sendRedirect("/admin/login");
            return false;
        }else {
            return true;
        }
    }


    /**
     * After controller operation and before the view render
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

        Integer filexplorerId = (Integer) request.getSession().getAttribute("filexplorer_s_admin");
        Boolean filexplorerBoolean = (Boolean) request.getSession().getAttribute("filexplorer_b_admin");

        // Get user entity in session for displaying on the top bar
        if(filexplorerBoolean != null && filexplorerBoolean){
            AdminEntity adminEntity = (AdminEntity) request.getSession().getAttribute("filexplorer_o_admin");
            if(adminEntity == null){
                modelAndView.addObject("currentUserEntity", null);
            }else {
                modelAndView.addObject("currentUserEntity", adminEntity);
                modelAndView.addObject("isAdmin", true);
            }
        }


    }

}
