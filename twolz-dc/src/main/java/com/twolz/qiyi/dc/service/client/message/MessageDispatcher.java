package com.twolz.qiyi.dc.service.client.message;

import com.twolz.qiyi.dc.service.client.message.config.MessageConfig;
import com.twolz.qiyi.dc.service.client.message.exception.RkMessageException;
import com.twolz.qiyi.dc.service.client.message.invoke.Invoker;
import com.twolz.qiyi.dc.service.client.message.monitor.MessageMonitor;
import com.twolz.qiyi.dc.service.client.message.serialize.Parser;
import com.twolz.qiyi.dc.service.client.message.serialize.ProtobufParser;
import com.twolz.qiyi.dc.service.client.message.util.InputStreamUtil;
import com.twolz.qiyi.dc.service.client.model.Message;
import com.twolz.qiyi.dc.service.client.model.RemoteResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
@Slf4j
public class MessageDispatcher extends Thread {


    private volatile boolean mQuit = false;

    HashMap<String, MessageConfig> allRequests;

    Invoker client;

    MessageDelivery messageDelivery;

    Parser mParser;

    public MessageDispatcher(HashMap<String, MessageConfig> allRequests, Invoker client, MessageDelivery messageDelivery) {
        this.allRequests = allRequests;
        this.client = client;
        this.messageDelivery = messageDelivery;
        this.mParser = new ProtobufParser();
    }

    public void quit() {
        mQuit = true;
        interrupt();
    }

    public boolean isRunning() {
        if ((!mQuit && isAlive())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        while (true) {

            if (mQuit) {
                return;
            }

            allRequests.forEach((topicTag, request) -> {

                RemoteResponse remoteResponse = null;
                try {

                    long interval =  System.currentTimeMillis() - MessageMonitor.getInstance().getRequestStatistics().getStartTimeMs();
                    if (interval > 0 && interval < request.getInterval()){
                        Thread.sleep((request.getInterval() - interval));
                        // log.info("interval:"+(request.getInterval() - interval));
                    }

                    MessageMonitor.getInstance().startRequest();
                    remoteResponse = client.perform(request);
                    MessageMonitor.getInstance().requestSuccess();

                } catch (Exception e) {

                    log.error("request-error", e);
                    MessageMonitor.getInstance().requestError(new RkMessageException(e));

                }

                if (remoteResponse != null && remoteResponse.getCode() == RemoteResponse.CODE_SUCCESS && remoteResponse.getContent() != null) {
                    MessageMonitor.getInstance().startPostMessage();

                    long msgSize = 0;
                    try {
                        Message message = new Message(this.mParser.rsbParse(remoteResponse.getContent()));

                        if (message.getEbikeList() != null) {
                            msgSize = message.getEbikeList().getSerializedSize();
                            //log.info(message.getEbikeList().toString());
                            messageDelivery.postMessage(topicTag, message);
                        }
                        MessageMonitor.getInstance().postMessageComplete();

                    } catch (Exception e) {

                        log.error("post-error", e);
                        MessageMonitor.getInstance().postMessageError(new RkMessageException(e));

                    }

                    MessageMonitor.getInstance().bodySize(msgSize);

                } else if (remoteResponse != null) {
                    log.error("post-error code:[" +remoteResponse.getCode() +"] content:"+ InputStreamUtil.convertStreamToString(remoteResponse.getContent()));
                }

            });


        }
    }


}
