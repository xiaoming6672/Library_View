package com.zhang.library.view.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroupOverlay;

import com.zhang.library.view.MRecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 上下拉刷新RecyclerView
 *
 * @author ZhangXiaoMing 2021-01-06 13:40 星期三
 */
class PullToRefreshRecyclerView extends MRecyclerView {

    private PullToRefreshAdapter mPullToRefreshAdapter;

    private @Mode
    int mMode = DISABLED;

    public PullToRefreshRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public PullToRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setAdapter(getPullToRefreshAdapter());
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING) {
                    getPullToRefreshAdapter().onDragging(dx, dy);
                }
            }
        });

//        ViewGroupOverlay overlay = getOverlay();
//        if (overlay != null) {
//            overlay.add(getPullToRefreshAdapter().getHeaderHolder(this).itemView);
//        }
    }

    public void setMode(@Mode int mode) {
        this.mMode = mode;

        updateRefreshMode();
    }

    public int getMode() {
        return mMode;
    }

    private void updateRefreshMode() {
        boolean hasHeader;
        boolean hasFooter;
        switch (mMode) {
            case PULL_FROM_START:
                hasHeader = true;
                hasFooter = false;
                break;
            case PULL_FROM_END:
                hasHeader = false;
                hasFooter = true;
                break;
            case BOTH:
                hasHeader = true;
                hasFooter = true;
                break;
            default:
            case DISABLED:
                hasHeader = false;
                hasFooter = false;
                break;
        }
        getPullToRefreshAdapter().setHeader(hasHeader);
        getPullToRefreshAdapter().setFooter(hasFooter);

        getPullToRefreshAdapter().notifyDataSetChanged();
    }

    private PullToRefreshAdapter getPullToRefreshAdapter() {
        if (mPullToRefreshAdapter == null) {
            mPullToRefreshAdapter = new PullToRefreshAdapter();
        }
        return mPullToRefreshAdapter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        if (adapter instanceof PullToRefreshAdapter) {
            super.setAdapter(adapter);
        } else {
            getPullToRefreshAdapter().setInnerAdapter(adapter);
        }
    }

    @IntDef({DISABLED, PULL_FROM_START, PULL_FROM_END, BOTH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public static final int DISABLED = 0;
    public static final int PULL_FROM_START = 1;
    public static final int PULL_FROM_END = 2;
    public static final int BOTH = 3;
}
