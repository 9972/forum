package com.my.forum.service;

import com.my.forum.exception.ServiceException;
import com.my.forum.model.DTO.RegisterDTO;
import com.my.forum.model.User;
import com.my.forum.repository.UserRepository;
import com.my.forum.util.SnowFlakeUtil;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务类
 *
 * @author yinzhijun
 * @date 2021/11/9 09:33
 */
@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        var example = new User();
        example.setUsername(s);
        return this.userRepository.findOne(Example.of(example)).orElseThrow(() -> new UsernameNotFoundException("用户名不存在"));
    }

    /**
     * 注册用户
     */
    public User register(RegisterDTO registerDTO) {
        var condition = new User();
        condition.setUsername(registerDTO.getUsername());
        var example = Example.of(condition);
        if (this.userRepository.exists(example)) {
            throw new ServiceException("用户名已存在");
        }

        var passwordEncoder = new BCryptPasswordEncoder();
        var user = new User();
        user.setId(SnowFlakeUtil.nextId());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        return this.userRepository.save(user);
    }
}
