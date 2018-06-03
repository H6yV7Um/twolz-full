package com.twolz.qiyi.domain.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_tip_info")
public class TipInfo {
    /**
     * 记录ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 编码
     */
    @Column(name = "tip_code")
    private String tipCode;

    @Column(name = "tip_msg")
    private String tipMsg;

    /**
     * 创建人或最后更改人ID
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
     * 获取记录ID
     *
     * @return id - 记录ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置记录ID
     *
     * @param id 记录ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取编码
     *
     * @return tip_code - 编码
     */
    public String getTipCode() {
        return tipCode;
    }

    /**
     * 设置编码
     *
     * @param tipCode 编码
     */
    public void setTipCode(String tipCode) {
        this.tipCode = tipCode == null ? null : tipCode.trim();
    }

    /**
     * @return tip_msg
     */
    public String getTipMsg() {
        return tipMsg;
    }

    /**
     * @param tipMsg
     */
    public void setTipMsg(String tipMsg) {
        this.tipMsg = tipMsg == null ? null : tipMsg.trim();
    }

    /**
     * 获取创建人或最后更改人ID
     *
     * @return create_by - 创建人或最后更改人ID
     */
    public Integer getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人或最后更改人ID
     *
     * @param createBy 创建人或最后更改人ID
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