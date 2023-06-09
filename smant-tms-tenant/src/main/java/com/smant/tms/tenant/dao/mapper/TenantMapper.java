package com.smant.tms.tenant.dao.mapper;

import com.smant.tms.tenant.dao.entity.TenantPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
    TenantPO selectOneById(@Param(value = "tenantId")String tenantId)throws Exception;

    /**
     * 根据租户编码，查询租户
     * @param tenantCode
     * @return
     * @throws Exception
     */
    TenantPO selectOneByCode(@Param(value = "tenantCode")String tenantCode)throws Exception;


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
    List<TenantPO> selectTenants(@Param(value = "tenantCode") String tenantCode,
                                 @Param(value = "tenantName") String tenantName,
                                 @Param(value = "tenantStatus") int tenantStatus,
                                 @Param(value = "startIndex") int startIndex,
                                 @Param(value = "pageSize") int pageSize)throws Exception;

    int countTenants(@Param(value = "tenantCode") String tenantCode,
                                 @Param(value = "tenantName") String tenantName,
                                 @Param(value = "tenantStatus") int tenantStatus)throws Exception;

}
