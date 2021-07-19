package com.zhang.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.zhang.library.utils.context.ViewUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * 圆角图控件
 *
 * @author ZhangXiaoMing 2021-07-19 11:38 星期一
 */
public class XMRoundedImageView extends AppCompatImageView {

    /** 圆角度数，四个圆角度数统一时候使用 */
    private Float mRadius;

    /** 左上角圆角度数，四个圆角度数不统一的时候使用 */
    private float mTopLeftRadius;
    /** 右上角圆角度数，四个圆角度数不统一的时候使用 */
    private float mTopRightRadius;
    /** 左下角圆角度数，四个圆角度数不统一的时候使用 */
    private float mBottomLeftRadius;
    /** 右下角圆角度数，四个圆角度数不统一的时候使用 */
    private float mBottomRightRadius;

    public XMRoundedImageView(@NonNull Context context) {
        this(context, null);
    }

    public XMRoundedImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XMRoundedImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttributes(context, attrs);
    }

    private void initAttributes(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XMRoundedImageView);

        if (a.hasValue(R.styleable.XMRoundedImageView_radius))
            mRadius = a.getDimension(R.styleable.XMRoundedImageView_radius, 0);
        else {
            mTopLeftRadius = a.getDimension(R.styleable.XMRoundedImageView_radiusTopLeft, 0);
            mTopRightRadius = a.getDimension(R.styleable.XMRoundedImageView_radiusTopRight, 0);
            mBottomLeftRadius = a.getDimension(R.styleable.XMRoundedImageView_radiusBottomLeft, 0);
            mBottomRightRadius = a.getDimension(R.styleable.XMRoundedImageView_radiusBottomRight, 0);
        }

        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = getRoundedPath();
        if (path != null)
            canvas.clipPath(path);

        super.onDraw(canvas);
    }

    /** 获取圆角矩形路径 */
    private Path getRoundedPath() {
        if (mRadius != null)
            return getUniteCornersPath();
        else
            return getDifferentCornersPath();
    }

    /** 获取四个圆角度数统一的路径 */
    private Path getUniteCornersPath() {
        Path path = new Path();

        RectF rect = new RectF(0, 0, ViewUtils.getWidth(this), ViewUtils.getHeight(this));
        path.addRoundRect(rect, mRadius, mRadius, Path.Direction.CW);

        return path;
    }

    /** 获取四个圆角度数不统一的路径 */
    private Path getDifferentCornersPath() {
        float[] radius = new float[]{
                mTopLeftRadius, mTopLeftRadius,
                mTopRightRadius, mTopRightRadius,
                mBottomRightRadius, mBottomRightRadius,
                mBottomLeftRadius, mBottomLeftRadius,
        };

        Path path = new Path();

        RectF rect = new RectF(0, 0, ViewUtils.getWidth(this), ViewUtils.getHeight(this));
        path.addRoundRect(rect, radius, Path.Direction.CW);

        return path;
    }

}
