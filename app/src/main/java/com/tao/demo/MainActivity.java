package com.tao.demo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.tao.demo.mvpDemo.MyActivity;
import com.tao.demo.nfcDemo.NFCActivity;
import com.tao.demo.rxDemo.RxDemoActivity;
import com.tao.demo.rxpermission.RxpermissionDemoActivity;
import com.tao.demo.scroll.ScrollActivity;
import com.tao.demo.viewpager.ViewPagerActivity;

import javax.xml.datatype.Duration;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_show).setOnClickListener(this);
        findViewById(R.id.btn_show_custom).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show:
                showToast();
                break;
            case R.id.btn_show_custom:
                showCustom();
                break;
        }

    }

    private void showCustom() {
//        Intent intent = new Intent(this, TempActivity.class);
//        Intent intent = new Intent(this, ScrollActivity.class);
//        Intent intent = new Intent(this, ViewPagerActivity.class);
        startNewActivity(TempActivity.class);
    }

    private void showToast() {
        Toast toast = new Toast(this);
        View rootView = LayoutInflater.from(this).inflate(R.layout.layout_toast, null);
        toast.setView(rootView);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void startNewActivity(Class<?> T) {
        Intent intent = new Intent(this, T);
        startActivity(intent);
    }

    public void showScrollDemo(View view) {
        startNewActivity(ScrollActivity.class);
    }

    public void showViewpager(View view) {
        startNewActivity(ViewPagerActivity.class);
    }

    public void showNFCDemo(View view) {
        startNewActivity(NFCActivity.class);
    }

    public void showRxDemo(View view) {
        startNewActivity(RxDemoActivity.class);
    }

    public void showRxPermissionDemo(View view) {
        startNewActivity(RxpermissionDemoActivity.class);
    }
}
