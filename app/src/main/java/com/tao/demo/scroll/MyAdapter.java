package com.tao.demo.scroll;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tao.demo.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
    List<String> dataList;

    public MyAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent, false);
        return new VH(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tv.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView tv;

        public VH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_item);
        }
    }
}
