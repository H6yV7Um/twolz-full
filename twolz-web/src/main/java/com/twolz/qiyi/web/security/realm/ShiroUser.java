package com.twolz.qiyi.web.security.realm;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by liuwei
 * date: 16/10/17
 */
public @Data
class ShiroUser implements Serializable {

    private Integer id;
    private String userName;
    private String realName;
    private String mobile;
    private String passwd;
    private String avatarUrl;
    private Set<String> roleSet;
    private Set<String> permissionSet;

}
