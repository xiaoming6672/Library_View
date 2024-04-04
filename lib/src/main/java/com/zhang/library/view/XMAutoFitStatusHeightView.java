package com.zhang.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Space;

import com.zhang.library.utils.context.ResUtils;

/**
 * 自动适应状态栏高度的View。仿照{@link Space}不显示任何内容
 *
 * @author ZhangXiaoMing 2023-02-13 14:19 周一
 */
public class XMAutoFitStatusHeightView extends View {

    public XMAutoFitStatusHeightView(Context context) {
        this(context, null);
    }

    public XMAutoFitStatusHeightView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XMAutoFitStatusHeightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode())
            ResUtils.set(context.getApplicationContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = ResUtils.getStatusBarHeight();
        setMeasuredDimension(1, height);
    }
}
