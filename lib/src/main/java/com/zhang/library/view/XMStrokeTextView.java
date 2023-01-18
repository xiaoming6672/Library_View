package com.zhang.library.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhang.library.utils.context.ResUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 文字带描边效果的TextView
 *
 * @author ZhangXiaoMing 2021-03-06 22:04 星期六
 */
public class XMStrokeTextView extends AppCompatTextView {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Orientation {
    }

    private final TextView mStrokeText;///用于描边的TextView

    private float mStrokeWidth;
    private ColorStateList mStrokeColor;


    public XMStrokeTextView(Context context) {
        this(context, null);
    }

    public XMStrokeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XMStrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mStrokeText = new TextView(context, attrs, defStyleAttr);
        mStrokeText.setGravity(getGravity());

        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            mStrokeColor = ColorStateList.valueOf(Color.TRANSPARENT);
            mStrokeWidth = 0;
        } else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.XMStrokeTextView);
            mStrokeColor = a.hasValue(R.styleable.XMStrokeTextView_strokeColor) ? a.getColorStateList(R.styleable.XMStrokeTextView_strokeColor) : ColorStateList.valueOf(Color.TRANSPARENT);
            mStrokeWidth = a.hasValue(R.styleable.XMStrokeTextView_strokeWidth) ? a.getDimension(R.styleable.XMStrokeTextView_strokeWidth, ResUtils.dp2px(1)) : 0;

            a.recycle();
        }
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        if (mStrokeText != null)
            mStrokeText.setLayoutParams(params);

        super.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        CharSequence text = mStrokeText.getText();
        if (TextUtils.isEmpty(text) || !text.equals(getText())) {
            mStrokeText.setText(getText());
            this.postInvalidate();
        }

        mStrokeText.measure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (mStrokeText != null)
            mStrokeText.layout(left, top, right, bottom);

        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (mStrokeText != null)
            mStrokeText.setEnabled(enabled);

        super.setEnabled(enabled);
    }

    @Override
    public void setSelected(boolean selected) {
        if (mStrokeText != null)
            mStrokeText.setSelected(selected);

        super.setSelected(selected);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (mStrokeText != null)
            mStrokeText.setText(text, type);

        super.setText(text, type);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mStrokeText.setTextColor(mStrokeColor);
        mStrokeText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize());
        TextPaint strokePaint = mStrokeText.getPaint();
        strokePaint.setStrokeWidth(mStrokeWidth);
        strokePaint.setStyle(TextPaint.Style.STROKE);
        mStrokeText.draw(canvas);

        super.onDraw(canvas);
    }

    /** 设置描边颜色 */
    public void setStrokeColor(int color) {
        mStrokeColor = ColorStateList.valueOf(color);
        invalidate();
    }

    /** 设置描边宽度，单位：px */
    public void setStrokeWidth(float width) {
        mStrokeWidth = width;
        invalidate();
    }


}
