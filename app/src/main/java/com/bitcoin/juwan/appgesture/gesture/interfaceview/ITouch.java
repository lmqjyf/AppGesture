package com.bitcoin.juwan.appgesture.gesture.interfaceview;

import android.view.MotionEvent;

/**
 * FileName：ITouch
 * Create By：liumengqiang
 * Description：TODO
 */
public interface ITouch {
    boolean touchDown(MotionEvent event);

    boolean touchMove(MotionEvent event);

    boolean touchUp(MotionEvent event);
}
