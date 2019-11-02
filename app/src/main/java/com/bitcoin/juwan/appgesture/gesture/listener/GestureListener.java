package com.bitcoin.juwan.appgesture.gesture.listener;

import java.util.List;

/**
 * FileName：GestureListener
 * Create By：liumengqiang
 * Description：TODO
 */
public interface GestureListener {

    void onStart(); //开始选中

    void onPointNumberChange(int selectIndex); //选中点触发

    void onComplete(List<Integer> list); //手指离开屏幕

    void onFailed(); //小于最低设置的点个数（默认：4个， 详见：AttrsModel）
}
