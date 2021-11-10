package com.my.forum.controller;

import com.my.forum.constant.ValidGroupInsert;
import com.my.forum.constant.ValidGroupUpdate;
import com.my.forum.model.Comment;
import com.my.forum.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 回复控制器
 *
 * @author yinzhijun
 * @date 2021/11/9 12:20
 */
@Tag(name = "回复相关")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    /**
     * 新增回复
     *
     * @param comment 回复实体
     */
    @Operation(summary = "新增回复")
    @PostMapping
    public void insert(@RequestBody @Validated(ValidGroupInsert.class) Comment comment) {
        this.commentService.insert(comment);
    }

    /**
     * 查询回复
     *
     * @param userId   用户ID
     * @param postId   文章ID
     * @param page     页码
     * @param pageSize 每页条数
     */
    @Operation(summary = "查询回复")
    @GetMapping
    public Page<Comment> page(@RequestParam(required = false) Long userId,
                              @RequestParam(required = false) Long postId,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int pageSize) {
        var condition = new Comment();
        condition.setUserId(userId);
        condition.setPostId(postId);
        return this.commentService.pageByExample(condition, page, pageSize);
    }

    /**
     * 修改回复
     *
     * @param comment 修改后回复实体
     */
    @Operation(summary = "修改回复")
    @PutMapping
    public void update(@Validated(ValidGroupUpdate.class) @RequestBody Comment comment) {
        this.commentService.update(comment);
    }

    /**
     * 删除回复
     *
     * @param id 回复ID
     */
    @Operation(summary = "删除回复")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.commentService.delete(id);
    }
}
