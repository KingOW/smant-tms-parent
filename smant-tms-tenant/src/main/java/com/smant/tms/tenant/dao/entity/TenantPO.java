package com.smant.tms.tenant.dao.entity;

import com.smant.common.beans.BaseBean;
import com.smant.tms.core.enums.TenantStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 租户po - 租户持久化对象
 */
@Data
//@TableName("tms_tenant")
public class TenantPO extends BaseBean {

    //@TableId(type=IdType.AUTO)
    private int id;

    /**
     * 租户id
     */
    //@TableField(value="tenant_id")
    private String tenantId;

    /**
     * 租户编码
     */
    //@TableField(value="tenant_code")
    private String tenantCode;

    /**
     * 租户名称
     */
    //@TableField(value="tenant_name")
    private String tenantName;

//    /**
//     * 租户别名
//     */
//    @TableField(value="tenant_alias_name")
//    private String tenantAliasName;

    /**
     * 租户全称
     */
    //@TableField(value="tenant_fullname")
    private String tenantFullName;

    private LocalDateTime joinTime;

    /**
     * 租户状态: 租户状态编码/名称
     */
    //@TableField(value="tenant_status")
    private int tenantStatus = TenantStatus.ENABLE.getCode();

    private String tenantStatusName = TenantStatus.ENABLE.getName();

    /**
     * 状态描述：生成/流转到该状态的原因描述
     */
    private String tenantStatusReason;
    /**
     * 租户描述
     */
    //@TableField(value="tenant_desc")
    private String tenantDesc;

    /**
     * 备注
     */
    //@TableField(value="remark")
    private String remark;

    /**
     * 版本
     */
    //@TableField(value="version",update="%s+1")
    //@Version
    private int version;

}
