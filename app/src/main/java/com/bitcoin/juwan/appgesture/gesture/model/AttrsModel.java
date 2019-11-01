package com.bitcoin.juwan.appgesture.gesture.model;

import android.content.res.TypedArray;
import android.graphics.Color;

import com.bitcoin.juwan.appgesture.R;

/**
 * FileName：AttrsModel
 * Create By：liumengqiang
 * Description：TODO
 */
public class AttrsModel {

    private boolean arrowIsNeed;
    private boolean isSkipMiddlePoint;
    private int bigGraphicalColor = Color.BLUE;
    private int bigGraphicalSelectColor = Color.BLUE;
    private int smallGraphicalColor = Color.TRANSPARENT;
    private int smallGraphicalSelectColor = Color.BLUE;
    private int lineColor = Color.BLUE;

    public AttrsModel() {}

    public AttrsModel(TypedArray ta) {
        bigGraphicalColor = ta.getColor(R.styleable.GestureView_bigGraphicalColor, Color.BLUE);
        bigGraphicalSelectColor = ta.getColor(R.styleable.GestureView_bigSelectGraphicalColor, Color.BLUE);
        smallGraphicalColor = ta.getColor(R.styleable.GestureView_smallGraphicalColor, Color.TRANSPARENT);
        smallGraphicalSelectColor = ta.getColor(R.styleable.GestureView_smallSelectGraphicalColor, Color.BLUE);
        arrowIsNeed = ta.getBoolean(R.styleable.GestureView_arrowIsNeed, true);
        isSkipMiddlePoint = ta.getBoolean(R.styleable.GestureView_isSkipMiddlePoint, false);
        lineColor = ta.getColor(R.styleable.GestureView_lineColor, Color.BLUE);
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
}
