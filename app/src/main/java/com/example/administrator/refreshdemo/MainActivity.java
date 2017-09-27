package com.example.administrator.refreshdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_RefreshLayout_ListView;
    private TextView tv_RecyclerRefresh;
    private TextView tv_XRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }

    private void initViews() {
        tv_RefreshLayout_ListView = (TextView) findViewById(R.id.tv_RefreshLayout_ListView);
        tv_RecyclerRefresh = (TextView) findViewById(R.id.tv_RecyclerRefresh);
        tv_XRefreshView = (TextView) findViewById(R.id.tv_XRefreshView);
    }

    private void initEvents() {
        tv_RefreshLayout_ListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RefreshLayoutListViewActivity.class);
                startActivity(intent);
            }
        });
        tv_RecyclerRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerRefreshActivity
                        .class);
                startActivity(intent);
            }
        });
        tv_XRefreshView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,XRefreshViewActivity
                        .class);
                startActivity(intent);
            }
        });
    }
}
