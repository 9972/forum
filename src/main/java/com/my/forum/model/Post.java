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
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yinzhijun
 * @date 2021/11/9 09:22
 */
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {
        @Index(name = "post_user_id_index", columnList = "user_id")
})
@SQLDelete(sql = "update post set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post implements Serializable {
    @Serial
    private static final long serialVersionUID = 5757957382127950620L;

    @NotNull(message = "id不能为空", groups = ValidGroupInsert.class)
    @Id
    @Column(name = "id", updatable = false)
    private Long id;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "user_id", updatable = false)
    private Long userId;

    @NotEmpty(message = "content不能为空")
    private String content;

    @NotEmpty(message = "title不能为空")
    private String title;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "create_time", updatable = false)
    @CreatedDate
    private Date createTime;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @LastModifiedDate
    private Date updateTime;
}