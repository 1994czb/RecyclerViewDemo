package com.recyclerview.quiet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.recyclerview.quiet.adapter.MyAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //listview展示方式
        linearLayoutManager = new LinearLayoutManager(this);

        //GridView展示方式
        gridLayoutManager = new GridLayoutManager(this, 3);
        //瀑布流的形式
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new MyAdapter());
    }


    //点击按钮切换布局
    public void onClick(View view) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager==null){
            return;
        }
        //if和elseif是有先后顺序的,先判断范围小的,然后再判断范围大的,
        //因为GridLayoutManager 是继承 LinearLayoutManager ,所以他本质上也是LinearLayoutManager,
        //所以不能先判断是否是LinearLayoutManager (LinearLayoutManager范围大)
        if (layoutManager instanceof GridLayoutManager){
            recyclerView.setLayoutManager(linearLayoutManager);
        }else  if (layoutManager instanceof LinearLayoutManager){
            recyclerView.setLayoutManager(gridLayoutManager);
        }

    }
}
