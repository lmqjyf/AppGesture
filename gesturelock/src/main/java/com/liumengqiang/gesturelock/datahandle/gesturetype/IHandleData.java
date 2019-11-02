package com.liumengqiang.gesturelock.datahandle.gesturetype;

import com.liumengqiang.gesturelock.listener.GestureListener;

/**
 * author: liumengqiang
 * Date : 2019/11/2
 * Description :
 */
public interface IHandleData {
    void handleData();

    void setGestureValue(String gestureValue);

    void setGestureListener(GestureListener listener);
}
