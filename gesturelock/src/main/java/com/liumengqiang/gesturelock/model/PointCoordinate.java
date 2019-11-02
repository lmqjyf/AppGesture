package com.liumengqiang.gesturelock.model;

/**
 * FileName：PointCoordinate
 * Create By：liumengqiang
 * Description：箭头坐标
 */
public class PointCoordinate {
    protected float x;

    protected float y;

    public PointCoordinate(float x, float y) {
        this.x = x;
        this.y = y;
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
}
