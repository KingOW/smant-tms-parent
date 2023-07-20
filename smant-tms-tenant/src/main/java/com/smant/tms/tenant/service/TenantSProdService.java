package com.smant.tms.tenant.service;

import com.smant.common.beans.ResultBean;
import com.smant.tms.core.exceptions.TmsException;

/**
 * 租户产品
 */
public interface TenantSProdService {

    /**
     * 给租户开通产品
     * @param tenantIdCode  租户id或者租户code
     * @param sprodIdCode   产品id或者产品code
     * @return
     * @throws TmsException
     */
    ResultBean<String> saveTenantSProduct(String tenantIdCode,String sprodIdCode)throws TmsException;
}
