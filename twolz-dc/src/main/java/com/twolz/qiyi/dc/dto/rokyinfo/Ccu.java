package com.twolz.qiyi.dc.dto.rokyinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by liuwei
 * date 2017-08-15
 */
@Data
public class Ccu implements Serializable {

    //设备序列号
    @JsonProperty("SN")
    private String SN;

    //硬件版本号
    private String hardware;

    //软件版本号
    private String software;

}
