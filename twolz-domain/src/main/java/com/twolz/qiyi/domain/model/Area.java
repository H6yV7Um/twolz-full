package com.twolz.qiyi.domain.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_area")
public class Area {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    @Column(name = "area_code")
    private Integer areaCode;

    /**
     * 父ID
     */
    @Column(name = "parent_code")
    private Integer parentCode;

    @Column(name = "order_no")
    private Integer orderNo;

    /**
     * 是否启用 1、启用 0、不启用
     */
    @Column(name = "is_use")
    private Integer isUse;

    /**
     * 索引路径
     */
    @Column(name = "index_path")
    private String indexPath;

    @Column(name = "name_path")
    private String namePath;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return area_code
     */
    public Integer getAreaCode() {
        return areaCode;
    }

    /**
     * @param areaCode
     */
    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 获取父ID
     *
     * @return parent_code - 父ID
     */
    public Integer getParentCode() {
        return parentCode;
    }

    /**
     * 设置父ID
     *
     * @param parentCode 父ID
     */
    public void setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * @return order_no
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取是否启用 1、启用 0、不启用
     *
     * @return is_use - 是否启用 1、启用 0、不启用
     */
    public Integer getIsUse() {
        return isUse;
    }

    /**
     * 设置是否启用 1、启用 0、不启用
     *
     * @param isUse 是否启用 1、启用 0、不启用
     */
    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    /**
     * 获取索引路径
     *
     * @return index_path - 索引路径
     */
    public String getIndexPath() {
        return indexPath;
    }

    /**
     * 设置索引路径
     *
     * @param indexPath 索引路径
     */
    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath == null ? null : indexPath.trim();
    }

    /**
     * @return name_path
     */
    public String getNamePath() {
        return namePath;
    }

    /**
     * @param namePath
     */
    public void setNamePath(String namePath) {
        this.namePath = namePath == null ? null : namePath.trim();
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