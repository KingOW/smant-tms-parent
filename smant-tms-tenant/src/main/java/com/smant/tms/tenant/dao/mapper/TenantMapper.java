package com.smant.tms.tenant.dao.mapper;

import com.smant.common.beans.PageDataBean;
import com.smant.tms.tenant.dao.entity.TenantPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 租户Mapper
 */
@Repository(value = "tenantMapper")
public interface TenantMapper{


    /**
     * 插入租户
     * @param tenantPO
     * @return
     * @throws Exception
     */
    int insertTenant(@Param(value = "tenantPO") TenantPO tenantPO)throws Exception;

    /**
     * 根据租户id，查询租户
     * @param tenantId
     * @return
     * @throws Exception
     */
    TenantPO selectTenantById(@Param(value = "tenantId")String tenantId)throws Exception;

    /**
     * 根据租户编码，查询租户
     * @param tenantCode
     * @return
     * @throws Exception
     */
    TenantPO selectTenantByCode(@Param(value = "tenantCode")String tenantCode)throws Exception;


    /**
     * 查询租户列表
     * @param tenantCode
     * @param tenantName
     * @param tenantStatus
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<TenantPO> selectTenantsByPage(@Param(value = "tenantCode") String tenantCode,
                                 @Param(value = "tenantName") String tenantName,
                                 @Param(value = "tenantStatus") int tenantStatus,
                                 @Param(value = "page")  PageDataBean page)throws Exception;

    int countTenants(@Param(value = "tenantCode") String tenantCode,
                                 @Param(value = "tenantName") String tenantName,
                                 @Param(value = "tenantStatus") int tenantStatus)throws Exception;

    /**
     * 更新租户状态：启用，禁用，删除
     * @param tenantId
     * @param tenantStatus
     * @param operUser
     * @param operTime
     * @return
     */
    int updateTenantStatusById(@Param(value = "tenantId")String tenantId,
                               @Param(value = "tenantStatus")int tenantStatus,
                               @Param(value = "operUser")String operUser,
                               @Param(value = "operTime") LocalDateTime operTime);

    /**
     * 更新租户状态：启用，禁用,删除
     * @param tenantCode
     * @param tenantStatus
     * @param operUser
     * @param operTime
     * @return
     */
    int updateTenantStatusByCode(@Param(value = "tenantCode")String tenantCode,
                                 @Param(value = "tenantStatus")int tenantStatus,
                                 @Param(value = "operUser")String operUser,
                                 @Param(value = "operTime") LocalDateTime operTime);

}
