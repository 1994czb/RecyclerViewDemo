package com.recyclerview.quiet.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.recyclerview.quiet.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/30.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final List<String> list;

    private HashMap<Integer, Boolean> isCheckedHashMap;

    public MyAdapter() {
        list = new ArrayList<>();
        isCheckedHashMap = new HashMap<>();
        for (int i = 0; i < 30; i++) {
            list.add("条目" + i);
            isCheckedHashMap.put(i,false);
        }
    }

    //创建布局和viewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate的时候,需要传入parent和attachToRoot==false; 使用传入三个参数的方法
        View inflate = null;
        RecyclerView.LayoutManager layoutManager = ((RecyclerView) parent).getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_vertical, parent, false);
        } else if (layoutManager instanceof LinearLayoutManager) {
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

    //移除数据
    public void remove(int position) {
        list.remove(position);
        notifyItemRangeRemoved(position, getItemCount());
    }

    //更改数据
    public void updata(int position, String content) {
        list.remove(position);
        list.add(position, content);
        notifyItemChanged(position);
    }
    //全选
    public void selectedAll() {
        Set<Map.Entry<Integer, Boolean>> entries = isCheckedHashMap.entrySet();

        //如果发现有没有选中的item,我就应该去全部选中,这个变量就应该设置成true,否则就是false
        boolean shouldSelectedAll = false;

        //这个for循环就是判断一下接下来要全部选中,还是全部不选中
        for (Map.Entry<Integer, Boolean> entry : entries) {
            Boolean value = entry.getValue();

            //如果有没选中的,那就去全部选中 ,如果发现全都选中了那就全部不选中,
            if (!value) {
                shouldSelectedAll = true;
                break;
            }
        }

        //如果shouldSelectedAll为true说明需要全部选中,
        // 如果为false说明没有没有选中的,已经是是全部选中的状态,需要全部不选中
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(shouldSelectedAll);
        }
        notifyDataSetChanged();
    }

    //反选
    public void revertSelected() {
        Set<Map.Entry<Integer, Boolean>> entries = isCheckedHashMap.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(!entry.getValue());
        }
        notifyDataSetChanged();
    }

    //单选,点击checkBox的时候只选中当前的item,在checkBox的点击事件中调用
    public void singleSelected(int postion) {
        Set<Map.Entry<Integer, Boolean>> entries = isCheckedHashMap.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(false);
        }
        isCheckedHashMap.put(postion, true);
        notifyDataSetChanged();
    }


    //点击事件接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //长按事件接口
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    //设置单击事件接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }

    //设置长按事件接口
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mItemLongClickListener = onItemLongClickListener;
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
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(view, position);

                }
            }
        });


        //item项图片长按事件
        holder.icon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mItemLongClickListener != null) {
                    mItemLongClickListener.onItemLongClick(view, position);
                }
                return true;
            }
        });
        //针对ListView中checkBox错乱的问题,解决办法是只操作数据不操作checkBox,数据改变之后去刷新
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheckedHashMap.put(position, !isCheckedHashMap.get(position));
                notifyDataSetChanged();
            }
        });

        holder.checkBox.setChecked(isCheckedHashMap.get(position));

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
        CheckBox checkBox;


        //findviewById 给控件绑定id
        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            title = itemView.findViewById(R.id.title);
            icon = itemView.findViewById(R.id.icon);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
