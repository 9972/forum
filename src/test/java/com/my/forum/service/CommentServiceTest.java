package com.my.forum.service;

import com.my.forum.config.security.AccessAuthenticationToken;
import com.my.forum.model.Comment;
import com.my.forum.model.Post;
import com.my.forum.model.User;
import com.my.forum.util.SnowFlakeUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
 * @date 2021/11/9 11:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {
    @Resource
    private CommentService commentService;
    @Resource
    private PostService postService;

    @Before
    public void setUp() throws Exception {
        var user = new User();
        user.setId(SnowFlakeUtil.nextId());
        user.setUsername("test");
        var authentication = new AccessAuthenticationToken(user, Collections.singleton(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Transactional
    @Test
    public void insert() {
        var post = new Post();
        post.setUserId(1L);
        post.setTitle("title");
        post.setContent("content");
        post = this.postService.insert(post);

        var comment = new Comment();
        comment.setContent("content");
        comment.setPostId(post.getId());
        comment.setUserId(2L);
        var result = this.commentService.insert(comment);
        Assert.assertNotNull(comment);
    }

    @Test
    public void pageByExample() {
        var condition = new Comment();
        condition.setPostId(1L);
        var comments = this.commentService.pageByExample(condition, 1, 10);
        Assert.assertNotNull(comments);
    }

    @Test
    public void pageByPostUser() {
        var commentPage = this.commentService.pageByPostUser(1L, 1, 10);
        Assert.assertNotNull(commentPage);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}