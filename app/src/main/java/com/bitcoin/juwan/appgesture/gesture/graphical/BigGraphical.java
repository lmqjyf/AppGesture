package com.bitcoin.juwan.appgesture.gesture.graphical;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * FileName：BigGraphical
 * Create By：liumengqiang
 * Description：TODO
 */
public class BigGraphical {

    private int unSelectColor; //

    private int selectColor;

    private Paint.Style unSelectStyle; //

    private Paint.Style selectStyle;

    public BigGraphical(int selectColor, int unSelectColor) {
        this(selectColor, unSelectColor, Paint.Style.STROKE, Paint.Style.STROKE);
    }

    private BigGraphical(int selectColor, int unSelectColor, Paint.Style unSelectStyle, Paint.Style selectStyle) {
        this.unSelectColor = selectColor;
        this.selectColor = unSelectColor;
        this.unSelectStyle = unSelectStyle;
        this.selectStyle = selectStyle;
    }

    public int getUnSelectColor() {
        return unSelectColor;
    }

    public void setUnSelectColor(int unSelectColor) {
        this.unSelectColor = unSelectColor;
    }

    public Paint.Style getUnSelectStyle() {
        return unSelectStyle;
    }

    public void setUnSelectStyle(Paint.Style unSelectStyle) {
        this.unSelectStyle = unSelectStyle;
    }

    public int getSelectColor() {
        return selectColor;
    }

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
    }

    public Paint.Style getSelectStyle() {
        return selectStyle;
    }

    public void setSelectStyle(Paint.Style selectStyle) {
        this.selectStyle = selectStyle;
    }
}
