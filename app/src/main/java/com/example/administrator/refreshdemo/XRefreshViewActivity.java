package com.example.administrator.refreshdemo;

import android.os.Handler;
import android.widget.ListView;

import com.andview.refreshview.XRefreshView;
import com.example.administrator.refreshdemo.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class XRefreshViewActivity extends BaseActivity {
    private ListView lv_list;
    private XRefreshView rv_refreshView;
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
        setContentView(R.layout.activity_xrefreshview);
        lv_list = (ListView) findViewById(R.id.lv_list);
        rv_refreshView = (XRefreshView) findViewById(R.id.rv_refreshView);
        rv_refreshView.setPullRefreshEnable(true);
        rv_refreshView.setPullLoadEnable(true);
        rv_refreshView.setAutoLoadMore(false);
        mListViewAdapter = new ListViewAdapter(getApplicationContext(), mDatas);
        lv_list.setAdapter(mListViewAdapter);


    }

    @Override
    protected void initEnvent() {
        super.initEnvent();
        rv_refreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        rv_refreshView.stopRefresh();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        rv_refreshView.stopLoadMore();
                    }
                }, 2000);
            }

            @Override
            public void onRelease(float direction) {

            }

            @Override
            public void onHeaderMove(double headerMovePercent, int offsetY) {

            }
        });
    }

    @Override
    protected void loadData() {

    }
}
