package com.smant.tms.tenant.service.impl;

import com.google.common.collect.Lists;
import com.smant.common.beans.PageDataBean;
import com.smant.common.beans.ResultBean;
import com.smant.common.enums.CommResultCode;
import com.smant.common.utils.DateUtils;
import com.smant.common.utils.FastJsonUtils;
import com.smant.common.utils.NumberExtUtils;
import com.smant.common.utils.StringExtUtils;
import com.smant.sdk.redis.model.CacheOption;
import com.smant.sdk.redis.service.RedisService;
import com.smant.tms.core.enums.TenantStatus;
import com.smant.tms.core.enums.TmsResultCode;
import com.smant.tms.core.exceptions.TmsException;
import com.smant.tms.core.model.Tenant;
import com.smant.tms.tenant.dao.entity.TenantPO;
import com.smant.tms.tenant.dao.mapper.TenantMapper;
import com.smant.tms.tenant.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 租户服务
 */
@Service(value = "tenantService")
@Slf4j
public class TenantServiceImpl implements TenantService {

    @Autowired
    @Qualifier(value = "redisService")
    private RedisService redisService;
    @Autowired
    @Qualifier(value = "tenantMapper")
    private TenantMapper tenantMapper;

    @Override
    public ResultBean<String> saveTenant(Tenant tenant) throws TmsException {
        log.info("新增租户:检查参数是否符合要求:" + FastJsonUtils.toJsonString(tenant));
        ResultBean<String> checkResult = this.checkSaveTenant(tenant);
        if (checkResult.isNotSuccess()) {
            log.warn(checkResult.getMessage());
            return checkResult;
        }
        log.info("新增租户:封装数据并保存到数据库");
        TenantPO tenantPO = this.BuildTenantPO(tenant);
        try {
            this.tenantMapper.insertTenant(tenantPO);
        } catch (Exception e) {
            log.error("新增租户失败:操作数据库出现异常.", e);
            throw new TmsException("新增租户失败:操作数据库出现异常.", e);
        }
        log.info("新增租户:封装结果并返回到客户端");
        return new ResultBean<String>(true, CommResultCode.SUCCESS, tenantPO.getTenantId());
    }

    @Override
    public ResultBean<Tenant> queryTenantById(String tenantId) throws TmsException {
        log.info("根据租户ID查询租户信息:检查参数是否符合要求." + tenantId);
        try {
            if (StringExtUtils.isEmpty(tenantId)) {
                log.warn("根据租户ID查询租户信息失败:没有提供租户ID.");
                return new ResultBean<>(false, TmsResultCode.TNANT_ID_NULL.getCode(), "查询租户失败:租户ID为空.");
            }
            TenantPO tenantPO = this.tenantMapper.selectTenantById(StringExtUtils.trim(tenantId));
            return getTenantResultBean(tenantPO);
        } catch (Exception e) {
            log.error("查询租户失败:服务器操作出现异常.", e);
            throw new TmsException("查询租户失败:服务器操作出现异常.", e);
        }
    }

    @Override
    public ResultBean<Tenant> queryTenantByCode(String tenantCode) throws TmsException {
        log.info("根据租户编码查询租户信息:检查参数是否符合要求." + tenantCode);
        try {
            if (StringExtUtils.isEmpty(tenantCode)) {
                log.warn("根据租户编码查询租户信息失败:没有提供租户编码.");
                return new ResultBean<>(false, TmsResultCode.TNANT_CODE_NULL.getCode(), "查询租户失败:租户编码为空.");
            }
            TenantPO tenantPO = this.tenantMapper.selectTenantByCode(StringExtUtils.trim(tenantCode));
            return getTenantResultBean(tenantPO);
        } catch (Exception e) {
            log.error("查询租户失败:服务器操作出现异常.", e);
            throw new TmsException("查询租户失败:服务器操作出现异常.", e);
        }
    }

    private ResultBean<Tenant> getTenantResultBean(TenantPO tenantPO) {
        if (tenantPO == null || tenantPO.getTenantStatus() == TenantStatus.DELETE.getCode()) {
            log.warn("查询租户信息失败:该租户不存在.");
            return new ResultBean<>(false, TmsResultCode.TENANT_NOTEXIST.getCode(), "查询租户失败:该租户不存在或被删除.");
        }
        return new ResultBean<>(true, CommResultCode.SUCCESS, this.BuildTenant(tenantPO));
    }

