package com.twolz.qiyi.common.service.sms.ali;


import java.io.Serializable;

/**
 * Created by Steven@chinaxiaopu.com on 10/13/16.
 */
public interface ITtsRequest extends Serializable{

    public void setCalledNum(String recNum);

    public void setTtsParam(String random);

    public void setSmsTemplateCode(String smsTemplateCode);

    public String getExtend();

    public String getCalledShowNum();

    public String getCalledNum();

    public String getTtsParam();

    public String getTtsCode();

}
