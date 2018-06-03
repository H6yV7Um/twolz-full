package com.twolz.qiyi.common.service.sms.ali;

import lombok.Getter;
import lombok.Setter;


/**
 * Created by Steven@chinaxiaopu.com on 10/13/16.
 */
public abstract class TtsRequest {
    /**
     * 公共回传参数，在“消息返回”中会透传回该参数；举例：用户可以传入自己下级的会员ID，在消息返回时，该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用
     */
    @Getter
    private String extend;

    /**
     * 被叫号码，支持国内手机号与固话号码
     */
    @Setter
    @Getter
    private String calledNum;

    /**
     * 被叫号显，传入的显示号码必须是阿里大于“管理中心-号码管理”中申请
     */
    protected String calledShowNum;

    /**
     * 文本转语音（TTS）模板变量，传参规则{"key"："value"}，key的名字须和TTS模板中的变量名一致，多个变量之间以逗号隔开，示例：{"name":"11","code":"1234"}
     */
    @Getter
    @Setter
    private String ttsParam;

    /**
     * TTS模板ID，传入的模板必须是在阿里大于“管理中心-语音TTS模板管理”中的可用模板
     */
    protected String ttsCode;

}