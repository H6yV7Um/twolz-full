package com.twolz.qiyi.common.core;


/**
 * @ClassName: BaseResultCode
 * @Description: TODO 基础返回码
 * @date 2016年3月11日 上午10:14:38
 *
 *
 */
public abstract class BaseResultCode {

	//操作成功！
	public final static String TRUE = "0000";
	//验证码输入有误，请重新输入
	public final static String COMMON_VERIFY_CODE_ERROR = "0001";
	//短信发送失败
	public final static String SEND_SMS_ERROR = "0002";
	//验证码已失效，请重新获取验证码
	public final static String SMS_OUT_VALID_TIME = "0003";
	//短信发送间隔时间太短
	public final static String SMS_DURATION_ERROR = "0005";
	//单日短信发送次数超过10条
	public final static String SMS_TODAY_LIMIT_ERROR = "0006";
	//数据不存在！
	public final static String COMMON_NO_DATA = "0007";
	//发生了未知错误，请重新操作或者联系客服人员!
	public final static String COMMON_SERVICE_ERROR = "0099";
}
