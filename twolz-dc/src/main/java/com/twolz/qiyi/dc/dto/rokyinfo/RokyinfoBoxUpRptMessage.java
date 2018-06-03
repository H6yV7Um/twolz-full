package com.twolz.qiyi.dc.dto.rokyinfo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuwei
 * date 2017-08-15
 */
@Data
@Document
public class RokyinfoBoxUpRptMessage implements Serializable {

    @Id
    private String id;

    private Ebike ebike;
    private Date sendTime;

}
