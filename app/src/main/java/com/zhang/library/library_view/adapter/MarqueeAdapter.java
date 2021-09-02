package com.zhang.library.library_view.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.zhang.library.adapter.BaseRecyclerAdapter;
import com.zhang.library.adapter.viewholder.base.BaseRecyclerViewHolder;
import com.zhang.library.library_view.R;

/**
 * @author ZhangXiaoMing 2021-09-01 19:33 星期三
 */
public class MarqueeAdapter extends BaseRecyclerAdapter<Integer> {

    @Override
    protected BaseRecyclerViewHolder<Integer> onCreateVHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    protected void onBindData(BaseRecyclerViewHolder<Integer> viewHolder, Integer data, int position) {
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
