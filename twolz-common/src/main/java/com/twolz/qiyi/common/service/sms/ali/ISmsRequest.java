package com.twolz.qiyi.common.service.sms.ali;


import java.io.Serializable;

/**
 * Created by liuwei on 10/13/16.
 */
public interface ISmsRequest extends Serializable{

    public void setRecNum(String recNum);

    public void setSmsParam(String smsParam);

    public void setSmsTemplateCode(String smsTemplateCode);

    public String getExtend();

    public String getRecNum();

    public String getSmsFreeSignName();

    public String getSmsParam();

    public String getSmsTemplateCode();

    public String getSmsType();
}
