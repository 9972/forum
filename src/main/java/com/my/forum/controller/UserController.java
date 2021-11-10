package com.my.forum.controller;

import com.my.forum.constant.ValidGroupInsert;
import com.my.forum.model.Comment;
import com.my.forum.model.DTO.LoginDTO;
import com.my.forum.model.DTO.RegisterDTO;
import com.my.forum.model.User;
import com.my.forum.service.CommentService;
import com.my.forum.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户控制器
 *
 * @author yinzhijun
 * @date 2021/11/9 13:55
 */
@Tag(name = "用户相关")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private CommentService commentService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public User register(@Validated(ValidGroupInsert.class) @RequestBody RegisterDTO registerDTO) {
        return this.userService.register(registerDTO);
    }

    @Operation(summary = "用户登录")
    @PutMapping("/login")
    public void login(@RequestBody LoginDTO loginDTO) {
        // 这里只为暴露swagger接口，无实际作用
    }

    @Operation(summary = "查询回复该作者的评论列表")
    @GetMapping("/comment")
    public Page<Comment> getCommentByPostUser(Long userId,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "10") int pageSize) {
        // TODO: 2021/11/9 考虑到未读提醒功能可能涉及多种纬度的数据源，建议使用事件触发的方式实现，提高性能，降低耦合度
        return this.commentService.pageByPostUser(userId, page, pageSize);
    }
}
