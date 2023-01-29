package com.zhang.library.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
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
import androidx.annotation.Nullable;
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

    /** 用于描边的TextView */
    private final TextView mStrokeTextView;

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

        init(attrs);

        mStrokeTextView = new TextView(context, attrs, defStyleAttr);
        initStrokeTextView();
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

    private void initStrokeTextView() {
        mStrokeTextView.setCompoundDrawablePadding(getCompoundDrawablePadding());

        Drawable[] drawables = getCompoundDrawablesRelative();
        if (drawables != null && drawables.length > 0)
            mStrokeTextView.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);

        mStrokeTextView.setTextColor(mStrokeColor);
        mStrokeTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize());
        TextPaint strokePaint = mStrokeTextView.getPaint();
        strokePaint.setStrokeWidth(mStrokeWidth);
        strokePaint.setStyle(TextPaint.Style.STROKE);
    }

    @Override
    public void setTypeface(@Nullable Typeface tf) {
        if (mStrokeTextView != null)
            mStrokeTextView.setTypeface(tf);

        super.setTypeface(tf);
    }

    @Override
    public void setTypeface(@Nullable Typeface tf, int style) {
        if (mStrokeTextView != null)
            mStrokeTextView.setTypeface(tf, style);

        super.setTypeface(tf, style);
    }

    @Override
    public void setGravity(int gravity) {
        if (mStrokeTextView != null)
            mStrokeTextView.setGravity(gravity);

        super.setGravity(gravity);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        if (mStrokeTextView != null)
            mStrokeTextView.setLayoutParams(params);

        super.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        CharSequence text = mStrokeTextView.getText();
        if (TextUtils.isEmpty(text) || !text.equals(getText())) {
            mStrokeTextView.setText(getText());
            this.postInvalidate();
        }

        mStrokeTextView.measure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (mStrokeTextView != null)
            mStrokeTextView.layout(left, top, right, bottom);

        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (mStrokeTextView != null)
            mStrokeTextView.setEnabled(enabled);

        super.setEnabled(enabled);
    }

    @Override
    public void setSelected(boolean selected) {
        if (mStrokeTextView != null)
            mStrokeTextView.setSelected(selected);

        super.setSelected(selected);
    }

    @Override
    public void setTextSize(float size) {
        if (mStrokeTextView != null)
            mStrokeTextView.setTextSize(size);

        super.setTextSize(size);
    }

    @Override
    public void setTextSize(int unit, float size) {
        if (mStrokeTextView != null)
            mStrokeTextView.setTextSize(unit, size);

        super.setTextSize(unit, size);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (!TextUtils.isEmpty(text)) {
            String space = " ";
            String s = text.toString();

            if (!s.startsWith(space))
                builder.append(space);

            builder.append(text);

            if (!s.endsWith(space))
                builder.append(space);
        }

        if (mStrokeTextView != null)
            mStrokeTextView.setText(builder.toString());

        super.setText(builder, type);
    }

    @Override
    public void setCompoundDrawablePadding(int pad) {
        if (mStrokeTextView != null)
            mStrokeTextView.setCompoundDrawablePadding(pad);

        super.setCompoundDrawablePadding(pad);
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        if (mStrokeTextView != null)
            mStrokeTextView.setCompoundDrawables(left, top, right, bottom);

        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public void setCompoundDrawablesRelative(@Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        if (mStrokeTextView != null)
            mStrokeTextView.setCompoundDrawablesRelative(start, top, end, bottom);

        super.setCompoundDrawablesRelative(start, top, end, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mStrokeTextView.draw(canvas);

        super.onDraw(canvas);
    }

    /** 设置描边颜色 */
    public void setStrokeColor(int color) {
        mStrokeColor = ColorStateList.valueOf(color);
        invalidate();
    }

    /** 获取描边颜色 */
    public ColorStateList getStrokeColor() {
        return mStrokeColor;
    }

    /** 设置描边宽度，单位：px */
    public void setStrokeWidth(float width) {
        mStrokeWidth = width;
        invalidate();
    }

    /** 获取描边宽度，单位：px */
    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    protected TextView getStrokeTextView() {
        return mStrokeTextView;
    }
}
