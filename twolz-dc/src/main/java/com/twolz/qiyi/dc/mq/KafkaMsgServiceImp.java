package com.twolz.qiyi.dc.mq;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


/**
 * @author liuwei
 * 消息服务实现类
 */
@Log4j
@Component
public class KafkaMsgServiceImp implements MsgService {

	@Autowired
	private KafkaTemplate kafkaTemplate;

	@Override
	public void sendMsg(String topic,byte[] msg) {
		kafkaTemplate.send(topic, msg);
	}

}
