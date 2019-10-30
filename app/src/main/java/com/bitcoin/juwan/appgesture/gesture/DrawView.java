package com.bitcoin.juwan.appgesture.gesture;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.bitcoin.juwan.appgesture.gesture.interfaceview.IDrawView;
import com.bitcoin.juwan.appgesture.gesture.interfaceview.ITouch;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName：DrawView
 * Create By：liumengqiang
 * Description：绘制图形
 */
public class DrawView implements IDrawView, ITouch {

    private static DrawView drawView = null;
    //存储九个点
    private static List<PointCoordinate> coordinateList = new ArrayList<>();
    //存储选中的点
    private static LinkedHashMap<Integer, PointCoordinate> selectMap = new LinkedHashMap<>();

    private static final int circleRadius = 50; //圆半径

    private static final int selectCircleRadius = 25; //内圆的半径

    private int needSelectPointNumber = 4; //最小选中点的数量。默认：4个

    private GestureListener gestureListener ;

    private DrawView(){}

    public static DrawView getInstance() {
        if(drawView == null) {
            synchronized (DrawView.class) {
                if(drawView == null) {
                    drawView = new DrawView();
                }
            }
        }
        return drawView;
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
                PointCoordinate pointCoordinate = new PointCoordinate(
                        divisionTransverse * j + (j * 2 - 1) * circleRadius,
                        divisionVertical * i + (i * 2 -1) * circleRadius);
                coordinateList.add(pointCoordinate);
            }
        }
    }

    @Override
    public void onDrawInitView(Paint paint, Canvas canvas) {
        if(coordinateList.size() != 0) { //设置画笔属性
            int color = coordinateList.get(0).getBigGraphical().getUnSelectColor();
            Paint.Style style = coordinateList.get(0).getBigGraphical().getUnSelectStyle();
            paint.setColor(color);
            paint.setStyle(style);//设置画笔属性
            paint.setStrokeWidth(2); //设置划线的宽度
        }
        for(PointCoordinate point : coordinateList) {
            canvas.drawCircle(point.getX(), point.getY(), circleRadius, paint);
        }

        if(coordinateList.size() != 0) { //设置画笔属性
            int color = coordinateList.get(0).getSmallGraphical().getUnSelectColor();
            Paint.Style style = coordinateList.get(0).getSmallGraphical().getUnSelectStyle();
            paint.setColor(color);
            paint.setStyle(style);//设置画笔属性是空心圆
            paint.setStrokeWidth(2); //设置划线的宽度
        }
        for(PointCoordinate point : coordinateList){
            canvas.drawCircle(point.getX(), point.getY(), selectCircleRadius, paint);
        }
    }

    @Override
    public void onDrawSelectView(Paint paint, Canvas canvas) {
        PointCoordinate pointCoordinate = null;
        for(Map.Entry<Integer, PointCoordinate> entry : selectMap.entrySet()) {
            if(pointCoordinate == null) {
                pointCoordinate = entry.getValue();
            } else {
                PointCoordinate currPoint = entry.getValue();
                paint.setStyle(Paint.Style.FILL);
                //画线
                canvas.drawLine(pointCoordinate.getX(), pointCoordinate.getY(), currPoint.getX(), currPoint.getY(), paint);
                pointCoordinate = currPoint;
            }
            //画内圆
            paint.setColor(entry.getValue().getSmallGraphical().getSelectColor());
            paint.setStyle(entry.getValue().getSmallGraphical().getSelectStyle());
            canvas.drawCircle(pointCoordinate.getX(), pointCoordinate.getY(), selectCircleRadius, paint);
            //画外圆
            paint.setColor(entry.getValue().getBigGraphical().getSelectColor());
            paint.setStyle(entry.getValue().getBigGraphical().getSelectStyle());
            canvas.drawCircle(pointCoordinate.getX(), pointCoordinate.getY(), circleRadius, paint);
        }
    }

    @Override
    public void onDrawLineView(Paint paint, Canvas canvas) {
//        paint.setColor();
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

        int index = checkIsAddPoint();
        //移动过程中有选中的点 && 该点还不在选中的集合中
        if(index > -1 && !selectMap.containsKey(index)) {
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
