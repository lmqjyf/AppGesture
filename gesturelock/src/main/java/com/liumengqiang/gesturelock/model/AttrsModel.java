package com.liumengqiang.gesturelock.model;

import android.content.res.TypedArray;
import android.graphics.Color;

import com.liumengqiang.gesturelock.R;

/**
 * FileName：AttrsModel
 * Create By：liumengqiang
 * Description：
 */
public class AttrsModel {

    private boolean arrowIsNeed;
    private boolean isSkipMiddlePoint;
    private int bigGraphicalColor = Color.BLUE;
    private int bigGraphicalSelectColor = Color.BLUE;
    private int smallGraphicalColor = Color.TRANSPARENT;
    private int smallGraphicalSelectColor = Color.BLUE;
    private int lineColor = Color.BLUE;
    private int arrowColor = Color.BLUE;
    private int errorColor = Color.RED;

    private float arrowLength = 12;
    private float bigGraphicalRadius = 50;
    private float smallGraphicalRadius = 15;

    private int needSelectPointNumber = 4; // 默认最低选中四个

    private int gestureType; //手势类型

    public AttrsModel() {}

    public AttrsModel(TypedArray ta) {
        bigGraphicalColor = ta.getColor(R.styleable.GestureView_bigGraphicalColor, Color.BLUE);
        bigGraphicalSelectColor = ta.getColor(R.styleable.GestureView_bigSelectGraphicalColor, Color.BLUE);
        smallGraphicalColor = ta.getColor(R.styleable.GestureView_smallGraphicalColor, Color.TRANSPARENT);
        smallGraphicalSelectColor = ta.getColor(R.styleable.GestureView_smallSelectGraphicalColor, Color.BLUE);
        errorColor = ta.getColor(R.styleable.GestureView_errorColor, Color.RED);
        arrowIsNeed = ta.getBoolean(R.styleable.GestureView_arrowIsNeed, true);
        isSkipMiddlePoint = ta.getBoolean(R.styleable.GestureView_isSkipMiddlePoint, false);
        lineColor = ta.getColor(R.styleable.GestureView_lineColor, Color.BLUE);
        arrowLength = ta.getDimension(R.styleable.GestureView_arrowRadius, 12);
        bigGraphicalRadius = ta.getDimension(R.styleable.GestureView_bigGraphicalRadius, 50);
        smallGraphicalRadius = ta.getDimension(R.styleable.GestureView_smallGraphicalRadius, 15);
        arrowColor = ta.getColor(R.styleable.GestureView_arrowColor, Color.BLUE);
        needSelectPointNumber = ta.getInteger(R.styleable.GestureView_needSelectPointNumber, 4);
        gestureType = ta.getInteger(R.styleable.GestureView_gestureType,3);
        ta.recycle();
    }

    public boolean isArrowIsNeed() {
        return arrowIsNeed;
    }

    public boolean isSkipMiddlePoint() {
        return isSkipMiddlePoint;
    }

    public int getBigGraphicalColor() {
        return bigGraphicalColor;
    }

    public void setBigGraphicalColor(int bigGraphicalColor) {
        this.bigGraphicalColor = bigGraphicalColor;
    }

    public int getBigGraphicalSelectColor() {
        return bigGraphicalSelectColor;
    }

    public void setBigGraphicalSelectColor(int bigGraphicalSelectColor) {
        this.bigGraphicalSelectColor = bigGraphicalSelectColor;
    }

    public int getSmallGraphicalColor() {
        return smallGraphicalColor;
    }

    public void setSmallGraphicalColor(int smallGraphicalColor) {
        this.smallGraphicalColor = smallGraphicalColor;
    }

    public int getSmallGraphicalSelectColor() {
        return smallGraphicalSelectColor;
    }

    public void setSmallGraphicalSelectColor(int smallGraphicalSelectColor) {
        this.smallGraphicalSelectColor = smallGraphicalSelectColor;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public float getBigGraphicalRadius() {
        return bigGraphicalRadius;
    }

    public  float getSmallGraphicalRadius() {
        return smallGraphicalRadius;
    }

    public float getArrowLength() {
        return arrowLength;
    }

    public int getArrowColor() {
        return arrowColor;
    }

    public int getNeedSelectPointNumber() {
        return needSelectPointNumber;
    }

    public int getErrorColor() {
        return errorColor;
    }

    public int getGestureType() {
        return gestureType;
    }
}
