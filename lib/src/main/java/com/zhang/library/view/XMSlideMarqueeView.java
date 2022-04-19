package com.zhang.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 跑马灯功能的RecyclerView
 *
 * @author ZhangXiaoMing 2021-06-23 17:01 星期三
 */
public class XMSlideMarqueeView extends RecyclerView {

    /** 滑动的Runnable */
    private XMSlideRunnable mSlideRunnable;
    private AdapterWrapper mAdapterWrapper;

    /** 每次滚动的距离 */
    private int mScrollDistance;
    /** 跑马灯滚动方向 */
    @Orientation
    private int mScrollOrientation;
    /** 跑马灯滑动间隔时间 */
    private int mScrollTimeMillis;

    /** 正在运行 */
    private boolean isRunning;

    public XMSlideMarqueeView(@NonNull Context context) {
        this(context, null);
    }

    public XMSlideMarqueeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.recyclerViewStyle);
    }

    public XMSlideMarqueeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    public void setAdapter(@Nullable Adapter adapter) {
//        super.setAdapter(adapter);
        getAdapterWrapper().setAdapter(adapter);
    }

    @Nullable
    @Override
    public Adapter getAdapter() {
        return getAdapterWrapper().getAdapter();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mSlideRunnable = new XMSlideRunnable(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        stop();

        if (mSlideRunnable != null) {
            mSlideRunnable = null;
        }

        super.onDetachedFromWindow();
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            mScrollDistance = 1;
            mScrollOrientation = HORIZONTAL;
            mScrollTimeMillis = 10;
        } else {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.XMSlideMarqueeView);

            mScrollDistance = typedArray.getInteger(R.styleable.XMSlideMarqueeView_slide_distance, 1);
            mScrollOrientation = typedArray.getInt(R.styleable.XMSlideMarqueeView_slide_orientation, 0);
            mScrollTimeMillis = typedArray.getInteger(R.styleable.XMSlideMarqueeView_slide_timemillis, 10);

            typedArray.recycle();
        }

        super.setAdapter(getAdapterWrapper());
    }

    /** 获取滑动距离 */
    public int getScrollDistance() {
        return mScrollDistance;
    }

    /** 设置滑动距离 */
    public void setScrollDistance(int scrollDistance) {
        this.mScrollDistance = scrollDistance;
    }

    /** 获取滑动方向 */
    @Orientation
    public int getScrollOrientation() {
        return mScrollOrientation;
    }

    /** 设置滑动方向 */
    public void setScrollOrientation(int scrollOrientation) {
        this.mScrollOrientation = scrollOrientation;
    }

    /** 获取滚动延时 */
    public int getScrollTimeMillis() {
        return mScrollTimeMillis;
    }

    public void setScrollTimeMillis(int scrollTimeMillis) {
        this.mScrollTimeMillis = scrollTimeMillis;
    }

    public boolean isRunning() {
        return isRunning;
    }

    /** 通知适配器刷新数据 */
    public void notifyDataSetChanged() {
        getAdapterWrapper().notifyDataSetChanged();
    }

    /** 开始滑动 */
    public void start() {
        start(0);
    }

    /** 开始滑动 */
    public void startDelay() {
        start(getScrollTimeMillis());
    }

    /**
     * 开始滑动
     *
     * @param delay 延时开始时间
     */
    public void start(long delay) {
        if (getAdapter() == null || getAdapter().getItemCount() == 0)
            return;

        stop();

        try {
            isRunning = true;
            postDelayed(mSlideRunnable, delay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 结束滑动 */
    public void stop() {
        try {
            isRunning = false;
            removeCallbacks(mSlideRunnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 适配器 */
    private AdapterWrapper getAdapterWrapper() {
        if (mAdapterWrapper == null) {
            mAdapterWrapper = new AdapterWrapper();
        }

        return mAdapterWrapper;
    }


    /** 滑动的Runnable */
    private static class XMSlideRunnable implements Runnable {

        private final WeakReference<XMSlideMarqueeView> mReference;

        public XMSlideRunnable(XMSlideMarqueeView slideMarqueeView) {
            this.mReference = new WeakReference<>(slideMarqueeView);
        }

        @Override
        public void run() {
            XMSlideMarqueeView view = mReference.get();

            if (view == null)
                return;

            if (!view.isRunning())
                return;

            int orientation = view.getScrollOrientation();
            if (orientation == HORIZONTAL)
                view.scrollBy(view.getScrollDistance(), 0);
            else
                view.scrollBy(0, view.getScrollDistance());

            view.postDelayed(this, view.getScrollTimeMillis());
        }
    }

    /** 包裹层适配器 */
    private static class AdapterWrapper extends RecyclerView.Adapter {

        private static final String TAG = AdapterWrapper.class.getSimpleName();

        private RecyclerView.Adapter mAdapter;

        public void setAdapter(RecyclerView.Adapter adapter) {
            if (mAdapter == adapter)
                return;

            if (mAdapter != null)
                mAdapter.unregisterAdapterDataObserver(mObserver);

            this.mAdapter = adapter;
            if (mAdapter == null)
                return;

            mAdapter.registerAdapterDataObserver(mObserver);

            notifyDataSetChanged();
        }

        public RecyclerView.Adapter getAdapter() {
            return mAdapter;
        }

        /** 获取真实的位置 */
        private int getRealPosition(int position) {
            if (mAdapter == null || mAdapter.getItemCount() == 0)
                return position;

            int itemCount = mAdapter.getItemCount();
            return position % itemCount;
        }

        @Override
        public int getItemCount() {
            if (mAdapter == null || mAdapter.getItemCount() == 0)
                return 0;

            int itemCount = mAdapter.getItemCount();
            return itemCount == 1 ? 1 : Integer.MAX_VALUE;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            mAdapter.onBindViewHolder(holder, getRealPosition(position));
        }

        private final AdapterDataObserver mObserver = new AdapterDataObserver() {
            @Override
            public void onChanged() {
                Log.i(TAG, "registerAdapterDataObserver>>>onChanged()");
                super.onChanged();

                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                Log.i(TAG, "registerAdapterDataObserver>>>onItemRangeChanged()");
                super.onItemRangeChanged(positionStart, itemCount);

                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                Log.i(TAG, "registerAdapterDataObserver>>>onItemRangeChanged()");
                super.onItemRangeChanged(positionStart, itemCount, payload);

                notifyItemRangeChanged(positionStart, itemCount, payload);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                Log.i(TAG, "registerAdapterDataObserver>>>onItemRangeInserted()");
                super.onItemRangeInserted(positionStart, itemCount);

                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                Log.i(TAG, "registerAdapterDataObserver>>>onItemRangeRemoved()");
                super.onItemRangeRemoved(positionStart, itemCount);

                notifyItemRangeRemoved(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                Log.i(TAG, "registerAdapterDataObserver>>>onItemRangeMoved()");
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);

                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onStateRestorationPolicyChanged() {
                Log.i(TAG, "registerAdapterDataObserver>>>onStateRestorationPolicyChanged()");
                super.onStateRestorationPolicyChanged();

                notifyDataSetChanged();
            }
        };

    }

}
