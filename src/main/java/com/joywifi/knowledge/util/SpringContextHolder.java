package com.joywifi.knowledge.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    public static boolean containsBean(String beanName) {
        return context.containsBean(beanName);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName, Object... args) {
        return (T) context.getBean(beanName, args);
    }

    public static <T> T getBean(Class<T> classType) {
        return context.getBean(classType);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextHolder.context = context;
    }
}
