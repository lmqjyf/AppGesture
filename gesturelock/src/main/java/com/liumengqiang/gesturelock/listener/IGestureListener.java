package com.liumengqiang.gesturelock.listener;

import java.util.List;

/**
 * FileName：IGestureListener
 * Create By：liumengqiang
 * Description：TODO
 */
public interface IGestureListener {

    void onStart(); //开始选中

    void onComplete(List<Integer> list); //手指离开屏幕

    void onFailed(); //设置不一致

    void onPointNumberChange(int selectIndex); //选中点触发(每次选中点都会触发)

    void transitionStatus(); //过渡状态（需要多次输入校验，比如：设置密码, 此时第一次手势设置完成之后回调）

    void onPointLessThanSetting(); //小于最小需要触摸的点数。小于最低设置的点个数（默认：4个， 详见：AttrsModel）
}
