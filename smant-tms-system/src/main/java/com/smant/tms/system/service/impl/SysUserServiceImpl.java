package com.smant.tms.system.service.impl;

import com.smant.auth.client.SimpleAuthClient;
import com.smant.auth.core.dto.LoginUserDto;
import com.smant.common.beans.ResultBean;
import com.smant.common.enums.CommResultCode;
import com.smant.common.utils.DateUtils;
import com.smant.common.utils.NumberExtUtils;
import com.smant.common.utils.StringExtUtils;
import com.smant.sdk.redis.model.CacheOption;
import com.smant.sdk.redis.service.RedisService;
import com.smant.tms.core.enums.SysUserStatus;
import com.smant.tms.core.exceptions.TmsException;
import com.smant.tms.core.model.SysUser;
import com.smant.tms.system.dao.entity.SysUserPO;
import com.smant.tms.system.dao.mapper.SysUserMapper;
import com.smant.tms.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 系统用户服务
 */
@Service(value = "sysUserService")
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    @Qualifier(value = "redisService")
    private RedisService redisService;

    /**
     * 密码编码器
     */
    @Autowired
    @Qualifier(value = "bcryptPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    /**
     * 系统用户mapper
     */
    @Autowired
    @Qualifier(value = "sysUserMapper")
    private SysUserMapper sysUserMapper;

    @Autowired
//    @Qualifier(value = "simpleAuthClient")
    private SimpleAuthClient simpleAuthClient;

    /**
     * 通过用户名密码登陆
     *
     * @param loginName
     * @param loginPwd
     * @return
     * @throws TmsException
     */
    @Override
    public ResultBean<String> userLogin(String loginName, String loginPwd) throws TmsException {
        log.info("使用用户名密码登陆:校验用户名密码是否正确");
        try {
            SysUserPO sysUserPO = this.sysUserMapper.selectSysUserPOByLoginName(StringExtUtils.trim(loginName));
            if (sysUserPO == null) {
                return new ResultBean<>(false, CommResultCode.DATA_ERROR.getCode(), "用户登陆名或密码错误,请重新登陆.");
            } else if (!passwordEncoder.matches(StringExtUtils.trim(loginPwd), sysUserPO.getLoginPwd())) {
                return new ResultBean<>(false, CommResultCode.DATA_ERROR.getCode(), "用户登陆名或密码错误,请重新登陆.");
            } else if(SysUserStatus.ENABLE.getCode() != sysUserPO.getUserStatus()){
                return new ResultBean<>(false, CommResultCode.DATA_ERROR.getCode(), "用户不可用,请联系系统管理员..");
            }else{
                LoginUserDto loginUserDto = new LoginUserDto();
                loginUserDto.setLoginName(loginName);
                loginUserDto.setSysCode("TMS");
                return  simpleAuthClient.createToken(loginUserDto);
            }
        } catch (Exception e) {
            throw new TmsException("用户登陆失败:服务器出现异常.",e);
        }
    }

//    private void logSysUserLogin(){
//
//    }
    @Override
    public ResultBean<String> saveSysUser(SysUser sysUser) throws TmsException {
        SysUserPO sysUserPO = this.BuildSysUserPO(sysUser);
        try {
            this.sysUserMapper.insertSysUserPO(sysUserPO);
        } catch (Exception e) {
            log.error("保存用户失败：操作数据库出现异常.", e);
            throw new RuntimeException(e);
        }
        return new ResultBean<>(true, CommResultCode.SUCCESS, sysUserPO.getUserId());
    }

    private SysUserPO BuildSysUserPO(SysUser sysUser) {
        SysUserPO sysUserPO = new SysUserPO();
        String userId = this.BuildTmsSysUserID();
        sysUserPO.setUserId(userId);
        sysUserPO.setLoginName(StringExtUtils.trim(sysUser.getLoginName()));
        sysUserPO.setLoginPwd(passwordEncoder.encode(sysUser.getLoginPwd()));
        sysUserPO.setUserName(StringExtUtils.isEmpty(sysUser.getUserName()) ? StringExtUtils.trim(sysUser.getLoginName()) : StringExtUtils.trim(sysUser.getUserName()));
        sysUserPO.UserStatus(SysUserStatus.ENABLE, "添加系统用户,系统用户初始化为【" + SysUserStatus.ENABLE.getName() + "】状态");
        LocalDateTime now = LocalDateTime.now();
        sysUserPO.setCreateTime(now);
        sysUserPO.setCreateUser("Init");
        sysUserPO.setUpdateTime(now);
        sysUserPO.setUpdateUser("Init");
        return sysUserPO;
    }

    private String BuildTmsSysUserID() {
        String currentDt = DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD);
        String tenantKey = "Seq-TmsSysUserId-" + currentDt;
        long seq = this.redisService.getSequence(tenantKey, new CacheOption(CacheOption.KEY_EXPIRE_TIME, 86400l));
        return "TSU-" + currentDt + NumberExtUtils.formatNumberDef(seq);
    }

    private ResultBean<String> checkSaveSysUser(SysUser sysUser) {

        if (sysUser == null || StringExtUtils.isEmpty(sysUser.getLoginName())) {

        }
        if (StringExtUtils.isEmpty(sysUser.getLoginPwd())) {

        }
        return null;
    }

}
