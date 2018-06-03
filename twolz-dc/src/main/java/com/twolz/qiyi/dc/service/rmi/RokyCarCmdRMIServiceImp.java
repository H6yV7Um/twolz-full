package com.twolz.qiyi.dc.service.rmi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twolz.qiyi.common.constant.CarConst;
import com.twolz.qiyi.common.dto.EBikeDeviceDto;
import com.twolz.qiyi.common.dto.EBikeRsDO;
import com.twolz.qiyi.common.exception.BizException;
import com.twolz.qiyi.dc.dto.rokyinfo.SendMsg;
import com.twolz.qiyi.dc.dto.rokyinfo.SendMsgConfig;
import com.twolz.qiyi.dc.retrofit.LatestEbikesService;
import com.twolz.qiyi.dc.retrofit.RokyInfoService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * @author guocf
 * 瑞骑电单车控制服务实现类
 */
@Slf4j
@Component
public class RokyCarCmdRMIServiceImp {

	/**
	 * 指令-开锁
	 */
	public static final String CMD_OPEN="1005";
	/**
	 * 指令-关门
	 */
	public static final String CMD_CLOSE="1004";

	/**
	 * 指令-寻车
	 */
	public static final String CMD_FIND="1007";

	/**
	 * 指令-开座锁
	 */
	public static final String CMD_OPEN_SITCUSHION="1010";

//	public static final String CMD_GEAR="1000";


	@Autowired
    private RokyInfoService rokyInfoService;
    
    @Autowired
	LatestEbikesService latestEbikesService;

    @Autowired
	CarCmdRMIServiceImp carCmdRMIServiceImp;
    
	public void sendCmd(String tboxNo, int cmd) throws BizException {
		SendMsg sendMsg = new SendMsg();
		//转换命令
		String cmdInfo=this.getCmdInfo(cmd);
		sendMsg.setCommand(cmdInfo);
		SendMsgConfig config = new SendMsgConfig();
		config.setKey("FREQ");
		config.setValue("15");
		sendMsg.setConfig(config);
		log.info("锐祺中控 "+tboxNo+" 执行远程控车，控车指令 = "+cmd);
		try {
			ObjectMapper mapper = new ObjectMapper();
			String cmdStr = mapper.writeValueAsString(sendMsg);
			Call<ResponseBody> call = rokyInfoService.singleEbike(tboxNo,cmdStr);
			Response<ResponseBody> response= call.execute();
			if(response==null){
				log.error(tboxNo+"：远程控车失败，控车指令："+cmd+",失败原因:锐祺response为null");
				throw new RemoteException();
			}
			
			if (response.code()!=200){
				log.error(tboxNo+"远程控车失败，控车指令："+cmd+"，响应码为："+response.code());
				throw new RemoteException();
			}
		} catch (Exception e) {
			log.error(tboxNo+"：远程控车失败，控车指令："+cmd,e);
			throw new BizException();
		}
		
	}

//	public void sendGearCmd(String tboxNo,String gear) throws BizException {
//		SendMsg sendMsg = new SendMsg();
//		//转换命令
//		sendMsg.setCommand(CMD_GEAR);
//		SendMsgConfig config = new SendMsgConfig();
//		config.setKey("GEAR");
//		config.setValue(gear);
//		sendMsg.setConfig(config);
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//			String cmdStr = mapper.writeValueAsString(sendMsg);
//			Call<ResponseBody> call = rokyInfoService.singleEbike(tboxNo,cmdStr);
//			Response<ResponseBody> response= call.execute();
//			if(response==null){
//				log.error(tboxNo+"：远程限速失败，限速档位："+gear);
//				throw new RemoteException();
//			}
//
//			if (response.code()!=200){
//				throw new RemoteException();
//			}
//		} catch (Exception e) {
//			log.error(tboxNo+"：远程限速失败，限速档位："+gear,e);
//			throw new BizException();
//		}
//	}

	public void sendFindCmd(String tboxNo) throws RemoteException {
		sendCmd(tboxNo, CarConst.DEVICE_FIND);
	}

	public void sendLockCmd(String tboxNo) throws RemoteException {
		// TODO Auto-generated method stub
		sendCmd(tboxNo,CarConst.DEVICE_CLOSE);
	}

	public void sendOpenCmd(String tboxNo) throws RemoteException {
		// TODO Auto-generated method stub
		sendCmd(tboxNo,CarConst.DEVICE_OPEN);
	}

	public void sendOpenSitcushion(String tboxNo) throws RemoteException {
		// TODO Auto-generated method stub
		sendCmd(tboxNo,CarConst.DEVICE_OPEN_SITCUSHION);
	}


	private String getCmdInfo(int cmd){
		if(CarConst.DEVICE_OPEN==cmd){
			return CMD_OPEN;
		} else if(CarConst.DEVICE_CLOSE==cmd){
			return CMD_CLOSE;
		} else if(CarConst.DEVICE_FIND==cmd){
			return CMD_FIND;
		} else if(CarConst.DEVICE_OPEN_SITCUSHION==cmd){
			return CMD_OPEN_SITCUSHION;
		}
		return "";
	}

	public EBikeDeviceDto getCarRealTimeInfo(String tboxNo) {
		Call<EBikeRsDO<EBikeDeviceDto>> call = this.latestEbikesService.latestEbikes(tboxNo);
		Response<EBikeRsDO<EBikeDeviceDto>> response=null;
		EBikeRsDO<EBikeDeviceDto> devicesMsg=null;
		try {
			response = call.execute();
			if(response!=null){
				//成功返回
				if (response.code()==200){
					devicesMsg= response.body();
				}
			}
			
			if(devicesMsg!=null){
				EBikeDeviceDto data=devicesMsg.getResult();
				if(data==null) {
					data=devicesMsg.getData();
				}
				log.info("实时获取设备信息成功-0，tboxNo："+tboxNo);
				return data;
			}
		} catch (IOException e) {
			log.error("实时获取设备信息失败-1，tboxNo："+tboxNo,e);	
		}
		return null;
	}
}
