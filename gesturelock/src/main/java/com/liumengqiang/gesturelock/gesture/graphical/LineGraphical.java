package com.liumengqiang.gesturelock.gesture.graphical;

import android.graphics.Paint;

/**
 * FileName：BigGraphical
 * Create By：liumengqiang
 * Description：TODO
 */
public class LineGraphical {

    private int unSelectColor; // 外部图形颜色

    private Paint.Style unSelectStyle; //

    private int selectColor;

    private Paint.Style selectStyle;

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
