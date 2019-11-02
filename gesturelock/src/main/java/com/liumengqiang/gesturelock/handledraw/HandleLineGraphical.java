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
 * FileName：HandleLineGraphical
 * Create By：liumengqiang
 * Description：TODO
 */
public class HandleLineGraphical implements IGraphicalView {

    @Override
    public void onDrawInitView(Paint paint, Canvas canvas, List<ChildGraphicalView> coordinateList, AttrsModel attrsModel) {

    }

    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, ChildGraphicalView> selectMap, AttrsModel attrsModel, int TYPE) {
        ChildGraphicalView pointCoordinate = null;
        boolean isNeedSet = true;
        for(Map.Entry<Integer, ChildGraphicalView> entry : selectMap.entrySet()) {
            if(pointCoordinate == null) {
                pointCoordinate = entry.getValue();
            } else {
                ChildGraphicalView currPoint = entry.getValue();
                if(isNeedSet) {
                    paint.setStyle(Paint.Style.FILL);
                    setPaintColor(paint, attrsModel, TYPE);
                    isNeedSet = false;
                }
                //画线
                canvas.drawLine(pointCoordinate.getX(), pointCoordinate.getY(), currPoint.getX(), currPoint.getY(), paint);
                pointCoordinate = currPoint;
            }
        }
    }

    private void setPaintColor(Paint paint, AttrsModel attrsModel, int TYPE) {
        switch (TYPE) {
            case GestureViewType.TYPE_COMPLETE : {
                paint.setColor(attrsModel.getLineColor());
                break;
            }
            case GestureViewType.TYPE_ERROR : {
                paint.setColor(attrsModel.getErrorColor());
                break;
            }
            case GestureViewType.TYPE_RESET: {
                paint.setColor(attrsModel.getLineColor());
            }
        }
    }
}
