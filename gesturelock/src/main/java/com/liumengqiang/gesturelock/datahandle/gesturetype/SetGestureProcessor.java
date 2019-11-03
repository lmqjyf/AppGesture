package com.liumengqiang.gesturelock.datahandle.gesturetype;

import com.liumengqiang.gesturelock.listener.GestureListener;
import com.liumengqiang.gesturelock.model.GestureViewType;

import java.util.Set;

/**
 * author: liumengqiang
 * Date : 2019/11/2
 * Description : 手势类型：设置数据类型
 */
public class SetGestureProcessor implements IProcessor {

    private GestureListener setGestureListener;

    String firstSelectValue = ""; //第一次选中的值

    String secondSelectValue = ""; //第二次设置

    private int count = 0;

    public SetGestureProcessor() {

    }

    /**
     *
     * @param selectIndexArray 当前选中点的索引
     * @return 返回状态 {@link GestureViewType}
     */
    public int handleData( Set<Integer> selectIndexArray) {
        if(count == 0) { //第一次设置
            firstSelectValue = getGestureValue(selectIndexArray);
            count ++;
            return GestureViewType.TYPE_TRANSITION;
        } else { //第二次设置
            secondSelectValue = getGestureValue(selectIndexArray);
            if(!firstSelectValue.equals(secondSelectValue)) { //两次密码不一致
                resetData();
                return GestureViewType.TYPE_ERROR;
            } else { //两次密码一致，设置成功
                resetData();
                return GestureViewType.TYPE_COMPLETE;
            }
        }
    }

    private void resetData() {
        count = 0;
        firstSelectValue = "";
        secondSelectValue = "";
    }

    private String getGestureValue(Set<Integer> selectIndexArray) {
        String value = "";
        for(Integer integer : selectIndexArray) {
            value += integer;
        }
        return value;
    }

    @Override
    public void setGestureValue(String gestureValue) {

    }
}
