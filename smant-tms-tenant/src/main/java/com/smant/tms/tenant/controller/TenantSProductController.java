package com.smant.tms.tenant.controller;

import com.smant.common.beans.ResultBean;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租户产品Controller
 */
@RestController
@RequestMapping("/tenant/sproduct")
@Slf4j
@Tag(name = "TenantSProductController", description = "租户产品相关接口")
public class TenantSProductController {

    public ResultBean<String> addTenantSProduct(){

        return null;
    }
}
