package com.example.administrator.refreshdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.refreshdemo.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/19.
 */

public class ListViewAdapter extends BaseAdapter {
    private List<String> mDatas;
    private Context mContext;

    public ListViewAdapter(Context context, List<String> data) {
        mDatas = data;
        mContext = context;
    }


    @Override
    public int getCount() {
        return null == mDatas ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.list_item, parent, false);
            holder.tv_Title = (TextView) convertView.findViewById(R.id.tv_Title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_Title.setText(mDatas.get(position).toString());


        return convertView;
    }

    private class ViewHolder {
        private TextView tv_Title;
    }
}
