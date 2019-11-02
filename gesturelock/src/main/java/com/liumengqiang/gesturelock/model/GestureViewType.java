package com.liumengqiang.gesturelock.model;

/**
 * author: liumengqiang
 * Date : 2019/11/2
 * Description :
 */
public interface GestureViewType {
    int TYPE_COMPLETE = 1; //移动完成（大于需要默认选中的点）

    int TYPE_ERROR = -1; //移动完成（小于需要默认选中的点）

    int TYPE_RESET = 2; //重置标记
}
