package com.smant.tms.tenant.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * 添加租户
 */
@Data
@Schema(name = "AddTenantDto", description = "租户添加信息")
public class AddTenantDto implements Serializable {

    /**
     * 租户编码
     */
    @Schema(name = "tenantCode",description = "租户编码")
    @NotBlank(message = "租户编码不能为空.")
    private String tenantCode;

    /**
     * 租户名称
     */
    @Schema(name = "tenantName",description = "租户名称")
    @NotBlank(message = "租户名称不能为空.")
    private String tenantName;

//    /**
//     * 租户别名
//     */
//    @TableField(value="tenant_alias_name")
//    private String tenantAliasName;

    /**
     * 租户全称
     */
    @Schema(name = "tenantFullName",description = "租户全称")
    private String tenantFullName;

    /**
     * 租户描述
     */
    @Schema(name = "tenantDesc",description = "租户描述")
    private String tenantDesc;

    /**
     * 备注
     */
    @Schema(name = "remark",description = "备注")
    private String remark;
}
