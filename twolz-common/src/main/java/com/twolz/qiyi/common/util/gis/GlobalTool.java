package com.twolz.qiyi.common.util.gis;


/**
 * Created by liuxi on 2017/4/12.
 */

public class GlobalTool {
    public final static double a = 6378245.0;
    public final static double ee = 0.00669342162296594323;
    public final static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    public static BDLocation BAIDU_to_WGS84(BDLocation bdLocation) {
        double x = bdLocation.getLongitude() - 0.0065;
        double y = bdLocation.getLatitude() - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        bdLocation.setLongitude(z * Math.cos(theta));
        bdLocation.setLatitude(z * Math.sin(theta));
        return GCJ02_to_WGS84(bdLocation);
    }

    public static BDLocation GCJ02_to_WGS84(BDLocation bdLocation) {
        BDLocation tmpLocation = new BDLocation();
        tmpLocation.setLatitude(bdLocation.getLatitude());
        tmpLocation.setLongitude(bdLocation.getLongitude());
        BDLocation tmpLatLng = WGS84_to_GCJ02(tmpLocation);
        double tmpLat = 2 * bdLocation.getLatitude() - tmpLatLng.getLatitude();
        double tmpLng = 2 * bdLocation.getLongitude()
                - tmpLatLng.getLongitude();
        for (int i = 0; i < 0; ++i) {
            tmpLocation.setLatitude(bdLocation.getLatitude());
            tmpLocation.setLongitude(bdLocation.getLongitude());
            tmpLatLng = WGS84_to_GCJ02(tmpLocation);
            tmpLat = 2 * tmpLat - tmpLatLng.getLatitude();
            tmpLng = 2 * tmpLng - tmpLatLng.getLongitude();
        }
        bdLocation.setLatitude(tmpLat);
        bdLocation.setLongitude(tmpLng);
        return bdLocation;
    }

    public static BDLocation WGS84_to_GCJ02(BDLocation bdLocation) {
        double dLat = transformLat(bdLocation.getLongitude() - 105.0,
                bdLocation.getLatitude() - 35.0);
        double dLon = transformLon(bdLocation.getLongitude() - 105.0,
                bdLocation.getLatitude() - 35.0);
        double radLat = bdLocation.getLatitude() / 180.0 * Math.PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0)
                / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
        bdLocation.setLatitude(bdLocation.getLatitude() + dLat);
        bdLocation.setLongitude(bdLocation.getLongitude() + dLon);
        return bdLocation;
    }

    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x
                * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0
                * Math.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y
                * Math.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x
                * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0
                * Math.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x
                / 30.0 * Math.PI)) * 2.0 / 3.0;
        return ret;
    }
    
    public static void main(String[] src){
     BDLocation bdLocation=new BDLocation(121.34915,31.22425);
     bdLocation=GlobalTool.WGS84_to_GCJ02(bdLocation);
     System.out.println(bdLocation.getLongitude()+","+bdLocation.getLatitude());
    }
    
    
    
}
