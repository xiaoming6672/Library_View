package com.zhang.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.zhang.library.adapter.BaseRecyclerAdapter;
import com.zhang.library.adapter.viewholder.base.BaseRecyclerViewHolder;
import com.zhang.library.utils.CollectionUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 跑马灯功能的RecyclerView
 *
 * @author ZhangXiaoMing 2021-06-23 17:01 星期三
 */
public class SmoothMarqueeRecyclerView extends RecyclerView {

    /** 滑动的Runnable */
    private Runnable mMarqueeRunnable;

    /** 每次滚动的距离 */
    private int mScrollDistance;
    /** 跑马灯滚动方向 */
    @Orientation
    private int mScrollOrientation;
    /** 跑马灯滑动间隔时间 */
    private int mScrollTimeMillis;

    public SmoothMarqueeRecyclerView(@NonNull Context context) {
        super(context);

        init(null);
    }

    public SmoothMarqueeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public SmoothMarqueeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        performClick();
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        stop();

        if (mMarqueeRunnable != null) {
            mMarqueeRunnable = null;
        }

        super.onDetachedFromWindow();
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            mScrollDistance = 1;
            mScrollOrientation = HORIZONTAL;
            mScrollTimeMillis = 10;
        } else {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SmoothMarqueeRecyclerView);

            mScrollDistance = typedArray.getInteger(R.styleable.SmoothMarqueeRecyclerView_smooth_distance, 1);
            mScrollOrientation = typedArray.getInt(R.styleable.SmoothMarqueeRecyclerView_smooth_orientation, 0);
            mScrollTimeMillis = typedArray.getInteger(R.styleable.SmoothMarqueeRecyclerView_smooth_timemillis, 10);

            typedArray.recycle();
        }
    }


    public int getScrollDistance() {
        return mScrollDistance;
    }

    public void setScrollDistance(int scrollDistance) {
        this.mScrollDistance = scrollDistance;
    }

    public int getScrollOrientation() {
        return mScrollOrientation;
    }

    public void setScrollOrientation(int scrollOrientation) {
        this.mScrollOrientation = scrollOrientation;
    }

    public int getScrollTimeMillis() {
        return mScrollTimeMillis;
    }

    public void setScrollTimeMillis(int scrollTimeMillis) {
        this.mScrollTimeMillis = scrollTimeMillis;
    }

    /** 开始滑动 */
    public void start() {
        if (getAdapter() == null || getAdapter().getItemCount() == 0)
            return;

        stop();

        try {
            getHandler().post(getMarqueeRunnable());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 结束滑动 */
    public void stop() {
        try {
            getHandler().removeCallbacks(getMarqueeRunnable());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Runnable getMarqueeRunnable() {
        if (mMarqueeRunnable == null) {
            mMarqueeRunnable = new Runnable() {
                @Override
                public void run() {
                    if (mScrollOrientation == VERTICAL)
                        scrollBy(0, mScrollDistance);
                    else
                        scrollBy(mScrollDistance, 0);

                    getHandler().postDelayed(this, mScrollTimeMillis);
                }
            };
        }

        return mMarqueeRunnable;
    }


    /**
     * 平滑移动的跑马灯列表的基类适配器
     *
     * @author ZhangXiaoMing 2021-06-23 17:15 星期三
     */
    public static abstract class SmoothMarqueeAdapter<VH extends SmoothMarqueeViewHolder<T>, T> extends BaseRecyclerAdapter<T> {

        @Override
        public int getItemCount() {
            return getDataHolder().size() == 0 ? 0 : Integer.MAX_VALUE;
        }

        @Override
        public int getItemViewType(int position) {
            return VIEW_TYPE_NORMAL_DATA;
        }

        protected abstract VH onCreateVHolder(ViewGroup parent, int viewType);

        @Override
        protected void onBindData(BaseRecyclerViewHolder<T> viewHolder, T data, int position) {
        }
    }

    /**
     * 平滑移动的跑马灯的基类ViewHolder
     *
     * @author ZhangXiaoMing 2021-06-23 17:19 星期三
     */
    public static abstract class SmoothMarqueeViewHolder<T> extends BaseRecyclerViewHolder<T> {

        public SmoothMarqueeViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public SmoothMarqueeViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void onBindData(T item, int position) {
            int size = getAdapter().getDataHolder().size();
            int realPosition = position % size;

            T itemData = CollectionUtils.get(getAdapter().getDataHolder().getDataList(), realPosition);
            bindData(itemData, position, realPosition);
        }

        protected abstract void bindData(T item, int position, int realPosition);
    }

}
