package com.my.forum.repository;

import com.my.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yinzhijun
 * @date 2021/11/9 09:32
 */
public interface UserRepository extends JpaRepository<User, Long> {
}