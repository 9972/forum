package com.my.forum.config.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 认证过滤器
 *
 * @author yinzhijun
 * @date 2021/11/9 16:09
 */
public class AccessAuthenticationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext.getAuthentication() == null || !securityContext.getAuthentication().isAuthenticated()) {
            var httpServletRequest = (HttpServletRequest) request;
            var session = httpServletRequest.getSession();
            if (session != null) {
                if (Boolean.TRUE.equals(session.getAttribute("isLogin"))) {
                    securityContext.setAuthentication(new AccessAuthenticationToken(session.getAttribute("userId")));
                }
            }
        }
        chain.doFilter(request, response);
    }
}
