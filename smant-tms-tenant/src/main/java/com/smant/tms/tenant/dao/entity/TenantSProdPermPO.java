package com.smant.tms.tenant.dao.entity;

import com.smant.common.beans.BaseBean;
import com.smant.pms.core.enums.SProdFunType;
import lombok.Data;

@Data
public class TenantSProdPermPO  extends BaseBean {

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 产品id
     */
    private String sproductId;

    /**
     * 产品编码
     */
    private String sproductCode;

    /**
     * 产品名称
     */
    private String sproductName;

    private String funId;
    /**
     * 功能编码：权限的根节点表示的是产品
     */
    private String funCode;

    /**
     * 功能名称
     */
    private String funName;
    /**
     * 类型： 1菜单，2功能页面 3按钮
     */
    private int funType = SProdFunType.MENU.getCode();
    private String funTypeName = SProdFunType.MENU.getName();

    /**
     * 状态：1启用，2禁用，99删除
     */
    private int funStatus;
    /**
     * 排序
     */
    private int sortNo;

    /**
     * 功能图标
     */
    private String funIcon;

    /**
     * 功能url或者前端路由路径 /功能页面路径
     */
    private String funUrl;

    /**
     * 按钮 - js方法： 点击按钮的触发操作
     */
    private String funJsMethod;

    /**
     * 功能描述
     */
    private String funDesc;
    private String remark;

}
