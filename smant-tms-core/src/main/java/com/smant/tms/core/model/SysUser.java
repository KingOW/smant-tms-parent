package com.smant.tms.core.model;

import com.smant.common.beans.BaseBean;
import com.smant.tms.core.constants.SysUserStatus;
import lombok.Data;

/**
 * 系统用户
 */
@Data
public class SysUser extends BaseBean {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户登陆名
     */
    private String loginName;

    /**
     * 用户登陆密码
     */
    private String loginPwd;

    /**
     * 用户状态 编码/名称
     */
    private int userStatus = SysUserStatus.ENABLE.getCode();

    /**
     * 用户状态名称
     */
    private String userStatusName = SysUserStatus.ENABLE.getName();

    private String userStatusReason;


    /**
     * 用户描述
     */
    private String userDesc;

    /**
     * 备注
     */
    private String remark;
}
