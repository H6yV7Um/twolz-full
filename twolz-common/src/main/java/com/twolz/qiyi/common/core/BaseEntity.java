package com.twolz.qiyi.common.core;

import java.io.Serializable;

/**
 * 基础信息
 * Created by liuwei
 * date: 16/8/25
 */
public class BaseEntity implements Serializable {

    private Integer id;

    private Integer page = 1;

    private Integer rows = 10;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