    @Override
    public ResultBean<List<Tenant>> queryTenants(String tenantCode, String tenantName, int tenantStatus, int pageNo, int pageSize) throws TmsException {
        log.info("查询租户列表:格式化查询参数");
        String tenantCode_ = StringExtUtils.isEmpty(tenantCode) ? "" : "%" + StringExtUtils.trim(tenantCode) + "%";
        String tenantName_ = StringExtUtils.isEmpty(tenantName) ? "" : "%" + StringExtUtils.trim(tenantName) + "%";
        try {
            log.info("查询租户列表:查询数据库并进行封装.");
            List<TenantPO> tenantPOList = this.tenantMapper.selectTenantsByPage(tenantCode_, tenantName_, tenantStatus, new PageDataBean(pageNo, pageSize));
            List<Tenant> tenantList = Lists.newArrayList();
            tenantPOList.stream().forEach(tenantPO -> {
                tenantList.add(this.BuildTenant(tenantPO));
            });
            int total = this.tenantMapper.countTenants(tenantCode_, tenantName_, tenantStatus);
            log.info("查询租户列表:返回结果到客户端.");
            return new ResultBean<List<Tenant>>(true, CommResultCode.SUCCESS, tenantList).CountAndTotal(tenantList.size(), total);
        } catch (Exception e) {
            log.error("查询租户列表失败:服务器操作出现异常.", e);
            throw new TmsException("查询租户列表失败:服务器操作出现异常.", e);
        }
    }


    /**
     * 启用租户
     * @param tenantId
     * @return
     * @throws TmsException
     */
    @Override
    public ResultBean<String> enableTenantById(String tenantId) throws TmsException {
        log.info("根据租户id，启用租户：检查参数是否符合要求");
        try {
            if (StringExtUtils.isEmpty(tenantId)) {
                log.warn("启用租户失败:没有提供租户ID.");
                return new ResultBean<>(false, TmsResultCode.TNANT_ID_NULL.getCode(), "启用租户失败:没有提供租户ID.");
            }
            TenantPO tenantPO = this.tenantMapper.selectTenantById(StringExtUtils.trim(tenantId));
            if(tenantPO == null || tenantPO.getTenantStatus() == TenantStatus.DELETE.getCode()){
                return new ResultBean<>(false, TmsResultCode.TENANT_NOTEXIST.getCode(), "启用租户失败:该租户不存在或被删除.");
            }else if(tenantPO.getTenantStatus() == TenantStatus.ENABLE.getCode()){
                return new ResultBean<>(true, CommResultCode.SUCCESS.getCode(), "该租户已经是启用状态,无需再次启用.");
            }
            this.tenantMapper.updateTenantStatusById(StringExtUtils.trim(tenantId),TenantStatus.ENABLE.getCode(),"",LocalDateTime.now() );
        } catch (Exception e) {
            log.error("启用租户失败:服务器操作出现异常.", e);
            throw new TmsException("启用租户失败:服务器操作出现异常.", e);
        }
        return ResultBean.DEFAULT_SUCCESS_RESULT;
    }

    @Override
    public ResultBean<String> enableTenantByCode(String tenantCode) throws TmsException {
        log.info("根据租户编码，启用租户：检查参数是否符合要求");
        try {
            if (StringExtUtils.isEmpty(tenantCode)) {
                log.warn("启用租户失败:没有提供租户编码.");
                return new ResultBean<>(false, TmsResultCode.TNANT_CODE_NULL.getCode(), "启用租户失败:没有提供租户编码.");
            }
            TenantPO tenantPO = this.tenantMapper.selectTenantByCode(StringExtUtils.trim(tenantCode));
            if(tenantPO == null || tenantPO.getTenantStatus() == TenantStatus.DELETE.getCode()){
                return new ResultBean<>(false, TmsResultCode.TENANT_NOTEXIST.getCode(), "启用租户失败:该租户不存在或被删除.");
            }else if(tenantPO.getTenantStatus() == TenantStatus.ENABLE.getCode()){
                return new ResultBean<>(true, CommResultCode.SUCCESS.getCode(), "该租户已经是启用状态,无需再次启用.");
            }
            this.tenantMapper.updateTenantStatusByCode(StringExtUtils.trim(tenantCode),TenantStatus.ENABLE.getCode(),"",LocalDateTime.now() );
        } catch (Exception e) {
            log.error("启用租户失败:服务器操作出现异常.", e);
            throw new TmsException("启用租户失败:服务器操作出现异常.", e);
        }
        return ResultBean.DEFAULT_SUCCESS_RESULT;
    }

