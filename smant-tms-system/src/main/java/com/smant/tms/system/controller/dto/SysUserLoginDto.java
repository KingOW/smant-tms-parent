package com.smant.tms.system.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登陆 dto
 */
@Data
public class SysUserLoginDto implements Serializable {

    /**
     * 用户登陆名
     */
    @NotBlank(message = "用户登陆名不能为空.")
    private String loginName;

    /**
     * 用户登陆密码
     */
    @NotBlank(message = "用户登陆密码不能为空.")
    private String loginPwd;
}
