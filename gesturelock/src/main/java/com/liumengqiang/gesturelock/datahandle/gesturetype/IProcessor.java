package com.liumengqiang.gesturelock.datahandle.gesturetype;

import java.util.Set;

/**
 * author: liumengqiang
 * Date : 2019/11/2
 * Description :
 */
public interface IProcessor {
    int handleData(Set<Integer> selectIndexArray);

    void setGestureValue(String gestureValue);
}
