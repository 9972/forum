package com.my.forum.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.forum.model.DTO.LoginDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于账号密码登陆的认证过滤器
 *
 * @author yinzhijun
 * @date 2021/11/9 13:56
 */
public class PasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public PasswordAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(new LoginAuthenticationSuccessHandler());
        setAuthenticationFailureHandler(new LoginAuthenticationFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException {
        LoginDTO loginDTO = new ObjectMapper().readValue(httpServletRequest.getInputStream(), LoginDTO.class);
        return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(loginDTO, null));
    }
}
