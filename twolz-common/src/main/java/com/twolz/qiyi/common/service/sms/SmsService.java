package com.twolz.qiyi.common.service.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twolz.qiyi.common.config.CommonConfig;
import com.twolz.qiyi.common.constant.Constants;
import com.twolz.qiyi.common.core.BaseResultCode;
import com.twolz.qiyi.common.service.sms.ali.AliSmsService;
import com.twolz.qiyi.common.service.sms.ali.ISmsRequest;
import com.twolz.qiyi.common.service.sms.ali.SmsRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by liuwei
 * date 2017-03-23
 */
@Slf4j
@Service
public class SmsService {

    @Value("${system.common.sms-duration}")
    private int smsDuration;

    @Value("${system.common.sms-today-limit}")
    private int smsTodayLimit;

    @Value("${system.common.sms-valid-time}")
    private Integer smsValidTime;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AliSmsService aliSmsService;

    /**
     * 发送验证码短信接口
     * 每次时间间隔90s，每天每手机号发送不超过10次
     * @param mobile 手机号码、短信模版参数必须设置
     * @param randomCode 短信验证码code、短信模版参数必须设置
     * */
    public boolean sendCodeSMS(final String mobile,final String randomCode) throws Exception {
        ISmsRequest smsRequest = new SmsRequest();
        smsRequest.setRecNum(mobile);
        smsRequest.setSmsTemplateCode("SMS_120550240");
        String smsParam = "{'code':'"+ randomCode+"'}";
        smsRequest.setSmsParam(smsParam);
        try {
            return aliSmsService.sendSms(smsRequest);
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 发送语音验证码接口
     * 每次时间间隔90s，每天每手机号发送不超过10次
     * @param mobile 手机号码、短信模版参数必须设置
     * @param randomCode 短信验证码code、短信模版参数必须设置
     * */
    public  boolean sendCodeTts(final String mobile,final String randomCode) throws Exception {
//        ITtsRequest ttsRequest = new RandomCodeTtsRequest();
//        ttsRequest.setCalledNum(phone);
//        ttsRequest.setTtsParam(randomCode);
//        return aliSmsService.sendTts(ttsRequest);
        return true;
    }

    /**
     * 短信验证码是否正确
     * @param mobile
     * @param smsCaptcha
     *
     * */
    public String confirmSmsCaptcha(final String mobile,final String smsCaptcha) {
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        if (StringUtils.isEmpty(smsCaptcha)) {
            return BaseResultCode.COMMON_VERIFY_CODE_ERROR;
        }
        try {
            //判断验证码是否正确
            String serverCaptcha = valueOperations.get(Constants.SMS_TITLE+mobile);
            if(null == serverCaptcha){
                return BaseResultCode.COMMON_VERIFY_CODE_ERROR;
            }
            ObjectMapper mapper = new ObjectMapper();
            SmsStatus sum = mapper.readValue(serverCaptcha,SmsStatus.class);
            if (StringUtils.isEmpty(sum.getLastRandom()) || !sum.getLastRandom().equals(smsCaptcha)){
                return BaseResultCode.COMMON_VERIFY_CODE_ERROR;
            }
            //判断当前短信是否超出有效期，并且短信是否没有被使用过
            Long smsInterval =  (System.currentTimeMillis()-sum.getLast())/1000;
            int smsState = sum.getState();
//            Integer smsValidTime = CommonConfig.getInstance().getSmsValidTime();
            if (smsValidTime > smsInterval && smsState == 1){
                sum.setState(0);
                valueOperations.set(Constants.SMS_TITLE+sum.getMobile(),mapper.writeValueAsString(sum));
                return BaseResultCode.TRUE;
            } else {
                return BaseResultCode.SMS_OUT_VALID_TIME;
            }
        } catch (Exception e) {
            return BaseResultCode.COMMON_SERVICE_ERROR;
        }
    }

    public String allownSend(String mobile) throws RuntimeException{
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        SmsStatus sum ;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonStr = valueOperations.get(Constants.SMS_TITLE+mobile);
            if (StringUtils.isBlank(jsonStr)){
                return BaseResultCode.TRUE;
            } else {
                sum = mapper.readValue(jsonStr,SmsStatus.class);
                if(sum == null) {
                    return BaseResultCode.TRUE;
                }
            }
            long last = sum.getLast();
            int todayCnt = sum.getTodayCnt();
            //两次短信间隔小于90S 或当天发送大于10，限制用户短信发送
//            Integer smsDuration = CommonConfig.getInstance().getSmsDuration();
//            Integer smsTodayLimit = CommonConfig.getInstance().getSmsTodayLimit();
            if(System.currentTimeMillis()-last < smsDuration*1000) {
                throw new RuntimeException(BaseResultCode.SMS_DURATION_ERROR);
            } else if(isSameDay(last) && todayCnt >=smsTodayLimit) {
                throw new RuntimeException(BaseResultCode.SMS_TODAY_LIMIT_ERROR);
            }
        } catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        } catch (Exception e){
            return BaseResultCode.SEND_SMS_ERROR;
        }
        return BaseResultCode.TRUE;
    }

    /**
     *
     * @param smsStatus  mobile smsParam必须设置
     * @return
     */
    public String updateSum(SmsStatus smsStatus){
        assert smsStatus != null && StringUtils.isNotEmpty(smsStatus.getMobile());
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        String json  = valueOperations.get(Constants.SMS_TITLE+smsStatus.getMobile());
        SmsStatus sum = null;
        try {
            if(StringUtils.isNotBlank(json)) {
                ObjectMapper mapper = new ObjectMapper();
                sum = mapper.readValue(json,SmsStatus.class);
                if(!isSameDay(sum.getLast())){
                    sum.setTodayCnt(0);
                }
            } else {
                sum = new SmsStatus(smsStatus.getMobile());
            }
            sum.setLast(System.currentTimeMillis());
            sum.setMobile(smsStatus.getMobile());
            sum.setTodayCnt(sum.getTodayCnt()+1);
            sum.setLastRandom(smsStatus.getLastRandom());
            sum.setState(smsStatus.getState());
            ObjectMapper mapper = new ObjectMapper();
            valueOperations.set(Constants.SMS_TITLE+sum.getMobile(),mapper.writeValueAsString(sum));
        } catch (Exception e){
            return BaseResultCode.SEND_SMS_ERROR;
        }
        return BaseResultCode.TRUE;
    }

    private boolean isSameDay(long day){
        Date date = new Date(day);
        Date current = new Date(System.currentTimeMillis());
        return DateUtils.isSameDay(date,current);
    }
}
