package com.smant.tms.tenant.service;

import com.smant.common.beans.PageDataBean;
import com.smant.common.beans.ResultBean;
import com.smant.tms.core.exceptions.TmsException;
import com.smant.tms.core.model.Tenant;

import java.util.List;

public interface TenantService {

    /**
     * 添加租户
     * @param tenant
     * @return
     * @throws TmsException
     */
    ResultBean<String> saveTenant(Tenant tenant)throws TmsException;

    /**
     * 根据租户id,查询租户
     * @param tenantId
     * @return
     * @throws TmsException
     */
    ResultBean<Tenant> queryTenantById(String tenantId)throws TmsException;

    /**
     * 根据租户编码，查询租户
     * @param tenantCode
     * @return
     * @throws TmsException
     */
    ResultBean<Tenant> queryTenantByCode(String tenantCode)throws TmsException;

    /**
     * 查询租户列表
     * @param tenantCode    租户编码，模糊查询
     * @param tenantName    租户名称，模糊查询
     * @param tenantStatus  租户状态，精确查询
     * @param pageNo        分页查询
     * @param pageSize
     * @return
     * @throws TmsException
     */
    ResultBean<List<Tenant>> queryTenants(String tenantCode, String tenantName, int tenantStatus, int pageNo, int pageSize)throws TmsException;


    /**
     * 启用租户
     * @param tenantId
     * @return
     * @throws TmsException
     */
    ResultBean<String> enableTenantById(String tenantId)throws TmsException;
    ResultBean<String> enableTenantByCode(String tenantCode)throws TmsException;

    /**
     * 禁用租户
     * @param tenantId
     * @return
     * @throws TmsException
     */
    ResultBean<String> disableTenantById(String tenantId)throws TmsException;
    ResultBean<String> disableTenantByCode(String tenantCode)throws TmsException;

    /**
     * 删除租户
     * @param tenantId
     * @return
     * @throws TmsException
     */
    ResultBean<String> deleteTenantById(String tenantId)throws TmsException;
    ResultBean<String> deleteTenantByCode(String tenantCode)throws TmsException;
}
