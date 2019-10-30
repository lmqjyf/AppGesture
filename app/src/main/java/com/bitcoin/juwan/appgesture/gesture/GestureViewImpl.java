package com.bitcoin.juwan.appgesture.gesture;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.bitcoin.juwan.appgesture.R;
import com.bitcoin.juwan.appgesture.gesture.graphical.BigGraphical;
import com.bitcoin.juwan.appgesture.gesture.graphical.SmallGraphical;
import com.bitcoin.juwan.appgesture.gesture.handledraw.HandleBigGraphical;
import com.bitcoin.juwan.appgesture.gesture.handledraw.HandleLineGraphical;
import com.bitcoin.juwan.appgesture.gesture.handledraw.HandleSmallGraphical;
import com.bitcoin.juwan.appgesture.gesture.handledraw.IHandleDraw;
import com.bitcoin.juwan.appgesture.gesture.interfaceview.IDrawView;
import com.bitcoin.juwan.appgesture.gesture.interfaceview.ITouch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * FileName：GestureViewImpl
 * Create By：liumengqiang
 * Description：When I wrote this, only God and I  understood what I was doing. Now, God only knows
 */
public class GestureViewImpl implements IDrawView, ITouch {

    //存储九个点
    private static List<PointCoordinate> coordinateList = new ArrayList<>();
    //存储选中的点
    private static LinkedHashMap<Integer, PointCoordinate> selectMap = new LinkedHashMap<>();
    private static List<Integer> indexList = new ArrayList<>();

    private static final int circleRadius = 50; //圆半径

    private static final int selectCircleRadius = 25; //内圆的半径

    private int needSelectPointNumber = 4; //最小选中点的数量。默认：4个

    private GestureListener gestureListener ;

    private int bigGraphicalColor = Color.BLUE;
    private int bigGraphicalSelectColor = Color.BLUE;
    private int smallGraphicalColor = Color.TRANSPARENT;
    private int smallGraphicalSelectColor = Color.BLUE;
    private int lineColor = Color.BLUE;

    protected IHandleDraw handleBigGraphical = new HandleBigGraphical();
    protected IHandleDraw handleSmallGraphical = new HandleSmallGraphical();
    protected IHandleDraw handleLineGraphical = new HandleLineGraphical();

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
     * 生成九个坐标
     */
    @Override
    public void initData(int viewWidth, int viewHeight) {
        if(coordinateList.size() != 0) {
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
                PointCoordinate pointCoordinate = new PointCoordinate(
                        divisionTransverse * j + (j * 2 - 1) * circleRadius,
                        divisionVertical * i + (i * 2 -1) * circleRadius, bigGraphical, smallGraphical);
                coordinateList.add(pointCoordinate);
            }
        }
    }

    @Override
    public void onDrawInitView(Paint paint, Canvas canvas) {
        handleBigGraphical.onDrawInitView(paint, canvas, coordinateList);
        handleSmallGraphical.onDrawInitView(paint, canvas, coordinateList);
        handleLineGraphical.onDrawInitView(paint, canvas, coordinateList);
    }

    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas) {
        handleBigGraphical.onDrawSelectView(paint, canvas, selectMap);
        handleSmallGraphical.onDrawSelectView(paint, canvas, selectMap);
        handleLineGraphical.onDrawSelectView(paint, canvas, selectMap);
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
            startX = coordinateList.get(index).getX();
            startY = coordinateList.get(index).getY();
            selectMap.put(index, coordinateList.get(index));
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
//        if(index > -1 && !selectMap.containsKey(index)) {
//            startX = coordinateList.get(index).getX();//重置线的起始点
//            startY = coordinateList.get(index).getY();
//            selectMap.put(index, coordinateList.get(index)); //将该起始点放入集合中
//        }

        //移动过程中有选中的点 && 该点还不在选中的集合中
        getSelectIndex();
        for(Integer index : indexList) {
            startX = coordinateList.get(index).getX();//重置线的起始点
            startY = coordinateList.get(index).getY();
            selectMap.put(index, coordinateList.get(index)); //将该起始点放入集合中
        }

        if(selectMap.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    private void getSelectIndex() {
        indexList.clear();
        PointCoordinate unSelectPoint = null; //未选中的点坐标
        PointCoordinate currSelectPoint = null; //上一个选中的点坐标
        for(PointCoordinate pointCoordinate : selectMap.values()) {
            currSelectPoint = pointCoordinate;
        }
        for(int index = 0; index < coordinateList.size(); index ++) {
            if(!selectMap.containsKey(index)) {
                /**
                 * 计算点投影是否在线段上
                 */
                unSelectPoint = coordinateList.get(index);
                double AB = MathUtil.getDistancePoints(currencyX, currencyX, currSelectPoint.getX(), currSelectPoint.getY());
                double BC = MathUtil.getDistancePoints(unSelectPoint.getX(), unSelectPoint.getY(), currSelectPoint.getX(), currSelectPoint.getY());
                double AC = MathUtil.getDistancePoints(currencyX, currencyX, unSelectPoint.getX(), unSelectPoint.getY());
                if(AB * AB >= (BC * BC + AC * AC)) { //投影在线段上
                    double pointToTargetLineOfHeight = MathUtil.getPointToTargetLineOfHeight(BC, AC, AB);
                    Log.e("-----:", " " + pointToTargetLineOfHeight + " " + circleRadius + " ");
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
//                        Log.e("--投影不在线段上---:", " " + AB + " " + BC + " " + AC);
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
        if(selectMap.size() < needSelectPointNumber) { //选中的数量小于最低需要选中的点数
            gestureListener.onFailed(); //回调
        } else { //大于最低需要选中的点数
            gestureListener.onComplete(new ArrayList<>(selectMap.keySet()));
        }
        currencyX = 0;
        currencyY = 0;
        startX = 0;
        startY = 0;
        selectMap.clear();
        return true;
    }


    /**
     * 判断该触摸点是否在九个触摸圆内
     * @return
     */
    private int checkIsAddPoint() {
        for(int index = 0; index < coordinateList.size();) {
            PointCoordinate pointCoordinate = coordinateList.get(index);
            double distance = Math.sqrt((currencyX - pointCoordinate.getX()) * (currencyX - pointCoordinate.getX()) +
                    (currencyY - pointCoordinate.getY()) * (currencyY - pointCoordinate.getY()));
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
