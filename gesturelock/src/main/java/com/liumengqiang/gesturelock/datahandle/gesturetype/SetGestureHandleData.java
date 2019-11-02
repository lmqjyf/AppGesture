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
 * Description : 手势类型：设置数据类型
 */
public class SetGestureHandleData implements IHandleData{

    private GestureViewImpl gestureViewImpl;

    private GestureListener setGestureListener;

    String firstSelectValue = ""; //第一次选中的值

    String secondSelectValue = ""; //第二次设置

    private int count = 0;

    public SetGestureHandleData(GestureViewImpl gestureViewImpl) {
        this.gestureViewImpl = gestureViewImpl;
    }

    public void handleData() throws RuntimeException{
        if(count == 0) { //第一次设置
            firstSelectValue = getGestureValue();
            count ++;
        } else { //第二次设置
            secondSelectValue = getGestureValue();
            if(!firstSelectValue.equals(secondSelectValue)) {
                if(setGestureListener != null) {
                    gestureViewImpl.gestureResultType = GestureViewType.TYPE_ERROR;
                    setGestureListener.passwordDisaccord();
                }
            } else {
                gestureViewImpl.gestureResultType = GestureViewType.TYPE_COMPLETE;
                if(setGestureListener != null) {
                    setGestureListener.onComplete(new ArrayList<>(gestureViewImpl.selectPointMap.keySet()));
                }
            }

            resetData();
        }
    }

    private void resetData() {
        count = 0;
        firstSelectValue = "";
        secondSelectValue = "";
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

    }

    @Override
    public void setGestureListener(GestureListener listener) {
        this.setGestureListener = listener;
    }

}
