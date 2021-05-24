package com.zhang.library.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhang.library.utils.context.ResUtils;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 文字带描边效果的TextView
 *
 * @author ZhangXiaoMing 2021-03-06 22:04 星期六
 */
public class XMStrokeTextView extends AppCompatTextView {

    private final TextView mInnerText;///用于描边的TextView

    private TextPaint mPaint;

    public XMStrokeTextView(Context context) {
        super(context);
        mInnerText = new TextView(context);

        init(null);
    }

    public XMStrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInnerText = new TextView(context, attrs);

        init(attrs);
    }

    public XMStrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInnerText = new TextView(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        ColorStateList innerColor;
        ColorStateList strokeColor;
        float strokeWidth;

        if (attrs == null) {
            innerColor = ColorStateList.valueOf(Color.BLACK);
            strokeColor = ColorStateList.valueOf(Color.BLACK);
            strokeWidth = ResUtils.dp2px(1);
        } else {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.XMStrokeTextView);
            innerColor = typedArray.getColorStateList(R.styleable.XMStrokeTextView_innerColor);
            strokeColor = typedArray.getColorStateList(R.styleable.XMStrokeTextView_strokeColor);
            strokeWidth = typedArray.getDimension(R.styleable.XMStrokeTextView_strokeWidth, ResUtils.dp2px(1));
            typedArray.recycle();
        }

        mInnerText.setTextColor(innerColor);
        TextPaint innerPaint = mInnerText.getPaint();
        innerPaint.setStrokeWidth(0);
        innerPaint.setStyle(TextPaint.Style.FILL_AND_STROKE);
        innerPaint.setFakeBoldText(true);
        mInnerText.setGravity(getGravity());

        this.setTextColor(strokeColor);
        TextPaint paint = getTextPaint();
        paint.setStyle(TextPaint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setFakeBoldText(false);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        mInnerText.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        CharSequence text = mInnerText.getText();
        if (TextUtils.isEmpty(text) || !text.equals(getText())) {
            mInnerText.setText(getText());
            this.postInvalidate();
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mInnerText.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mInnerText.layout(left, top, right, bottom);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mInnerText.setEnabled(enabled);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        mInnerText.setSelected(selected);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (mInnerText != null) {
            mInnerText.setText(text, type);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mInnerText.draw(canvas);
    }

    /** 设置内部文字颜色 */
    public void setInnerTextColor(int color) {
        mInnerText.setTextColor(color);
    }

    /** 设置描边颜色 */
    public void setStrokeColor(int color) {
        setTextColor(color);
    }

    /** 设置描边宽度，单位：px */
    public void setStrokeWidth(float width) {
        TextPaint paint = getTextPaint();
        paint.setStrokeWidth(width);
    }

    private TextPaint getTextPaint() {
        if (mPaint == null) {
            mPaint = getPaint();
        }
        return mPaint;
    }
}
