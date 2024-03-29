package com.smant.tms.core.enums;

public enum TenantStatus {

    ENABLE(1,"启用","启用"),
    DISABLE(2,"禁用","禁用"),
    LOCKED(3,"锁定","锁定"),
    DELETE(99,"删除","删除"),
    ;


    private final int code;
    private final String name;
    private final String desc;

    TenantStatus(int code, String name, String desc) {
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
