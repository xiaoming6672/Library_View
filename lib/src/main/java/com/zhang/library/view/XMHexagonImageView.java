package com.zhang.library.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 正六边形ImageView
 *
 * @author ZhangXiaoMing 2020-12-23 13:42 星期三
 */
public class XMHexagonImageView extends AppCompatImageView {

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLOR_DRAWABLE_DIMENSION = 1;
    private static final Matrix mShaderMatrix = new Matrix();
    private static final Paint mBitmapPaint = new Paint();
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private int mViewWidth = 0;
    private Bitmap mBitmap;

    public XMHexagonImageView(Context context) {
        super(context);
        init();
    }

    public XMHexagonImageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public XMHexagonImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setClickable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null || getWidth() * getHeight() == 0) {
            return;
        }
        mBitmap = getBitmapFromDrawable(drawable);
        if (mBitmap == null) {
            return;
        }

        setup();
        canvas.drawPath(getHexagonPath(), mBitmapPaint);

    }

    private Path mPath = null;

    /** 绘制正六边形的边，可以参照图 Posy/doc/HexagonImageView.jpg */
    @SuppressWarnings("all")
    public Path getHexagonPath() {
        if (mPath == null) {
            mPath = new Path();
        }
        float radius = mViewWidth / 2;
        float distance = (float) ((float) mViewWidth / 4 * (2 - Math.sqrt(3)));//六边形到边到内切圆的距离
        float halfRadius = radius / 2;

        float p0x = radius;
        float p0y = 0;

        float p1x = mViewWidth - distance;
        float p1y = halfRadius;

        float p2x = p1x;
        float p2y = mViewWidth * 3 / 4;

        float p3x = radius;
        float p3y = mViewWidth;

        float p4x = distance;
        float p4y = p2y;

        float p5x = p4x;
        float p5y = p1y;

        mPath.reset();
        mPath.moveTo(p0x, p0y);
        mPath.lineTo(p1x, p1y);
        mPath.lineTo(p2x, p2y);
        mPath.lineTo(p3x, p3y);
        mPath.lineTo(p4x, p4y);
        mPath.lineTo(p5x, p5y);
        mPath.lineTo(p0x, p0y);

        return mPath;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//限制为正方形
        mViewWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(mViewWidth, mViewWidth);
    }

    public int getViewWidth() {
        return mViewWidth;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        invalidate();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        invalidate();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        invalidate();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;
            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLOR_DRAWABLE_DIMENSION,
                        COLOR_DRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, mViewWidth, mViewWidth);
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    private void setup() {
        if (mBitmap != null) {
            mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mBitmapPaint.setAntiAlias(true);
            mBitmapHeight = mBitmap.getHeight();
            mBitmapWidth = mBitmap.getWidth();
            updateShaderMatrix();
            mBitmapPaint.setShader(mBitmapShader);
        }
    }


    private void updateShaderMatrix() {
        float scale;
        mShaderMatrix.set(null);
        if (mBitmapWidth != mBitmapHeight) {
            scale = Math.max((float) mViewWidth / mBitmapWidth, (float) mViewWidth / mBitmapHeight);
        } else {
            scale = (float) mViewWidth / mBitmapWidth;
        }

        mShaderMatrix.setScale(scale, scale);//放大铺满

        float dx = mViewWidth - mBitmapWidth * scale;
        float dy = mViewWidth - mBitmapHeight * scale;
        mShaderMatrix.postTranslate(dx / 2, dy / 2);//平移居中
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

}
