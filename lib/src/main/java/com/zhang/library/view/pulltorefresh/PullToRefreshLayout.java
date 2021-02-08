package com.zhang.library.view.pulltorefresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.zhang.library.view.pulltorefresh.constant.PullMode;
import com.zhang.library.view.pulltorefresh.constant.PullOrientation;
import com.zhang.library.view.pulltorefresh.constant.PullState;
import com.zhang.library.utils.LogUtils;
import com.zhang.library.utils.context.ResUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 上下拉刷新布局
 *
 * @author ZhangXiaoMing 2021-01-28 9:20 星期四
 */
class PullToRefreshLayout extends FrameLayout {

    private static final String TAG = PullToRefreshLayout.class.getSimpleName();

    private float mLastY;
    private float mOffsetY;

    private PullOrientation mPullOrientation;
    private PullMode mPullMode;
    private PullState mPullState;

    public PullToRefreshLayout(@NonNull Context context) {
        super(context);

        init();
    }

    public PullToRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public PullToRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        setPullMode(PullMode.BOTH);
    }

    public void setPullMode(PullMode mode) {
        this.mPullMode = mode;
    }

    public PullMode getPullMode() {
        return mPullMode;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        LogUtils.debug("View", "PullToRefreshLayout>>>dispatchTouchEvent>>>b = " + b);
        return b;
//        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = super.onInterceptTouchEvent(ev);
        LogUtils.verbose("View", "PullToRefreshLayout>>>onInterceptTouchEvent>>>b = " + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.info("View", "PullToRefreshLayout>>>onTouchEvent>>>b = " + super.onTouchEvent(event));
        if (mPullMode == PullMode.NONE
                || mPullState == PullState.REFRESHING) {
            return super.onTouchEvent(event);
        }

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                LogUtils.debug(TAG, "ACTION_DOWN>>>y = %f", event.getY());
                mLastY = event.getY();

                mOffsetY = 0;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                LogUtils.info(TAG, "ACTION_MOVE>>>y = %f", event.getY());
                float y = event.getY();

                float diffY = y - mLastY;
                float absDiffY = Math.abs(diffY);
                if (diffY > 0) {
                    //向下拖动
                    mOffsetY -= absDiffY;
                } else {
                    //向上拖动
                    mOffsetY += absDiffY;
                }
//                mOffsetY = mOffsetY + diffY;

                mLastY = y;

                processScroll();
                LogUtils.verbose(TAG, "ACTION_MOVE>>>diffY = %f", diffY);
                break;
            }
            case MotionEvent.ACTION_UP: {
                LogUtils.error(TAG, "ACTION_UP>>>y = %f", event.getY());

                mLastY = 0;
                performClick();

                scrollTo(0, 0);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void processScroll() {
        int max = ResUtils.dp2px(50);

        if (mOffsetY > max) {
            mOffsetY = max;
        } else if (mOffsetY < -max) {
            mOffsetY = -max;
        }
        scrollTo(0, (int) mOffsetY);

    }

    private void processReset() {
        ValueAnimator.ofFloat(mOffsetY, 0);
    }
}
