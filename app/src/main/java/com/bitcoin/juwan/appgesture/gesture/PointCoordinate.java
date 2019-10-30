package com.bitcoin.juwan.appgesture.gesture;

import android.graphics.Color;

import com.bitcoin.juwan.appgesture.gesture.graphical.BigGraphical;
import com.bitcoin.juwan.appgesture.gesture.graphical.SmallGraphical;

/**
 * FileName：PointCoordinate
 * Create By：liumengqiang
 * Description：TODO
 */
public class PointCoordinate {

    private float x; //坐标
    private float y;

    private BigGraphical bigGraphical;
    private SmallGraphical smallGraphical;


    public PointCoordinate(float x, float y) {
        this(x, y, new BigGraphical(Color.BLUE, Color.BLUE), new SmallGraphical(Color.BLUE, Color.TRANSPARENT));
    }

    public PointCoordinate(float x, float y, BigGraphical bigGraphical, SmallGraphical smallGraphical) {
        this.x = x;
        this.y = y;
        this.bigGraphical = bigGraphical;
        this.smallGraphical = smallGraphical;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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
}