    /**
     * 禁用租户
     * @param tenantId
     * @return
     * @throws TmsException
     */
    @Override
    public ResultBean<String> disableTenantById(String tenantId) throws TmsException {
        log.info("根据租户id，禁用租户：检查参数是否符合要求");
        try {
            if (StringExtUtils.isEmpty(tenantId)) {
                log.warn("禁用租户失败:没有提供租户ID.");
                return new ResultBean<>(false, TmsResultCode.TNANT_ID_NULL.getCode(), "禁用租户失败:没有提供租户ID.");
            }
            TenantPO tenantPO = this.tenantMapper.selectTenantById(StringExtUtils.trim(tenantId));
            if(tenantPO == null || tenantPO.getTenantStatus() == TenantStatus.DELETE.getCode()){
                return new ResultBean<>(false, TmsResultCode.TENANT_NOTEXIST.getCode(), "禁用租户失败:该租户不存在或被删除.");
            }else if(tenantPO.getTenantStatus() == TenantStatus.DISABLE.getCode()){
                return new ResultBean<>(true, CommResultCode.SUCCESS.getCode(), "该租户已经是禁用状态,无需再次禁用.");
            }
            this.tenantMapper.updateTenantStatusById(StringExtUtils.trim(tenantId),TenantStatus.DISABLE.getCode(),"",LocalDateTime.now() );
        } catch (Exception e) {
            log.error("禁用租户失败:服务器操作出现异常.", e);
            throw new TmsException("禁用租户失败:服务器操作出现异常.", e);
        }
        return ResultBean.DEFAULT_SUCCESS_RESULT;
    }

    @Override
    public ResultBean<String> disableTenantByCode(String tenantCode) throws TmsException {
        log.info("根据租户编码，禁用租户：检查参数是否符合要求");
        try {
            if (StringExtUtils.isEmpty(tenantCode)) {
                log.warn("禁用租户失败:没有提供租户编码.");
                return new ResultBean<>(false, TmsResultCode.TNANT_CODE_NULL.getCode(), "禁用租户失败:没有提供租户编码.");
            }
            TenantPO tenantPO = this.tenantMapper.selectTenantByCode(StringExtUtils.trim(tenantCode));
            if(tenantPO == null || tenantPO.getTenantStatus() == TenantStatus.DELETE.getCode()){
                return new ResultBean<>(false, TmsResultCode.TENANT_NOTEXIST.getCode(), "禁用租户失败:该租户不存在或被删除.");
            }else if(tenantPO.getTenantStatus() == TenantStatus.DISABLE.getCode()){
                return new ResultBean<>(true, CommResultCode.SUCCESS.getCode(), "该租户已经是禁用状态,无需再次禁用.");
            }
            this.tenantMapper.updateTenantStatusByCode(StringExtUtils.trim(tenantCode),TenantStatus.DISABLE.getCode(),"",LocalDateTime.now() );
        } catch (Exception e) {
            log.error("禁用租户失败:服务器操作出现异常.", e);
            throw new TmsException("禁用租户失败:服务器操作出现异常.", e);
        }
        return ResultBean.DEFAULT_SUCCESS_RESULT;
    }

    /**
     * 删除租户
     * @param tenantId
     * @return
     * @throws TmsException
     */
    @Override
    public ResultBean<String> deleteTenantById(String tenantId) throws TmsException {
        log.info("根据租户id，删除租户：检查参数是否符合要求");
        try {
            if (StringExtUtils.isEmpty(tenantId)) {
                log.warn("删除租户失败:没有提供租户ID.");
                return new ResultBean<>(false, TmsResultCode.TNANT_ID_NULL.getCode(), "删除租户失败:没有提供租户ID.");
            }
            TenantPO tenantPO = this.tenantMapper.selectTenantById(StringExtUtils.trim(tenantId));
            if(tenantPO == null || tenantPO.getTenantStatus() == TenantStatus.DELETE.getCode()){
                return new ResultBean<>(true, CommResultCode.SUCCESS.getCode(), "该租户不存在或已经被删除.");
            }
            this.tenantMapper.updateTenantStatusById(StringExtUtils.trim(tenantId),TenantStatus.DELETE.getCode(),"",LocalDateTime.now() );
        } catch (Exception e) {
            log.error("删除租户失败:服务器操作出现异常.", e);
            throw new TmsException("删除租户失败:服务器操作出现异常.", e);
        }
        return ResultBean.DEFAULT_SUCCESS_RESULT;
    }

