package com.my.forum.repository;

import com.my.forum.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author yinzhijun
 * @date 2021/11/9 11:04
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment as c left join Post as p on p.id=c.postId where p.userId=:userId")
    Page<Comment> listCommentByPostUserId(@Param("userId") Long userId, Pageable pageable);
}
