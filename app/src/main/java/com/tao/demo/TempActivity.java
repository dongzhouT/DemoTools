package com.tao.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tao.demo.widget.TempImageview;

public class TempActivity extends Activity {
    ScrollView scrollView;
    CustomTempView view1;
    TempImageview tempImageview;
    TextView tvTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        scrollView = findViewById(R.id.scrollView);
        view1 = findViewById(R.id.view1);
        view1.setmScrollView(scrollView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("tagggg111", Thread.currentThread().toString());
                Looper.prepare();
                Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Log.e("tagggg222", Thread.currentThread().toString());
                    }
                };
                handler.sendMessageDelayed(Message.obtain(), 2000);
                Looper.loop();
                Looper.myQueue();
            }
        }).start();
        tvTemp = findViewById(R.id.tv_temp);
        tempImageview = findViewById(R.id.id_tp_temp);
        tempImageview.setChangeListener(new TempImageview.OnTempChangeListener() {
            @Override
            public void onTempChange(String temp) {
                tvTemp.setText("" + temp);
            }
        });
//        tempImageview.setTempWithAnim(26.0);
        tempImageview.setTempWithAnim(26);
//        tempImageview.setEnabled(false);
    }
}
