package com.bitcoin.juwan.appgesture.gesture;

import android.util.Log;

import com.bitcoin.juwan.appgesture.gesture.model.ArrowPointCoordinate;
import com.bitcoin.juwan.appgesture.gesture.model.ChildGraphicalView;
import com.bitcoin.juwan.appgesture.gesture.model.PointCoordinate;

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
            List<ArrowPointCoordinate> pointCoordinateList = pointI.getPointCoordinateList();
            for(int j = 0; j < list.size(); j ++) {
                ChildGraphicalView pointJ = list.get(j);
                if(i == j) {
                    continue;
                } else {
                    ArrowPointCoordinate arrowPointCoordinate = new ArrowPointCoordinate();
                    arrowPointCoordinate.setNextLinkedIndex(j);
                    //顶点
                    PointCoordinate point3 = getArrowPoint(i, j, pointJ.getPointCenter(), pointI.getPointCenter(), 40);
                    arrowPointCoordinate.setPoint3(point3);
                    //高坐标点
                    PointCoordinate heightPoint = getArrowPoint(i, j, pointJ.getPointCenter(), pointI.getPointCenter(), 30);
                    //todo 获取剩余两个坐标点
                    PointCoordinate[] pointCoordinate = getBottomCoordinate(i, j, point3, heightPoint, 10);
                    arrowPointCoordinate.setPointCoordinate(pointCoordinate);
                    //
                    pointCoordinateList.add(arrowPointCoordinate);
                }
            }
        }
    }

    private static PointCoordinate[] getBottomCoordinate(int i, int j, PointCoordinate  topPoint, PointCoordinate heightPoint, int lengthLine) {
        PointCoordinate[] pointCoordinates = new PointCoordinate[2];
//        x1 = bX + [L * (aY - bY)] / √[(aX - bX)² + (aY – bY)²]
//        y1 = bY - [L * (aX - bX)] / √[(aX - bX)² + (aY – bY)²]
//        x1 = bX - [L * (aY - bY)] / √[(aX - bX)² + (aY – bY)²]
//        y1 = bY + [L * (aX - bX)] / √[(aX - bX)² + (aY – bY)²]
//        1弧度=(180/π)°角度
        double radian = 180 / (Math.PI * 30);
        double L = lengthLine * Math.tan(radian);
        float aX = topPoint.getX();
        float aY = topPoint.getY();
        float bX = heightPoint.getX();
        float bY = heightPoint.getY();
        double x1 = bX + L * (aY - bY) / Math.sqrt((aX - bX) * (aX - bX)+ (aY - bY) * (aY - bY));
        double y1 = bY - L * (aX - bX) / Math.sqrt((aX - bX) * (aX - bX) + (aY - bY) + (aY - bY));
        pointCoordinates[0] = new PointCoordinate((float) x1, (float) y1);
        double x2 = bX - L * (aY - bY) / Math.sqrt((aX - bX) * (aX - bX)+ (aY - bY) * (aY - bY));
        double y2 = bY + L * (aX - bX) / Math.sqrt((aX - bX) * (aX - bX) + (aY - bY) + (aY - bY));
        pointCoordinates[1] = new PointCoordinate((float) x2, (float) y2);
//        Log.e("-------:", i + " " + j + " " + (int)topPoint.getX() + " " + (int) topPoint.getY() + " " + (int)x1 + " " + (int)y1 + " " + (int)x2 + " " + (int)y2);
        return pointCoordinates;
    }

    /**
     * @param point1
     * @param point2
     * @param lineLength  线段长度
     * @return
     */
    private static PointCoordinate getArrowPoint(int index, int j, PointCoordinate point1, PointCoordinate point2, int lineLength) {
        float x1 = point1.getX();
        float y1 = point1.getY();
        float x2 = point2.getX();
        float y2 = point2.getY();

        double x3 = 0;
        double y3 = 0;
        if(y2 == y1) {
            y3 = y2;
            if(index > j) {
                x3 = getMaxDouble(x1, x2) - lineLength;
            } else {
                x3 = getMinDouble(x1, x2) + lineLength;
            }
        } else if( x1 == x2) {
            if(index > j) {
                y3 = getMaxDouble(y1, y2) - lineLength;
            } else {
                y3 = getMinDouble(y1, y2) + lineLength;
            }
            x3 = x1;
        } else {
            if(index > j && x2 > x1) {
                y3 = y2 - Math.sqrt(lineLength * lineLength / (1 + (x2 - x1) * (x2 - x1) / ((y2 - y1) * (y2 - y1))));
                x3 = x2 - (x2 - x1) * (y2 - y3) / (y2 - y1);
            } else if(index > j && x2 < x1) {
                y3 = y2 - Math.sqrt(lineLength * lineLength / (1 + (x2 - x1) * (x2 - x1) / ((y2 - y1) * (y2 - y1))));
                x3 = x2 + (x1 - x2) * (y2 - y3) / (y2 - y1);
            } else if(index < j && x2 > x1) {
                y3 = y2 + Math.sqrt(lineLength * lineLength / (1 + (x2 - x1) * (x2 - x1) / ((y2 - y1) * (y2 - y1))));
                x3 = x2 - (x2 - x1) * (y3 - y2) / (y1 - y2);
            } else {
                y3 = y2 + Math.sqrt(lineLength * lineLength / (1 + (x2 - x1) * (x2 - x1) / ((y2 - y1) * (y2 - y1))));
                x3 = x2 + (x1 - x2) * (y3 - y2) / (y1 - y2);
            }
        }
        return new PointCoordinate((float) x3, (float) y3);
    }

    private static double getMinDouble(double x1, double x2) {
        return x1 > x2 ? x2 : x1;
    }
    private static double getMaxDouble(double x1, double x2) {
        return x1 < x2 ? x2 : x1;
    }
}
