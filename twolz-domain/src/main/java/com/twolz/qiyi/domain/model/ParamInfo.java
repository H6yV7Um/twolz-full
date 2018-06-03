package com.twolz.qiyi.domain.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_param_info")
public class ParamInfo {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "param_key")
    private String paramKey;

    @Column(name = "param_value")
    private String paramValue;

    @Column(name = "param_name")
    private String paramName;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_by")
    private Integer updateBy;

    @Column(name = "update_time")
    private Date updateTime;

    private String memo;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return param_key
     */
    public String getParamKey() {
        return paramKey;
    }

    /**
     * @param paramKey
     */
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey == null ? null : paramKey.trim();
    }

    /**
     * @return param_value
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * @param paramValue
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }

    /**
     * @return param_name
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * @param paramName
     */
    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    /**
     * @return create_by
     */
    public Integer getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_by
     */
    public Integer getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     */
    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}