package com.twolz.qiyi.domain.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_operat_log")
public class OperatLog {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_mobile")
    private String userMobile;

    @Column(name = "ip_addr")
    private String ipAddr;

    @Column(name = "action_url")
    private String actionUrl;

    @Column(name = "sub_module")
    private String subModule;

    private String module;

    private String mean;

    private String function;

    @Column(name = "sub_function")
    private String subFunction;

    @Column(name = "param_data")
    private String paramData;

    @Column(name = "create_time")
    private Date createTime;

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
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * @return user_mobile
     */
    public String getUserMobile() {
        return userMobile;
    }

    /**
     * @param userMobile
     */
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    /**
     * @return ip_addr
     */
    public String getIpAddr() {
        return ipAddr;
    }

    /**
     * @param ipAddr
     */
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr == null ? null : ipAddr.trim();
    }

    /**
     * @return action_url
     */
    public String getActionUrl() {
        return actionUrl;
    }

    /**
     * @param actionUrl
     */
    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl == null ? null : actionUrl.trim();
    }

    /**
     * @return sub_module
     */
    public String getSubModule() {
        return subModule;
    }

    /**
     * @param subModule
     */
    public void setSubModule(String subModule) {
        this.subModule = subModule == null ? null : subModule.trim();
    }

    /**
     * @return module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param module
     */
    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    /**
     * @return mean
     */
    public String getMean() {
        return mean;
    }

    /**
     * @param mean
     */
    public void setMean(String mean) {
        this.mean = mean == null ? null : mean.trim();
    }

    /**
     * @return function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @param function
     */
    public void setFunction(String function) {
        this.function = function == null ? null : function.trim();
    }

    /**
     * @return sub_function
     */
    public String getSubFunction() {
        return subFunction;
    }

    /**
     * @param subFunction
     */
    public void setSubFunction(String subFunction) {
        this.subFunction = subFunction == null ? null : subFunction.trim();
    }

    /**
     * @return param_data
     */
    public String getParamData() {
        return paramData;
    }

    /**
     * @param paramData
     */
    public void setParamData(String paramData) {
        this.paramData = paramData == null ? null : paramData.trim();
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