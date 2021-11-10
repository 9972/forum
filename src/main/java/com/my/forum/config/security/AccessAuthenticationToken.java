package com.my.forum.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author yinzhijun
 * @date 2021/11/9 16:10
 */
public class AccessAuthenticationToken extends AbstractAuthenticationToken {
    private Object principal;
    private Object credentials;

    public AccessAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = null;
    }

    public AccessAuthenticationToken(Object credentials) {
        super(null);
        this.principal = null;
        this.credentials = credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }
}
