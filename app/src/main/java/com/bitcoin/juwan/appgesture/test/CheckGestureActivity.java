package com.bitcoin.juwan.appgesture.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bitcoin.juwan.appgesture.R;
import com.liumengqiang.gesturelock.GestureView;
import com.liumengqiang.gesturelock.listener.GestureListener;

import java.util.List;

public class CheckGestureActivity extends AppCompatActivity {

    private final static String TAG = "CheckGestureActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_gesture);
        GestureView gestureView = findViewById(R.id.gesture_view);
        //方法1：如果XML不声明：app:gestureType="check_gesture" 可如下设置：
//        CheckGestureProcessor checkGestureHandleData = new CheckGestureProcessor();
//        checkGestureHandleData.setGestureValue("1234");
//        gestureView.setProcessor(checkGestureHandleData);//设置手势结果处理器
        //方法2：
        gestureView.setGestureValue("1234");
        gestureView.setGestureListener(new GestureListener() {
            @Override
            public void onFailed() {
                Toast.makeText(CheckGestureActivity.this, "输入密码错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart() {
                Log.e(TAG , "初始化完成");
            }

            @Override
            public void onPointNumberChange(int selectIndex) {
                Log.e(TAG, "点被选中");
            }

            @Override
            public void onComplete(List<Integer> list) {
                Toast.makeText(CheckGestureActivity.this, "校验成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPointLessThanSetting() {
                Toast.makeText(CheckGestureActivity.this, "个数小于四个", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
