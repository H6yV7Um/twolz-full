package com.twolz.qiyi.api.web.controller;

import com.alibaba.boot.dubbo.annotation.DubboConsumer;
import com.alibaba.dubbo.config.annotation.Reference;
import com.twolz.qiyi.common.core.ResultDO;
import com.twolz.qiyi.common.core.SysCode;
import com.twolz.qiyi.common.exception.BizException;
import com.twolz.qiyi.rmi.dto.TestDto;
import com.twolz.qiyi.rmi.service.ITestSendMsgService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * Created by liuwei
 * date 2017-05-16
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestMsgController {


    @DubboConsumer(check = false,timeout = 15000)
    private ITestSendMsgService testSendMsgService;

    @ApiOperation(value = "远程测试kafka消息", notes = "测试kafka消息")
    @PostMapping(value="/senMsg")
    public ResultDO<String> sendMsg(@ApiParam(name = "testDto", value = "测试消息") @RequestBody TestDto testDto) throws BizException{
        ResultDO result = new ResultDO();
        try {
            testDto.setUpdateTime(new Date());
            testSendMsgService.sendMsg(testDto);
            result.setSuccess(true);
            result.setModule("发送远程消息成功");
        } catch (Exception e) {
            log.error("发送远程消息出错",e);
            throw new BizException(SysCode.COMMON_SERVICE_ERROR);
        }
        return result;
    }


}
