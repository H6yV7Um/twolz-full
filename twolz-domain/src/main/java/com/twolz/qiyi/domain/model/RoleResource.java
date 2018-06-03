package com.twolz.qiyi.domain.model;

import javax.persistence.*;

@Table(name = "sys_role_resource")
public class RoleResource {
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "resource_id")
    private Integer resourceId;

    /**
     * @return role_id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * @return resource_id
     */
    public Integer getResourceId() {
        return resourceId;
    }

    /**
     * @param resourceId
     */
    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }
}