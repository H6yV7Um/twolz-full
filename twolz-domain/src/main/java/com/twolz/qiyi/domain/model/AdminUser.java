package com.twolz.qiyi.domain.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_admin_user")
public class AdminUser {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 用户登录名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 用户手机号码
     */
    private String mobile;

    /**
     * 密码
     */
    private String passwd;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 1.正常；0.停用
     */
    private Integer status;

    /**
     * 创建者ID
     */
    @Column(name = "create_by")
    private Integer createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 最后一次update时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 头像地址
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * 是否删除：0：删除，1：未删除。
     */
    @Column(name = "is_delete")
    private Integer isDelete;

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
     * 获取用户登录名
     *
     * @return user_name - 用户登录名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户登录名
     *
     * @param userName 用户登录名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取真实姓名
     *
     * @return real_name - 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * 获取用户手机号码
     *
     * @return phone - 用户手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置用户手机号码
     *
     * @param mobile 用户手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取密码
     *
     * @return passwd - 密码
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * 设置密码
     *
     * @param passwd 密码
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    /**
     * 获取盐值
     *
     * @return salt - 盐值
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置盐值
     *
     * @param salt 盐值
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * 获取1.正常；0.停用
     *
     * @return status - 1.正常；0.停用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1.正常；0.停用
     *
     * @param status 1.正常；0.停用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建者ID
     *
     * @return create_by - 创建者ID
     */
    public Integer getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建者ID
     *
     * @param createBy 创建者ID
     */
    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取最后一次update时间
     *
     * @return update_time - 最后一次update时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置最后一次update时间
     *
     * @param updateTime 最后一次update时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取头像地址
     *
     * @return avatar_url - 头像地址
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 设置头像地址
     *
     * @param avatarUrl 头像地址
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    /**
     * 获取是否删除：0：删除，1：未删除。
     *
     * @return is_delete - 是否删除：0：删除，1：未删除。
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除：0：删除，1：未删除。
     *
     * @param isDelete 是否删除：0：删除，1：未删除。
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}