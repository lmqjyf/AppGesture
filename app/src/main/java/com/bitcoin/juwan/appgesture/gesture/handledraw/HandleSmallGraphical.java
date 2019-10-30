package com.bitcoin.juwan.appgesture.gesture.handledraw;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bitcoin.juwan.appgesture.gesture.PointCoordinate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName：HandleSmallGraphical
 * Create By：liumengqiang
 * Description：TODO
 */
public class HandleSmallGraphical implements IHandleDraw {

    private static final int selectCircleRadius = 25; //内圆的半径

    @Override
    public void onDrawInitView(Paint paint, Canvas canvas, List<PointCoordinate> coordinateList) {
        if(coordinateList.size() != 0) { //设置画笔属性
            int color = coordinateList.get(0).getSmallGraphical().getUnSelectColor();
            Paint.Style style = coordinateList.get(0).getSmallGraphical().getUnSelectStyle();
            paint.setColor(color);
            paint.setStyle(style);//设置画笔属性是空心圆
            paint.setStrokeWidth(2); //设置划线的宽度
        }
        for(PointCoordinate point : coordinateList){
            canvas.drawCircle(point.getX(), point.getY(), selectCircleRadius, paint);
        }
    }

    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, PointCoordinate> selectMap) {
        for(Map.Entry<Integer, PointCoordinate> entry : selectMap.entrySet()) {
            PointCoordinate pointCoordinate = entry.getValue();
            //画内圆
            paint.setColor(entry.getValue().getSmallGraphical().getSelectColor());
            paint.setStyle(entry.getValue().getSmallGraphical().getSelectStyle());
            canvas.drawCircle(pointCoordinate.getX(), pointCoordinate.getY(), selectCircleRadius, paint);
        }
    }
}
