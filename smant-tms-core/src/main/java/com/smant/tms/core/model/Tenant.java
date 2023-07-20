package com.smant.tms.core.model;
import com.smant.common.beans.BaseBean;
import com.smant.tms.core.enums.TenantStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 租户对象
 */
@Data
public class Tenant extends  BaseBean{

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 租户别名
     */
    private String tenantAliasName;

    /**
     * 租户全称
     */
    private String tenantFullName;

    /**
     * 入驻时间/加入时间
     */
    private String joinTimeStr;
    private LocalDateTime joinTime;

    /**
     * 租户状态: 租户状态编码/名称
     */
    private int tenantStatus = TenantStatus.ENABLE.getCode();
    private String tenantStatusName = TenantStatus.ENABLE.getName();

    /**
     * 租户描述
     */
    private String tenantDesc;

    /**
     * 备注
     */
    private String remark;
    public void TenantSataus(TenantStatus tenantStatus){
        this.tenantStatus = tenantStatus.getCode();
        this.tenantDesc = tenantStatus.getName();
    }
}
