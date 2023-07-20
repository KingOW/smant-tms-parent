package com.smant.tms.tenant.controller.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddTenantPermDto implements Serializable {

    private String sprodIdCode;
    private List<String> permsions = Lists.newArrayList();
}
