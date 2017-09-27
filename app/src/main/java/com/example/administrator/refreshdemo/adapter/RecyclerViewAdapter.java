package com.example.administrator.refreshdemo.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.refreshdemo.R;

import java.util.List;
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Context mContext;
    private List<String> mDatas;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public RecyclerViewAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        mDatas = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view =
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_footer, parent, false);
            return new FootViewHolder(view);
        }
        else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final View itemView = ((ItemViewHolder) holder).mView;
            ((ItemViewHolder) holder).tv_Title.setText(mDatas.get(position));
            //这块也可以setTag这种数据和View的绑定
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnItemClickListener) {
                        mOnItemClickListener.onItemClick(itemView, mDatas.get(position));
                    }
                }
            });
        } else {
            //此时holder属于FootViewHolder

        }
    }
    @Override
    public int getItemCount() {
        return null == mDatas ? 0 : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView tv_Title;

        public ItemViewHolder(View view) {
            super(view);
            mView = view;
            tv_Title = (TextView) mView.findViewById(R.id.tv_Title);
        }
    }

    public static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }

    //添加item事件点击接口
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String datas);
    }

    /* 我们只要修改后数据用Adapter的notifyDatasetChange一下就可以更新界面。
    然而在RecyclerView中还有一些更高级的用法*/
    //添加数据：
    //添加数据：
    public void addItem(String content, int position) {
        mDatas.add(position, content);
        notifyItemInserted(position); //Attention!
        notifyItemRangeChanged(position, getItemCount());
    }

    //删除数据：
//    先remove,再notifyItemRemoved， 最后再notifyItemRangeChanged
//    remove：把数据从list中remove掉，
//    notifyItemRemoved：显示动画效果
//    notifyItemRangeChanged：对于被删掉的位置及其后range大小范围内的view进行重新onBindViewHolder
    public void removeItem(String model) {
        int position = mDatas.indexOf(model);
        mDatas.remove(position);
        notifyItemRemoved(position);//Attention!
        //notifyDataSetChanged();
        notifyItemRangeChanged(position, getItemCount());
    }

    public void removeItem(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);//Attention!
        //notifyDataSetChanged();
        notifyItemRangeChanged(position, getItemCount());
    }

    //LRRecycleerView 中的
//    public void addAll(Collection<T> list) {
//        int lastIndex = this.mDataList.size();
//        if (this.mDataList.addAll(list)) {
//            notifyItemRangeInserted(lastIndex, list.size());
//        }
//    }
//
    public void remove(int position) {
        this.mDatas.remove(position);
        notifyItemRemoved(position);

        if (position != (mDatas.size())) { // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position, this.mDatas.size() - position);
        }
    }

}
