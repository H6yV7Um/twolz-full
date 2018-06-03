package com.twolz.qiyi.domain.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_dict_item")
public class DictItem {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "dict_id")
    private Integer dictId;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_name")
    private String itemName;

    /**
     * 排序号
     */
    @Column(name = "order_no")
    private Integer orderNo;

    /**
     * 是否启用
     */
    @Column(name = "is_use")
    private Integer isUse;

    /**
     * 创建人ID
     */
    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人ID
     */
    @Column(name = "update_by")
    private Integer updateBy;

    /**
     * 更新时间
     */
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
     * @return dict_id
     */
    public Integer getDictId() {
        return dictId;
    }

    /**
     * @param dictId
     */
    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    /**
     * @return item_code
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * @param itemCode
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    /**
     * @return item_name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    /**
     * 获取排序号
     *
     * @return order_no - 排序号
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * 设置排序号
     *
     * @param orderNo 排序号
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取是否启用
     *
     * @return is_use - 是否启用
     */
    public Integer getIsUse() {
        return isUse;
    }

    /**
     * 设置是否启用
     *
     * @param isUse 是否启用
     */
    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    /**
     * 获取创建人ID
     *
     * @return create_by - 创建人ID
     */
    public Integer getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人ID
     *
     * @param createBy 创建人ID
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
     * 获取更新人ID
     *
     * @return update_by - 更新人ID
     */
    public Integer getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新人ID
     *
     * @param updateBy 更新人ID
     */
    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
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