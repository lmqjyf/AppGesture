package com.bitcoin.juwan.appgesture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName：GestureView
 * Create By：liumengqiang
 * Description：TODO
 */
public class GestureView extends View {

    private Paint paint; //画笔

    private int viewWidth; //宽高
    private int viewHeight;

    private static final int circleRadius = 50; //圆半径

    //存储九个点
    private static List<PointCoordinate> coordinateList = new ArrayList<>();
    //存储选中的点
    private static LinkedHashMap<Integer, PointCoordinate> selectMap = new LinkedHashMap<>();

    public GestureView(Context context) {
        super(context);
        this.init();
    }

    public GestureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public GestureView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        checkWidthHeight();
        //设置点
        checkLinkMap();
        //画圆
        drawCircle(canvas);
        paint.setStyle(Paint.Style.FILL);
        //画选中的线
        drawSelectView(canvas);
        //手势
        canvas.drawLine(startX, startY, currencyX, currencyY, paint);
    }

    private void drawSelectView(Canvas canvas) {
        PointCoordinate pointCoordinate = null;
        for(Map.Entry<Integer, PointCoordinate> entry : selectMap.entrySet()) {
            if(pointCoordinate == null) {
                pointCoordinate = entry.getValue();
            } else {
                PointCoordinate currPoint = entry.getValue();
                canvas.drawLine(pointCoordinate.x, pointCoordinate.y, currPoint.x, currPoint.y, paint);
                pointCoordinate = currPoint;
            }
            canvas.drawCircle(pointCoordinate.x, pointCoordinate.y, 25, paint);
        }
    }

    float startY;
    float startX ;

    float currencyX;
    float currencyY;

    boolean isValid = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN : {
                startX = event.getX();
                startY = event.getY();
                currencyX = event.getX();
                currencyY = event.getY();
                int index = checkIsAddPoint();
                isValid = index > -1 ? true : false;
                if(isValid) {
                    selectMap.put(index, coordinateList.get(index));
                    this.postInvalidate();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE : {
                if(!isValid) {
                    break;
                }
                currencyX = event.getX();
                currencyY = event.getY();

                int index = checkIsAddPoint();

                if(index > -1 && !selectMap.containsKey(index)) {
                    startX = coordinateList.get(index).x;
                    startY = coordinateList.get(index).y;
                    selectMap.put(index, coordinateList.get(index));
                }

                if(selectMap.size() != 0) {
                    //重新画图
                    this.postInvalidate();
                }
                break;
            }
            case MotionEvent.ACTION_UP : {
                if(!isValid) {
                    break;
                }
                currencyX = 0;
                currencyY = 0;
                startX = 0;
                startY = 0;
                selectMap.clear();
                this.postInvalidate();
                break;
            }
        }
        return true;
    }

    private int checkIsAddPoint() {
        for(int index = 0; index < coordinateList.size();) {
            PointCoordinate pointCoordinate = coordinateList.get(index);
            double distance = Math.sqrt((currencyX - pointCoordinate.x) * (currencyX - pointCoordinate.x) +
                    (currencyY - pointCoordinate.y) * (currencyY - pointCoordinate.y));
            if(distance <= circleRadius) {//选中
                return index;
            } else {
                index ++;
            }
        }
        return -1;
    }

    private void drawCircle(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);//设置画笔属性是空心圆
        paint.setStrokeWidth(2); //设置划线的宽度
        for(PointCoordinate point : coordinateList) {
            canvas.drawCircle(point.x, point.y, circleRadius, paint);
        }
    }

    private void checkLinkMap() {
        if(coordinateList.size() != 0) {
            return;
        }
        //横向间隔
        int divisionTransverse = (viewWidth - 6 * circleRadius) / 4;
        //竖向间隔
        int divisionVertical = (viewHeight - 6 * circleRadius) / 4;

        for(int i = 1; i <= 3; i++) {
            for(int j = 1; j <= 3; j++) {
                PointCoordinate pointCoordinate = new PointCoordinate();
                pointCoordinate.x = divisionTransverse * j + (j * 2 - 1) * circleRadius;
                pointCoordinate.y = divisionVertical * i + (i * 2 -1) * circleRadius;
                coordinateList.add(pointCoordinate);
            }
        }
    }

    private void checkWidthHeight() {
        if(this.viewWidth == 0) {
            this.viewWidth = getWidth();
            this.viewHeight = getHeight();
        }
    }

    class PointCoordinate {
        float x;
        float y;
    }
}
