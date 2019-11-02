package com.liumengqiang.gesturelock.gesture.model;

/**
 * FileName：ArrowPointCoordinate
 * Create By：liumengqiang
 * Description：TODO
 */
public class ArrowPointCoordinate {
    private PointCoordinate point1;

    private PointCoordinate point2;

    private PointCoordinate point3; //三角形顶点

    private int nextLinkedIndex;

    public ArrowPointCoordinate() {
    }

    public PointCoordinate getPoint1() {
        return point1;
    }

    public void setPoint1(PointCoordinate point1) {
        this.point1 = point1;
    }

    public PointCoordinate getPoint2() {
        return point2;
    }

    public void setPoint2(PointCoordinate point2) {
        this.point2 = point2;
    }

    public PointCoordinate getPoint3() {
        return point3;
    }

    public void setPoint3(PointCoordinate point3) {
        this.point3 = point3;
    }

    public void setPointCoordinate(PointCoordinate[] pointCoordinate) {
        point1 = pointCoordinate[0];
        point2 = pointCoordinate[1];
    }

    public int getNextLinkedIndex() {
        return nextLinkedIndex;
    }

    public void setNextLinkedIndex(int nextLinkedIndex) {
        this.nextLinkedIndex = nextLinkedIndex;
    }
}
