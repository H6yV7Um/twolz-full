package com.twolz.qiyi.api.web.controller;

import com.twolz.qiyi.common.core.BaseResultCode;
import com.twolz.qiyi.common.core.ResultDO;
import com.twolz.qiyi.common.exception.BizException;
import com.twolz.qiyi.common.service.sms.SmsService;
import com.twolz.qiyi.common.service.sms.SmsStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by liuwei
 * date 2017-03-28
 */
@Slf4j
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    SmsService smsService;

    @ApiOperation(value = "发送验证码短信",notes = "发送注册验证码短信")
    @RequestMapping(value = "v1/sendSmsCode",method = RequestMethod.POST)
    public ResultDO<String> sendSmsCode(
            @ApiParam(name = "mobile", value = "手机号码") @RequestParam String mobile) throws BizException {
        ResultDO<String> result = new ResultDO<>();
        assert StringUtils.isNotEmpty(mobile);
        try{
            String resultCode = smsService.allownSend(mobile);
            if(resultCode != BaseResultCode.TRUE){
                throw new BizException(BaseResultCode.SEND_SMS_ERROR);
            }
        } catch (RuntimeException e) {
            log.error("验证短信：",e);
            throw new BizException(e.getMessage());
        }
        try {
            String randomCode = RandomStringUtils.randomNumeric(6);
            if (smsService.sendCodeSMS(mobile,randomCode)){
                SmsStatus smsStatus = new SmsStatus(mobile);
                smsStatus.setLastRandom(String.valueOf(randomCode));
                String resultCode = smsService.updateSum(smsStatus);
                if(resultCode != BaseResultCode.TRUE){
                    throw new BizException(BaseResultCode.SEND_SMS_ERROR);
                } else {
                    //TODO 测试返回验证码
                    result.setModule(randomCode);

                    result.setSuccess(true);
                }
            } else {
                log.info(mobile+":发送注册短信失败");
                throw new BizException(BaseResultCode.SEND_SMS_ERROR);
            }

        }
        catch (Exception e) {
            log.error("发送短信失败：",e);
            throw new BizException(BaseResultCode.SEND_SMS_ERROR);
        }
        return result;
    }

    @ApiOperation(value = "发送注册语音验证码",notes = "发送注册语音验证码")
    @RequestMapping(value = "v1/sendTtsCode",method = RequestMethod.POST)
    public ResultDO<String> sendTtsCode(
            @ApiParam(name = "mobile", value = "手机号码") @RequestParam String mobile) throws BizException {
        ResultDO<String> result = new ResultDO<>();
        assert StringUtils.isNotEmpty(mobile);
        try{
            String resultCode = smsService.allownSend(mobile);
            if(resultCode != BaseResultCode.TRUE){
                throw new BizException(BaseResultCode.SEND_SMS_ERROR);
            }
        } catch (RuntimeException e) {
            log.error("验证语音短信：",e);
            throw new BizException(e.getMessage());
        }
        try {
            String randomCode = RandomStringUtils.randomNumeric(6);
            if (smsService.sendCodeTts(mobile,randomCode)){
                log.debug(mobile+"发送语音短信成功");
                SmsStatus smsStatus = new SmsStatus(mobile);
                smsStatus.setLastRandom(String.valueOf(randomCode));
                String resultCode = smsService.updateSum(smsStatus);
                if(resultCode != BaseResultCode.TRUE){
                    log.debug("更新发送短信状态");
                    throw new BizException(BaseResultCode.SEND_SMS_ERROR);
                } else {
                    result.setSuccess(true);
                }
            } else {
                log.info(mobile+"发送短信失败");
                throw new BizException(BaseResultCode.SEND_SMS_ERROR);
            }

        }
//        catch (Exception e) {
//            log.error("发送短信失败：",e);
//            if(e.getErrCode().equals("isv.BUSINESS_LIMIT_CONTRO")){
//                log.error("发送短信失败：",e);
//                throw new BizException(BaseResultCode.SMS_DURATION_ERROR);
//            } else {
//                throw new BizException(BaseResultCode.SEND_SMS_ERROR);
//            }
//        }
        catch (Exception e) {
            log.error("发送语音短信失败：",e);
            throw new BizException(BaseResultCode.SEND_SMS_ERROR);
        }
        return result;
    }

//    @ApiOperation(value = "发送短信模板",notes = "发送短信模板")
//    @RequestMapping(value = "v1/smsMsg",method = RequestMethod.POST)
//    public ResultDO<String> smsMsg(@ApiParam(name = "phone", value = "手机号码") @RequestParam Long phone,
//                                   @ApiParam(name = "type", value = "短信类型，1：违章，2：回收，3：押金申请，4：押金通过，5，押金未通过，6：用车") @RequestParam Integer type) throws BizException {
//        ResultDO<String> result = new ResultDO<>();
//        try {
//            System.out.println(paramService.getValue(SysConst.SERVICE_TELEPHONE_NUMBER));
//            log.debug("sms ->"+CommonConfig.getInstance().getIllegalSms());
//            log.debug(MessageFormat.format(CommonConfig.getInstance().getIllegalSms(),new Object[]{"沪A-1234656","2017-10-01 10:00","凌空soho","逆向行驶","4001234567"}));
//            if(type==1){
//                String smsParam = "{'carNum':'沪A-1234656','date':'2017-10-01 10:00','address':'凌空soho','desc':'逆向行驶','serviceNumber':'4001234567'}";
//                smsService.sendIllegalSms(phone.toString(),smsParam);
//            } else if(type==2){
//                smsService.sendReclaimSms(phone.toString());
//            } else if(type==3){
//                smsService.sendRefundSms(phone.toString());
//            } else if(type==4){
//                smsService.sendRefundOKSms(phone.toString(),"2017-06-01",paramService.getValue(SysConst.SERVICE_TELEPHONE_NUMBER));
//            } else if(type==5){
//                smsService.sendRefundNOSms(phone.toString(),"2017-06-01",paramService.getValue(SysConst.SERVICE_TELEPHONE_NUMBER));
//            } else if(type==6){
//                smsService.sendUseCarSMS(phone.toString(),"沪A10007",paramService.getValue(SysConst.SERVICE_TELEPHONE_NUMBER));
//            }
//            result.setSuccess(true);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return result;
//    }

}
