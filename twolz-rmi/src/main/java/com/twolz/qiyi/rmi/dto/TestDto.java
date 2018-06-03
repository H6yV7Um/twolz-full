package com.twolz.qiyi.rmi.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuwei
 * @date 2018-06-03
 */
@Data
public class TestDto implements Serializable{

    private String name;

    private String passwd;

    private String email;

    private Date updateTime;
}
