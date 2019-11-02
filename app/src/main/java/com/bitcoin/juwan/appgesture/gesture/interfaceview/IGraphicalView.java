package com.bitcoin.juwan.appgesture.gesture.interfaceview;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.bitcoin.juwan.appgesture.gesture.model.AttrsModel;
import com.bitcoin.juwan.appgesture.gesture.model.ChildGraphicalView;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * FileName：IGraphicalView
 * Create By：liumengqiang
 * Description：TODO
 */
public interface IGraphicalView {

    void onDrawInitView(Paint paint, Canvas canvas, List<ChildGraphicalView> coordinateList, AttrsModel attrsModel);

    void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, ChildGraphicalView> selectMap, AttrsModel attrsModel);
}
