package com.bitcoin.juwan.appgesture.gesture.handledraw;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bitcoin.juwan.appgesture.gesture.interfaceview.IGraphicalView;
import com.bitcoin.juwan.appgesture.gesture.model.AttrsModel;
import com.bitcoin.juwan.appgesture.gesture.model.ChildGraphicalView;

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
    public void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, ChildGraphicalView> selectMap, AttrsModel attrsModel) {
        boolean isNeedSet = true;
        for(Map.Entry<Integer, ChildGraphicalView> entry : selectMap.entrySet()) {
            ChildGraphicalView pointCoordinate = entry.getValue();
            if(isNeedSet) {
                paint.setColor(attrsModel.getSmallGraphicalSelectColor());
                paint.setStyle(Paint.Style.FILL);
                isNeedSet = false;
            }
            canvas.drawCircle(pointCoordinate.getX(), pointCoordinate.getY(), attrsModel.getSmallGraphicalRadius(), paint);
        }
    }
}
