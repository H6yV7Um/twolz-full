package com.twolz.qiyi.common.util.gis;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


public class MapTools {  
       
     public static boolean isInPolygon(BDLocation mobilelocationEntity,List<BDLocation> enclosureList){  
         double p_x =mobilelocationEntity.getLongitude();  
         double p_y =mobilelocationEntity.getLatitude();  
         Point2D.Double point = new Point2D.Double(p_x, p_y);  
  
         List pointList= new ArrayList();  
           
         for (BDLocation enclosure : enclosureList){  
             double polygonPoint_x=enclosure.getLongitude();  
             double polygonPoint_y=enclosure.getLatitude();  
             Point2D.Double polygonPoint = new Point2D.Double(polygonPoint_x,polygonPoint_y);  
             pointList.add(polygonPoint);  
         }  
         MapTools test = new MapTools();  
         return test.checkWithJdkGeneralPath(point,pointList);  
     }  
       
    private boolean checkWithJdkGeneralPath(Point2D.Double point, List<Point2D.Double> polygon) {  
           java.awt.geom.GeneralPath p = new java.awt.geom.GeneralPath();  
  
           Point2D.Double first = polygon.get(0);  
           p.moveTo(first.x, first.y);  
           polygon.remove(0);  
           for (Point2D.Double d : polygon) {  
              p.lineTo(d.x, d.y);  
           }  
  
           p.lineTo(first.x, first.y);  
  
           p.closePath();  
  
           return p.contains(point);  
  
        }  
}  