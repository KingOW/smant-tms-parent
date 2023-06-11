package com.smant.tms.tenant.controller;

import com.smant.common.beans.ResultBean;
import com.smant.common.enums.CommResultCode;
import com.smant.tms.core.exceptions.TmsException;
import com.smant.tms.core.model.Tenant;
import com.smant.tms.tenant.controller.dto.AddTenantDto;
import com.smant.tms.tenant.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 租户控制器
 */
@RestController
@RequestMapping("/tenant")
@Slf4j
@Tag(name = "TenantController", description = "租户管理相关接口")
public class TenantController {


    /**
     * 租户服务
     */
    @Autowired
    @Qualifier(value = "tenantService")
    private TenantService tenantService;

    /**
     * 添加租户
     *
     * @param addTenantDto
     * @return
     */
    @Operation(summary = "新增/添加租户")
    @PostMapping(value = {"/", ""})
    public ResultBean<String> saveTenant(@RequestBody AddTenantDto addTenantDto) {
        try {
            Tenant tenant = new Tenant();
            BeanUtils.copyProperties(addTenantDto, tenant);
            return this.tenantService.saveTenant(tenant);
        } catch (Exception ex) {
            log.error("添加租户失败:服务器出现异常.", ex);
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "添加租户失败:服务器出现异常.");
        }

    }

    /**
     * 根据租户id-查询租户
     *
     * @param tenantId
     * @return
     */
    @Operation(summary = "根据租户Id,查询租户信息")
    @Parameter(name = "tenantId", description = "租户ID", required = true)
    @GetMapping("/id/{tenantId}")
    public ResultBean<Tenant> queryTenantById(@PathVariable(value = "tenantId", required = true) String tenantId) {
        try {
            return this.tenantService.queryTenantById(tenantId);
        } catch (Exception ex) {
            log.error("查询租户失败:服务器出现异常.", ex);
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "查询租户失败:服务器出现异常.");
        }
    }

    /**
     * 根据租户编码-查询租户
     *
     * @param tenantCode
     * @return
     */
    @Operation(summary = "根据租户编码,查询租户信息")
    @Parameter(name = "tenantCode", description = "租户编码", required = true)
    @GetMapping("/code/{tenantCode}")
    public ResultBean<Tenant> queryTenantByCode(@PathVariable(value = "tenantCode", required = true) String tenantCode) {
        try {
            return this.tenantService.queryTenantByCode(tenantCode);
        } catch (Exception ex) {
            log.error("查询租户失败:服务器出现异常.", ex);
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "查询租户失败:服务器出现异常.");
        }
    }

    /**
     * 查询租户列表
     *
     * @param tenantCode
     * @param tenantName
     * @param tenantStatus
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Operation(summary = "查询租户列表(分页查询)",
            parameters = {
                    @Parameter(name = "tenantCode", description = "租户编码,模糊查询", required = false),
                    @Parameter(name = "tenantName", description = "租户名称,模糊查询", required = false),
                    @Parameter(name = "tenantStatus", description = "租户状态,精确查询,默认为0,表示查询所有(不包括已删除状态)", required = false),
                    @Parameter(name = "pageNo", description = "分页参数:页码,第几页;默认为1", required = false),
                    @Parameter(name = "pageSize", description = "分页参数:每页数量;默认为20", required = false)
            }
    )
    @GetMapping("/list")
    public ResultBean<List<Tenant>> listTenants(@RequestParam(value = "tenantCode", required = false) String tenantCode,
                                                @RequestParam(value = "tenantName", required = false) String tenantName,
                                                @RequestParam(value = "tenantStatus", required = false, defaultValue = "0") int tenantStatus,
                                                @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize
    ) {
        try {
            return this.tenantService.queryTenants(tenantCode, tenantName, tenantStatus, pageNo, pageSize);
        } catch (TmsException e) {
            log.error("查询租户列表失败:服务器出现异常.", e);
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "查询租户列表失败:服务器出现异常.");
        }
    }

    /**
     * 启用租户
     *
     * @return
     */
    @Operation(summary = "启用租户")
    @Parameter(name = "tenantId", description = "租户ID", required = true)
    @PutMapping("/id/{tenantId}/en")
    public ResultBean<String> enableTenantById(@PathVariable(value = "tenantId") String tenantId) {
        try {
            return this.tenantService.enableTenantById(tenantId);
        } catch (TmsException e) {
            log.error("启用租户失败:服务器出现异常.");
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "启用租户失败:服务器出现异常.");
        }
    }

    @Operation(summary = "禁用租户")
    @Parameter(name = "tenantId", description = "租户ID", required = true)
    @PutMapping("/id/{tenantId}/dis")
    public ResultBean<String> disableTenantById(@PathVariable(value = "tenantId") String tenantId) {
        try {
            return this.tenantService.disableTenantById(tenantId);
        } catch (TmsException e) {
            log.error("禁用租户失败:服务器出现异常.");
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "禁用租户失败:服务器出现异常.");
        }
    }

    @Operation(summary = "启用租户")
    @Parameter(name = "tenantCode", description = "租户编码", required = true)
    @PutMapping("/code/{tenantCode}/en")
    public ResultBean<String> enableTenantByCode(@PathVariable(value = "tenantCode", required = true) String tenantCode) {
        try {
            return this.tenantService.enableTenantByCode(tenantCode);
        } catch (TmsException e) {
            log.error("启用租户失败:服务器出现异常.");
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "启用租户失败:服务器出现异常.");
        }
    }

    @Operation(summary = "禁用租户")
    @Parameter(name = "tenantCode", description = "租户编码", required = true)
    @PutMapping("/code/{tenantCode}/dis")
    public ResultBean<String> disableTenantByCode(@PathVariable(value = "tenantCode", required = true) String tenantCode) {
        try {
            return this.tenantService.disableTenantByCode(tenantCode);
        } catch (TmsException e) {
            log.error("启用租户失败:服务器出现异常.");
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "启用租户失败:服务器出现异常.");
        }
    }

    @Operation(summary = "删除租户")
    @Parameter(name = "tenantCode", description = "租户编码", required = true)
    @DeleteMapping("/code/{tenantCode}")
    public ResultBean<String> deleteTenantByCode(@PathVariable(value = "tenantCode", required = true) String tenantCode) {
        try {
            return this.tenantService.deleteTenantByCode(tenantCode);
        } catch (TmsException e) {
            log.error("删除租户失败:服务器出现异常.");
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "删除租户失败:服务器出现异常.");
        }
    }

    @Operation(summary = "删除租户")
    @Parameter(name = "tenantId", description = "租户ID", required = true)
    @DeleteMapping("/id/{tenantId}")
    public ResultBean<String> deleteTenantById(@PathVariable(value = "tenantId") String tenantId) {
        try {
            return this.tenantService.deleteTenantById(tenantId);
        } catch (TmsException e) {
            log.error("删除租户失败:服务器出现异常.");
            return new ResultBean<>(false, CommResultCode.SERVER_ERROR.getCode(), "删除租户失败:服务器出现异常.");
        }
    }
}
