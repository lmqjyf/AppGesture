package com.bitcoin.juwan.appgesture.gesture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * FileName：GestureView
 * Create By：liumengqiang
 * Description：
 */
public class GestureView extends View {

    private Paint paint; //画笔

    private int viewWidth; //宽高
    private int viewHeight;

    private GestureViewImpl viewImpl;

    public GestureView(Context context) {
        super(context);
        this.init(null);
    }

    public GestureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(attrs);
    }

    public GestureView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(attrs);
    }

    private void init(AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        viewImpl = new GestureViewImpl(getContext(), attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        checkWidthHeight();
        viewImpl.initData(viewWidth, viewHeight); //初始化9个点数据
        viewImpl.onDrawInitView(paint, canvas); //初始化View
        viewImpl.onDrawSelectView(paint, canvas); //绘制选中的View
        viewImpl.onDrawLineView(paint, canvas); //绘制连接线
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN : {
                if(viewImpl.touchDown(event)) {
                    this.postInvalidate();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE : {
                if(viewImpl.touchMove(event)) {
                    this.postInvalidate();
                }
                break;
            }
            case MotionEvent.ACTION_UP : {
                if(viewImpl.touchUp(event)) {
                    this.postInvalidate();
                }
                break;
            }
        }
        return true;
    }

    private void checkWidthHeight() {
        if (this.viewWidth == 0) {
            this.viewWidth = getWidth();
            this.viewHeight = getHeight();
        }
    }

    public void setGestureListener(GestureListener gestureListener) {
        this.viewImpl.setGestureListener(gestureListener);
    }
}