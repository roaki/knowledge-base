package com.joywifi.knowledge.plugin.web;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class RequestWrapperFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(new CustomerRequestWrapper((HttpServletRequest) req), resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