    @Override
    public ResultBean<String> deleteTenantByCode(String tenantCode) throws TmsException {
        log.info("根据租户编码，删除租户：检查参数是否符合要求");
        try {
            if (StringExtUtils.isEmpty(tenantCode)) {
                log.warn("删除租户失败:没有提供租户编码.");
                return new ResultBean<>(false, TmsResultCode.TNANT_CODE_NULL.getCode(), "删除租户失败:没有提供租户编码.");
            }
            TenantPO tenantPO = this.tenantMapper.selectTenantByCode(StringExtUtils.trim(tenantCode));
            if(tenantPO == null || tenantPO.getTenantStatus() == TenantStatus.DELETE.getCode()){
                return new ResultBean<>(true, CommResultCode.SUCCESS.getCode(), "该租户不存在或已经被删除.");
            }
            this.tenantMapper.updateTenantStatusByCode(StringExtUtils.trim(tenantCode),TenantStatus.DELETE.getCode(),"",LocalDateTime.now() );
        } catch (Exception e) {
            log.error("删除租户失败:服务器操作出现异常.", e);
            throw new TmsException("删除租户失败:服务器操作出现异常.", e);
        }
        return ResultBean.DEFAULT_SUCCESS_RESULT;
    }

    /**
     * 封装Tenant
     *
     * @param tenantPO
     * @return
     */
    private Tenant BuildTenant(TenantPO tenantPO) {
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(tenantPO, tenant);
        tenant.setCreateTimeStr(DateUtils.formatLocalDateTime(tenantPO.getCreateTime()));
        tenant.setUpdateTimeStr(DateUtils.formatLocalDateTime(tenantPO.getUpdateTime()));
        return tenant;
    }

    private TenantPO BuildTenantPO(Tenant tenant) {
        TenantPO tenantPO = new TenantPO();
        String tenantId = this.BuildTenantID();
        BeanUtils.copyProperties(tenant, tenantPO);
        tenantPO.setTenantId(tenantId);
        tenantPO.setTenantStatus(TenantStatus.ENABLE.getCode());
        LocalDateTime now = LocalDateTime.now();
        tenantPO.setCreateTime(now);
        tenantPO.setUpdateTime(now);
        return tenantPO;
    }

    private ResultBean<String> checkSaveTenant(Tenant tenant) {
        if (tenant == null || StringExtUtils.isEmpty(tenant.getTenantCode())) {
            return new ResultBean<>(false, TmsResultCode.TNANT_CODE_NULL);
        }
        if (StringExtUtils.isEmpty(tenant.getTenantFullName())) {
            return new ResultBean<>(false, TmsResultCode.TENANT_FULLNAME_NULL);
        }
        try {
            TenantPO tenantPO = this.tenantMapper.selectTenantByCode(StringExtUtils.trim(tenant.getTenantCode()));
            if (tenantPO != null) {
                return new ResultBean<>(false, TmsResultCode.TENANT_CODE_EXIST.getCode(),"新增失败:租户编码已经存在.");
            }
        } catch (Exception e) {
            log.error("新增租户失败:查询数据库出现异常.", e);
            return ResultBean.SERVER_ERROR_RESULT;
        }
        return ResultBean.DEFAULT_SUCCESS_RESULT;
    }

    private String BuildTenantID() {
        String currentDt = DateUtils.getCurrentDateStr(DateUtils.YYYYMMDD);
        String tenantKey = "Seq-TenantID-" + currentDt;
        long seq = this.redisService.getSequence(tenantKey, new CacheOption(CacheOption.KEY_EXPIRE_TIME, 86400l));
        return "TEN-" + currentDt +"-"+NumberExtUtils.formatNumber(NumberExtUtils.NUMBER_FORMATE_PATTERN_06d,seq);
    }


}
