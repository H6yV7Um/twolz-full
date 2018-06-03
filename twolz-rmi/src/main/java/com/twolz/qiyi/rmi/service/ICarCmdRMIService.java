package com.twolz.qiyi.rmi.service;

import com.twolz.qiyi.common.exception.BizException;

import java.rmi.RemoteException;

/**
 * @author liuwei
 * 车辆控制命令服务接口
 */
public interface ICarCmdRMIService {

	/**
	 * 向车辆发送命令
	 * @param boxNo
	 * @param cmd
	 * @throws BizException
	 */
	public void sendCmd(String boxNo, int cmd) throws RemoteException;

	/**
	 * 发送寻车命令
	 * @param tboxNo
	 * @throws BizException
	 */
	public void sendFindCmd(String tboxNo)throws RemoteException;

	/**
	 * 发送锁车命令，锁车或关门时调用
	 * @param tboxNo
	 * @throws BizException
	 */
	public void sendLockCmd(String tboxNo)throws RemoteException;

	/**
	 * 发送打开车门命令
	 * @param tboxNo
	 * @throws BizException
	 */
	public void sendOpenCmd(String tboxNo)throws RemoteException;

	/**
	 * 发送打开车座锁命令
	 * @param tboxNo
	 * @throws BizException
	 */
	public void sendOpenSitcushion(String tboxNo)throws RemoteException;

	/**
	 * TBox厂商标识
	 */
	public enum TBoxFactory {
	    EBIKE_ROKY("roky","锐祺");

		private String key;
	    
	    private String text;
	    
	    private TBoxFactory(String key,String text) {
	        this.key=key;
	        this.text=text;
	    }

		public String getKey() {
			return key;
		}

		public String getText() {
			return text;
		}

	}

	
}
