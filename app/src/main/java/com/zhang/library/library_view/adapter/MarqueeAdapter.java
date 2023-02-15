package com.zhang.library.library_view.adapter;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhang.library.adapter.BaseRecyclerAdapter;
import com.zhang.library.adapter.viewholder.base.BaseRecyclerViewHolder;
import com.zhang.library.library_view.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ZhangXiaoMing 2021-09-01 19:33 星期三
 */
public class MarqueeAdapter extends BaseRecyclerAdapter<Integer> {

    private static final String TAG = MarqueeAdapter.class.getSimpleName();

    @Override
    protected BaseRecyclerViewHolder<Integer> onCreateVHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    protected void onBindData(BaseRecyclerViewHolder<Integer> viewHolder, Integer data, int position) {
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseRecyclerViewHolder<Integer> holder) {
        super.onViewAttachedToWindow(holder);
        Log.i(TAG, "onViewAttachedToWindow()");
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseRecyclerViewHolder<Integer> holder) {
        super.onViewDetachedFromWindow(holder);
        Log.d(TAG, "onViewDetachedFromWindow()");
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.w(TAG, "onAttachedToRecyclerView()");
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        Log.e(TAG, "onDetachedFromRecyclerView()");
    }

    private static final class ViewHolder extends BaseRecyclerViewHolder<Integer> {

        private TextView tvText;

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_marquee);
        }

        @Override
        public void onInit() {
            tvText = findViewById(R.id.tv_text);
        }

        @Override
        public void onBindData(Integer item, int position) {
            tvText.setText(String.valueOf(item));
        }
    }
}
