package com.twolz.qiyi.common.service.sms;


import lombok.Data;

import java.io.Serializable;

/**
 * Created by liuwei
 * date 2017-03-23
 */
@Data
public class SmsStatus implements Serializable {

    public SmsStatus(String mobile) {
        this.mobile = mobile;
    }

    public SmsStatus() {

    }

    private String mobile;
    private String lastRandom;
    private long last = 0;
    private int todayCnt = 0;
    private int state = 1;    //1：可用，0：不可用
}