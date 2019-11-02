package com.liumengqiang.gesturelock.graphical;

import android.graphics.Paint;

/**
 * FileName：BigGraphical
 * Create By：liumengqiang
 * Description：TODO
 */
public class SmallGraphical {

    private int unSelectColor; // 外部图形颜色

    private Paint.Style unSelectStyle; //

    private int selectColor;

    private Paint.Style selectStyle;

    public SmallGraphical(int selectColor, int unSelectColor) {
        this(selectColor, unSelectColor, Paint.Style.FILL, Paint.Style.FILL);
    }

    private SmallGraphical(int selectColor, int unSelectColor, Paint.Style unSelectStyle, Paint.Style selectStyle) {
        this.selectColor = selectColor;
        this.unSelectColor = unSelectColor;
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
