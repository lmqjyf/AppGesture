package com.bitcoin.juwan.appgesture.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bitcoin.juwan.appgesture.R;
import com.liumengqiang.gesturelock.GestureView;
import com.liumengqiang.gesturelock.listener.GestureListener;

import java.util.List;

public class SetGestureActivity extends AppCompatActivity {

    private static final String TAG = "SetGestureActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_gesture);
        GestureView gestureView = findViewById(R.id.gesture_view);
        gestureView.setGestureValue("1234"); //设置需要校验的值
        gestureView.setGestureListener(new GestureListener() {
            @Override
            public void valueDisaccord() {
                Toast.makeText(SetGestureActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void transitionStatus() {
                Toast.makeText(SetGestureActivity.this, "请再一次输入密码", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SetGestureActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(SetGestureActivity.this, "输入个数小于4", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
