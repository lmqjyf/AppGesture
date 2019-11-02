package com.liumengqiang.gesturelock.datahandle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;

import com.liumengqiang.gesturelock.GestureView;
import com.liumengqiang.gesturelock.datahandle.gesturetype.CheckGestureHandleData;
import com.liumengqiang.gesturelock.datahandle.gesturetype.IHandleData;
import com.liumengqiang.gesturelock.datahandle.gesturetype.SetGestureHandleData;
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
    public static List<ChildGraphicalView> childGraphicalList = new ArrayList<>();
    //存储选中的点
    public static LinkedHashMap<Integer, ChildGraphicalView> selectPointMap = new LinkedHashMap<>();

    private static List<Integer> indexList = new ArrayList<>();

    public IGraphicalView handleBigGraphical = new HandleBigGraphical();
    public IGraphicalView handleSmallGraphical = new HandleSmallGraphical();
    public IGraphicalView handleLineGraphical = new HandleLineGraphical();
    public IGraphicalView handleArrowGraphical = new HandleArrowGraphicalView();

    private HandleCoordinate handleCoordinate;

    private GestureListener gestureListener;

    public AttrsModel attrsModel;

    private GestureView gestureView;

    public int gestureResultType = GestureViewType.TYPE_RESET;

    private IHandleData iHandleData = null;

    public GestureViewImpl(GestureView gestureView, AttrsModel attrsModel, HandleCoordinate handleCoordinate) {
        this.attrsModel = attrsModel;
        this.gestureView = gestureView;
        this.handleCoordinate = handleCoordinate;

        if(attrsModel.getGestureType() == GestureViewType.TYPE_CHECK_GESTURE) {
            iHandleData = new CheckGestureHandleData(this);
        } else {
            iHandleData = new SetGestureHandleData(this);
        }
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
        handleBigGraphical.onDrawSelectView(paint, canvas, selectPointMap, attrsModel, gestureResultType);
        handleSmallGraphical.onDrawSelectView(paint, canvas, selectPointMap, attrsModel, gestureResultType);
        handleLineGraphical.onDrawSelectView(paint, canvas, selectPointMap, attrsModel, gestureResultType);
        handleArrowGraphical.onDrawSelectView(paint, canvas, selectPointMap, attrsModel, gestureResultType);
    }

    @Override
    public void onDrawLineView(Paint paint, Canvas canvas) {
        if(gestureResultType == GestureViewType.TYPE_RESET) {
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
        if (!isValid || selectPointMap.size() == 0) { //判断触摸点是否有效
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

        if (!isValid || selectPointMap.size() == 0) { //判读触摸点是否有效
            return;
        }
        currencyX = 0;
        currencyY = 0;
        startX = 0;
        startY = 0;
        /**
         * 处理数据
         */
        if (selectPointMap.size() < attrsModel.getNeedSelectPointNumber()) { //选中的数量小于最低需要选中的点数
            gestureResultType = GestureViewType.TYPE_ERROR;
            if(gestureListener != null) {
                gestureListener.onFailed(); //回调
            }
        } else { //单独处理
            iHandleData.handleData();
        }

        //刷新View
        gestureView.postInvalidate();
        gestureView.isUserTouch = true;
        //1S之后在此刷新View
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                selectPointMap.clear();
                gestureView.isUserTouch = false;
                gestureResultType = GestureViewType.TYPE_RESET;
                gestureView.postInvalidate();
            }
        }, 1000);

    }

    private static Handler handler = new Handler();

    public void setGestureListener(GestureListener gestureListener) {
        this.gestureListener = gestureListener;
        iHandleData.setGestureListener(gestureListener);
    }

    public void setGestureValue(String gestureValue) {
        iHandleData.setGestureValue(gestureValue);
    }

    public void setiHandleData(IHandleData iHandleData) {
        this.iHandleData = iHandleData;
    }
}
