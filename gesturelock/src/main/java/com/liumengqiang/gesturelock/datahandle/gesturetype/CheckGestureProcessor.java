package com.liumengqiang.gesturelock.datahandle.gesturetype;


import com.liumengqiang.gesturelock.model.GestureViewType;
import java.util.Set;

/**
 * author: liumengqiang
 * Date : 2019/11/2
 * Description : 手势类型：检查功能
 */
public class CheckGestureProcessor implements IProcessor {


    private String gestureValue;

    public CheckGestureProcessor() {
    }

    /**
     *
     * @param selectIndexArray
     * @return
     */
    public int handleData(Set<Integer> selectIndexArray) {
        if(gestureValue.equals(getGestureValue(selectIndexArray))) { //密码一致
            return GestureViewType.TYPE_COMPLETE;
        }  else { // 密码不一致
            return GestureViewType.TYPE_ERROR;
        }
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
        if(gestureValue == null) {
            throw new IllegalArgumentException("输入参数GestureValue非法！");
        } else {
            this.gestureValue = gestureValue;
        }
    }
}
