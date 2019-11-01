package com.bitcoin.juwan.appgesture.gesture.handledraw;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bitcoin.juwan.appgesture.gesture.interfaceview.IGraphicalView;
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

    private static final int selectCircleRadius = 15; //内圆的半径

    @Override
    public int getGraphicalViewRadius() {
        return selectCircleRadius;
    }

    @Override
    public void onDrawInitView(Paint paint, Canvas canvas, List<ChildGraphicalView> coordinateList) {
        if(coordinateList.size() != 0) { //设置画笔属性
            int color = coordinateList.get(0).getSmallGraphical().getUnSelectColor();
            Paint.Style style = coordinateList.get(0).getSmallGraphical().getUnSelectStyle();
            paint.setColor(color);
            paint.setStyle(style);//设置画笔属性是空心圆
            paint.setStrokeWidth(2); //设置划线的宽度
        }
        for(ChildGraphicalView point : coordinateList){
            canvas.drawCircle(point.getX(), point.getY(), selectCircleRadius, paint);
        }
    }

    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, ChildGraphicalView> selectMap) {
        for(Map.Entry<Integer, ChildGraphicalView> entry : selectMap.entrySet()) {
            ChildGraphicalView pointCoordinate = entry.getValue();
            //画内圆
            paint.setColor(entry.getValue().getSmallGraphical().getSelectColor());
            paint.setStyle(entry.getValue().getSmallGraphical().getSelectStyle());
            canvas.drawCircle(pointCoordinate.getX(), pointCoordinate.getY(), selectCircleRadius, paint);
        }
    }
}
