package com.twolz.qiyi.common.service.sms.ali;

import org.apache.commons.lang3.StringUtils;

/**
 * 验证码短信
 * Created by liuwei
 * date 2017-03-23
 */
public class SmsRequest extends AbstractSmsRequest implements ISmsRequest{

    private String smsParam;

    private String smsTemplateCode;

    @Override
    public void setRecNum(String recNum) {
        super.setRecNum(recNum);
    }

    @Override
    public String getExtend() {
        return null;
    }

    @Override
    public String getRecNum() {
        assert StringUtils.isNoneEmpty(super.getRecNum());
        return super.getRecNum();
    }

    @Override
    public String getSmsParam() {
        assert this.smsParam !=null;
        return smsParam;
    }

    @Override
    public void setSmsParam(String smsParam) {
        this.smsParam =smsParam;
    }

    @Override
    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode =smsTemplateCode;
    }

    @Override
    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }

    @Override
    public String getSmsFreeSignName() {
        return "骑大师";
    }

}
