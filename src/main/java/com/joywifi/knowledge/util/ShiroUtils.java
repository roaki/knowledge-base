package com.joywifi.knowledge.util;

import org.apache.shiro.SecurityUtils;

import com.joywifi.knowledge.security.ShiroDbRealm;

public class ShiroUtils {
    public static ShiroDbRealm.ShiroUser getCurrentUser(){
        return (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }
}
