package com.example.administrator.refreshdemo;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.example.administrator.refreshdemo.adapter.ListViewAdapter;
import com.example.administrator.refreshdemo.views.RefreshLayout_Listview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */

public class RefreshLayoutListViewActivity extends BaseActivity {
    private ListView lv_list;
    private RefreshLayout_Listview swipeLayout;
    private ListViewAdapter mListViewAdapter;
    private List<String> mDatas = new ArrayList<String>();

    @Override
    protected void initVariables() {
        for (int i = 0; i < 10; i++) {
            mDatas.add("item" + i);
        }
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_refreshlayout_listview);
        lv_list = (ListView) findViewById(R.id.lv_list);
        swipeLayout = (RefreshLayout_Listview) findViewById(R.id.swipe_layout);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mListViewAdapter = new ListViewAdapter(getApplicationContext(), mDatas);
        lv_list.setAdapter(mListViewAdapter);
    }

    @Override
    protected void initEnvent() {
        super.initEnvent();

        //刷新
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setLoading(false);//防止同时刷新
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stopRefreshing();
                    }
                }, 2000);
            }
        });
        //加载更多
        swipeLayout.setOnLoadListener(new RefreshLayout_Listview.OnLoadListener() {
            @Override
            public void onLoad() {
                swipeLayout.setRefreshing(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        stopRefreshing();
                    }
                }, 2000);
            }
        });

    }

    @Override
    protected void loadData() {

    }

    //停止刷新的方法
    private void stopRefreshing() {
        if (swipeLayout.isRefreshing())
            swipeLayout.setRefreshing(false);
        if (swipeLayout.isLoading) {
            swipeLayout.setLoading(false);
        }
    }
}
