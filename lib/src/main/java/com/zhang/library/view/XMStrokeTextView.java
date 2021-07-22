package com.zhang.library.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
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
    public @interface Orientation {
    }

    private final TextView mStrokeText;///用于描边的TextView

    private float mStrokeWidth;
    private ColorStateList mStrokeColor;

    private int mOrientation = HORIZONTAL;
    private int[] mGradientColor;

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
            mOrientation = a.getInteger(R.styleable.XMStrokeTextView_gradientOrientation, HORIZONTAL);

            Integer startColor = null, centerColor = null, endColor = null;
            if (a.hasValue(R.styleable.XMStrokeTextView_gradientStart))
                startColor = a.getColor(R.styleable.XMStrokeTextView_gradientStart, getCurrentTextColor());

            if (a.hasValue(R.styleable.XMStrokeTextView_gradientCenter))
                centerColor = a.getColor(R.styleable.XMStrokeTextView_gradientCenter, getCurrentTextColor());

            if (a.hasValue(R.styleable.XMStrokeTextView_gradientEnd))
                endColor = a.getColor(R.styleable.XMStrokeTextView_gradientEnd, getCurrentTextColor());

            a.recycle();

            if (startColor != null && endColor != null) {
                if (centerColor != null)
                    mGradientColor = new int[]{startColor, centerColor, endColor};
                else
                    mGradientColor = new int[]{startColor, endColor};
            }

        }
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        mStrokeText.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        CharSequence text = mStrokeText.getText();
        if (TextUtils.isEmpty(text) || !text.equals(getText())) {
            mStrokeText.setText(getText());
            this.postInvalidate();
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mStrokeText.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mStrokeText.layout(left, top, right, bottom);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mStrokeText.setEnabled(enabled);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mStrokeText.setSelected(selected);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (mStrokeText != null) {
            mStrokeText.setText(text, type);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mStrokeText.setTextColor(mStrokeColor);
        mStrokeText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize());
        TextPaint strokePaint = mStrokeText.getPaint();
        strokePaint.setStrokeWidth(mStrokeWidth);
        strokePaint.setStyle(TextPaint.Style.FILL_AND_STROKE);
        mStrokeText.draw(canvas);

        if (mGradientColor != null) {
            TextPaint paint = getPaint();
            paint.setShader(getGradient());
        }
        super.onDraw(canvas);
    }

    private LinearGradient getGradient() {
        LinearGradient gradient;
        if (mOrientation == VERTICAL) {
            gradient = new LinearGradient(0, 0, 0, getHeight(), mGradientColor, null, Shader.TileMode.CLAMP);
        } else {
            gradient = new LinearGradient(0, 0, getWidth(), 0, mGradientColor, null, Shader.TileMode.CLAMP);
        }

        return gradient;
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

    /**
     * 设置颜色渐变方向
     *
     * @param orientation 渐变方向
     */
    public void setGradientOrientation(@Orientation int orientation) {
        this.mOrientation = orientation;
        invalidate();
    }

    /**
     * 设置渐变颜色
     *
     * @param startColor 起始颜色
     * @param endColor   结束颜色
     */
    public void setGradientColor(int startColor, int endColor) {
        mGradientColor = new int[]{startColor, endColor};
        invalidate();
    }

    /**
     * 设置渐变颜色
     *
     * @param startColor  起始颜色
     * @param centerColor 中间颜色
     * @param endColor    结束颜色
     */
    public void setGradientColor(int startColor, int centerColor, int endColor) {
        mGradientColor = new int[]{startColor, centerColor, endColor};
        invalidate();
    }
}
