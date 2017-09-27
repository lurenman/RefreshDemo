package com.example.administrator.refreshdemo.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.example.administrator.refreshdemo.R;

/**
 * 继承自SwipeRefreshLayout,从而实现滑动到底部时上拉加载更多的功能.ListView
 *
 * @author mrsimple
 */
public class RefreshLayout_Listview extends SwipeRefreshLayout implements OnScrollListener {

    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;
    /**
     * listview实例
     */
    private ListView mListView;

    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;

    private OnScrollListener mOnScrollListener;

    /**
     * ListView的加载中footer
     */
    private View mListViewFooter;

    /**
     * 按下时的y坐标
     */
    private int mYDown;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    public boolean isLoading = false;

    /**
     * @param context
     */
    public RefreshLayout_Listview(Context context) {
        this(context, null);
    }

    public RefreshLayout_Listview(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 初始化ListView对象
        if (mListView == null) {
            getListView();
        }
    }

    /**
     * 获取ListView对象
     */
    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                mListView.setOnScrollListener(this);
                // Log.d(VIEW_LOG_TAG, "### 找到listview");
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();

                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();

                break;

            case MotionEvent.ACTION_UP:
                // 抬起

                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {

        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }
        return false;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        if (mLastY > 0 && mListView.getAdapter().getCount() > 0) {
            return (mYDown - mLastY) >= mTouchSlop;
        }
        return false;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (mOnLoadListener != null) {
            // 设置状态
            setLoading(true);
            //
            mOnLoadListener.onLoad();
        }
    }

    /**
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            mListView.addFooterView(mListViewFooter);
        } else {
            mListView.removeFooterView(mListViewFooter);
            mYDown = 0;
            mLastY = 0;
        }
    }

    /**
     * @param loadListener
     */
    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case OnScrollListener.SCROLL_STATE_FLING:
                if (mOnScrollListener != null)
                    mOnScrollListener.onScrollStateChanged(mListView, OnScrollListener.SCROLL_STATE_FLING);
                break;
            case OnScrollListener.SCROLL_STATE_IDLE:
                if (mOnScrollListener != null)
                    mOnScrollListener.onScrollStateChanged(mListView, OnScrollListener.SCROLL_STATE_IDLE);
                break;
            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                if (mOnScrollListener != null)
                    mOnScrollListener.onScrollStateChanged(mListView, OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 滚动时到了最底部也可以加载更多
        if (mOnScrollListener != null)
            mOnScrollListener.onScroll(mListView, firstVisibleItem, visibleItemCount, totalItemCount);
        if (canLoad()) {
            loadData();
        }
    }

    /**
     * 加载更多的监听器
     *
     * @author mrsimple
     */
    public static interface OnLoadListener {
        public void onLoad();
    }

    public void setOnScrollChangeListener(OnScrollListener onScrollListener) {
        // TODO 自动生成的方法存根
        mOnScrollListener = onScrollListener;
    }



    // private float startY;
    // private float startX;
    // // 记录viewPager是否拖拽的标记
    // public boolean mIsVpDragger;
    //
    // @Override
    // public boolean onInterceptTouchEvent(MotionEvent ev) {
    // int action = ev.getAction();
    // switch (action) {
    // case MotionEvent.ACTION_DOWN:
    // // 记录手指按下的位置 startY =
    // ev.getY();
    // startX = ev.getX();
    // // 初始化标记
    // mIsVpDragger = false;
    // break;
    // case MotionEvent.ACTION_MOVE:
    // // 如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
    // if (mIsVpDragger) {
    //// this.setEnabled(false);
    // return false;
    // }
    // // 获取当前手指位置
    // float endY = ev.getY();
    // float endX = ev.getX();
    // float distanceX = Math.abs(endX - startX);
    // float distanceY = Math.abs(endY - startY);
    // // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
    // if (distanceX >= distanceY) {
    // mIsVpDragger = true;
    //// this.setEnabled(false);
    // return false;
    // }
    // break;
    // case MotionEvent.ACTION_UP:
    // case MotionEvent.ACTION_CANCEL:
    // // 初始化标记
    // mIsVpDragger = false;
    // break;
    // }
    // this.setEnabled(true);
    // // 如果是Y轴位移大于X轴，事件交给swipeRefreshLayout处理。
    // return super.onInterceptTouchEvent(ev);
    // }
}
