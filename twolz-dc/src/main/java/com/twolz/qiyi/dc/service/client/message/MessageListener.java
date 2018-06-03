package com.twolz.qiyi.dc.service.client.message;

import com.twolz.qiyi.dc.service.client.model.Message;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public interface MessageListener {
    void onMessage(Message message) throws Exception;
}
