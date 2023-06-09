package com.smant.tms.tenant.service.impl;

import com.smant.common.beans.PageDataBean;
import com.smant.common.beans.ResultBean;
import com.smant.common.enums.CommResultCode;
import com.smant.common.utils.DateUtils;
import com.smant.common.utils.FastJsonUtils;
import com.smant.common.utils.NumberExtUtils;
import com.smant.common.utils.StringExtUtils;
import com.smant.sdk.redis.model.CacheOption;
import com.smant.sdk.redis.service.RedisService;
import com.smant.tms.core.constants.TenantStatus;
import com.smant.tms.core.constants.TmsResultCode;
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
        log.info("新增租户:检查参数是否符合要求:\n" + FastJsonUtils.toJsonString(tenant));
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
        log.info("根据租户ID查询租户信息:检查参数是否符合要求.\n" + tenantId);
        try {
            TenantPO tenantPO = this.tenantMapper.selectOneById(StringExtUtils.trim(tenantId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ResultBean<Tenant> queryTenantByCode(String tenantCode) throws TmsException {
        log.info("根据租户编码查询租户信息:检查参数是否符合要求.\n" + tenantCode);
        try {
            TenantPO tenantPO = this.tenantMapper.selectOneByCode(StringExtUtils.trim(tenantCode));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ResultBean<PageDataBean<Tenant>> queryTenants(String tenantCode, String tenantName, int tenantStatus, int pageNo, int pageSize) throws TmsException {
        String tenantCode_ = StringExtUtils.isEmpty(tenantCode) ? "" : "%" + StringExtUtils.trim(tenantCode) + "%";
        String tenantName_ = StringExtUtils.isEmpty(tenantName) ? "" : "%" + StringExtUtils.trim(tenantName) + "%";
        int startIndex = (pageNo - 1) * pageSize + 1;
        try {
            this.tenantMapper.selectTenants(tenantCode_, tenantName_, tenantStatus, startIndex, pageSize);
            int total = this.tenantMapper.countTenants(tenantCode_, tenantName_, tenantStatus);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private TenantPO BuildTenantPO(Tenant tenant) {
        TenantPO tenantPO = new TenantPO();
        String tenantId = this.BuildTenantID();
        BeanUtils.copyProperties(tenant, tenantPO);
        tenantPO.setTenantId(tenantId);
        tenantPO.setTenantStatus(TenantStatus.ENABLE.getCode());
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
            TenantPO tenantPO = this.tenantMapper.selectOneByCode(StringExtUtils.trim(tenant.getTenantCode()));
            if (tenantPO != null) {
                return new ResultBean<>(false, TmsResultCode.TENANT_CODE_EXIST);
            }
        } catch (Exception e) {
            log.error("新增租户失败:查询数据库出现异常.", e);
            return ResultBean.SERVER_ERROR_RESULT;
        }
        return ResultBean.DEFAULT_SUCCESS_RESULT;
    }

    private String BuildTenantID() {
        String currentDt = DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD);
        String tenantKey = "Seq-TenantID-" + currentDt;
        long seq = this.redisService.getSequence(tenantKey, new CacheOption(CacheOption.KEY_EXPIRE_TIME, 86400l));
        return "TEN-" + currentDt + NumberExtUtils.formatNumberDef(seq);
    }


}
