package com.twolz.qiyi.dc.dto.rokyinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by liuwei
 * date 2017-08-15
 */
@Data
public class Bms implements Serializable {

    //电量百分比（铅酸电池 101 表示剩余 1 格电）
    @JsonProperty("SOC")
    private int SOC;

    //电池健康状态（值大于 100 表示铅酸电池总格数）
    @JsonProperty("SOH")
    private int SOH;

    //充电次数
    private int chargeCount;

    //电池温度
    @JsonProperty("T")
    private double T;

    //电池状态
    private int status;
}
