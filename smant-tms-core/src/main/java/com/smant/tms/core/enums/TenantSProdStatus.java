package com.smant.tms.core.enums;

/**
 * 租户-产品状态：1未启用/未开通/未激活，2已开通/已启用/已激活，3临时禁用，4已到期 5已关闭
 */
public enum TenantSProdStatus {
    NO_ENABLE(1,"未开通","1未启用/未开通/未激活"),
    ENABLE(2,"已开通","2已开通/已启用/已激活"),

    DISABLE(3,"临时禁用","3已禁用/临时禁用"),
    CLOSED(4,"已关闭","4已关闭/已到期"),


    ;
    /**
     * 状态码 / 状态名称  / 状态描述
     */
    private final int code;
    private final String name;
    private final String desc;

    TenantSProdStatus(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
