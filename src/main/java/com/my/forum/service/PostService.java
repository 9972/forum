package com.my.forum.service;

import com.my.forum.exception.ServiceException;
import com.my.forum.model.Post;
import com.my.forum.repository.PostRepository;
import com.my.forum.util.LoginUserUtils;
import com.my.forum.util.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yinzhijun
 * @date 2021/11/9 09:49
 */
@Slf4j
@Service
public class PostService {
    @Resource
    private PostRepository postRepository;

    public Post getById(Long id) {
        return this.postRepository.getById(id);
    }

    public boolean isExist(Long id) {
        return this.postRepository.existsById(id);
    }

    public Page<Post> pageByExample(Post condition, int page, int pageSize) {
        var example = Example.of(condition);
        var pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, "id");
        return this.postRepository.findAll(example, pageable);
    }

    public Post insert(Post post) {
        post.setId(SnowFlakeUtil.nextId());
        post.setUserId(LoginUserUtils.getUser().getId());
        return this.postRepository.save(post);
    }

    public void update(@NonNull Post post) {
        var beforePost = this.postRepository.findById(post.getId()).orElseThrow();
        var user = LoginUserUtils.getUser();
        if (!beforePost.getUserId().equals(user.getId())) {
            throw new ServiceException("您不能修改别人的文章");
        }
        beforePost.setTitle(post.getTitle());
        beforePost.setContent(post.getContent());
        this.postRepository.save(beforePost);
    }

    public void delete(Long id) {
        var beforePost = this.postRepository.findById(id).orElseThrow();
        var user = LoginUserUtils.getUser();
        if (!beforePost.getUserId().equals(user.getId())) {
            throw new ServiceException("您不能删除别人的文章");
        }
        this.postRepository.deleteById(id);
    }
}
