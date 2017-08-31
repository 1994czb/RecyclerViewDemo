package com.recyclerview.quiet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.recyclerview.quiet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private final List<String> list;

    public MyAdapter() {
        list = new ArrayList<>();
        for (int i=0;i<30;i++){
            list.add("条目"+i);
        }
    }

    //创建布局和viewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, null);
        return new MyViewHolder(inflate);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(list.get(position));
        if (position%2==1){
            holder.icon.setImageResource(R.drawable.c);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            icon=itemView.findViewById(R.id.icon);
        }
    }
}
