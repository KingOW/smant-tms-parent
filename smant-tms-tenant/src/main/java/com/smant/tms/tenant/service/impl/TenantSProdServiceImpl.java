package com.smant.tms.tenant.service.impl;

import com.smant.common.beans.ResultBean;
import com.smant.common.enums.CommResultCode;
import com.smant.common.utils.StringExtUtils;
import com.smant.sdk.redis.service.RedisService;
import com.smant.tms.core.enums.TenantStatus;
import com.smant.tms.core.enums.TmsResultCode;
import com.smant.tms.core.exceptions.TmsException;
import com.smant.tms.tenant.dao.entity.TenantPO;
import com.smant.tms.tenant.dao.entity.TenantSProdPO;
import com.smant.tms.tenant.dao.mapper.TenantMapper;
import com.smant.tms.tenant.service.TenantSProdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 租户-产品服务
 */
@Service(value = "tenantSProdService")
@Slf4j
public class TenantSProdServiceImpl implements TenantSProdService {

    /**
     * 租户mapper
     */
    @Autowired
    @Qualifier(value = "tenantMapper")
    private TenantMapper tenantMapper;

    /**
     * 缓存服务
     */
    @Autowired
    @Qualifier(value = "redisService")
    private RedisService redisService;


    @Override
    public ResultBean<String> saveTenantSProduct(String tenantIdCode, String sprodIdCode) throws TmsException {
        log.info("给租户添加产品:校验参数是否符合要求.");
        if (StringExtUtils.isEmpty(tenantIdCode)) {
            log.warn("给租户添加产品失败:没有提供租户信息.");
            return new ResultBean<>(false, TmsResultCode.TNANT_ID_NULL.getCode(), "给租户添加产品失败:没有提供租户信息.");
        }
        if(StringExtUtils.isEmpty(sprodIdCode)){
            log.warn("给租户添加产品失败:没有提供产品信息.");
            return new ResultBean<>(false, TmsResultCode.TNANT_ID_NULL.getCode(), "给租户添加产品失败:没有提供产品信息.");
        }
        log.warn("");
        TenantPO tenantPO = null;
        try {
             tenantPO = this.tenantMapper.selectTenantByIdCode(StringExtUtils.trim(tenantIdCode));
            if (tenantPO == null || TenantStatus.DELETE.getCode() == tenantPO.getTenantStatus()) {
                return new ResultBean<>(false, TmsResultCode.TENANT_NOTEXIST.getCode(), "给租户添加产品失败:该租户不存在或已经删除.");
            } else if (TenantStatus.DISABLE.getCode() == tenantPO.getTenantStatus()) {
                return new ResultBean<>(false, TmsResultCode.TENANT_NOTEXIST.getCode(), "给租户添加产品失败:该租户不可用.");
            }
        } catch (Exception e) {
            log.error("给租户添加产品失败:服务器出现异常.",e);
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(),"给租户添加产品失败:服务器出现异常.");
        }

        try{
            //List<Object> datas = this.redisService.getObj("SP:*"+sprodIdCode+"*");
            TenantSProdPO tenantSProdPO = this.tenantMapper.selectTenantSProd(StringExtUtils.trim(tenantIdCode),StringExtUtils.trim(sprodIdCode));
            if(tenantSProdPO != null){
                return null;
            }
        } catch (Exception e) {
            log.error("租户开通产品失败:服务器出现异常.",e);
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(),"给租户添加产品失败:服务器出现异常.");
        }
//        TenantPO tenantPO = checkTenant.getData();
//        TenantSProdPO tenantSProdPO = new TenantSProdPO();
//this.tenantMapper.insert
        return null;
    }






}
