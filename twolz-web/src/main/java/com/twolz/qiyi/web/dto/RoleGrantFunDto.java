package com.twolz.qiyi.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuwei
 * date 2017-04-13
 */
@Data
public class RoleGrantFunDto implements Serializable {

    private Integer roleId;
    private List<Integer> resourceList;
}
