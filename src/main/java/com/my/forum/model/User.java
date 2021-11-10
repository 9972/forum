package com.my.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 *
 * @author yinzhijun
 * @date 2021/11/9 09:22
 */
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "update user set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -2248163511821447214L;

    @Id
    private Long id;

    @NotEmpty(message = "username不能为空")
    private String username;

    @NotEmpty(message = "password不能为空")
    @JsonIgnore
    private String password;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;
}
