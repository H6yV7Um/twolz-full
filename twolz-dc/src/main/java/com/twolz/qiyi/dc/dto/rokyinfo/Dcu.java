package com.twolz.qiyi.dc.dto.rokyinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by liuwei
 * date 2017-08-15
 */
@Data
public class Dcu implements Serializable {

    //国际移动设备身份码
    @JsonProperty("IMEI")
    private String IMEI;

    //国际移动用户识别码
    @JsonProperty("IMSI")
    private String IMSI;

    //硬件版本号
    private String hardware;

    //软件版本号
    private String software;

    //在线状态 0：在线 1：离线
    private int online;

    //GSM 信号强度 0~10 值越大信号越好
    @JsonProperty("GSMRSSI")
    private int GSMRSSI;

    //GPS 卫星数
    @JsonProperty("GPSRSSI")
    private int GPSRSSI;

    //GPS 设备与中控设备通讯状态 0：正常 1：异常
    @JsonProperty("RS485")
    private int RS485;

    //GPS 设备外部供电状态 0：正常 1：异常
    private int extPower;

    //GPS 设备内置锂电池电压
    @JsonProperty("VBAT")
    private int VBAT;

}
