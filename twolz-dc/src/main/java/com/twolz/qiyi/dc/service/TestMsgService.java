package com.twolz.qiyi.dc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twolz.qiyi.dc.mongo.TestMsgRepository;
import com.twolz.qiyi.dc.mq.MsgService;
import com.twolz.qiyi.rmi.dto.TestDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author liuwei
 * @date 2018-06-03
 */
@Slf4j
@Service
public class TestMsgService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TestMsgRepository testMsgRepository;

    @KafkaListener(topics = MsgService.TEST_TOPIC)
    public void processMessage(byte[] msg) {
        TestDto testDto = SerializationUtils.deserialize(msg);
        try {
            log.info("进入消费测试消息！！！");
            log.info(objectMapper.writeValueAsString(testDto));
            testMsgRepository.save(testDto);
        } catch (Exception e) {
            log.error("error ",e);
        }
    }
}
