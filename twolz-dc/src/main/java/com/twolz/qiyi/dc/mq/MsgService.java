package com.twolz.qiyi.dc.mq;

/**
 * @author liuwei
 * 消息服务接口
 */
public interface MsgService {

	public static final String TOPIC_ROKY_BOX_RPT = "rokyTboxRpt";

	public static final String TEST_TOPIC = "testTopic";

	/**
	 * 发送消息接口
	 * @param msg   消息内容
	 */

	public void sendMsg(String topic, byte[] msg);

}
