package com.twolz.qiyi.dc.service.client;


import com.twolz.qiyi.dc.dto.rokyinfo.EbikeListProtos;
import com.twolz.qiyi.dc.mq.MsgService;
import com.twolz.qiyi.dc.service.client.message.*;
import com.twolz.qiyi.dc.service.client.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;


/**
 * Created by liuwei
 * date 2017-07-04
 */
@Slf4j
@Service
public class SyncReceivedCacheService implements ApplicationListener<ContextRefreshedEvent> {

    private @Value("${system.rokyinfo.pull-state}") boolean pullState;

	private @Value("${system.rokyinfo.user-name}") String userName;

	private @Value("${system.rokyinfo.password}") String password;

	private @Value("${system.rokyinfo.host1}") String host;

	private @Value("${system.rokyinfo.port}")  int port;

	private @Value("${system.rokyinfo.path}") String path;

	@Autowired
    MsgService msgService;
     
    private Connection connection;

    private void syncReceivedCache(){
        ConnectionFactory connectionFactory = new RkMQConnectionFactory(userName,password,host,port,path,1000,1000);
        connection = connectionFactory.createConnection();
        connection.start();
        Session session =  connection.createSession();
        MessageConsumer messageConsumer = session.createConsumer(session.createTopic(Topic.TOPIC_RECEIVED_EBIKE));
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //如果有车辆数据上报这个方法会被自动回调
                EbikeListProtos.EbikeList ebikeList = message.getEbikeList();
				if (ebikeList.getEbikeCount() > 0) {
					log.debug("原始报文: \n"+ebikeList.getEbikeList());
					ebikeList.getEbikeList().forEach(ebike -> {
						try {
							msgService.sendMsg(MsgService.TOPIC_ROKY_BOX_RPT,ebike.toByteArray());
						} catch (Exception ex) {
							log.error("处理主动拉取车辆上报信息出错：",ex);
						}
					});
				}
            }
        });
    }

	@PreDestroy
    private void sotpSync(){
        if (connection!=null){
            connection.stop();
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null && pullState) {
			log.info("syncReceivedCache is start ");
			syncReceivedCache();
        }
    }

}
