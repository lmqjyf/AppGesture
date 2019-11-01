package com.bitcoin.juwan.appgesture.gesture;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.bitcoin.juwan.appgesture.R;
import com.bitcoin.juwan.appgesture.gesture.graphical.BigGraphical;
import com.bitcoin.juwan.appgesture.gesture.graphical.SmallGraphical;
import com.bitcoin.juwan.appgesture.gesture.handledraw.HandleArrowGraphicalView;
import com.bitcoin.juwan.appgesture.gesture.handledraw.HandleBigGraphical;
import com.bitcoin.juwan.appgesture.gesture.handledraw.HandleLineGraphical;
import com.bitcoin.juwan.appgesture.gesture.handledraw.HandleSmallGraphical;
import com.bitcoin.juwan.appgesture.gesture.handledraw.IHandleDraw;
import com.bitcoin.juwan.appgesture.gesture.interfaceview.IDrawView;
import com.bitcoin.juwan.appgesture.gesture.interfaceview.ITouch;
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

    private static final int circleRadius = 50; //圆半径

    private int needSelectPointNumber = 4; //最小选中点的数量。默认：4个

    private GestureListener gestureListener ; //事件回调

    private int bigGraphicalColor = Color.BLUE;
    private int bigGraphicalSelectColor = Color.BLUE;
    private int smallGraphicalColor = Color.TRANSPARENT;
    private int smallGraphicalSelectColor = Color.BLUE;
    private int lineColor = Color.BLUE;

    protected IHandleDraw handleBigGraphical = new HandleBigGraphical();
    protected IHandleDraw handleSmallGraphical = new HandleSmallGraphical();
    protected IHandleDraw handleLineGraphical = new HandleLineGraphical();
    protected IHandleDraw handleArrowGraphical = new HandleArrowGraphicalView();


    public GestureViewImpl(Context context, AttributeSet attrs){
        if(attrs == null) {
            return;
        }
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GestureView);
        bigGraphicalColor = ta.getColor(R.styleable.GestureView_bigGraphicalColor, Color.BLUE);
        bigGraphicalSelectColor = ta.getColor(R.styleable.GestureView_bigSelectGraphicalColor, Color.BLUE);
        smallGraphicalColor = ta.getColor(R.styleable.GestureView_smallGraphicalColor, Color.TRANSPARENT);
        smallGraphicalSelectColor = ta.getColor(R.styleable.GestureView_smallSelectGraphicalColor, Color.BLUE);
        lineColor = ta.getColor(R.styleable.GestureView_lineColor, Color.BLUE);
        ta.recycle();  //注意回收
    }

    /**
     * 生成九个坐标以及箭头坐标
     */
    @Override
    public void initData(int viewWidth, int viewHeight) {
        if(childGraphicalList.size() != 0) {
            return;
        }
        //横向间隔
        int divisionTransverse = (viewWidth - 6 * circleRadius) / 4;
        //竖向间隔
        int divisionVertical = (viewHeight - 6 * circleRadius) / 4;
        for(int i = 1; i <= 3; i++) {
            for(int j = 1; j <= 3; j++) {
                BigGraphical bigGraphical = new BigGraphical(bigGraphicalSelectColor, bigGraphicalColor);
                SmallGraphical smallGraphical = new SmallGraphical(smallGraphicalSelectColor, smallGraphicalColor);
                ChildGraphicalView childGraphicalView = new ChildGraphicalView(
                        divisionTransverse * j + (j * 2 - 1) * circleRadius,
                        divisionVertical * i + (i * 2 -1) * circleRadius, bigGraphical, smallGraphical, 3 * j - 1);
                childGraphicalList.add(childGraphicalView);
            }
        }

        //生成箭头坐标
        MathUtil.getArrowCoordinate(childGraphicalList);
    }

    @Override
    public void onDrawInitView(Paint paint, Canvas canvas) {
        handleBigGraphical.onDrawInitView(paint, canvas, childGraphicalList);
        handleSmallGraphical.onDrawInitView(paint, canvas, childGraphicalList);
        handleLineGraphical.onDrawInitView(paint, canvas, childGraphicalList);

        //
        handleArrowGraphical.onDrawInitView(paint, canvas, childGraphicalList);
    }

    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas) {
        handleBigGraphical.onDrawSelectView(paint, canvas, selectPointMap);
        handleSmallGraphical.onDrawSelectView(paint, canvas, selectPointMap);
        handleLineGraphical.onDrawSelectView(paint, canvas, selectPointMap);
        handleArrowGraphical.onDrawSelectView(paint, canvas, selectPointMap);
    }

    @Override
    public void onDrawLineView(Paint paint, Canvas canvas) {
        canvas.drawLine(startX, startY, currencyX, currencyY, paint);
    }

    float startY; //触摸开始的坐标点
    float startX ;
    float currencyX; //移动过程中的坐标点
    float currencyY;
    boolean isValid = false; //是否是有效触摸

    @Override
    public boolean touchDown(MotionEvent event) {
        currencyX = event.getX();
        currencyY = event.getY();
        int index = checkIsAddPoint();
        isValid = index > -1 ? true : false;
        if(isValid) {
            startX = childGraphicalList.get(index).getX();
            startY = childGraphicalList.get(index).getY();
            childGraphicalList.get(index).setIndex(index);
            selectPointMap.put(index, childGraphicalList.get(index));
            return true;
        }
        return false;
    }

    @Override
    public boolean touchMove(MotionEvent event) {
        if(!isValid) { //判断触摸点是否有效
            return false;
        }
        currencyX = event.getX();
        currencyY = event.getY();

//        int index = checkIsAddPoint();
//        //移动过程中有选中的点 && 该点还不在选中的集合中
//        if(index > -1 && !selectPointMap.containsKey(index)) {
//            startX = childGraphicalList.get(index).getX();//重置线的起始点
//            startY = childGraphicalList.get(index).getY();
//             currSelectIndex = index;
//            selectPointMap.put(index, childGraphicalList.get(index)); //将该起始点放入集合中
//        }

        //移动过程中有选中的点 && 该点还不在选中的集合中
        getSelectIndex();
        for(Integer index : indexList) {
            startX = childGraphicalList.get(index).getX();//重置线的起始点
            startY = childGraphicalList.get(index).getY();
            childGraphicalList.get(index).setIndex(index);
            selectPointMap.put(index, childGraphicalList.get(index)); //将该起始点放入集合中
        }

        if(selectPointMap.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    private void getSelectIndex() {
        indexList.clear();
        ChildGraphicalView unSelectPoint = null; //未选中的点坐标 （C点）
        ChildGraphicalView currSelectPoint = null; //上一个选中的点坐标 （B点）
        for(ChildGraphicalView childGraphicalView : selectPointMap.values()) {
            currSelectPoint = childGraphicalView;
        }
        for(int index = 0; index < childGraphicalList.size(); index ++) {
            if(!selectPointMap.containsKey(index)) {
                unSelectPoint = childGraphicalList.get(index); // B 点
                double AB = MathUtil.getDistancePoints(currencyX, currencyY, currSelectPoint.getX(), currSelectPoint.getY());
                double BC = MathUtil.getDistancePoints(unSelectPoint.getX(), unSelectPoint.getY(), currSelectPoint.getX(), currSelectPoint.getY());
                double AC = MathUtil.getDistancePoints(currencyX, currencyY, unSelectPoint.getX(), unSelectPoint.getY());
                if(AB * AB >= (BC * BC + AC * AC)) { //投影在线段上
                    double pointToTargetLineOfHeight = MathUtil.getPointToTargetLineOfHeight(BC, AC, AB);
                    if(pointToTargetLineOfHeight <= circleRadius) {
                        indexList.add(index);
                    } else {
                        //不包含
                    }
                } else { //投影不在线段上
                    double distancePoints = MathUtil.getDistancePoints(currencyX, currencyY, unSelectPoint.getX(), unSelectPoint.getY());
                    if(distancePoints <= circleRadius) {
                        indexList.add(index);
                    } else {
                        //不包含
                    }
                }
            }
        }
    }

    @Override
    public boolean touchUp(MotionEvent event) {
        if(!isValid) { //判读触摸点是否有效
            return false;
        }
        if(selectPointMap.size() < needSelectPointNumber) { //选中的数量小于最低需要选中的点数
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


    /**
     * 判断该触摸点是否在九个触摸圆内
     * @return
     */
    private int checkIsAddPoint() {
        for(int index = 0; index < childGraphicalList.size();) {
            ChildGraphicalView childGraphicalView = childGraphicalList.get(index);
            double distance = Math.sqrt((currencyX - childGraphicalView.getX()) * (currencyX - childGraphicalView.getX()) +
                    (currencyY - childGraphicalView.getY()) * (currencyY - childGraphicalView.getY()));
            if(distance <= circleRadius) {//选中
                return index;
            } else {
                index ++;
            }
        }
        return -1;
    }

    void setGestureListener(GestureListener gestureListener) {
        this.gestureListener = gestureListener;
    }
}
