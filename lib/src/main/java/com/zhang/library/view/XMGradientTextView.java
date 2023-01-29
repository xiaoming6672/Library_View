package com.zhang.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 渐变色的TextView
 *
 * @author ZhangXiaoMing 2023-01-18 14:38 周三
 */
public class XMGradientTextView extends AppCompatTextView {


    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Orientation {
    }

    private int mOrientation = HORIZONTAL;
    private int[] mGradientColor;

    public XMGradientTextView(@NonNull Context context) {
        this(context, null);
    }

    public XMGradientTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XMGradientTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.XMGradientTextView);
        mOrientation = a.getInteger(R.styleable.XMGradientTextView_gradientOrientation, HORIZONTAL);

        Integer startColor = null, centerColor = null, endColor = null;
        if (a.hasValue(R.styleable.XMGradientTextView_gradientStart))
            startColor = a.getColor(R.styleable.XMGradientTextView_gradientStart, getCurrentTextColor());

        if (a.hasValue(R.styleable.XMGradientTextView_gradientCenter))
            centerColor = a.getColor(R.styleable.XMGradientTextView_gradientCenter, getCurrentTextColor());

        if (a.hasValue(R.styleable.XMGradientTextView_gradientEnd))
            endColor = a.getColor(R.styleable.XMGradientTextView_gradientEnd, getCurrentTextColor());

        a.recycle();

        if (startColor != null && endColor != null) {
            if (centerColor != null)
                mGradientColor = new int[]{startColor, centerColor, endColor};
            else
                mGradientColor = new int[]{startColor, endColor};
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
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
