package com.liumengqiang.gesturelock.interfaceview;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * FileName：IDrawView
 * Create By：liumengqiang
 * Description：
 */
public interface IDrawView {

    void  initData(int  width, int height);

    void onDrawInitView(Paint paint, Canvas canvas);

    void onDrawSelectView(Paint paint, Canvas canvas);

    void onDrawLineView(Paint paint, Canvas canvas);
}
