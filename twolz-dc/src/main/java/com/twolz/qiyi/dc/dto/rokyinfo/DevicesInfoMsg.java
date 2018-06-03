package com.twolz.qiyi.dc.dto.rokyinfo;

import lombok.Data;

/**
 * Created by liuwei
 * date 2017-07-03
 */
@Data
public class DevicesInfoMsg {
    private String authCode;
    private String category;
    private String mac1;
    private String mac2;
    private String model;
    private String sn;
}
