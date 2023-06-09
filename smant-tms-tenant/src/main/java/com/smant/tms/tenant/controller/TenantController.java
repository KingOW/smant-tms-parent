package com.smant.tms.tenant.controller;

import com.smant.common.beans.ResultBean;
import com.smant.tms.core.model.Tenant;
import org.springframework.web.bind.annotation.*;

/**
 * 租户控制器
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {

    @PostMapping("/")
    public ResultBean<String> saveTenant() {

        return null;
    }

    /**
     * 查询租户
     *
     * @param tenantId
     * @return
     */
    @GetMapping("/id/{tenantId}")
    public ResultBean<Tenant> queryTenantById(@PathVariable(value = "tenantId", required = true) String tenantId) {

        return null;
    }

    @GetMapping("/code/{tenantCode}")
    public ResultBean<Tenant> queryTenantByCode(@PathVariable(value = "tenantCode", required = true) String tenantCode) {

        return null;
    }
}
