package com.liumengqiang.gesturelock.interfaceview;

import android.view.MotionEvent;

/**
 * FileName：ITouch
 * Create By：liumengqiang
 * Description：TODO
 */
public interface ITouch {
    void touchDown(MotionEvent event);

    void touchMove(MotionEvent event);

    void touchUp(MotionEvent event);
}
