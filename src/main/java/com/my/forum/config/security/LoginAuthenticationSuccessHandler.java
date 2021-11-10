package com.my.forum.config.security;

import com.my.forum.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理器
 *
 * @author yinzhijun
 * @date 2021/11/9 14:56
 */
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 时间关系这里只采用传统的方案存储登录状态，实际可使用JWT等方式
        var user = (User) authentication.getPrincipal();
        var session = request.getSession(true);
        session.setAttribute("isLogin", true);
        session.setAttribute("userId", user);
    }
}
