package com.my.forum.model.DTO;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author yinzhijun
 * @date 2021/11/9 14:06
 */
@Data
public class RegisterDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
