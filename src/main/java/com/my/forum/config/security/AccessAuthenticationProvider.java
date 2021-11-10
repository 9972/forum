package com.my.forum.config.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 登陆验证
 *
 * @author yinzhijun
 * @date 2021/11/9 16:09
 */
@Component
public class AccessAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String username = authentication.getPrincipal().toString();
            return new AccessAuthenticationToken(username, null);
        } catch (Exception e) {
            throw new AuthenticationServiceException("认证失败");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (AccessAuthenticationToken.class.isAssignableFrom(authentication));
    }
}