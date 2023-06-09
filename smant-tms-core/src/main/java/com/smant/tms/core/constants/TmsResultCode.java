package com.smant.tms.core.constants;

import com.smant.common.enums.ResultCode;

public enum TmsResultCode implements ResultCode {

    TNANT_CODE_NULL(401, "租户编码为空", "参数错误:租户编码为空"),
    TENANT_NAME_NULL(402, "租户名称为空", "参数错误:租户名称为空"),
    TENANT_FULLNAME_NULL(402, "租户全称为空", "参数错误:租户全称为空"),

    TENANT_CODE_EXIST(301,"租户编码已经存在","数据错误:租户编码已经存在"),
    ;
    private final int code;

    private final String msg;

    private final String desc;

    TmsResultCode(int code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public static void main(String[] args) {
        ResultCode result =  TmsResultCode.TENANT_NAME_NULL;
        System.out.println(result.getMsg());
    }

}
