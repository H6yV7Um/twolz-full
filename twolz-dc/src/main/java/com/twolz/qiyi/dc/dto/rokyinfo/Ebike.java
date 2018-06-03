package com.twolz.qiyi.dc.dto.rokyinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by liuwei
 * date 2017-08-15
 */
@Data
public class Ebike implements Serializable {

    //车辆状态 0：设防 1：撤防 2：驻车 3：骑行 15：状态不明
    private int status;

    //0：没有告警 1：震动告警：2：内置电池低告警：4：外电断开告警
    private int alarm;

    //总里程
    @JsonProperty("ODO")
    private long ODO;

    //车辆实时速度
    private int speed;

    //故障，解析方法见 faultParser 库
    private String fault;

    //车辆实时电压
    private int voltage;

    //车辆实时电流
    private int current;

    //车辆实时转把值
    private int turn;

    //车辆状态数据来源 0：GPS 设备上报 1：手机上报
    private int source;

    //车辆状态数据更新时间
    private String reportTime;

    //档位
    private String gear;

    Bms bms;

    Lbs lbs;

    Ccu ccu;

    Pcu pcu;

    Dcu dcu;

    Ecu ecu;
}
