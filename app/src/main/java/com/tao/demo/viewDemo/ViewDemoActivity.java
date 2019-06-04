package com.tao.demo.viewDemo;

import android.app.Activity;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;

import com.blankj.utilcode.util.ScreenUtils;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.tao.demo.R;

import java.util.ArrayList;
import java.util.List;

public class ViewDemoActivity extends Activity {
    RecyclerView mRecyclerView;
    MyAdapter myAdapter;
    List<String> dataList = new ArrayList<>();
    AutoCompleteTextView autoTv;
    ArrayAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_demo);
        mRecyclerView = findViewById(R.id.id_recycler);
        initData();
        myAdapter = new MyAdapter(dataList);
        RecyclerView.LayoutManager layoutManager = new FlexboxLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(myAdapter);
        autoTv = findViewById(R.id.auto_textview);
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, dataList);
        autoTv.setAdapter(dataAdapter);
        autoTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initData() {
        dataList.add("1111111111");
        dataList.add("222");
        dataList.add("3333333");
        dataList.add("444444");
        dataList.add("5");
        dataList.add("6");
        dataList.add("7777777777777777777");
        dataList.add("8888888");
        dataList.add("999999");
    }
}
