package com.twolz.qiyi.dc.service.rmi;

import com.alibaba.dubbo.config.annotation.Service;
import com.twolz.qiyi.common.constant.CarConst;
import com.twolz.qiyi.common.core.CarCode;
import com.twolz.qiyi.common.exception.BizException;
import com.twolz.qiyi.rmi.service.ICarCmdRMIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;

/**
 * @author liuwei
 * 车辆命令控制适配器服务
 */
@Slf4j
@Component
@Service(interfaceClass = ICarCmdRMIService.class)
public class CarCmdRMIServiceImp implements ICarCmdRMIService {

	@Autowired
	private RokyCarCmdRMIServiceImp rokyCarCmdRMIServiceImp;
	
	@Override
	public void sendCmd(String tboxNo, int cmd) throws RemoteException {
		String boxNo = "";
		try {
			rokyCarCmdRMIServiceImp.sendCmd(boxNo, cmd);
		} catch (Exception e) {
			log.error("锐琪盒子控车失败！carNo:"+boxNo+",tboxNo:"+boxNo+",cmd:"+cmd,e);
			throw new BizException(CarCode.CAR_CMD_ERROR);
		}
		log.debug("控车结束！指令="+cmd+"中控编号="+boxNo);
	}

	@Override
	public void sendFindCmd(String tboxNo) throws RemoteException {
		sendCmd(tboxNo, CarConst.DEVICE_FIND);
	}

	@Override
	public void sendLockCmd(String tboxNo) throws RemoteException {
		sendCmd(tboxNo,CarConst.DEVICE_CLOSE);
	}

	@Override
	public void sendOpenCmd(String tboxNo) throws RemoteException {
		// TODO Auto-generated method stub
		sendCmd(tboxNo,CarConst.DEVICE_OPEN);
	}

	@Override
	public void sendOpenSitcushion(String tboxNo) throws RemoteException {
		sendCmd(tboxNo,CarConst.DEVICE_OPEN_SITCUSHION);
	}

}
