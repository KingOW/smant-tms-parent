package com.smant.tms.tenant.controller;

import com.smant.common.beans.ResultBean;
import com.smant.common.enums.CommResultCode;
import com.smant.tms.core.exceptions.TmsException;
import com.smant.tms.tenant.service.TenantSProdService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * 租户产品Controller
 */
@RestController
@RequestMapping("/tenant/{tenantIdCode}/sproduct/{sprodIdCode}")
@Slf4j
@Tag(name = "TenantSProductController", description = "租户产品相关接口")
public class TenantSProductController {

    /**
     * 租户-产品服务
     */
    @Autowired
    @Qualifier(value = "tenantSProdService")
    private TenantSProdService tenantSProdService;

    /**
     * 为租户开通产品
     *
     * @param tenantIdCode 租户id或者租户编码
     * @param sprodIdCode  租户id或者租户编码
     * @return
     */
    @PostMapping(value = {"/", ""})
    public ResultBean<String> saveTenantSProduct(
            @PathVariable(value = "tenantIdCode") String tenantIdCode,
            @PathVariable(value = "sprodIdCode") String sprodIdCode) {
        try {
            return this.tenantSProdService.saveTenantSProduct(tenantIdCode, sprodIdCode);
        } catch (TmsException e) {
            log.error("为租户开通产品失败:服务器出现异常.", e);
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "为租户开通产品失败:服务器出现异常.");
        }
    }
    @PutMapping(value = {"/en", "en"})
    public ResultBean<String> enableTenantSProduct(
            @PathVariable(value = "tenantIdCode") String tenantIdCode,
            @PathVariable(value = "sprodIdCode") String sprodIdCode) {
        try {
            return this.tenantSProdService.saveTenantSProduct(tenantIdCode, sprodIdCode);
        } catch (TmsException e) {
            log.error("为租户开通产品失败:服务器出现异常.", e);
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "为租户开通产品失败:服务器出现异常.");
        }
    }

    @PutMapping(value = {"/dis", "dis"})
    public ResultBean<String> disableTenantSProduct(
            @PathVariable(value = "tenantIdCode") String tenantIdCode,
            @PathVariable(value = "sprodIdCode") String sprodIdCode) {
        try {
            return this.tenantSProdService.saveTenantSProduct(tenantIdCode, sprodIdCode);
        } catch (TmsException e) {
            log.error("为租户开通产品失败:服务器出现异常.", e);
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "为租户开通产品失败:服务器出现异常.");
        }
    }
    @DeleteMapping(value = {"/", ""})
    public ResultBean<String> deleteTenantSProduct(
            @PathVariable(value = "tenantIdCode") String tenantIdCode,
            @PathVariable(value = "sprodIdCode") String sprodIdCode) {
        try {
            return this.tenantSProdService.saveTenantSProduct(tenantIdCode, sprodIdCode);
        } catch (TmsException e) {
            log.error("为租户开通产品失败:服务器出现异常.", e);
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "为租户开通产品失败:服务器出现异常.");
        }
    }
}
