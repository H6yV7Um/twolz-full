package com.twolz.qiyi.common.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 
 * @author liuwei
 * 车辆实时信息DTO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarRealTimeDto implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 终端Id
	 */
	private String boxNo;
	/**
	 * 流水号
	 */
    private Integer info; 
    
    /**
     *厂商标记
     */
    private String fkey;

    /**
	 *上报时间
	 */
    private String rptTime;

	/**
	 * 经度
	 */
    private Double lng;

	/**
	 * 纬度
	 */
    private Double lat;

    /**
	 * 原始的经度
	 */
    private Double lngSrc;

    /**
	 * 原始的纬度
	 */
    private Double latSrc;
    
    /**
     * GSM 信号强 度百分比
     * 知豆 0-31
     */
    private Integer gsm;
	/**
	 * gps信号
	 */
    private Integer gps;

    /**
	 * 电源状态
	 */
    private Integer powerStatus; 
    
    /**
     * 电源电压值 单位：V
     * 知豆4轮 有效值范围：0~10*10（表示0V~10V），最小计量单元： 0.1V， “0xFF， 0xFE” 表示异常， “0xFF ，0xFF” 表示无效
     */
    private Float powerValue=10f;

    /**
	 * redis 更新时间
	 */
    private long upTime;

    /**
     * 车辆状态
     * Roky--->00：设防 01：撤防 02：驻车 03：骑行
     */
    private int carStatus;
    
	/**
	 * 车辆充电状态 1
	 * 0x01：停车充电； 0x02：行驶充电； 0x03：未充电状态； 0x04：充电完成； “ 0xFE” 表示异常，“ 0xFF” 表示无效
	 */
	private int vechicleCharge;

	/**
	 * 剩余电量 1，单位1%
	 * 有效值范围：0~100（表示0%~100%），最小计量单元：1%，“0xFE” 表示异常， “0xFF” 表示无效
	 */
	private Integer soc;

}
