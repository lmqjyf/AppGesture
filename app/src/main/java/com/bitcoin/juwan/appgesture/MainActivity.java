package com.bitcoin.juwan.appgesture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bitcoin.juwan.appgesture.gesture.listener.GestureListener;
import com.bitcoin.juwan.appgesture.gesture.GestureView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        GestureView gestureView = findViewById(R.id.gesture_view);
        gestureView.setGestureListener(new GestureListener() {
            @Override
            public void onComplete(List<Integer> list) {
                Toast.makeText(MainActivity.this, "选中", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(MainActivity.this, "个数小于四个", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

