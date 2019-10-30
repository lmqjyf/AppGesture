package com.bitcoin.juwan.appgesture.gesture.handledraw;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bitcoin.juwan.appgesture.gesture.PointCoordinate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName：HandleLineGraphical
 * Create By：liumengqiang
 * Description：TODO
 */
public class HandleLineGraphical implements IHandleDraw {
    @Override
    public void onDrawInitView(Paint paint, Canvas canvas, List<PointCoordinate> coordinateList) {

    }

    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, PointCoordinate> selectMap) {
        PointCoordinate pointCoordinate = null;
        for(Map.Entry<Integer, PointCoordinate> entry : selectMap.entrySet()) {
            if(pointCoordinate == null) {
                pointCoordinate = entry.getValue();
            } else {
                PointCoordinate currPoint = entry.getValue();
                paint.setStyle(Paint.Style.FILL);
                //画线
                canvas.drawLine(pointCoordinate.getX(), pointCoordinate.getY(), currPoint.getX(), currPoint.getY(), paint);
                pointCoordinate = currPoint;
            }
        }
    }
}
