package com.twolz.qiyi.dc.dto.rokyinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liuwei
 * date 2017-08-15
 */
@Data
public class Lbs implements Serializable {

    //1：东经、北纬 2：东经、南纬 3：西经、南纬 4：西经、 北纬
    private int lonLatType;

    //经度 WGS-84 坐标系
    private double lon;

    //纬度 WGS-84 坐标系
    private double lat;

    //GSM 小区信息 （只有 GPG 设备上报时才会有此值）
    private String baseStation;

    //定位水平精度单位：米 （只有手机上报时才会有此值）
    private int hAccuracy;

    //位置来源 0：GPS 设备上报 1：手机上报
    private int source;

    //位置更新时间
    private String reportTime;
}
