package com.smant.tms.system.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加系统用户
 */
@Data
public class AddSysUserDto implements Serializable {

    /**
     * 用户名称
     */
    private String userName;

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

    /**
     * 用户描述
     */
    private String userDesc;

    /**
     * 备注
     */
    private String remark;
}
