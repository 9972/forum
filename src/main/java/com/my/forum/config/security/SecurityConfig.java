package com.my.forum.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证授权配置类
 *
 * @author yinzhijun
 * @date 2021/11/9 12:47
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.GET, "/post/**")
                .antMatchers(HttpMethod.GET, "/comment/**")
                .antMatchers("/user/register/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/v3/api-docs/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new PasswordAuthenticationFilter("/user/login", authenticationManager()), BasicAuthenticationFilter.class)
                .addFilterAfter(new AccessAuthenticationFilter(), BasicAuthenticationFilter.class);
    }

    @Override
    protected AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providerList = new ArrayList<>();
        providerList.add(passwordAuthenticationProvider());
        providerList.add(accessAuthenticationProvider());
        return new ProviderManager(providerList);
    }

    @Bean("passwordAuthenticationProviderBean")
    PasswordAuthenticationProvider passwordAuthenticationProvider() {
        return new PasswordAuthenticationProvider();
    }

    @Bean("accessAuthenticationProviderBean")
    AccessAuthenticationProvider accessAuthenticationProvider() {
        return new AccessAuthenticationProvider();
    }
}
