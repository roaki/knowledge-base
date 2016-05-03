package com.joywifi.knowledge.controller.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UriTemplate;

import com.joywifi.knowledge.annotation.CurrentUserQuery;
import com.joywifi.knowledge.entity.BaseEntity;
import com.joywifi.knowledge.exception.CustomException;
import com.joywifi.knowledge.plugin.web.CustomerRequestWrapper;
import com.joywifi.knowledge.util.ShiroUtils;

public class CurrentUserQueryInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(request instanceof CustomerRequestWrapper && handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        CurrentUserQuery annotation = handlerMethod.getMethodAnnotation(CurrentUserQuery.class);
        if (annotation == null) {
            return true;
        }

        if (annotation.type() == CurrentUserQuery.QueryEnum.LIST) {
            CustomerRequestWrapper req = (CustomerRequestWrapper) request;
            req.addParameter("criteria_EQ_creator.username", ShiroUtils.getCurrentUser().getUsername());
        } else {
            String pk = annotation.pkField();
            String reqId = request.getParameter(pk);
            if (StringUtils.isBlank(reqId)) {
                reqId = resolveReqId(handlerMethod, request);
            }

            if (StringUtils.isBlank(reqId)) {
                throw new CustomException("请检查id参数");
            }

            Class<?> clazz = annotation.clazz();
            BaseEntity entity = (BaseEntity) mongoTemplate.findById(reqId, clazz);
            if (entity != null && !entity.getCreator().getUsername().equals(ShiroUtils.getCurrentUser().getUsername())) {
                throw new UnauthorizedException();
            }

        }
        return true;
    }

    private String resolveReqId(HandlerMethod handlerMethod, HttpServletRequest request) {
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        for (MethodParameter methodParameter : methodParameters) {
            PathVariable pathVariable = methodParameter.getParameterAnnotation(PathVariable.class);
            if (pathVariable != null) {
                String reqIdField = pathVariable.value();
                RequestMapping requestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
                String[] methodRequestMapping = requestMapping.value();
                UriTemplate uriTemplate = new UriTemplate(methodRequestMapping[0]);
                Map<String, String> templateMap = uriTemplate.match(request.getRequestURI());
                return templateMap.get(reqIdField);
            }
        }
        return "";
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        if (!(request instanceof CustomerRequestWrapper && handler instanceof HandlerMethod)) {
            return;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        CurrentUserQuery annotation = handlerMethod.getMethodAnnotation(CurrentUserQuery.class);
        if (annotation != null && annotation.type() == CurrentUserQuery.QueryEnum.LIST) {
            CustomerRequestWrapper req = (CustomerRequestWrapper) request;
            req.removeParameter("criteria_EQ_creator.username");
        }
    }
}
