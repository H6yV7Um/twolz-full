package com.twolz.qiyi.web.dto;

import lombok.Data;

import java.util.Set;

/**
 * Created by liuwei
 */
@Data
public class UserPermsDto {

    private Set<String> roleSet;
    private Set<String> permissions;
}
