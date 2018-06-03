package com.twolz.qiyi.common.dto;

import lombok.Data;

/**
 * @author liuwei
 * 电单车设备信息DTO

"baseStation": "58783:63232:8:63692",
"batteryStatus": "0",
"batteryTemperature": "23",
"carFault": "000000080000F0000000",
"carStatus": "00",
"chargeCount": "0",
"current": "0",
"gpsBat": "6",
"gpsSignal": "0",
"gsmSignal": "4",
"lastReportTime": "2017-08-09 19:21:10",
"lat": "31.548845643007557",
"locationType": "0",
"lon": "120.24090764121786",
"soc": "105",
"soh": "105",
"speed": "0",
"t12Time": "[2017-08-09 19:20:55,0,R1,860124000234374,B00G250BB6,T12,1]",
"t3Time": "[2017-08-09 19:21:10,0,R1,860124000234374,B00G250BB6,T3,,,,,,,,,,,,,,,,,,]",
"travelMiles": "0",
"turn": "0",
"voltage": "5095",
"warn": "00"

 */
@Data
public class EBikeDeviceDto implements java.io.Serializable {

	private String baseStation;
	private String batteryStatus;
	private String batteryTemperature;
	private String carFault;
	private String carStatus;
	private String chargeCount;
	private String current;
	private String gpsBat;
	private String gpsSignal;
	private String gsmSignal;
	private String lastReportTime;
	private String lat;
	private String locationType;
	private String lon;
	private String soc;
	private String soh;
	private String speed;
	private String travelMiles;
	private String turn;
	private String voltage;
	private String warn;
}
