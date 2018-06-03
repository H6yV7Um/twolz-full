package com.twolz.qiyi.dc.service.client;

import com.google.gson.Gson;
import com.googlecode.protobuf.format.JsonFormat;
import com.twolz.qiyi.common.constant.CarConst;
import com.twolz.qiyi.common.dto.CarRealTimeDto;
import com.twolz.qiyi.common.dto.EBikeDeviceDto;
import com.twolz.qiyi.common.util.DateTool;
import com.twolz.qiyi.common.util.gis.BDLocation;
import com.twolz.qiyi.common.util.gis.GlobalTool;
import com.twolz.qiyi.dc.dto.rokyinfo.*;
import com.twolz.qiyi.dc.mongo.RokyinfoBoxMsgRepository;
import com.twolz.qiyi.dc.mq.MsgService;
import com.twolz.qiyi.rmi.service.ICarCmdRMIService;
import com.twolz.qiyi.dc.service.CarRealTimeInfoCacheService;
import com.twolz.qiyi.dc.service.rmi.RokyCarCmdRMIServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by liuwei
 * date 2017-08-15
 */
@Slf4j
@Service
public class RokyinfoBoxConsumerService {

    private static final String SN_PREFIX = "_";

    @Autowired
    RokyinfoBoxMsgRepository rokyinfoBoxMsgRepository;

    @Autowired
    RokyCarCmdRMIServiceImp rokyCarCmdRMIServiceImp;

    @Autowired
    CarRealTimeInfoCacheService carRealTimeInfoCacheService;


    @KafkaListener(topics = MsgService.TOPIC_ROKY_BOX_RPT)
    public void processMessage(byte[] msg) {
        try {

            EbikeListProtos.Ebike ebike = EbikeListProtos.Ebike.parseFrom(msg);

            Gson gson = new Gson();
            String ebikeStr = JsonFormat.printToString(ebike);
            Ebike ebikeJson = gson.fromJson(ebikeStr, Ebike.class);

            if(ebike.getDcu()!=null && ebike.getDcu().getGSMRSSI()!=-1){
                if(ebikeJson.getDcu()==null){
                    Dcu dcu = new Dcu();
                    dcu.setGSMRSSI(ebike.getDcu().getGSMRSSI());
                } else {
                    ebikeJson.getDcu().setGSMRSSI(ebike.getDcu().getGSMRSSI());
                }
            }
            log.debug("-->消费锐琪拉取原始数据："+ebike.toString());
            log.debug("-->消费锐琪拉取转换数据："+new Gson().toJson(ebikeJson,Ebike.class));

            //同步车辆信息到redis中
            String sn ="";
            try {
                sn = ebike.getCcu().getSN();
                CarRealTimeDto carRealTimeDto = changeRokyRptInfo(ebike);

                carRealTimeInfoCacheService.putObject(sn, carRealTimeDto);
                carRealTimeInfoCacheService.putLastObject(sn, carRealTimeDto);

                //如果获取不到车辆位置信息或者获取到位置信息为0或者-1，则取redis中最后一次有效数据
                if(ebike.getLbs()==null || ebike.getLbs().getLat()==0 || ebike.getLbs().getLon()==0 || ebike.getLbs().getLat()==-1 || ebike.getLbs().getLon()==-1){
                    log.info("同步锐琪车辆最新数据到Redis中[中控编号="+sn+"]");
                    Lbs lbs = new Lbs();
                    lbs.setLat(carRealTimeDto.getLatSrc());
                    lbs.setLon(carRealTimeDto.getLngSrc());
                    ebikeJson.setLbs(lbs);
                }
            } catch (Exception e) {
                log.error("同步锐琪车辆最新数据到Redis中失败[中控编号="+sn+"]：",e);
            }

            RokyinfoBoxRptMessage boxRptMessage = new RokyinfoBoxRptMessage();
            boxRptMessage.setId(ebike.getCcu().getSN()+SN_PREFIX+ebike.getReportTime());
            boxRptMessage.setEbike(ebikeJson);
            boxRptMessage.setSendTime(new Date());
            rokyinfoBoxMsgRepository.save(boxRptMessage);
            log.info("---->>消费锐琪拉取数据完毕[中控编号="+sn+"]");
        } catch (Exception e) {
            log.error("消费锐琪拉取数据失败：",e);
        }
    }

