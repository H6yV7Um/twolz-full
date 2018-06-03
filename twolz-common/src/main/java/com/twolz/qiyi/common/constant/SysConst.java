package com.twolz.qiyi.common.constant;

import java.math.BigDecimal;

public class SysConst {
    /**
     * 分割符号
     */
    public static final String SPLITER_GANG="-";

    /**
     * 分割符号
     */
    public static final String SPLITER_BOTTOM_LINE="_";

    /**
     * 启用
     */
    public static final Integer USE_YES=1;
    /**
     * 不启用
     */
    public static final Integer USE_NO=0;

    /**
     * 分页表每页记录条数
     */
    public static final Integer PAGE_SIZE=10;

    /**
     * 系统管理员
     */
    public static final String SYSTEM_ADMIN = "admin";

    /**
     * 总公司或地区根节点编码
     */
    public static final Integer AREA_CODE_ROOT=10000;

    public static BigDecimal getZero(){
        return new BigDecimal(0);
    }
}
