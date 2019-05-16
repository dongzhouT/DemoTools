package com.tao.demo.mvpDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tao.demo.R;

public class MyActivity extends Activity implements MyContract.IView {
    MyContract.IMyPresent present;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        new MyPresent(this, this);
        textView = findViewById(R.id.tv_textview);
        button = findViewById(R.id.btn_click);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.sendData();
            }
        });
    }

    @Override
    public void setViewText(String text) {
        textView.setText(text);
    }

    @Override
    public void setPresent(MyContract.IMyPresent present) {
        this.present = present;
    }
}
