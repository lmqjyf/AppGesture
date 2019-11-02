package com.liumengqiang.gesturelock.handledraw;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.liumengqiang.gesturelock.interfaceview.IGraphicalView;
import com.liumengqiang.gesturelock.model.AttrsModel;
import com.liumengqiang.gesturelock.model.ChildGraphicalView;
import com.liumengqiang.gesturelock.model.GestureViewType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName：HandleSmallGraphical
 * Create By：liumengqiang
 * Description：TODO
 */
public class HandleSmallGraphical implements IGraphicalView {

    @Override
    public void onDrawInitView(Paint paint, Canvas canvas, List<ChildGraphicalView> coordinateList, AttrsModel attrsModel) {
        if(coordinateList.size() != 0) { //设置画笔属性
            int color = attrsModel.getSmallGraphicalColor();
            Paint.Style style = Paint.Style.FILL;
            paint.setColor(color);
            paint.setStyle(style);//设置画笔属性是空心圆
            paint.setStrokeWidth(2); //设置划线的宽度
        }
        for(ChildGraphicalView point : coordinateList){
            canvas.drawCircle(point.getX(), point.getY(), attrsModel.getSmallGraphicalRadius(), paint);
        }
    }

    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, ChildGraphicalView> selectMap, AttrsModel attrsModel, int TYPE) {
        boolean isNeedSet = true;
        for(Map.Entry<Integer, ChildGraphicalView> entry : selectMap.entrySet()) {
            ChildGraphicalView pointCoordinate = entry.getValue();
            if(isNeedSet) {
                setPaintColor(paint, attrsModel, TYPE);
                paint.setStyle(Paint.Style.FILL);
                isNeedSet = false;
            }
            canvas.drawCircle(pointCoordinate.getX(), pointCoordinate.getY(), attrsModel.getSmallGraphicalRadius(), paint);
        }
    }

    private void setPaintColor(Paint paint, AttrsModel attrsModel, int TYPE) {
        switch (TYPE) {
            case GestureViewType.TYPE_COMPLETE : {
                paint.setColor(attrsModel.getSmallGraphicalSelectColor());
                break;
            }
            case GestureViewType.TYPE_ERROR : {
                paint.setColor(attrsModel.getErrorColor());
                break;
            }
            case GestureViewType.TYPE_RESET: {
                paint.setColor(attrsModel.getSmallGraphicalSelectColor());
            }
        }
    }
}
