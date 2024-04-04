package com.zhang.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
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

        processFitAutoSize();
    }


    /** 自适应字号 */
    private void processFitAutoSize() {
        if (!isAutoSizeEnabled)
            return;

        if (this.getLineCount() <= 1)
            return;

        Paint paint = getPaint();
        float textSize = getTextSize();

        Drawable[] drawables = getCompoundDrawables();
        int drawableWidth = 0;
        if (drawables[0] != null) {
            drawableWidth += drawables[0].getIntrinsicWidth();
            drawableWidth += getCompoundDrawablePadding();
        }
        if (drawables[2] != null) {
            drawableWidth += drawables[2].getIntrinsicWidth();
            drawableWidth += getCompoundDrawablePadding();
        }

        int availableTextViewWidth = this.getWidth() - getPaddingLeft() - getPaddingRight() - drawableWidth;
        float measureWidth = paint.measureText(this.getText().toString());
        if (measureWidth > availableTextViewWidth) {
            textSize = textSize * ((float) availableTextViewWidth / measureWidth);
        }

        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

}
