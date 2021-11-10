package com.my.forum.service;

import com.my.forum.config.security.AccessAuthenticationToken;
import com.my.forum.model.Post;
import com.my.forum.model.User;
import com.my.forum.util.SnowFlakeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author yinzhijun
 * @date 2021/11/9 09:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {
    @Resource
    private PostService postService;

    @org.junit.Before
    public void setUp() throws Exception {
        var user = new User();
        user.setId(SnowFlakeUtil.nextId());
        user.setUsername("test");
        var authentication = new AccessAuthenticationToken(user, Collections.singleton(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @Transactional
    @org.junit.Test
    public void insert() {
        var post = new Post();
        post.setId(SnowFlakeUtil.nextId());
        post.setUserId(1L);
        post.setTitle("标题");
        post.setContent("内容");
        this.postService.insert(post);
    }

    @Transactional
    @Test
    public void update() {
        var post = new Post();
        post.setId(18196560085024L);
        post.setUserId(1L);
        post.setTitle("after title");
        post.setContent("content after");
        this.postService.update(post);
    }

    @Test
    public void pageByExample() {
        var condition = new Post();
        var posts = this.postService.pageByExample(condition, 1, 10);
    }

    @Transactional
    @Test
    public void delete() {
    }
}