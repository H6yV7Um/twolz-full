package com.twolz.qiyi.dc.service.client.message.serialize;

import com.twolz.qiyi.dc.dto.rokyinfo.EbikeListProtos;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yuanzhijian on 2017/2/26.
 */
public class ProtobufParser implements Parser {
    @Override
    public <T> T rsbParse(InputStream result) throws IOException {
        return (T) EbikeListProtos.EbikeList.parseFrom(result);
    }
}
