package com.recyclerview.quiet.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
        //inflate的时候,需要传入parent和attachToRoot==false; 使用传入三个参数的方法
         View inflate=null;
        RecyclerView.LayoutManager layoutManager = ((RecyclerView) parent).getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_vertical, parent, false);
        }else if (layoutManager instanceof LinearLayoutManager){
             inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        }

        return new MyViewHolder(inflate);
    }
    //增加的方法
    public void add(int position) {

        //改变数据
        list.add(position + 1, "这是新加的");

        //调用它带的方法去刷新
        notifyItemRangeChanged(position + 1, getItemCount());
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRangeRemoved(position, getItemCount());
    }


    public void updata(int position, String content) {
        list.remove(position);
        list.add(position, content);
        notifyItemChanged(position);
    }

    //点击事件接口
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    //长按事件接口
    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    //设置单击事件接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mItemClickListener=onItemClickListener;
    }
    //设置长按事件接口
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        mItemLongClickListener=onItemLongClickListener;
    }

    //绑定数据
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(list.get(position));

       /* //整个item条目的点击
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v, position);
                }
            }
        });*/

        /*//瀑布流必须判定item项
        if (position%2==1){
            holder.icon.setImageResource(R.drawable.c);
        }*/


        //item项图片点击事件
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  暴露一个单击回调接口
                if (mItemClickListener!=null){
                    mItemClickListener.onItemClick(view,position);

                }
            }
        });



        //item项图片长按事件
        holder.icon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mItemLongClickListener!=null){
                    mItemLongClickListener.onItemLongClick(view,position);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //把整个view放到viewHolder中，实现点击条目监听事件
        View itemView;
        TextView title;
        ImageView icon;

        //findviewById 给控件绑定id
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            title=itemView.findViewById(R.id.title);
            icon=itemView.findViewById(R.id.icon);
        }
    }
}
