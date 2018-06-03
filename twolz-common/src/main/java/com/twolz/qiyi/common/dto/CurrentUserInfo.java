package com.twolz.qiyi.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liuwei
 * date 2017-05-22
 */
@Data
public class CurrentUserInfo implements Serializable {

    private static final long serialVersionUID = -3434272908589805662L;

    /**用户ID*/
    private Integer userId;

    /**账号ID*/
    private Integer acctId;

    /**用户名称*/
    private String nickName;

    /**客户真实姓名*/
    private String realName;

    /**身份认证状态 -1:未认证；0：待认证；1:通过认证;2：未通过认证*/
    private Integer confirmStatus;

    /**押金状态 0:未交押金；1:已交押金；2:已退押金；3:押金退还中*/
    private Integer depositStatus;

    /**用户手机号码*/
    private String mobile;

    /**用户头像地址*/
    private String avatarUrl;

    /**用户token密钥*/
    private String secret;

}
