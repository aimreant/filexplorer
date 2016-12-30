package com.jianyujianyu.interceptor;

import com.jianyujianyu.model.UserEntity;
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
public class AuthInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    UserRepository userRepository;

    // Before the actual handler will be executed
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {

        if(Objects.equals(request.getRequestURI(), "/login")){
            System.out.println("[AuthInterceptor]Enter login page, true forever.");
            return true;
        }

        if(Objects.equals(request.getRequestURI(), "/admin/login")){
            System.out.println("[AuthInterceptor]Enter admin login page, true forever.");
            return true;
        }

        // Get sessions
        Integer filexplorerId = (Integer) request.getSession().getAttribute("filexplorer_s");
        Boolean filexplorerBoolean = (Boolean) request.getSession().getAttribute("filexplorer_b");

        // If sessions haven't been set
        if(filexplorerBoolean == null){
            System.out.println("[AuthInterceptor]Session filexplorer_b not found.");
            response.sendRedirect("/login");
            return false;
        }

        // If sessions haven't been removed
        if(!filexplorerBoolean){
            System.out.println("[AuthInterceptor]Session filexplorer_b not found.");
            response.sendRedirect("/login");
            return false;
        }else {

            // Simply check the user id in session
            UserEntity userEntity = userRepository.findOneByIdAndDeletedAtIsNull(filexplorerId);
            if (userEntity == null) {
                System.out.println("[AuthInterceptor]User entity not found.");
                response.sendRedirect("/login");
                return false;
            } else {
                return true;
            }
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

        Integer filexplorerId = (Integer) request.getSession().getAttribute("filexplorer_s");
        Boolean filexplorerBoolean = (Boolean) request.getSession().getAttribute("filexplorer_b");

        // Get user entity in session for displaying on the top bar
        if(filexplorerBoolean != null && filexplorerBoolean){
            UserEntity userEntity = (UserEntity) request.getSession().getAttribute("filexplorer_o");
            // System.out.println("[AuthInterceptor]User object: " + userEntity.getUsername());
            if(modelAndView != null) {
                modelAndView.addObject("currentUserEntity", userEntity);
            }
        }


    }

}
