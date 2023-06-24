package com.zhang.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 自动调整字号的TextView
 *
 * @author ZhangXiaoMing 2021-04-23 10:59 星期五
 */
public class XMAutoSizeTextView extends AppCompatTextView {

    /** 自适应字号是否可用 */
    private boolean isAutoSizeEnabled;
    /** 原始设置的字号大小 */
    private float mOriginalTextSize;

    public XMAutoSizeTextView(Context context) {
        this(context, null);
    }

    public XMAutoSizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public XMAutoSizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XMAutoSizeTextView);

        isAutoSizeEnabled = a.getBoolean(R.styleable.XMAutoSizeTextView_autoSizeEnable, true);

        a.recycle();

        mOriginalTextSize = getTextSize();
    }

    public void setAutoSizeEnabled(boolean autoSizeEnabled) {
        isAutoSizeEnabled = autoSizeEnabled;
    }

    public boolean isAutoSizeEnabled() {
        return isAutoSizeEnabled;
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        if (mOriginalTextSize > 0)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, mOriginalTextSize);

        super.setText(text, type);
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

        int availableTextViewWidth = this.getWidth() - getPaddingLeft() - getPaddingRight();
        float measureWidth = paint.measureText(this.getText().toString());
        if (measureWidth > availableTextViewWidth) {
            textSize = textSize * ((float) availableTextViewWidth / measureWidth);
        }

        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }
}
