package com.zhang.library.view.pulltorefresh;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhang.library.utils.context.ResUtils;
import com.zhang.library.view.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.RecyclerView;

/**
 * {@link PullToRefreshAdapter}使用的ViewHolder
 *
 * @author ZhangXiaoMing 2021-01-06 11:19 星期三
 */
class PullToRefreshViewHolder extends RecyclerView.ViewHolder {

    private ImageView iv_pull_to_refresh_icon;
    private TextView tv_pull_to_refresh_text;

    private float mMaxHeight;

    public PullToRefreshViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pull_to_refresh_layout, parent, false));

        init();
    }

    private void init() {
        iv_pull_to_refresh_icon = itemView.findViewById(R.id.iv_pull_to_refresh_icon);
        tv_pull_to_refresh_text = itemView.findViewById(R.id.tv_pull_to_refresh_text);

        int paddingVertical = ResUtils.dp2px(10);
        float viewHeight = ResUtils.get().getResources().getDimension(R.dimen.dp_pull_to_refresh_icon_size);

        mMaxHeight = paddingVertical * 2 + viewHeight;

        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        params.height = 0;
        itemView.setLayoutParams(params);
    }

    public void initLabelType(@LabelType int type) {
        if (type == HEADER) {
            iv_pull_to_refresh_icon.setImageResource(R.mipmap.ic_pull_to_refresh_icon_drag_down);
            tv_pull_to_refresh_text.setText(R.string.text_pull_to_refresh_header_drag);
        } else {
            iv_pull_to_refresh_icon.setImageResource(R.mipmap.ic_pull_to_refresh_icon_drag_up);
            tv_pull_to_refresh_text.setText(R.string.text_pull_to_refresh_footer_drag);
        }
    }

    public void onDrag(float dragHeight) {

    }

    @IntDef({HEADER, FOOTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LabelType {
    }

    public static final int HEADER = 0;
    public static final int FOOTER = 1;
}
