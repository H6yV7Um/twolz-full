package com.twolz.qiyi.dc.service.client.message;

import java.util.ArrayList;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public interface Session {

    Topic createTopic(String topic);

    MessageConsumer createConsumer(Topic topic);

    void close();

    void clearAllConsumers();

    ArrayList<MessageConsumer> allConsumers();


}
