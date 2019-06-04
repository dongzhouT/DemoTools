package com.tao.demo.scroll;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.tao.demo.R;

import java.util.ArrayList;
import java.util.List;

public class ScrollActivity extends Activity {
    //    MyRecyclerView rv;
    RecyclerView rv;
    MyAdapter myAdapter;
    List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        rv = findViewById(R.id.rv_list);
        init();
    }

    private void init() {
        for (int i = 0; i < 30; i++) {
            String rd = Math.random() > 0.5 ? "1" : "rrrrrrrrr";
            dataList.add("item-" + rd + i);
        }
        myAdapter = new MyAdapter(dataList);
//        rv.setLayoutManager(new LinearLayoutManager(this));
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(myAdapter);
    }
}
