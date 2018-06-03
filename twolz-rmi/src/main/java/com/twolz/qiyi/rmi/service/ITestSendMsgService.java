package com.twolz.qiyi.rmi.service;

import com.twolz.qiyi.rmi.dto.TestDto;

import java.rmi.RemoteException;

/**
 * @author liuwei
 * @date 2018-06-03
 */
public interface ITestSendMsgService {

    void sendMsg(TestDto testDto) throws RemoteException;
}
