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

    int TYPE_RESET = 2; //重置标记 （移动过程中或者没开始移动，或者不错特殊处理）

    int TYPE_TRANSITION = -2; //过渡状态（需要多次输入校验，比如：设置密码）


    /**
     * 手势解锁类型
     */
    int TYPE_CHECK_GESTURE = 3; //校验数据

    int TYPE_SET_GESTURE = 4; //设置手势
}
