package com.bitcoin.juwan.appgesture.gesture.interfaceview;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * FileName：IDrawView
 * Create By：liumengqiang
 * Description：
 */
public interface IDrawView {

    void  initData(int  width, int height);

    void onDrawBigView(Paint paint, Canvas canvas);

    void onDrawSmallView(Paint paint, Canvas canvas);

    void onDrawLineView(Paint paint, Canvas canvas);
}
