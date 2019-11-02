package com.liumengqiang.gesturelock.datahandle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.liumengqiang.gesturelock.GestureView;
import com.liumengqiang.gesturelock.R;
import com.liumengqiang.gesturelock.handledraw.HandleArrowGraphicalView;
import com.liumengqiang.gesturelock.handledraw.HandleBigGraphical;
import com.liumengqiang.gesturelock.handledraw.HandleLineGraphical;
import com.liumengqiang.gesturelock.handledraw.HandleSmallGraphical;
import com.liumengqiang.gesturelock.interfaceview.IGraphicalView;
import com.liumengqiang.gesturelock.interfaceview.IDrawView;
import com.liumengqiang.gesturelock.interfaceview.ITouch;
import com.liumengqiang.gesturelock.listener.GestureListener;
import com.liumengqiang.gesturelock.model.AttrsModel;
import com.liumengqiang.gesturelock.model.ChildGraphicalView;
import com.liumengqiang.gesturelock.model.GestureViewType;

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

    private GestureListener gestureListener; //事件回调

    public IGraphicalView handleBigGraphical = new HandleBigGraphical();
    public IGraphicalView handleSmallGraphical = new HandleSmallGraphical();
    public IGraphicalView handleLineGraphical = new HandleLineGraphical();
    public IGraphicalView handleArrowGraphical = new HandleArrowGraphicalView();

    private HandleCoordinate handleCoordinate = null;

    private AttrsModel attrsModel;

    private GestureView gestureView;

    private int gestureType = GestureViewType.TYPE_RESET;

    public GestureViewImpl(GestureView gestureView, AttributeSet attrs) {
        if (attrs == null) {
            attrsModel = new AttrsModel();
            handleCoordinate = new HandleCoordinate();
            return;
        }
        this.gestureView = gestureView;
        attrsModel = new AttrsModel(gestureView.getContext().obtainStyledAttributes(attrs, R.styleable.GestureView));
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
        handleCoordinate.initNinePointCoordinate(viewWidth, viewHeight, attrsModel.getBigGraphicalRadius(), childGraphicalList);

        //生成每个中心点包含箭头坐标（每个小箭头包含三个小坐标）
        handleCoordinate.getArrowCoordinate(childGraphicalList, attrsModel);
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
        handleBigGraphical.onDrawSelectView(paint, canvas, selectPointMap, attrsModel, gestureType);
        handleSmallGraphical.onDrawSelectView(paint, canvas, selectPointMap, attrsModel, gestureType);
        handleLineGraphical.onDrawSelectView(paint, canvas, selectPointMap, attrsModel, gestureType);
        handleArrowGraphical.onDrawSelectView(paint, canvas, selectPointMap, attrsModel, gestureType);
    }

    @Override
    public void onDrawLineView(Paint paint, Canvas canvas) {
        if(gestureType == GestureViewType.TYPE_RESET) {
            canvas.drawLine(startX, startY, currencyX, currencyY, paint);
        }
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
    public void touchDown(MotionEvent event) {
        if(gestureListener != null) {
            gestureListener.onStart();
        }
        currencyX = event.getX();
        currencyY = event.getY();
        handleCoordinate.checkChildGraphicalPointIsContains(indexList, currencyX, currencyY, attrsModel.getBigGraphicalRadius(), selectPointMap, childGraphicalList);
        isValid = indexList.size() != 0 && indexList.get(0) > -1 ? true : false;
        if (isValid) {
            setStartXAndStartY();
            gestureView.postInvalidate();
        }
    }

    private void setStartXAndStartY() {
        for (Integer index : indexList) {
            startX = childGraphicalList.get(index).getX();//重置线的起始点
            startY = childGraphicalList.get(index).getY();
            //
            if(gestureListener != null) {
                gestureListener.onPointNumberChange(index);
            }
        }
    }

    @Override
    public void touchMove(MotionEvent event) {
        if (!isValid) { //判断触摸点是否有效
            return;
        }
        currencyX = event.getX();
        currencyY = event.getY();
        //
        handleCoordinate.getSelectIndex(indexList, selectPointMap, childGraphicalList, currencyX, currencyY, attrsModel);

        setStartXAndStartY();

        if (selectPointMap.size() != 0) {
            gestureView.postInvalidate();
        }
    }


    @Override
    public void touchUp(MotionEvent event) {
        if (!isValid) { //判读触摸点是否有效
            return;
        }
        if (selectPointMap.size() < attrsModel.getNeedSelectPointNumber()) { //选中的数量小于最低需要选中的点数
            gestureType = GestureViewType.TYPE_ERROR;
            gestureListener.onFailed(); //回调
        } else { //大于最低需要选中的点数
            gestureType = GestureViewType.TYPE_COMPLETE;
            gestureListener.onComplete(new ArrayList<>(selectPointMap.keySet()));
        }
        //刷新View
        gestureView.postInvalidate();
        //1S之后在此刷新View
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gestureType = GestureViewType.TYPE_RESET;
                currencyX = 0;
                currencyY = 0;
                startX = 0;
                startY = 0;
                selectPointMap.clear();
                gestureView.postInvalidate();
            }
        }, 1000);
    }

    private static Handler handler = new Handler();

    public void setGestureListener(GestureListener gestureListener) {
        this.gestureListener = gestureListener;
    }
}
