package com.twolz.qiyi.dc.service.client.message;

import com.twolz.qiyi.dc.service.client.message.config.MessageConfig;
import com.twolz.qiyi.dc.service.client.message.invoke.HttpInvoker;
import com.twolz.qiyi.dc.service.client.message.invoke.Invoker;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
@Slf4j
public class ConnectionImpl implements Connection {


    ArrayList<Session> sessions = new ArrayList<Session>();
    HashMap<String, MessageConfig> allRequests = new HashMap<String, MessageConfig>();
    Invoker client = new HttpInvoker();

    MessageConfig request;

    MessageDispatcher messageDispatcher;

    MessageDelivery messageDelivery;

    public ConnectionImpl(MessageConfig request) {
        this.request = request;
    }

    public void start() {
        if (messageDispatcher != null && messageDispatcher.isRunning()) {
            return;
        }
        messageDelivery = new MessageDelivery(sessions);
        messageDispatcher = new MessageDispatcher(allRequests, client, messageDelivery);
        messageDispatcher.start();
        log.info("Connection start");
    }

    public void stop() {
        if (messageDispatcher != null) {
            messageDispatcher.quit();
        }
        closeAllSessions();
        log.info("Connection stop");
    }

    public Session createSession() {
        Session session = new SessionImpl(this);
        sessions.add(session);
        return session;
    }

    public void closeSession(Session session) {

        session.clearAllConsumers();
        sessions.remove(session);
        clearInvalidRequests();

    }

    private void clearInvalidRequests() {
        ArrayList<String> unregisterRequests = new ArrayList<String>();
        for (String topicTag : allRequests.keySet()) {
            boolean deleteFlag = true;
            for (Session item : sessions) {
                ArrayList<MessageConsumer> arrayList = item.allConsumers();
                for (MessageConsumer consumer : arrayList) {
                    if (consumer.getTopic().getTag().equals(topicTag)) {
                        deleteFlag = false;
                        break;
                    }
                }
            }
            if (deleteFlag) {
                unregisterRequests.add(topicTag);
            }
        }

        unregisterRequests.forEach(key -> {
            allRequests.remove(key);
        });
    }

    @Override
    public void registerRequestByTopic(Topic topic) {
        if (!allRequests.containsKey(topic.getTag())) {
            allRequests.put(topic.getTag(), request);
        }
    }

    private void closeAllSessions() {

        sessions.forEach(Session::clearAllConsumers);
        sessions.clear();
        allRequests.clear();

    }

}
