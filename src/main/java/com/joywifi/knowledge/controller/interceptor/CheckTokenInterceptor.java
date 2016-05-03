package com.joywifi.knowledge.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.joywifi.knowledge.annotation.CheckToken;

public class CheckTokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        CheckToken annotation = handlerMethod.getMethodAnnotation(CheckToken.class);
        if (annotation == null) {
            return true;
        }

        boolean removeToken = annotation.removeToken();
        if (removeToken) {
            if (isRepeatSubmit(request)) {
                request.setAttribute("error", "重复提交");
                return false;
            }
            request.getSession(false).removeAttribute("token");
        }

        return true;
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(false).getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }

}