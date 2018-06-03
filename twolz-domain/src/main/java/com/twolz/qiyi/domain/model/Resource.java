package com.twolz.qiyi.domain.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_resource")
public class Resource {
    @Id
    private Integer id;

    /**
     * 父节点编号
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 父节点链编号
     */
    @Column(name = "parent_ids")
    private String parentIds;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 资源类型:对应数据字典表中数据
     */
    private Integer type;

    private String url;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 排序
     */
    private String sort;

    /**
     * 资源图标
     */
    private String icon;

    /**
     * 创建者ID
     */
    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新者ID
     */
    @Column(name = "update_by")
    private Integer updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否启用，1：启用，0：未启用
     */
    @Column(name = "status")
    private Integer status;

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
     * 获取父节点编号
     *
     * @return parent_id - 父节点编号
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父节点编号
     *
     * @param parentId 父节点编号
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取父节点链编号
     *
     * @return parent_ids - 父节点链编号
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * 设置父节点链编号
     *
     * @param parentIds 父节点链编号
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }

    /**
     * 获取节点名称
     *
     * @return name - 节点名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置节点名称
     *
     * @param name 节点名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取资源类型:对应数据字典表中数据
     *
     * @return type - 资源类型:对应数据字典表中数据
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置资源类型:对应数据字典表中数据
     *
     * @param type 资源类型:对应数据字典表中数据
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取权限标识
     *
     * @return permission - 权限标识
     */
    public String getPermission() {
        return permission;
    }

    /**
     * 设置权限标识
     *
     * @param permission 权限标识
     */
    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public String getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    /**
     * 获取资源图标
     *
     * @return icon - 资源图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置资源图标
     *
     * @param icon 资源图标
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
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
     * 获取更新者ID
     *
     * @return update_by - 更新者ID
     */
    public Integer getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新者ID
     *
     * @param updateBy 更新者ID
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
}