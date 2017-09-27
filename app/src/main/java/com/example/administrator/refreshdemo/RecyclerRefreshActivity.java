package com.example.administrator.refreshdemo;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.refreshdemo.adapter.RecyclerViewAdapter;
import com.example.administrator.refreshdemo.divider.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 * 加载更多的时候没有往回取消这说，感觉这种方法不咋实用
 *
 */

public class RecyclerRefreshActivity extends BaseActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private List<String> mDatas = new ArrayList<String>();
    private  boolean isLoading=false;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void initVariables() {
        for (int i = 0; i < 10; i++) {
            mDatas.add("item" + i);
        }
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_recycler_refresh);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 20, getResources().getColor(R.color.blue)));
        mRecyclerViewAdapter = new RecyclerViewAdapter(mContext, mDatas);
        recyclerView.setAdapter(mRecyclerViewAdapter);


    }

    @Override
    protected void initEnvent() {
        super.initEnvent();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.clear();
                        List<String> array = new ArrayList<String>();
                        for (int i = 0; i < 15; i++) {
                            array.add("item" + i);
                        }
                        mDatas.addAll(array);
                        mRecyclerViewAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        //mRecyclerViewAdapter.notifyItemRemoved(mRecyclerViewAdapter.getItemCount());
                    }
                }, 2000);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mRecyclerViewAdapter.getItemCount()) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        mRecyclerViewAdapter.notifyItemRemoved(mRecyclerViewAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < 3; i++) {
                                    mDatas.add("itemload" + i);
                                }
                                mRecyclerViewAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                                //mRecyclerViewAdapter.notifyItemRemoved(mRecyclerViewAdapter.getItemCount());
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 1500);
                    }
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
