package com.liumengqiang.gesturelock.datahandle.gesturetype;


import android.os.Handler;

import com.liumengqiang.gesturelock.GestureView;
import com.liumengqiang.gesturelock.datahandle.GestureViewImpl;
import com.liumengqiang.gesturelock.listener.GestureListener;
import com.liumengqiang.gesturelock.model.GestureViewType;
import java.util.ArrayList;
import java.util.Set;

/**
 * author: liumengqiang
 * Date : 2019/11/2
 * Description : 手势类型：检查功能
 */
public class CheckGestureHandleData implements IHandleData{


    private GestureViewImpl gestureViewImpl;

    private String gestureValue;

    private GestureListener checkGestureListener;

    public CheckGestureHandleData(GestureViewImpl gestureViewImpl) {
        this.gestureViewImpl = gestureViewImpl;
    }

    public void handleData() throws RuntimeException{
        if(gestureValue.equals(getGestureValue())) { //密码一致
            gestureViewImpl.gestureResultType = GestureViewType.TYPE_COMPLETE;
            if(checkGestureListener != null) {
                checkGestureListener.onComplete(new ArrayList<>(gestureViewImpl.selectPointMap.keySet()));
            }
        }  else { // 密码不一致
            gestureViewImpl.gestureResultType = GestureViewType.TYPE_ERROR;
            if(checkGestureListener != null) {
                checkGestureListener.passwordDisaccord();
            }
        }
    }

    private String getGestureValue() {
        String value = "";
        Set<Integer> keyValue = gestureViewImpl.selectPointMap.keySet();
        for(Integer integer : keyValue) {
            value += integer;
        }
        return value;
    }

    @Override
    public void setGestureValue(String gestureValue) {
        if(gestureValue == null) {
            throw new IllegalArgumentException("输入参数GestureValue非法！");
        } else {
            this.gestureValue = gestureValue;
        }
    }

    @Override
    public void setGestureListener(GestureListener listener) {
        this.checkGestureListener = listener;
    }
}
