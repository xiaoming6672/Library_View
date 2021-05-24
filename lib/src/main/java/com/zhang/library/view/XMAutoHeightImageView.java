package com.zhang.library.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 自适应高度图片控件，等比例拉伸控件高度
 *
 * @author ZhangXiaoMing 2020-05-29 15:22 星期五
 */
public class XMAutoHeightImageView extends AppCompatImageView {

    public XMAutoHeightImageView(Context context) {
        super(context);
        init(context, null);
    }

    public XMAutoHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public XMAutoHeightImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setScaleType(ScaleType.FIT_XY);
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        super.setScaleType(ScaleType.FIT_XY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final Drawable drawable = getDrawable();
        if (drawable != null) {
            final int measuredWidth = getMeasuredWidth();
            final int height = getScaleHeight(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), measuredWidth);

            setMeasuredDimension(measuredWidth, height);
        }
    }

    private static int getScaleHeight(int scaleWidth, int scaleHeight, int width) {
        if (scaleWidth == 0)
            return 0;
        return scaleHeight * width / scaleWidth;
    }
}