    /**
     * 转换锐琪上报信息
     * @return
     */
    private CarRealTimeDto changeRokyRptInfo(EbikeListProtos.Ebike ebike){
        CarRealTimeDto carInfo=new CarRealTimeDto();
        carInfo.setFkey(ICarCmdRMIService.TBoxFactory.EBIKE_ROKY.getKey());
        carInfo.setPowerStatus(-9);
        carInfo.setUpTime(System.currentTimeMillis());

        if(ebike.getDcu()!=null && ebike.getDcu().getGSMRSSI()!=-1){
            carInfo.setGsm(this.changeGsm(ebike.getDcu().getGSMRSSI()));
        }

        CarRealTimeDto carLastDto = null;
        boolean falg = true;
        int status = ebike.getStatus();
        if(status == -1){
            if (carLastDto==null ){
                carLastDto = carRealTimeInfoCacheService.getLastCar(ebike.getCcu().getSN());
                falg = false;
                if (carLastDto!=null) {
                    carInfo.setCarStatus(carLastDto.getCarStatus());
                }
            }
        } else {
            carInfo.setCarStatus(status);
        }
        if(ebike.getLbs()==null || ebike.getLbs().getLat()==0 || ebike.getLbs().getLon()==0 || ebike.getLbs().getLat()==-1 || ebike.getLbs().getLon()==-1){
            //查询最后一次redis数据
            if (falg){
                carLastDto = carRealTimeInfoCacheService.getLastCar(ebike.getCcu().getSN());
            }
            if (carLastDto==null || carLastDto.getLatSrc()==0 || carLastDto.getLatSrc()==0 || carLastDto.getLngSrc()==-1 || carLastDto.getLngSrc()!=-1){
                //如果上一次缓存中车辆信息位置也不准，则调用锐琪车辆最新信息接口
                EBikeDeviceDto device = rokyCarCmdRMIServiceImp.getCarRealTimeInfo(ebike.getCcu().getSN());
                carInfo.setLatSrc(Double.parseDouble(device.getLat()));
                carInfo.setLngSrc(Double.parseDouble(device.getLon()));

                //84坐标转GCJ坐标
                BDLocation bdLocation=new BDLocation(carInfo.getLngSrc(),carInfo.getLatSrc());
                bdLocation=GlobalTool.WGS84_to_GCJ02(bdLocation);
                carInfo.setLng(bdLocation.getLongitude());
                carInfo.setLat(bdLocation.getLatitude());
            } else {
                carInfo.setLatSrc(carLastDto.getLatSrc());
                carInfo.setLngSrc(carLastDto.getLngSrc());

                carInfo.setLat(carLastDto.getLat());
                carInfo.setLng(carLastDto.getLng());
            }
            log.info("锐琪最后一次位置数据[中控编号="+ebike.getCcu().getSN()+"],lat:"+carInfo.getLatSrc()+",lng:"+carInfo.getLngSrc());

        } else {
            carInfo.setLatSrc(ebike.getLbs().getLat());
            carInfo.setLngSrc(ebike.getLbs().getLon());
            //84坐标转GCJ坐标
            BDLocation bdLocation=new BDLocation(carInfo.getLngSrc(),carInfo.getLatSrc());
            bdLocation= GlobalTool.WGS84_to_GCJ02(bdLocation);
            carInfo.setLng(bdLocation.getLongitude());
            carInfo.setLat(bdLocation.getLatitude());

        }
        //电压
        //int powerValue=ebike.getVoltage();
        //carInfo.setPowerValue(powerValue*10);
        
        float powerValue=(float)(ebike.getVoltage()/100);
        carInfo.setPowerValue(powerValue);
        
        //根据电压计算剩余电量
        int soc=this.getSocByPower(powerValue);
        carInfo.setSoc(soc);
        //上报时间
        carInfo.setRptTime(DateFormatUtils.format(DateTool.strToDate(ebike.getReportTime(),DateTool.DATE_TIME_Sec),DateTool.DATE_TIME_SORT));
        return carInfo;
    }

    /**
     * 将Roky上报上来的GSM信号转换成我们统一的100分制表示
     * @param rokyGsmSrc
     * @return
     */
    public int changeGsm(Integer rokyGsmSrc){
    	if(rokyGsmSrc==null){
    		return -1;
    	}

    	int ourGsm=rokyGsmSrc*20;
        if(ourGsm<0){
            return 0;
        }

    	if(ourGsm>100){
    		return 100;
    	}

    	return ourGsm;
    }
    
    /**
     * 根据电压计算剩余电量
     * @param powerValue
     * @return
     */
    private int getSocByPower(Float powerValue){
    	if(powerValue==null){
    		return 0;
    	}
    	Float pv=powerValue;
    	if(pv> CarConst.VOLTAGE_MAX_ROKY){
			pv=CarConst.VOLTAGE_MAX_ROKY;
		}
		if(pv<CarConst.VOLTAGE_MIN_ROKY){
			pv=CarConst.VOLTAGE_MIN_ROKY;
		}
		
		float fenMu=CarConst.VOLTAGE_MAX_ROKY-CarConst.VOLTAGE_MIN_ROKY;
		float chaInt=pv-CarConst.VOLTAGE_MIN_ROKY;
		
		double pvPer =chaInt/ fenMu;
		Double socSrc=pvPer*100;
		int soc=socSrc.intValue();
		return soc;
    }
    
}
