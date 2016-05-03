package com.joywifi.knowledge.plugin.web;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.google.common.collect.Maps;

public class CustomerRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> wrapperParams = Maps.newHashMap();

    public CustomerRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public Enumeration getParameterNames() {
        Set<String> paramNames = new LinkedHashSet<>();
        Enumeration<String> paramEnum = super.getParameterNames();
        while (paramEnum.hasMoreElements()) {
            paramNames.add(paramEnum.nextElement());
        }
        paramNames.addAll(wrapperParams.keySet());
        return Collections.enumeration(paramNames);
    }

    @Override
    public String getParameter(String name) {
        String[] values = wrapperParams.get(name);
        if (values != null) {
            return values[0];
        }
        return super.getParameter(name);
    }

    public String[] getParameterValues(String name) {
        String[] values = wrapperParams.get(name);
        if (values != null) {
            return values;
        }
        return super.getParameterValues(name);
    }

    public void addParameter(String name, Object value) {
        if (value != null) {
            if (value instanceof String[]) {
                wrapperParams.put(name, (String[]) value);
            } else if (value instanceof String) {
                wrapperParams.put(name, new String[]{(String) value});
            } else {
                wrapperParams.put(name, new String[]{String.valueOf(value)});
            }
        }
    }

    public void removeParameter(String name) {
        wrapperParams.remove(name);
    }
}
