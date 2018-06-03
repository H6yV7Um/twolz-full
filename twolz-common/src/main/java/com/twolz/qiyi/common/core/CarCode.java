/**
 * 
 */
package com.twolz.qiyi.common.core;

/**
 * @author guocf
 * 车辆相关的错误码
 *  1、用户中心(10000~19999)
 *	2、车辆中心(20000~29999)
 *	3、账单中心(30000~39999)
 */
public class CarCode extends BaseResultCode{
    //车牌号不能为空
	public final static String PLATE_NO_IS_BLANK = "20001";

	//未找到车辆信息
	public final static String NOT_FOUND_CAR_INFO= "20012";

	public final static String CAR_CMD_ERROR= "20013";
	
}
