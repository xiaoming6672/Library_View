package com.zhang.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 自动调整字号的TextView
 *
 * @author ZhangXiaoMing 2021-04-23 10:59 星期五
 */
public class XMAutoSizeTextView extends AppCompatTextView implements ViewTreeObserver.OnGlobalLayoutListener {

    private boolean isAutoSizeEnabled = true;

    public XMAutoSizeTextView(Context context) {
        this(context, null);
    }

    public XMAutoSizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public XMAutoSizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void setAutoSizeEnabled(boolean autoSizeEnabled) {
        isAutoSizeEnabled = autoSizeEnabled;
    }

    public boolean isAutoSizeEnabled() {
        return isAutoSizeEnabled;
    }

    @Override
    public void onGlobalLayout() {
        if (!isAutoSizeEnabled)
            return;

        int lineCount = getLineCount();

        if (lineCount > 1) {
            float size = getTextSize();
            size--;
            setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }
}
