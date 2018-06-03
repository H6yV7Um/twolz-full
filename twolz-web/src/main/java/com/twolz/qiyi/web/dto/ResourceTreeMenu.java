package com.twolz.qiyi.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuwei
 * date: 2018-05-12
 */
@Data
public class ResourceTreeMenu implements Serializable {

    private Integer id;

    private Integer parentId;

    private String title;

    private String icon;

    private Boolean spread;

    private String href;

    private List<ResourceTreeMenu> children;

}
