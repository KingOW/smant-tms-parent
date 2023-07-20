package com.smant.tms.core.enums;

import com.smant.common.enums.ResultCode;

public enum TmsResultCode implements ResultCode {

    TNANT_ID_NULL(10401, "租户ID为空", "参数错误:租户ID为空"),
    TNANT_CODE_NULL(10402, "租户编码为空", "参数错误:租户编码为空"),
    TENANT_NAME_NULL(10403, "租户名称为空", "参数错误:租户名称为空"),
    TENANT_FULLNAME_NULL(10404, "租户全称为空", "参数错误:租户全称为空"),

    TENANT_CODE_EXIST(10301,"租户编码已经存在","数据错误:租户编码已经存在"),
    TENANT_NOTEXIST(10302,"租户不存在或已经被删除","数据错误:租户不存在或已经被删除"),
    TENANT_DISABLE(10304,"租户不可用","数据错误:租户不可用"),
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
