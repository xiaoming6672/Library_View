package com.zhang.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * 自动字号的描边文字控件
 *
 * @author ZhangXiaoMing 2021-04-25 18:26 星期日
 */
public class XMAutoSizeStrokeTextView extends XMStrokeTextView {

    private boolean isAutoSizeEnabled = true;

    public XMAutoSizeStrokeTextView(Context context) {
        super(context);
    }

    public XMAutoSizeStrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XMAutoSizeStrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAutoSizeEnabled(boolean autoSizeEnabled) {
        isAutoSizeEnabled = autoSizeEnabled;
    }

    public boolean isAutoSizeEnabled() {
        return isAutoSizeEnabled;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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
