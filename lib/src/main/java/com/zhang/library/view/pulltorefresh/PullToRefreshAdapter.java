package com.zhang.library.view.pulltorefresh;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * {@link PullToRefreshRecyclerView}内部使用的adapter
 *
 * @author ZhangXiaoMing 2021-01-06 13:41 星期三
 */
class PullToRefreshAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final int VIEW_TYPE_HEADER = Integer.MAX_VALUE;
    private final int VIEW_TYPE_FOOTER = Integer.MIN_VALUE;

    private boolean hasHeader;
    private boolean hasFooter;

    private int mLastX;
    private int mLastY;

    /** 下拉刷新View的holder */
    private PullToRefreshViewHolder mHeaderHolder;
    /** 上拉刷新View的holder */
    private PullToRefreshViewHolder mFooterHolder;

    private RecyclerView.Adapter<ViewHolder> mInnerAdapter;

    public PullToRefreshAdapter() {
    }

    public void setHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
        notifyDataSetChanged();
    }

    public void setFooter(boolean hasFooter) {
//        this.hasFooter = hasFooter;
        notifyDataSetChanged();
    }

    public void setInnerAdapter(RecyclerView.Adapter<ViewHolder> innerAdapter) {
        this.mInnerAdapter = innerAdapter;
        notifyDataSetChanged();
    }

    public void onDragging(final int dx, final int dy) {

    }

    private int getInnerItemCount() {
        return mInnerAdapter == null ? 0 : mInnerAdapter.getItemCount();
    }

    private boolean isHeaderPosition(int position) {
        return hasHeader && position == 0;
    }

    private boolean isFooterPosition(int position) {
        return hasFooter && position == getItemCount() - 1;
    }

    private int getRealPosition(int position) {
        if (hasHeader) {
            return position - 1;
        }
        return position;
    }

    public PullToRefreshViewHolder getHeaderHolder(@NonNull ViewGroup parent) {
        if (mHeaderHolder == null) {
            mHeaderHolder = new PullToRefreshViewHolder(parent);
            mHeaderHolder.initLabelType(PullToRefreshViewHolder.HEADER);
        }
        return mHeaderHolder;
    }

    public PullToRefreshViewHolder getFooterHolder(@NonNull ViewGroup parent) {
        if (mFooterHolder == null) {
            mFooterHolder = new PullToRefreshViewHolder(parent);
            mFooterHolder.initLabelType(PullToRefreshViewHolder.FOOTER);
        }
        return mFooterHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position))
            return VIEW_TYPE_HEADER;
        else if (isFooterPosition(position))
            return VIEW_TYPE_FOOTER;
        else if (mInnerAdapter != null)
            return mInnerAdapter.getItemViewType(getRealPosition(position));

        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return getHeaderHolder(parent);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            return getFooterHolder(parent);
        } else if (mInnerAdapter != null) {
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        }
        return new EmptyHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!isHeaderPosition(position) && !isFooterPosition(position)
                && mInnerAdapter != null) {
            mInnerAdapter.onBindViewHolder(holder, getRealPosition(position));
        }
    }

    @Override
    public int getItemCount() {
        int innerItemCount = getInnerItemCount();

        if (hasHeader)
            innerItemCount++;

        if (hasFooter)
            innerItemCount++;

        return innerItemCount;
    }
}
