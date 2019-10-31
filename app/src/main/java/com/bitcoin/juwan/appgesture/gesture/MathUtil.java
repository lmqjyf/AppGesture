package com.bitcoin.juwan.appgesture.gesture;

import android.util.Log;

import com.bitcoin.juwan.appgesture.gesture.model.ChildGraphicalView;
import com.bitcoin.juwan.appgesture.gesture.model.PointCoordinate;

import java.util.HashMap;
import java.util.List;

/**
 * FileName：MathUtil
 * Create By：liumengqiang
 * Description：TODO
 */
public class MathUtil {
    public static double getDistancePoints(float x1, float y1, float x2, float y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    /**
     * 点的投影是否在线段上
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param pointX
     * @param pointY
     * @return
     */
    public static boolean isOfLine(float x1, float y1, float x2, float y2, float pointX, float pointY) {
        double AB = getDistancePoints(x1, y1, x2, y2);
        double BC = getDistancePoints(pointX, pointY, x2, y2);
        double AC = getDistancePoints(x1, y1, pointX, pointY);

        if( AB * AB >= (BC * BC + AC * AC)) {
            return true;
        } else {
            return false;
        }
    }

    public static double getPointToTargetLineOfHeight(double distance1, double distance2, double targetDistance) {
        double q = (distance1 + distance2 + targetDistance) /2;
        double s = Math.sqrt(q * (q - distance1) * (q - distance2) * (q - targetDistance));
        return s * 2 / targetDistance;
    }

    public static void getArrowCoordinate(List<ChildGraphicalView> list) {
        for(int i = 0; i < list.size(); i++) {
            ChildGraphicalView pointI = list.get(i);
            HashMap<Integer, PointCoordinate> pointCoordinateList = pointI.getPointCoordinateMap();
            for(int j = 0; j < list.size(); j ++) {
                ChildGraphicalView pointJ = list.get(j);
                if(i == j) {
                    continue;
                } else {
                    PointCoordinate point = getArrowPoint(pointJ.getPointCenter(), pointI.getPointCenter(), 40);
                    Log.e("----:", i + " " + point.getX() + " " + point.getY());
                    pointCoordinateList.put(j, point);
                }
            }
        }

        PointCoordinate arrowPoint = getArrowPoint(new PointCoordinate(200, 100), new PointCoordinate(100, 100), 50);
    }

    /**
     * @param point1
     * @param point2
     * @param lineLength  线段长度
     * @return
     */
    private static PointCoordinate getArrowPoint(PointCoordinate point1, PointCoordinate point2, int lineLength) {
        float x1 = point1.getX();
        float y1 = point1.getY();
        float x2 = point2.getX();
        float y2 = point2.getY();

        double y3 = 0;
        double x3 = 0;
        if(y2 == y1) {
            y3 = y2;
            x3 = getMinDouble(x1, x2) + lineLength;
        } else if( x1 == x2) {
            y3 = getMinDouble(y1, y2) + lineLength;
            x3 = x1;
        } else {
            y3 = y2 - Math.sqrt(lineLength * lineLength / (1 + (x2 - x1) * (x2 - x1) / ((y2 - y1) * (y2 - y1))));
            x3 = x2 - (x2 - x1) * (y2 - y3) / (y2 - y1);
        }

        return new PointCoordinate((float) x3, (float) y3);
    }

    private static double getMinDouble(double x1, double x2) {
        return x1 > x2 ? x2 : x1;
    }
}
