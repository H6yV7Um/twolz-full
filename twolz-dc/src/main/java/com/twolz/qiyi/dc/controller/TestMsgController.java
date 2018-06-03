package com.twolz.qiyi.dc.controller;

import com.twolz.qiyi.common.core.ResultDO;
import com.twolz.qiyi.common.core.SysCode;
import com.twolz.qiyi.common.exception.BizException;
import com.twolz.qiyi.dc.mq.MsgService;
import com.twolz.qiyi.rmi.dto.TestDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * Created by liuwei
 * date 2017-05-16
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestMsgController {


    @Autowired
    MsgService msgService;

    @ApiOperation(value = "测试kafka消息", notes = "测试kafka消息")
    @PostMapping(value="/senMsg")
    public ResultDO<String> fileUpload(@ApiParam(name = "testDto", value = "测试消息") @RequestBody TestDto testDto) throws BizException{
        ResultDO result = new ResultDO();
        try {
            log.debug(" is in ");
            testDto.setUpdateTime(new Date());
            msgService.sendMsg(MsgService.TEST_TOPIC,SerializationUtils.serialize(testDto));
            result.setSuccess(true);
            result.setModule("发送成功");
        } catch (Exception e) {
            log.error("测试kafka消息出错",e);
            throw new BizException(SysCode.COMMON_SERVICE_ERROR);
        }
        return result;
    }


}
