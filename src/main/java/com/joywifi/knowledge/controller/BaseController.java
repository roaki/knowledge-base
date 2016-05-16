package com.joywifi.knowledge.controller;

import com.joywifi.knowledge.entity.BaseEntity;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.utils.Reflections;

public class BaseController<M extends BaseEntity> {

    protected final Class<M> entityClass;
    private String viewPrefix;
    protected String flashCreateMessage;
    protected String flashUpdateMessage;
    protected String flashDeleteMessage;

    public BaseController() {
        this.entityClass = Reflections.getClassGenricType(getClass());
        setViewPrefix(defaultViewPrefix());
    }

    public String getViewPrefix() {
        return viewPrefix;
    }

    /**
     * 当前模块 视图的前缀
     * 默认
     * 1、获取当前类头上的@RequestMapping中的value作为前缀
     * 2、如果没有就使用当前模型小写的简单类名
     */
    public void setViewPrefix(String viewPrefix) {
        if (viewPrefix.startsWith("/")) {
            viewPrefix = viewPrefix.substring(1);
        }
        this.viewPrefix = viewPrefix;
    }

    protected M newModel() {
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("can not instantiated model : " + this.entityClass, e);
        }
    }

    /**
     * 获取视图名称：即prefixViewName + "/" + suffixName
     *
     * @return
     */
    public String viewName(String suffixName) {
        if (!suffixName.startsWith("/")) {
            suffixName = "/" + suffixName;
        }
        return getViewPrefix() + suffixName;
    }

    /**
     * 共享的验证规则
     * 验证失败返回true
     *
     * @param m
     * @param result
     * @return
     */
    protected boolean hasError(M m, BindingResult result) {
        Assert.notNull(m);
        return result.hasErrors();
    }

    /**
     * @param backURL null 将重定向到默认getViewPrefix()
     * @return
     */
    protected String redirectToUrl(String backURL) {
        if (StringUtils.isEmpty(backURL)) {
            backURL = getViewPrefix();
        }
        if (!backURL.startsWith("/") && !backURL.startsWith("http")) {
            backURL = "/" + backURL;
        }
        return "redirect:" + backURL;
    }

    protected String defaultViewPrefix() {
        String currentViewPrefix = "";
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(getClass(), RequestMapping.class);
        if (requestMapping != null && requestMapping.value().length > 0) {
            currentViewPrefix = requestMapping.value()[0];
        }

        if (StringUtils.isEmpty(currentViewPrefix)) {
            currentViewPrefix = this.entityClass.getSimpleName();
        }

        return currentViewPrefix;
    }

    protected String entityName() {
        return StringUtils.uncapitalize(entityClass.getSimpleName());
    }

    protected void beforeList(Model model) {
    }

    protected void beforeNew(Model model) {
    }

    protected void beforeEdit(Model model) {
    }

    protected void beforeShow(Model model) {
    }

    protected void setFlashCreateMessage(String flashCreateMessage) {
        this.flashCreateMessage = flashCreateMessage;
    }

    protected void setFlashUpdateMessage(String flashUpdateMessage) {
        this.flashUpdateMessage = flashUpdateMessage;
    }

    protected void setFlashDeleteMessage(String flashDeleteMessage) {
        this.flashDeleteMessage = flashDeleteMessage;
    }
}
