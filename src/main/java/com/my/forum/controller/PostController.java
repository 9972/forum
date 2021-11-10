package com.my.forum.controller;

import com.my.forum.constant.ValidGroupInsert;
import com.my.forum.constant.ValidGroupUpdate;
import com.my.forum.model.Post;
import com.my.forum.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 文章控制器
 *
 * @author yinzhijun
 * @date 2021/11/9 11:35
 */
@Tag(name = "文章相关")
@RestController
@RequestMapping("/post")
public class PostController {
    @Resource
    private PostService postService;

    /**
     * 发布文章
     *
     * @param post 文章实体
     */
    @Operation(summary = "发布文章")
    @PostMapping
    public void insert(@Validated(ValidGroupInsert.class) @RequestBody Post post) {
        this.postService.insert(post);
    }

    /**
     * 文章通用分页查询方法
     * 目前只提供用户ID查询，后续可以扩展参数
     *
     * @param userId   用户ID
     * @param page     页码
     * @param pageSize 每页数量
     */
    @Operation(summary = "文章通用分页查询方法")
    @GetMapping
    public Page<Post> listPost(@RequestParam(required = false) Long userId,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int pageSize) {
        var condition = new Post();
        condition.setUserId(userId);
        return this.postService.pageByExample(condition, page, pageSize);
    }

    /**
     * 查询某文章的详情
     *
     * @param id 文章ID
     */
    @Operation(summary = "查询某文章的详情")
    @GetMapping("/{id}")
    public Post getPostDetail(@PathVariable Long id) {
        return this.postService.getById(id);
    }

    /**
     * 修改文章
     *
     * @param post 修改后实体
     */
    @Operation(summary = "修改文章")
    @PutMapping
    public void update(@Validated(ValidGroupUpdate.class) @RequestBody Post post) {
        this.postService.update(post);
    }

    /**
     * 删除文章
     *
     * @param id 文章ID
     */
    @Operation(summary = "删除文章")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.postService.delete(id);
    }
}
