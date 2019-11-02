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
 * FileName：HandleLineGraphical
 * Create By：liumengqiang
 * Description：TODO
 */
public class HandleLineGraphical implements IGraphicalView {

    @Override
    public void onDrawInitView(Paint paint, Canvas canvas, List<ChildGraphicalView> coordinateList, AttrsModel attrsModel) {

    }

    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, ChildGraphicalView> selectMap, AttrsModel attrsModel) {
        ChildGraphicalView pointCoordinate = null;
        boolean isNeedSet = true;
        for(Map.Entry<Integer, ChildGraphicalView> entry : selectMap.entrySet()) {
            if(pointCoordinate == null) {
                pointCoordinate = entry.getValue();
            } else {
                ChildGraphicalView currPoint = entry.getValue();
                if(isNeedSet) {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(attrsModel.getLineColor());
                    isNeedSet = false;
                }
                //画线
                canvas.drawLine(pointCoordinate.getX(), pointCoordinate.getY(), currPoint.getX(), currPoint.getY(), paint);
                pointCoordinate = currPoint;
            }
        }
    }
}
