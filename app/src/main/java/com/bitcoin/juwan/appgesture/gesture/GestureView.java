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
 * Description：TODO
 */
public class GestureView extends View {

    private Paint paint; //画笔

    private int viewWidth; //宽高
    private int viewHeight;

    private DrawView drawView;

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
        drawView = drawView.getInstance();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        checkWidthHeight();
        drawView.initData(viewWidth, viewHeight); //初始化9个点数据
        drawView.onDrawInitView(paint, canvas); //初始化View
        drawView.onDrawSelectView(paint, canvas); //绘制选中的View
        drawView.onDrawLineView(paint, canvas); //绘制连接线
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN : {
                if(drawView.touchDown(event)) {
                    this.postInvalidate();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE : {
                if(drawView.touchMove(event)) {
                    this.postInvalidate();
                }
                break;
            }
            case MotionEvent.ACTION_UP : {
                if(drawView.touchUp(event)) {
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
        this.drawView.setGestureListener(gestureListener);
    }
}
