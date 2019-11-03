package com.liumengqiang.gesturelock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.liumengqiang.gesturelock.datahandle.GestureViewImpl;
import com.liumengqiang.gesturelock.datahandle.HandleCoordinate;
import com.liumengqiang.gesturelock.datahandle.gesturetype.IProcessor;
import com.liumengqiang.gesturelock.interfaceview.IGraphicalView;
import com.liumengqiang.gesturelock.listener.GestureListener;
import com.liumengqiang.gesturelock.model.AttrsModel;

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

    public boolean isUserTouch = false;

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
        AttrsModel attrsModel = new AttrsModel(getContext().obtainStyledAttributes(attrs, R.styleable.GestureView));
        HandleCoordinate handleCoordinate = new HandleCoordinate(attrsModel);
        viewImpl = new GestureViewImpl(this, attrsModel, handleCoordinate);
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
                viewImpl.touchDown(event);
                break;
            }
            case MotionEvent.ACTION_MOVE : {
                viewImpl.touchMove(event);
                break;
            }
            case MotionEvent.ACTION_UP : {
                viewImpl.touchUp(event);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return isUserTouch ? true : super.dispatchTouchEvent(event);
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

    public void setProcessor(IProcessor iProcessor) {
        viewImpl.setProcessor(iProcessor);
    }

    /**
     * 自定义绘制View
     * @param iHandleDraw
     */
    public void setHandleBigGraphical(IGraphicalView iHandleDraw) {
        viewImpl.handleBigGraphical = iHandleDraw;
        this.postInvalidate();
    }

    public void setHandleSmallGraphical(IGraphicalView iHandleDraw) {
        viewImpl.handleSmallGraphical= iHandleDraw;
        this.postInvalidate();
    }

    public void setHandleLineGraphical(IGraphicalView iHandleDraw) {
        viewImpl.handleLineGraphical = iHandleDraw;
        this.postInvalidate();
    }

    public void setGestureValue(String gestureValue) {
        viewImpl.setGestureValue(gestureValue);
    }
}
