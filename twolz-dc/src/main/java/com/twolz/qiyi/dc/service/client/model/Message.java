package com.twolz.qiyi.dc.service.client.model;

import com.twolz.qiyi.dc.dto.rokyinfo.EbikeListProtos;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public class Message {

    private EbikeListProtos.EbikeList ebikeList;

    public Message(EbikeListProtos.EbikeList ebikeList) {
        this.ebikeList = ebikeList;
    }

    public EbikeListProtos.EbikeList getEbikeList() {
        return ebikeList;
    }

    public void setEbikeList(EbikeListProtos.EbikeList ebikeList) {
        this.ebikeList = ebikeList;
    }

}
