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

    private List<ArrowPointCoordinate> arrowCoordinateMap = new ArrayList<>();

    private BigGraphical bigGraphical;
    private SmallGraphical smallGraphical;

    private int index = -1;

    public ChildGraphicalView(float x, float y, int index) {
        this(x, y, new BigGraphical(Color.BLUE, Color.BLUE), new SmallGraphical(Color.BLUE, Color.TRANSPARENT), index);
    }

    public ChildGraphicalView(float x, float y, BigGraphical bigGraphical, SmallGraphical smallGraphical, int index) {
        this.index = index;
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