package com.smant.tms.system.dao.mapper;

import com.smant.tms.system.dao.entity.SysUserPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "sysUserMapper")
public interface SysUserMapper {

    /**
     * 插入系统用户
     * @param sysUserPO
     * @return
     * @throws Exception
     */
    int insertSysUserPO(@Param(value = "sysUserPO") SysUserPO sysUserPO)throws Exception;
    /**
     * 根据登陆名，登陆密码 查询系统用户
     * @param loginName
     * @param loginPwd
     * @return
     * @throws Exception
     */
    SysUserPO selectSysUserPOByLoginNameAndPwd(
            @Param(value = "loginName") String loginName,
            @Param(value = "loginPwd") String loginPwd)throws Exception;

    /**
     * 通过登陆名 查询系统用户
     * @param loginName
     * @return
     * @throws Exception
     */
    SysUserPO selectSysUserPOByLoginName(
            @Param(value = "loginName") String loginName)throws Exception;

}
