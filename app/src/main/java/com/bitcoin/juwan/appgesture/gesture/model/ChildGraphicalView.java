package com.bitcoin.juwan.appgesture.gesture.model;

import android.graphics.Color;

import com.bitcoin.juwan.appgesture.gesture.graphical.BigGraphical;
import com.bitcoin.juwan.appgesture.gesture.graphical.SmallGraphical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * FileName：ChildGraphicalView
 * Create By：liumengqiang
 * Description：TODO
 */
public class ChildGraphicalView {

//    private float x; //坐标
//    private float y;

    private PointCoordinate pointCenter;

    private HashMap<Integer, PointCoordinate> arrowCoordinateMap = new HashMap<>();

    private BigGraphical bigGraphical;
    private SmallGraphical smallGraphical;

    public ChildGraphicalView(float x, float y) {
        this(x, y, new BigGraphical(Color.BLUE, Color.BLUE), new SmallGraphical(Color.BLUE, Color.TRANSPARENT));
    }

    public ChildGraphicalView(float x, float y, BigGraphical bigGraphical, SmallGraphical smallGraphical) {
        this.pointCenter = new PointCoordinate(x, y);
        this.bigGraphical = bigGraphical;
        this.smallGraphical = smallGraphical;
    }

    public float getX() {
        return this.pointCenter.x;
    }

    public float getY() {
        return this.pointCenter.y;
    }

    public BigGraphical getBigGraphical() {
        return bigGraphical;
    }

    public void setBigGraphical(BigGraphical bigGraphical) {
        this.bigGraphical = bigGraphical;
    }

    public SmallGraphical getSmallGraphical() {
        return smallGraphical;
    }

    public void setSmallGraphical(SmallGraphical smallGraphical) {
        this.smallGraphical = smallGraphical;
    }

    public HashMap<Integer, PointCoordinate> getPointCoordinateMap() {
        return arrowCoordinateMap;
    }

    public PointCoordinate getPointCenter() {
        return pointCenter;
    }
}