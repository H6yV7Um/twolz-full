/**
 * 
 */
package com.twolz.qiyi.common.util.gis;

import lombok.Data;

/**
 * @author liuwei
 * 地理位置坐标对象
 */
@Data
public class GisPoint implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private double lng;
	
	private double lat;

	public GisPoint(double lng, double lat) {
		super();
		this.lng = lng;
		this.lat = lat;
	}
	
	
}
