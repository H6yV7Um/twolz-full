package com.twolz.qiyi.dc.service.rmi;

import com.alibaba.dubbo.config.annotation.Service;
import com.twolz.qiyi.dc.mq.MsgService;
import com.twolz.qiyi.rmi.dto.TestDto;
import com.twolz.qiyi.rmi.service.ITestSendMsgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.util.Date;

/**
 * @author liuwei
 * @date 2018-06-03
 */
@Slf4j
@Component
@Service(interfaceClass = ITestSendMsgService.class)
public class TestSendMsgServiceImp implements ITestSendMsgService{

    @Autowired
    MsgService msgService;

    @Override
    public void sendMsg(TestDto testDto) throws RemoteException {
        log.debug(" rmi is in ");
        testDto.setUpdateTime(new Date());
        msgService.sendMsg(MsgService.TEST_TOPIC, SerializationUtils.serialize(testDto));
        log.debug(" rmi send is ok ");
    }
}
