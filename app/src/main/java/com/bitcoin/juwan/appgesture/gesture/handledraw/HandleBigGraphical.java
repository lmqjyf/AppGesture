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
 * FileName：HandleBigGraphical
 * Create By：liumengqiang
 * Description：TODO
 */
public class HandleBigGraphical implements IGraphicalView {

    private static final int circleRadius = 50; //圆半径

    @Override
    public int getGraphicalViewRadius() {
        return circleRadius;
    }

    @Override
    public void onDrawInitView(Paint paint, Canvas canvas, List<ChildGraphicalView> coordinateList, AttrsModel attrsModel) {
        if(coordinateList.size() != 0) { //设置画笔属性
            int color = attrsModel.getBigGraphicalColor();
            Paint.Style style = Paint.Style.STROKE;
            paint.setColor(color);
            paint.setStyle(style);//设置画笔属性
            paint.setStrokeWidth(2); //设置划线的宽度
        }
        for(ChildGraphicalView point : coordinateList) {
            canvas.drawCircle(point.getX(), point.getY(), circleRadius, paint);
        }
    }

    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, ChildGraphicalView> selectMap, AttrsModel attrsModel) {
        boolean isNeedSet = true;
        for(Map.Entry<Integer, ChildGraphicalView> entry : selectMap.entrySet()) {
            ChildGraphicalView pointCoordinate = entry.getValue();
            if(isNeedSet) {
                paint.setColor(attrsModel.getBigGraphicalSelectColor());
                paint.setStyle(Paint.Style.STROKE);
                isNeedSet = false;
            }
            canvas.drawCircle(pointCoordinate.getX(), pointCoordinate.getY(), circleRadius, paint);
        }
    }
}
