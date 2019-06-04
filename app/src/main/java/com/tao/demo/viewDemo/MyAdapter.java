package com.tao.demo.viewDemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tao.demo.R;

import java.util.List;

/**
 * @author taodzh
 * create at 2019/6/1
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyVH> {
    List<String> datas;

    public MyAdapter(List<String> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, null, false);
        return new MyVH(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVH holder, int position) {
        holder.tv.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MyVH extends RecyclerView.ViewHolder {
        TextView tv;

        public MyVH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_item);
        }
    }
}
