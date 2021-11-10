package com.my.forum.util;

import com.my.forum.exception.ServiceException;
import com.my.forum.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author yinzhijun
 * @date 2021/11/9 19:07
 */
public class LoginUserUtils {
    public static User getUser() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) {
            throw new ServiceException("无法获取登录信息");
        }
        return (User) principal;
    }
}
