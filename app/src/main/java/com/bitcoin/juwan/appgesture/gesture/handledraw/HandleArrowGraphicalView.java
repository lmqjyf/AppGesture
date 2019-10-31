package com.bitcoin.juwan.appgesture.gesture.handledraw;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bitcoin.juwan.appgesture.gesture.model.ChildGraphicalView;
import com.bitcoin.juwan.appgesture.gesture.model.PointCoordinate;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * FileName：HandleArrowGraphicalView
 * Create By：liumengqiang
 * Description：指向箭头
 */
public class HandleArrowGraphicalView implements IHandleDraw {

    @Override
    public void onDrawInitView(Paint paint, Canvas canvas, List<ChildGraphicalView> coordinateList) {
        if(coordinateList.size() != 0) { //设置画笔属性
            int color = coordinateList.get(0).getBigGraphical().getUnSelectColor();
            Paint.Style style = coordinateList.get(0).getBigGraphical().getUnSelectStyle();
            paint.setColor(color);
            paint.setStyle(style);//设置画笔属性
            paint.setStrokeWidth(2); //设置划线的宽度
        }
        for(ChildGraphicalView point : coordinateList) {
            Collection<PointCoordinate> values = point.getPointCoordinateMap().values();
            for(PointCoordinate arrowPoint : values) {
                canvas.drawCircle(arrowPoint.getX(), arrowPoint.getY(), 5, paint);
            }
        }
    }

    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, ChildGraphicalView> selectMap) {

    }
}
