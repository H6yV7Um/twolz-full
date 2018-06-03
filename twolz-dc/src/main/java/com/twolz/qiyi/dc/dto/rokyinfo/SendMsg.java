package com.twolz.qiyi.dc.dto.rokyinfo;


import lombok.Data;

import java.io.Serializable;

/**
 * Created by liuwei
 * date 2017-07-03
 */
@Data
public class SendMsg implements Serializable {

    private String command;
    private SendMsgConfig config;
}