package com.liumengqiang.gesturelock.listener;

import java.util.List;

/**
 * author: liumengqiang
 * Date : 2020/2/7
 * Description :
 */
public abstract class GestureListener implements IGestureListener {
    @Override
    public void onFailed() {
    }

    @Override
    public void transitionStatus() {

    }

    @Override
    public void onStart() {
    }

    @Override
    public void onPointNumberChange(int selectIndex) {
    }

    @Override
    public void onComplete(List<Integer> list) {
    }

    @Override
    public void onPointLessThanSetting() {
    }

}
