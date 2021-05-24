package com.zhang.library.library_view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zhang.library.utils.LogUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author ZhangXiaoMing 2021-01-28 17:43 星期四
 */
public class TestTextView extends AppCompatTextView {

    public TestTextView(@NonNull Context context) {
        super(context);
    }

    public TestTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    private float mStartX;
    private float mStartY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.debug(TestTextView.class.getSimpleName(), "x = " + event.getX() + "  y = " + event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                scrollTo((-(int) (event.getX() - mStartX)), -(int) (event.getY() - mStartY));
                break;
            case MotionEvent.ACTION_UP:
                performClick();
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
