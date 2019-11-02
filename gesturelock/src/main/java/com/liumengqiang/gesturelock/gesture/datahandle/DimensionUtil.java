package com.liumengqiang.gesturelock.gesture.datahandle;

import android.content.Context;
import android.util.TypedValue;

/**
 * author: liumengqiang
 * Date : 2019/11/2
 * Description :
 */
public class DimensionUtil {

    /**
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static float dp2px(Context context, float dpValue){
        return  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpValue,context.getResources().getDisplayMetrics());
    }


    //px转dp
    public static float px2dp(Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
