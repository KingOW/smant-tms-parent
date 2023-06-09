package com.smant.tms.system.service;

import com.smant.common.beans.ResultBean;
import com.smant.tms.core.exceptions.TmsException;
import com.smant.tms.core.model.SysUser;

/**
 * 系统用户服务
 */
public interface SysUserService {

    /**
     * 通过用户名 密码登陆
     * @param loginName  登陆名
     * @param loginPwd   登陆密码
     * @return
     * @throws TmsException
     */
    ResultBean<String> userLogin(String loginName, String loginPwd)throws TmsException;

    /**
     * 添加系统用户
     * @param sysUser
     * @return
     * @throws TmsException
     */
    ResultBean<String> saveSysUser(SysUser sysUser)throws TmsException;
}
