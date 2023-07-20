package com.smant.tms.tenant.service;

import com.smant.common.beans.ResultBean;
import com.smant.tms.core.exceptions.TmsException;

import java.util.List;
import java.util.Map;

/**
 * 租户权限服务
 */
public interface TenantPermsionService {

    /**
     * 给租户开通产品权限
     * @param tenantIdCode
     * @param sprodPerms
     * @return
     * @throws TmsException
     */
     ResultBean<String> saveTenantPermsions(String tenantIdCode, Map<String, List<String>> sprodPerms)throws TmsException;


    /**
     * 给租户开通某一个产品的权限
     * @param tenantIdCode
     * @param sprodIdCode
     * @param permsIds
     * @return
     * @throws TmsException
     */
     ResultBean<String> saveTenantPermstions(String tenantIdCode,String sprodIdCode,List<String> permsIds)throws TmsException;
}
