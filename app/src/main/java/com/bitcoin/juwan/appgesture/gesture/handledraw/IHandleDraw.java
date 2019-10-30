package com.bitcoin.juwan.appgesture.gesture.handledraw;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bitcoin.juwan.appgesture.gesture.PointCoordinate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * FileName：IHandleDraw
 * Create By：liumengqiang
 * Description：TODO
 */
public interface IHandleDraw {

     List<PointCoordinate> coordinateList = null;

     LinkedHashMap<Integer, PointCoordinate> selectMap = null;

    void onDrawInitView(Paint paint, Canvas canvas, List<PointCoordinate> coordinateList);

    void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, PointCoordinate> selectMap);

}
