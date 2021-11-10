package com.my.forum.model;

import com.my.forum.constant.ValidGroupInsert;
import com.my.forum.constant.ValidGroupUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author yinzhijun
 * @date 2021/11/9 10:59
 */
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "update comment set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comment {
    @NotNull(message = "id不能为空", groups = ValidGroupUpdate.class)
    @Id
    private Long id;

    private Long pid;

    private Long userId;

    @Column(name = "post_id", updatable = false)
    @NotNull(message = "post_id不能为空", groups = ValidGroupInsert.class)
    private Long postId;

    @NotEmpty(message = "回复内容不能为空")
    private String content;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "create_time", updatable = false)
    @CreatedDate
    private Date createTime;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @LastModifiedDate
    private Date updateTime;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @OneToOne
    @JoinColumn(insertable = false, updatable = false)
    private Post post;

    /**
     * 子回复列表
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(insertable = false, updatable = false, name = "pid", referencedColumnName = "id")
    private List<Comment> subComment;
}
