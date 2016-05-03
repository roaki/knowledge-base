package com.joywifi.knowledge.util;

import javax.servlet.http.HttpServletRequest;

public class AjaxUtils {
    private AjaxUtils() {
    }

    public static boolean isAjaxRequest(HttpServletRequest webRequest) {
        String requestedWith = webRequest.getHeader("X-Requested-With");
        return requestedWith != null && "XMLHttpRequest".equals(requestedWith);
    }
}
