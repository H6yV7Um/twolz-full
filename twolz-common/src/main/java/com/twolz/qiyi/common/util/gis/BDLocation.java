/**
 * 
 */
package com.twolz.qiyi.common.util.gis;

/**
 * @author Administrator
 *
 */
public class BDLocation {

	private double longitude;
	
	private double latitude;
	
	/**
	 * 
	 */
	public BDLocation() {
		// TODO Auto-generated constructor stub
	}

	
	
	public BDLocation(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}



	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	

}
