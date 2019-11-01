package com.bitcoin.juwan.appgesture.gesture.datahandle;

import com.bitcoin.juwan.appgesture.gesture.model.ArrowPointCoordinate;
import com.bitcoin.juwan.appgesture.gesture.model.AttrsModel;
import com.bitcoin.juwan.appgesture.gesture.model.ChildGraphicalView;
import com.bitcoin.juwan.appgesture.gesture.model.PointCoordinate;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * FileName：HandleCoordinate
 * Create By：liumengqiang
 * Description：处理坐标
 */
public class HandleCoordinate {

    private AttrsModel model;

    public HandleCoordinate() {}

    public HandleCoordinate(AttrsModel model) {
        this.model = model;
    }

    public static double getDistancePoints(float x1, float y1, float x2, float y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static double getPointToTargetLineOfHeight(double distance1, double distance2, double targetDistance) {
        double q = (distance1 + distance2 + targetDistance) /2;
        double s = Math.sqrt(q * (q - distance1) * (q - distance2) * (q - targetDistance));
        return s * 2 / targetDistance;
    }

    public void initNinePointCoordinate(int viewWidth, int viewHeight, int graphicalRadius, List<ChildGraphicalView> childGraphicalList) {
        //横向间隔
        int divisionTransverse = (viewWidth - 6 * graphicalRadius) / 4;
        //竖向间隔
        int divisionVertical = (viewHeight - 6 * graphicalRadius) / 4;
        for(int i = 1; i <= 3; i++) {
            for(int j = 1; j <= 3; j++) {
                ChildGraphicalView childGraphicalView = new ChildGraphicalView(
                        divisionTransverse * j + (j * 2 - 1) * graphicalRadius,
                        divisionVertical * i + (i * 2 -1) * graphicalRadius, (i - 1) * 3 +  j - 1);
                childGraphicalList.add(childGraphicalView);
            }
        }
    }

    public void getArrowCoordinate(List<ChildGraphicalView> list, int outRadius, int inRadius) {
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
                    int pointLength = (outRadius - 12 - inRadius) / 2 + 12 + inRadius;
                    //获取小箭头第一个坐标
                    PointCoordinate point3 = getArrowPointCoordinate(i, j, pointJ.getPointCenter(), pointI.getPointCenter(), pointLength);
                    arrowPointCoordinate.setPoint3(point3);
                    //高坐标点
                    PointCoordinate heightPoint = getArrowPointCoordinate(i, j, pointJ.getPointCenter(), pointI.getPointCenter(), pointLength - 12);
                    //获取剩余两个坐标点
                    PointCoordinate[] pointCoordinate = getBottomCoordinate(point3, heightPoint, 12);
                    arrowPointCoordinate.setPointCoordinate(pointCoordinate);
                    //
                    pointCoordinateList.add(arrowPointCoordinate);
                }
            }
        }
    }

    private static PointCoordinate[] getBottomCoordinate(PointCoordinate  topPoint, PointCoordinate heightPoint, int lengthLine) {
        PointCoordinate[] pointCoordinates = new PointCoordinate[2];
        double radian = 180 / (Math.PI * 60);
        double L = lengthLine * Math.tan(radian);
        float aX = topPoint.getX();
        float aY = topPoint.getY();
        float bX = heightPoint.getX();
        float bY = heightPoint.getY();
        double x1 = bX + L * (aY - bY) / Math.sqrt((aX - bX) * (aX - bX)+ (aY - bY) * (aY - bY));
        double y1 = bY - L * (aX - bX) / Math.sqrt((aX - bX) * (aX - bX) + (aY - bY) * (aY - bY));
        pointCoordinates[0] = new PointCoordinate((float) x1, (float) y1);
        double x2 = bX - L * (aY - bY) / Math.sqrt((aX - bX) * (aX - bX)+ (aY - bY) * (aY - bY));
        double y2 = bY + L * (aX - bX) / Math.sqrt((aX - bX) * (aX - bX) + (aY - bY) * (aY - bY));
        pointCoordinates[1] = new PointCoordinate((float) x2, (float) y2);
        return pointCoordinates;
    }

    /**
     * 获取坐标
     * @param point1
     * @param point2
     * @param lineLength  线段长度
     * @return
     */
    private static PointCoordinate getArrowPointCoordinate(int index, int j, PointCoordinate point1, PointCoordinate point2, int lineLength) {
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


    public void getSelectIndex(List<Integer> indexList, LinkedHashMap<Integer, ChildGraphicalView> selectPointMap, List<ChildGraphicalView> childGraphicalList,
                               float currencyX, float currencyY, int graphicalRadius, AttrsModel attrsModel) {
        indexList.clear();
        if(attrsModel.isSkipMiddlePoint()) {
            checkChildGraphicalPointIsContains(indexList,currencyX, currencyY, graphicalRadius, selectPointMap, childGraphicalList);
        } else {
            getSelectIndex(indexList, selectPointMap, childGraphicalList, currencyX, currencyY, graphicalRadius);
        }
    }
    /**
     * 获取选中点的集合
     * @param selectPointMap
     * @param childGraphicalList
     * @param currencyX
     * @param currencyY
     * @param graphicalRadius
     */
    public void getSelectIndex(List<Integer> indexList,LinkedHashMap<Integer, ChildGraphicalView> selectPointMap, List<ChildGraphicalView> childGraphicalList,
                               float currencyX, float currencyY, int graphicalRadius) {
        ChildGraphicalView unSelectPoint = null; //未选中的点坐标 （C点）
        ChildGraphicalView currSelectPoint = null; //上一个选中的点坐标 （B点）
        for(ChildGraphicalView childGraphicalView : selectPointMap.values()) {
            currSelectPoint = childGraphicalView;
        }
        for(int index = 0; index < childGraphicalList.size(); index ++) {
            if(!selectPointMap.containsKey(index)) {
                unSelectPoint = childGraphicalList.get(index); // B 点
                double AB = HandleCoordinate.getDistancePoints(currencyX, currencyY, currSelectPoint.getX(), currSelectPoint.getY());
                double BC = HandleCoordinate.getDistancePoints(unSelectPoint.getX(), unSelectPoint.getY(), currSelectPoint.getX(), currSelectPoint.getY());
                double AC = HandleCoordinate.getDistancePoints(currencyX, currencyY, unSelectPoint.getX(), unSelectPoint.getY());
                if(AB * AB >= (BC * BC + AC * AC)) { //投影在线段上
                    double pointToTargetLineOfHeight = HandleCoordinate.getPointToTargetLineOfHeight(BC, AC, AB);
                    if(pointToTargetLineOfHeight <= graphicalRadius) {
                        selectPointMap.put(index, childGraphicalList.get(index)); //将该起始点放入集合中
                        indexList.add(index);
                    } else {
                        //不包含
                    }
                } else { //投影不在线段上
                    double distancePoints = HandleCoordinate.getDistancePoints(currencyX, currencyY, unSelectPoint.getX(), unSelectPoint.getY());
                    if(distancePoints <= graphicalRadius) {
                        selectPointMap.put(index, childGraphicalList.get(index)); //将该起始点放入集合中
                        indexList.add(index);
                    } else {
                        //不包含
                    }
                }
            }
        }
    }

    /**
     * 判断该触摸点是否在九个触摸圆内
     * @return
     */
    public void checkChildGraphicalPointIsContains(List<Integer> indexList, float currencyX, float currencyY, int graphicalRadius, LinkedHashMap<Integer, ChildGraphicalView> selectPointMap, List<ChildGraphicalView> childGraphicalList) {
        indexList.clear();
        for(int index = 0; index < childGraphicalList.size();) {
            ChildGraphicalView childGraphicalView = childGraphicalList.get(index);
            double distance = Math.sqrt((currencyX - childGraphicalView.getX()) * (currencyX - childGraphicalView.getX()) +
                    (currencyY - childGraphicalView.getY()) * (currencyY - childGraphicalView.getY()));
            if(distance <= graphicalRadius &&  !selectPointMap.containsKey(index)) {//选中
                selectPointMap.put(index, childGraphicalList.get(index)); //将该起始点放入集合中
                indexList.add(index);
                return;
            } else {
                index ++;
            }
        }
    }

}
