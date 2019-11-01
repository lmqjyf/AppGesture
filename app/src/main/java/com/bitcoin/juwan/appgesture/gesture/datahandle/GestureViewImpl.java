package com.bitcoin.juwan.appgesture.gesture.datahandle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.bitcoin.juwan.appgesture.R;
import com.bitcoin.juwan.appgesture.gesture.handledraw.HandleArrowGraphicalView;
import com.bitcoin.juwan.appgesture.gesture.handledraw.HandleBigGraphical;
import com.bitcoin.juwan.appgesture.gesture.handledraw.HandleLineGraphical;
import com.bitcoin.juwan.appgesture.gesture.handledraw.HandleSmallGraphical;
import com.bitcoin.juwan.appgesture.gesture.interfaceview.IGraphicalView;
import com.bitcoin.juwan.appgesture.gesture.interfaceview.IDrawView;
import com.bitcoin.juwan.appgesture.gesture.interfaceview.ITouch;
import com.bitcoin.juwan.appgesture.gesture.listener.GestureListener;
import com.bitcoin.juwan.appgesture.gesture.model.AttrsModel;
import com.bitcoin.juwan.appgesture.gesture.model.ChildGraphicalView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * FileName：GestureViewImpl
 * Create By：liumengqiang
 * Description：When I wrote this, only God and I  understood what I was doing. Now, God only knows
 */
public class GestureViewImpl implements IDrawView, ITouch {

    //存储九个点
    private static List<ChildGraphicalView> childGraphicalList = new ArrayList<>();
    //存储选中的点
    private static LinkedHashMap<Integer, ChildGraphicalView> selectPointMap = new LinkedHashMap<>();

    private static List<Integer> indexList = new ArrayList<>();

    private int needSelectPointNumber = 4; //最小选中点的数量。默认：4个

    private GestureListener gestureListener; //事件回调

    public IGraphicalView handleBigGraphical = new HandleBigGraphical();
    public IGraphicalView handleSmallGraphical = new HandleSmallGraphical();
    public IGraphicalView handleLineGraphical = new HandleLineGraphical();
    public IGraphicalView handleArrowGraphical = new HandleArrowGraphicalView();

    private int graphicalRadius = handleBigGraphical.getGraphicalViewRadius();

    private HandleCoordinate handleCoordinate = null;

    private AttrsModel attrsModel;

    public GestureViewImpl(Context context, AttributeSet attrs) {
        if (attrs == null) {
            attrsModel = new AttrsModel();
            handleCoordinate = new HandleCoordinate();
            return;
        }
        attrsModel = new AttrsModel(context.obtainStyledAttributes(attrs, R.styleable.GestureView));
        handleCoordinate = new HandleCoordinate(attrsModel);
    }

    /**
     * 初始化坐标
     */
    @Override
    public void initData(int viewWidth, int viewHeight) {
        if (childGraphicalList.size() != 0) {
            return;
        }
        //生成九个中心点坐标
        handleCoordinate.initNinePointCoordinate(viewWidth, viewHeight, graphicalRadius, childGraphicalList);

        //生成每个中心点包含箭头坐标（每个小箭头包含三个小坐标）
        handleCoordinate.getArrowCoordinate(childGraphicalList, handleBigGraphical.getGraphicalViewRadius(), handleSmallGraphical.getGraphicalViewRadius());
    }

    @Override
    public void onDrawInitView(Paint paint, Canvas canvas) {
        handleBigGraphical.onDrawInitView(paint, canvas, childGraphicalList, attrsModel);
        handleSmallGraphical.onDrawInitView(paint, canvas, childGraphicalList, attrsModel);
        handleLineGraphical.onDrawInitView(paint, canvas, childGraphicalList, attrsModel);
        handleArrowGraphical.onDrawInitView(paint, canvas, childGraphicalList, attrsModel);
    }

    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas) {
        handleBigGraphical.onDrawSelectView(paint, canvas, selectPointMap, attrsModel);
        handleSmallGraphical.onDrawSelectView(paint, canvas, selectPointMap, attrsModel);
        handleLineGraphical.onDrawSelectView(paint, canvas, selectPointMap, attrsModel);
        handleArrowGraphical.onDrawSelectView(paint, canvas, selectPointMap, attrsModel);
    }

    @Override
    public void onDrawLineView(Paint paint, Canvas canvas) {
        canvas.drawLine(startX, startY, currencyX, currencyY, paint);
    }

    /**
     * 处理触摸事件
     */
    float startY; //触摸开始的坐标点
    float startX;
    float currencyX; //移动过程中的坐标点
    float currencyY;
    boolean isValid = false; //是否是有效触摸

    @Override
    public boolean touchDown(MotionEvent event) {
        currencyX = event.getX();
        currencyY = event.getY();
        handleCoordinate.checkChildGraphicalPointIsContains(indexList, currencyX, currencyY, graphicalRadius, selectPointMap, childGraphicalList);
        isValid = indexList.size() != 0 && indexList.get(0) > -1 ? true : false;
        if (isValid) {
            startX = childGraphicalList.get(indexList.get(0)).getX();
            startY = childGraphicalList.get(indexList.get(0)).getY();
            selectPointMap.put(indexList.get(0), childGraphicalList.get(indexList.get(0)));
            return true;
        }
        return false;
    }

    @Override
    public boolean touchMove(MotionEvent event) {
        if (!isValid) { //判断触摸点是否有效
            return false;
        }
        currencyX = event.getX();
        currencyY = event.getY();
        //
        handleCoordinate.getSelectIndex(indexList, selectPointMap, childGraphicalList, currencyX, currencyY, graphicalRadius, attrsModel);
        for (Integer index : indexList) {
            startX = childGraphicalList.get(index).getX();//重置线的起始点
            startY = childGraphicalList.get(index).getY();
        }

        if (selectPointMap.size() != 0) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean touchUp(MotionEvent event) {
        if (!isValid) { //判读触摸点是否有效
            return false;
        }
        if (selectPointMap.size() < needSelectPointNumber) { //选中的数量小于最低需要选中的点数
            gestureListener.onFailed(); //回调
        } else { //大于最低需要选中的点数
            gestureListener.onComplete(new ArrayList<>(selectPointMap.keySet()));
        }
        currencyX = 0;
        currencyY = 0;
        startX = 0;
        startY = 0;
        selectPointMap.clear();
        return true;
    }

    public void setGestureListener(GestureListener gestureListener) {
        this.gestureListener = gestureListener;
    }
}
