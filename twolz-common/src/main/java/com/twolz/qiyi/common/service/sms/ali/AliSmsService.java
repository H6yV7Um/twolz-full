package com.twolz.qiyi.common.service.sms.ali;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * Created by liuwei
 * date 2017-06-20
 */
@Slf4j
@Service
public class AliSmsService {
    /**
     * 发送短信接口
     * 每次时间间隔90s，每天每手机号发送不超过10次
     * @param smsRequest 手机号码、短信模版参数必须设置
     * */
    public boolean sendSms(ISmsRequest smsRequest) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        AliSmsConfig aliSmsConfig = AliSmsFactory.getInstance("ALIDAYU");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliSmsConfig.getAppkey(), aliSmsConfig.getSecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", aliSmsConfig.getProduct(), aliSmsConfig.getDomain());
        IAcsClient acsClient = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(smsRequest.getRecNum());
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(smsRequest.getSmsFreeSignName());
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(smsRequest.getSmsTemplateCode());
        //可选:模板中的变量替换JSON串
        request.setTemplateParam(smsRequest.getSmsParam());

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("yourOutId");
        try {
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            return true;
        } catch (ClientException ex) {
            throw ex;
        }
    }

}

interface AliSmsConfig {
    public String getAppkey();
    public String getSecret();
    public String getProduct();
    public String getDomain();
}


class AliSmsFactory{
    public static final String ALIDAYU="ALIDAYU";
    private static AliSmsConfig aliSmsConfig;
    public static AliSmsConfig getInstance(String whichSmsProvider){
        assert StringUtils.isNoneEmpty(whichSmsProvider);
        if(whichSmsProvider.equals(ALIDAYU)){
            if (aliSmsConfig == null) {
                aliSmsConfig = new AliDayuSmsConfig();
            }
        }
        return aliSmsConfig;
    }
    static final class AliDayuSmsConfig implements AliSmsConfig {
        @Getter
        private String appkey = "LTAIPc6kF21K5EfO";
        @Getter
        private String secret = "oUzB2jB5u6JeRNhemRFsE05T2CqKMl";
        @Getter
        private String product = "Dysmsapi";
        @Getter
        private String domain = "dysmsapi.aliyuncs.com";
    }

}


