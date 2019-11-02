package com.liumengqiang.gesturelock.model;

/**
 * author: liumengqiang
 * Date : 2019/11/2
 * Description :
 */
public interface GestureViewType {
    /**
     * 触摸过程类型
     */
    int TYPE_COMPLETE = 1; //移动完成（大于需要默认选中的点）

    int TYPE_ERROR = -1; //移动完成（小于需要默认选中的点）

    int TYPE_RESET = 2; //重置标记


    /**
     * 手势解锁类型
     */
    int TYPE_CHECK_GESTURE = 3; //校验数据

    int TYPE_SET_GESTURE = 4; //设置手势
}
