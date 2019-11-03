package com.liumengqiang.gesturelock.interfaceview;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.liumengqiang.gesturelock.model.AttrsModel;
import com.liumengqiang.gesturelock.model.ChildGraphicalView;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * FileName：IGraphicalView
 * Create By：liumengqiang
 * Description：自定义需要实现的接口
 */
public interface IGraphicalView {

    /**
     * 初始化绘制
     * @param paint 画笔
     * @param canvas 画板
     * @param coordinateList 9个点的中心点坐标
     * @param attrsModel 属性对象
     */
    void onDrawInitView(Paint paint, Canvas canvas, List<ChildGraphicalView> coordinateList, AttrsModel attrsModel);

    /**
     *
     * @param paint
     * @param canvas
     * @param selectMap 当前选中点的信息
     * @param attrsModel
     * @param TYPE 当前的类型（成功？错误？绘制中？）
     */
    void onDrawSelectView(Paint paint, Canvas canvas, LinkedHashMap<Integer, ChildGraphicalView> selectMap, AttrsModel attrsModel, int TYPE);
}
