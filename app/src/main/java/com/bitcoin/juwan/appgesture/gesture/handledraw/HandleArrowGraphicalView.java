package com.bitcoin.juwan.appgesture.gesture.handledraw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.bitcoin.juwan.appgesture.gesture.model.ArrowPointCoordinate;
import com.bitcoin.juwan.appgesture.gesture.model.ChildGraphicalView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            Collection<ArrowPointCoordinate> values = point.getPointCoordinateList();
            for(ArrowPointCoordinate arrowPoint : values) {
//                canvas.drawCircle(arrowPoint.getPoint3().getX(), arrowPoint.getPoint3().getY(), 5, paint);
//                canvas.drawLine(arrowPoint.getPoint1().getX(), arrowPoint.getPoint1().getY(), arrowPoint.getPoint2().getX(), arrowPoint.getPoint2().getY(), paint);
//                canvas.drawLine(arrowPoint.getPoint1().getX(), arrowPoint.getPoint1().getY(), arrowPoint.getPoint3().getX(), arrowPoint.getPoint3().getY(), paint);
//                canvas.drawLine(arrowPoint.getPoint2().getX(), arrowPoint.getPoint2().getY(), arrowPoint.getPoint3().getX(), arrowPoint.getPoint3().getY(), paint);
            }
        }
    }

    private List<ChildGraphicalView> replaceArray = new ArrayList();
    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, ChildGraphicalView> selectMap) {
        Collection<ChildGraphicalView> values = selectMap.values();
        replaceArray.clear();
        replaceArray.addAll(values);
        for(int i = 0; i < values.size() -1; i++) {
            ChildGraphicalView pointCoordinate = replaceArray.get(i);
            int nextIndex = replaceArray.get(i + 1).getIndex(); //下一个选中的坐标
//            Log.e("---------:", " " + nextIndex);
            List<ArrowPointCoordinate> pointCoordinateList = pointCoordinate.getPointCoordinateList();
            for(ArrowPointCoordinate arrowPointCoordinate : pointCoordinateList) {
//                Log.e("-----:", arrowPointCoordinate.getNextLinkedIndex() + " " + nextIndex);
                if(arrowPointCoordinate.getNextLinkedIndex() == nextIndex) {
                    paint.setColor(Color.BLUE);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(arrowPointCoordinate.getPoint3().getX(), arrowPointCoordinate.getPoint3().getY(), 10, paint);
                }
            }
        }
    }
}
