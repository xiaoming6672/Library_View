package com.zhang.library.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;

import com.zhang.library.pulltorefresh.constant.Mode;
import com.zhang.library.pulltorefresh.constant.Orientation;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author ZhangXiaoMing 2021-01-29 10:57 星期五
 */
public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {

    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        RecyclerView view = getRefreshableView();
        if (view != null && view.getLayoutManager() != null) {
            RecyclerView.LayoutManager layoutManager = view.getLayoutManager();

            if (layoutManager instanceof StaggeredGridLayoutManager) {
                //StaggeredGridLayoutManager
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
                return manager.getOrientation() == RecyclerView.VERTICAL ? Orientation.VERTICAL : Orientation.HORIZONTAL;
            } else if (layoutManager instanceof LinearLayoutManager) {
                //LinearLayoutManager
                LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                return manager.getOrientation() == RecyclerView.VERTICAL ? Orientation.VERTICAL : Orientation.HORIZONTAL;
            } else {
                return Orientation.VERTICAL;
            }
        }
        return null;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        return new RecyclerView(context, attrs);
    }

    @Override
    protected boolean isReadyForPullEnd() {
        return false;
    }

    @Override
    protected boolean isReadyForPullStart() {
        return false;
    }
}
