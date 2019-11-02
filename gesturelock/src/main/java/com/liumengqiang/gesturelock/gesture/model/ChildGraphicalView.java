package com.liumengqiang.gesturelock.gesture.model;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName：ChildGraphicalView
 * Create By：liumengqiang
 * Description：TODO
 */
public class ChildGraphicalView {

//    private float x; //坐标
//    private float y;

    //中心点坐标
    private PointCoordinate pointCenter;

    private List<ArrowPointCoordinate> arrowCoordinateMap = new ArrayList<>();

    private int index = -1;

    public ChildGraphicalView(float x, float y, int index) {
        this.index = index;
        this.pointCenter = new PointCoordinate(x, y);
    }

    public float getX() {
        return this.pointCenter.x;
    }

    public float getY() {
        return this.pointCenter.y;
    }

    public List<ArrowPointCoordinate> getPointCoordinateList() {
        return arrowCoordinateMap;
    }

    public PointCoordinate getPointCenter() {
        return pointCenter;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}