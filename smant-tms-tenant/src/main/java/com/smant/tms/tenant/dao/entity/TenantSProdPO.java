package com.smant.tms.tenant.dao.entity;

import com.smant.common.beans.BaseBean;
import com.smant.tms.core.enums.TenantSProdStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 租户-产品关联
 */
@Data
public class TenantSProdPO extends BaseBean {

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 产品id
     */
    private String sproductId;

    /**
     * 产品编码
     */
    private String sproductCode;

    /**
     * 产品名称
     */
    private String sproductName;

    /**
     * 状态：1未启用/未开通/未激活，2已开通/已启用/已激活，3临时禁用，4已到期 5已关闭
     */
    private  int status = TenantSProdStatus.NO_ENABLE.getCode();
    /**
     * 激活时间 / 启用时间
     */
    private LocalDateTime activeTime;

    /**
     * 有效时间：有效期
     */
    private LocalDateTime expiryTime;

    /**
     * 备注
     */
    private String remark;
}
