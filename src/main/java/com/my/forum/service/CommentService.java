package com.my.forum.service;

import com.my.forum.exception.ServiceException;
import com.my.forum.model.Comment;
import com.my.forum.repository.CommentRepository;
import com.my.forum.util.LoginUserUtils;
import com.my.forum.util.SnowFlakeUtil;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @author yinzhijun
 * @date 2021/11/9 11:06
 */
@Service
public class CommentService {
    @Resource
    private CommentRepository commentRepository;
    @Resource
    private PostService postService;

    public Comment insert(Comment comment) {
        if (!this.postService.isExist(comment.getPostId())) {
            throw new ServiceException("post不存在");
        }
        comment.setId(SnowFlakeUtil.nextId());
        comment.setUserId(LoginUserUtils.getUser().getId());
        return this.commentRepository.save(comment);
    }

    public Page<Comment> pageByExample(Comment condition, int page, int pageSize) {
        Assert.notNull(condition.getPostId(), "post不能为空");
        var example = Example.of(condition);
        var pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, "id");
        return this.commentRepository.findAll(example, pageable);
    }

    /**
     * 根据文章作者查询回复该作者的评论列表
     *
     * @param postUserId 被回复的用户ID
     * @param page       页码
     * @param pageSize   每页条数
     */
    public Page<Comment> pageByPostUser(Long postUserId, int page, int pageSize) {
        var pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, "createTime");
        return this.commentRepository.listCommentByPostUserId(postUserId, pageable);
    }

    public void update(Comment comment) {
        Assert.notNull(comment.getId(), "comment_id不能为空");
        var beforeComment = this.commentRepository.findById(comment.getId()).orElseThrow(() -> new ServiceException("comment_id不存在"));
        var user = LoginUserUtils.getUser();
        if (!beforeComment.getUserId().equals(user.getId())) {
            throw new ServiceException("您不能修改别人的回复");
        }
        beforeComment.setContent(comment.getContent());
        this.commentRepository.save(beforeComment);
    }

    public void delete(Long id) {
        var beforeComment = this.commentRepository.findById(id).orElseThrow(() -> new ServiceException("comment_id不存在"));
        var user = LoginUserUtils.getUser();
        if (!beforeComment.getUserId().equals(user.getId())) {
            throw new ServiceException("您不能删除别人的回复");
        }
        this.commentRepository.deleteById(id);
    }
}
