package com.twolz.qiyi.dc.dto.rokyinfo;

import lombok.Data;

import java.util.List;

/**
 * Created by liuwei
 * date 2017-07-03
 */
@Data
public class DevicesMsg {

    private List<DevicesInfoMsg> data;

    private Integer state;
}
