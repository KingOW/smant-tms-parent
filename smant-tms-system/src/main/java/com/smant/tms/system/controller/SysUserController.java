package com.smant.tms.system.controller;

import com.smant.common.beans.ResultBean;
import com.smant.common.enums.CommResultCode;
import com.smant.common.utils.StringExtUtils;
import com.smant.tms.core.exceptions.TmsException;
import com.smant.tms.core.model.SysUser;
import com.smant.tms.system.controller.dto.AddSysUserDto;
import com.smant.tms.system.controller.dto.SysUserLoginDto;
import com.smant.tms.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/user")
@Slf4j
public class SysUserController {

    /**
     * 系统用户服务
     */
    @Autowired
    @Qualifier(value = "sysUserService")
    private SysUserService sysUserService;


    /**
     * 用户登陆
     * @param userLoginDto
     * @return
     */
    @PostMapping(value = {"/login","/login/"})
    public ResultBean<String> userLogin(@RequestBody @Validated SysUserLoginDto userLoginDto){
        try {
            return this.sysUserService.userLogin(StringExtUtils.trim(userLoginDto.getLoginName()),StringExtUtils.trim(userLoginDto.getLoginPwd()));
        } catch (TmsException e) {
            log.error("用户登陆失败:服务器出现异常.",e);
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(),"用户登陆失败:服务器出现异常");
        }
    }

    /**
     * 添加系统用户
     * @param addUserDto
     * @return
     */
    @PostMapping(value = {"/",""})
    public ResultBean<String> addSysUser(@RequestBody @Validated AddSysUserDto addUserDto){
        try {
            SysUser sysUser = new SysUser();
            BeanUtils.copyProperties(addUserDto,sysUser);
            return this.sysUserService.saveSysUser(sysUser);
        } catch (TmsException e) {
            log.error("添加用户失败:服务器出现异常.",e);
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(),"添加用户失败:服务器出现异常");
        }
    }

}
