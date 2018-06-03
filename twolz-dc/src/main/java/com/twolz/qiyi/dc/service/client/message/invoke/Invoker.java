package com.twolz.qiyi.dc.service.client.message.invoke;


import com.twolz.qiyi.dc.service.client.message.config.MessageConfig;
import com.twolz.qiyi.dc.service.client.model.RemoteResponse;

import java.io.IOException;

/**
 * Created by yuanzhijian on 2017/2/21.
 */
public interface Invoker {

    public RemoteResponse perform(MessageConfig request) throws IOException;

}
