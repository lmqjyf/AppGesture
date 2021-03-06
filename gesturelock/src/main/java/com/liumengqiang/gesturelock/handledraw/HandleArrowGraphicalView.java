package com.liumengqiang.gesturelock.handledraw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.liumengqiang.gesturelock.interfaceview.IGraphicalView;
import com.liumengqiang.gesturelock.model.ArrowPointCoordinate;
import com.liumengqiang.gesturelock.model.AttrsModel;
import com.liumengqiang.gesturelock.model.ChildGraphicalView;
import com.liumengqiang.gesturelock.model.GestureViewType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * FileName：HandleArrowGraphicalView
 * Create By：liumengqiang
 * Description：指向箭头
 */
public class HandleArrowGraphicalView implements IGraphicalView {

    @Override
    public void onDrawInitView(Paint paint, Canvas canvas, List<ChildGraphicalView> coordinateList, AttrsModel attrsModel) {
//        if (coordinateList.size() != 0) { //设置画笔属性
//            int color = coordinateList.get(0).getBigGraphical().getUnSelectColor();
//            Paint.Style style = coordinateList.get(0).getBigGraphical().getUnSelectStyle();
//            paint.setColor(color);
//            paint.setStyle(style);//设置画笔属性
//            paint.setStrokeWidth(2); //设置划线的宽度
//        }
//        for (ChildGraphicalView point : coordinateList) {
//            Collection<ArrowPointCoordinate> values = point.getPointCoordinateList();
//            for (ArrowPointCoordinate arrowPoint : values) {
//
//            }
//        }
    }

    private List<ChildGraphicalView> replaceArray = new ArrayList();

    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, ChildGraphicalView> selectMap, AttrsModel attrsModel, int TYPE) {
        Collection<ChildGraphicalView> values = selectMap.values();
        replaceArray.clear();
        replaceArray.addAll(values);
        for (int i = 0; i < values.size() - 1; i++) {
            ChildGraphicalView pointCoordinate = replaceArray.get(i);
            int nextIndex = replaceArray.get(i + 1).getIndex(); //下一个选中的坐标
            List<ArrowPointCoordinate> pointCoordinateList = pointCoordinate.getPointCoordinateList();
            for (ArrowPointCoordinate arrowPoint : pointCoordinateList) {
                if (arrowPoint.getNextLinkedIndex() == nextIndex) {
                    setPaintColor(paint, attrsModel, TYPE);
                    paint.setStyle(Paint.Style.FILL);
                    Path path = new Path();
                    path.moveTo(arrowPoint.getPoint1().getX(), arrowPoint.getPoint1().getY());// 此点为多边形的起点
                    path.lineTo(arrowPoint.getPoint2().getX(), arrowPoint.getPoint2().getY());
                    path.lineTo(arrowPoint.getPoint3().getX(), arrowPoint.getPoint3().getY());
                    path.close(); // 使这些点构成封闭的多边形
                    canvas.drawPath(path, paint);
                }
            }
        }
    }

    private void setPaintColor(Paint paint, AttrsModel attrsModel, int TYPE) {
        switch (TYPE) {
            case GestureViewType.TYPE_COMPLETE : {
                paint.setColor(attrsModel.getArrowColor());
                break;
            }
            case GestureViewType.TYPE_ERROR : {
                paint.setColor(attrsModel.getErrorColor());
                break;
            }
            case GestureViewType.TYPE_RESET : {
                paint.setColor(attrsModel.getArrowColor());
                break;
            }
        }
    }
}
