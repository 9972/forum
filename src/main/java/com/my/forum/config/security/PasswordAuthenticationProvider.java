package com.my.forum.config.security;

import com.my.forum.model.DTO.LoginDTO;
import com.my.forum.model.User;
import com.my.forum.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 登陆验证（使用账号密码）
 *
 * @author yinzhijun
 * @date 2021/11/9 14:01
 */
@Component
public class PasswordAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginDTO loginForm = (LoginDTO) authentication.getPrincipal();
        String username = loginForm.getUsername().trim();
        String password = loginForm.getPassword();

        // 获取用户信息
        User userInfo = this.userService.loadUserByUsername(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 认证逻辑
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(password, userInfo.getPassword())) {
            // 设置权限和角色
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("USER"));
            return new UsernamePasswordAuthenticationToken(userInfo, null, authorities);
        } else {
            throw new BadCredentialsException("身份认证失败");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}