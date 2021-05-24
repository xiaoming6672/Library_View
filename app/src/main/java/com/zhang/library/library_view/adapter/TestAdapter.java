package com.zhang.library.library_view.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.zhang.library.adapter.BaseRecyclerAdapter;
import com.zhang.library.adapter.viewholder.base.BaseRecyclerViewHolder;
import com.zhang.library.library_view.R;
import com.zhang.library.library_view.model.TestModel;

/**
 * @author ZhangXiaoMing 2021-01-05 15:57 星期二
 */
public class TestAdapter extends BaseRecyclerAdapter<TestModel> {

    @Override
    protected BaseRecyclerViewHolder<TestModel> onCreateVHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_test_recyclerview);
    }

    @Override
    protected void onBindData(BaseRecyclerViewHolder<TestModel> viewHolder, TestModel data, int position) {

    }

    private static class ViewHolder extends BaseRecyclerViewHolder<TestModel> {

        private TextView tv_text;

        private ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void onInit() {
            tv_text = findViewById(R.id.tv_text);
        }

        @Override
        public void onBindData(TestModel item, int position) {
            tv_text.setText(String.valueOf(position + 1));
        }
    }

}
