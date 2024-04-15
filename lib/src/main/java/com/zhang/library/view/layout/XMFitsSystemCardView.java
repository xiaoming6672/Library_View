package com.zhang.library.view.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.zhang.library.utils.LogUtils;
import com.zhang.library.utils.constant.ViewDirection;
import com.zhang.library.utils.context.ContextUtils;
import com.zhang.library.utils.context.ResUtils;
import com.zhang.library.utils.context.ViewUtils;
import com.zhang.library.view.R;

/**
 * 适应状态栏高度的{@link CardView}，并且能兼容
 *
 * @author ZhangXiaoMing 2024-04-14 21:49 周日
 */
public class XMFitsSystemCardView extends CardView {

    private static final String TAG = "XMFitsSystemCardView";

    private final int[] mInsets = new int[4];

    public XMFitsSystemCardView(@NonNull Context context) {
        this(context, null);
    }

    public XMFitsSystemCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XMFitsSystemCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode())
            ContextUtils.set(context.getApplicationContext());
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        mInsets[0] = insets.left;
        mInsets[1] = insets.top;
        mInsets[2] = insets.right;
        insets.left = 0;
        insets.top = 0;
        insets.right = 0;
//        }

        return super.fitSystemWindows(insets);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
        mInsets[0] = insets.getSystemWindowInsetLeft();
        LogUtils.info(TAG, "mInsets[0]=" + mInsets[0]);

        mInsets[1] = insets.getSystemWindowInsetTop();
        LogUtils.info(TAG, "mInsets[1]" + mInsets[1]);

        mInsets[2] = insets.getSystemWindowInsetRight();
        LogUtils.info(TAG, "mInsets[2]" + mInsets[2]);

        int bottom = insets.getSystemWindowInsetBottom();
        WindowInsets replaceSystemWindowInsets = insets.replaceSystemWindowInsets(0, 0, 0, bottom);
        return super.onApplyWindowInsets(replaceSystemWindowInsets);
//        } else {
//        return super.onApplyWindowInsets(insets);
//        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    public void onViewAdded(View view) {
        super.onViewAdded(view);

        if (view == null || !(view.getLayoutParams() instanceof LayoutParams))
            return;

        LayoutParams params = (LayoutParams) view.getLayoutParams();
        switch (params.fitsSystemStyle) {
            case FitsSystemStyle.PADDING:
                if (!view.getFitsSystemWindows()) {
                    int top = view.getPaddingTop() + ResUtils.getStatusBarHeight();
                    view.setPadding(view.getPaddingLeft(), top, view.getPaddingRight(), view.getPaddingBottom());
                }
                break;
            case FitsSystemStyle.MARGIN:
                int marginTop = ViewUtils.getMarginValue(view, ViewDirection.TOP);
                int value = marginTop + ResUtils.getStatusBarHeight();
                ViewUtils.setMarginValue(view, value, ViewDirection.TOP);
                break;
            case FitsSystemStyle.NONE:
                break;
        }
    }


    public static class LayoutParams extends FrameLayout.LayoutParams {
        @FitsSystemStyle
        private int fitsSystemStyle = FitsSystemStyle.NONE;

        public LayoutParams(LayoutParams source) {
            super(source);
            this.fitsSystemStyle = source.fitsSystemStyle;
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            TypedArray array = c.obtainStyledAttributes(attrs, R.styleable.XMFitsSystemFrameLayout);
            fitsSystemStyle = array.getInteger(R.styleable.XMFitsSystemFrameLayout_fitsSystemStyle, FitsSystemStyle.NONE);
            array.recycle();
        }

        public LayoutParams() {
            this(WRAP_CONTENT, WRAP_CONTENT);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

}
