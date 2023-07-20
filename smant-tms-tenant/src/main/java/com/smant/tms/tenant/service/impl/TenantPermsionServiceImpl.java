package com.smant.tms.tenant.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.smant.common.beans.ResultBean;
import com.smant.common.enums.CommKeys;
import com.smant.common.utils.StringExtUtils;
import com.smant.sdk.redis.service.RedisService;
import com.smant.tms.core.exceptions.TmsException;
import com.smant.tms.tenant.dao.entity.TenantPO;
import com.smant.tms.tenant.dao.entity.TenantSProdPermPO;
import com.smant.tms.tenant.dao.mapper.TenantMapper;
import com.smant.tms.tenant.service.TenantPermsionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Vector;

@Service(value = "tenantPermsionService")
@Slf4j
public class TenantPermsionServiceImpl implements TenantPermsionService {

    @Autowired
    @Qualifier(value = "tenantMapper")
    private TenantMapper tenantMapper;

    @Autowired
    @Qualifier(value = "redisService")
    private RedisService redisService;

    @Override
    public ResultBean<String> saveTenantPermsions(String tenantIdCode, Map<String, List<String>> sprodPerms) throws TmsException {

        TenantPO tenantPO = null;
        try {
            tenantPO = this.tenantMapper.selectTenantByIdCode(StringExtUtils.trim(tenantIdCode));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<TenantSProdPermPO> tntSProdPerms = Lists.newArrayList();
        sprodPerms.forEach((sprodCode, sperms) -> {
            JSONObject sprodJson = (JSONObject) this.redisService.getObj(CommKeys.RedisKey.SPRODUCT_KEY_PREFIX + "_" + sprodCode);
            if(this.redisService.exist(CommKeys.RedisKey.SPRODUCT_KEY_PREFIX + "_" + sprodCode)){
                String key  = CommKeys.RedisKey.TENANT_KEY_PREFIX+"-"+tenantPO.getTenantCode()+"-"+sprodCode+"-perms",;
                this.redisService.setMap(key,"perms",sperms);
                JSONObject sperm = (JSONObject) this.redisService.getObj("");
                TenantSProdPermPO tntSProdPerm = new TenantSProdPermPO();
                tntSProdPerms.add(tntSProdPerm);

            }
        });
        try {
            this.tenantMapper.insertTenantSProdPerms(tntSProdPerms);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        this.redisService.getMap(CommKeys.RedisKey.SPRODUCT_FUNC_KEY_PREFIX + "_" +);

        return null;
    }

    @Override
    public ResultBean<String> saveTenantPermstions(String tenantIdCode, String sprodIdCode, List<String> permsIds) throws TmsException {
        return null;
    }
}
