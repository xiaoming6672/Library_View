package com.zhang.library.view.pulltorefresh;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zhang.library.view.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 空，什么都没有的ViewHolder
 *
 * @author ZhangXiaoMing 2021-01-06 15:35 星期三
 */
class EmptyHolder extends RecyclerView.ViewHolder {

    public EmptyHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_nothing, parent, false));
    }


}
