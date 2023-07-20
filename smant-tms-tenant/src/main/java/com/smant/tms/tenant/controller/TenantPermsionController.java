package com.smant.tms.tenant.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.smant.common.beans.ResultBean;
import com.smant.tms.core.exceptions.TmsException;
import com.smant.tms.tenant.controller.dto.AddTenantPermDto;
import com.smant.tms.tenant.service.TenantPermsionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 租户权限
 */
@RestController
@RequestMapping(value = "/tenant/{tenantIdCode}/permsion")
@Tag(name = "TenantPermsionController", description = "租户权限相关接口")
public class TenantPermsionController {

    @Autowired
    @Qualifier(value = "tenantPermsionService")
    private TenantPermsionService tenantPermsionService;

    @Operation(summary = "保存租户权限")
    @Parameter(name = "sprodPerms", description = "产品权限信息", required = true)
    @PostMapping("/")
    public ResultBean<String> saveTenantPermsions(@PathVariable(value = "tenantIdCode")String tenantIdCode,
                                                  @RequestBody Map<String,List<String>> sprodPerms){

        try {
            return this.tenantPermsionService.saveTenantPermsions(tenantIdCode,sprodPerms);
        } catch (TmsException e) {
            throw new RuntimeException(e);
        }
    }
}
