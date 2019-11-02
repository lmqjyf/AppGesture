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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_gesture);
        GestureView gestureView = findViewById(R.id.gesture_view);
        gestureView.setGestureValue("1234");
        gestureView.setGestureListener(new GestureListener() {
            @Override
            public void passwordDisaccord() {
                Toast.makeText(CheckGestureActivity.this, "输入密码错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart() {
                Log.e("----:" , "初始化完成");
            }

            @Override
            public void onPointNumberChange(int selectIndex) {
                Log.e("-----:", "点被选中");
            }

            @Override
            public void onComplete(List<Integer> list) {
                Toast.makeText(CheckGestureActivity.this, "校验成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(CheckGestureActivity.this, "个数小于四个", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
