package com.zhang.library.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.zhang.library.utils.context.ResUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 均等分割线
 *
 * @author ZhangXiaoMing 2020-11-27 10:28 星期五
 */
public class EqualDividerItemDecoration extends RecyclerView.ItemDecoration {

    /** 分割线方向，如果设置的是{@link RecyclerView#VERTICAL}，则表示在列表竖直方向话分割线，即分割线是横向的；反之是竖向的 */
    private int mOrientation = RecyclerView.VERTICAL;
    /** 分割间距，单位：px */
    private int mSize;

    /** 左边缩进，{@link #mOrientation}为{@link RecyclerView#VERTICAL}的时候生效 */
    private int mPaddingLeft;
    /** 右边缩进，{@link #mOrientation}为{@link RecyclerView#VERTICAL}的时候生效 */
    private int mPaddingRight;
    /** 顶部缩进，{@link #mOrientation}为{@link RecyclerView#HORIZONTAL}的时候生效 */
    private int mPaddingTop;
    /** 底部缩进，{@link #mOrientation}为{@link RecyclerView#HORIZONTAL}的时候生效 */
    private int mPaddingBottom;

    /** 分割线Drawable */
    private Drawable mDivider;

    private final Rect mBounds = new Rect();

    public EqualDividerItemDecoration() {
        mSize = ResUtils.dp2px(1);
        mDivider = new ColorDrawable(Color.TRANSPARENT);
    }

    public EqualDividerItemDecoration(@RecyclerView.Orientation int orientation, int size) {
        checkOrientation(orientation);

        this.mOrientation = orientation;
        this.mSize = size;
        mDivider = new ColorDrawable(Color.TRANSPARENT);
    }

    public EqualDividerItemDecoration(@RecyclerView.Orientation int orientation, Drawable drawable) {
        checkOrientation(orientation);
        checkDrawable(drawable);

        this.mOrientation = orientation;
        this.mDivider = drawable;
        this.mSize = mDivider.getIntrinsicWidth();
    }

    public EqualDividerItemDecoration(@RecyclerView.Orientation int orientation, int size, int color) {
        checkOrientation(orientation);

        this.mOrientation = orientation;
        this.mSize = size;
        mDivider = new ColorDrawable(color);
    }

    //<editor-fold desc="设置、获取属性">
    public EqualDividerItemDecoration setOrientation(@RecyclerView.Orientation int orientation) {
        checkOrientation(orientation);

        this.mOrientation = orientation;
        return this;
    }

    /** 设置分割线图案 */
    public EqualDividerItemDecoration setDrawable(Drawable drawable) {
        checkDrawable(drawable);

        this.mDivider = drawable;
        this.mSize = mDivider.getIntrinsicWidth();
        return this;
    }

    /** 设置分割线大小 */
    public EqualDividerItemDecoration setSize(int size) {
        this.mSize = size;
        return this;
    }

    /** 设置分割线缩进大小 */
    public EqualDividerItemDecoration setPadding(int padding) {
        if (mOrientation == RecyclerView.VERTICAL) {
            this.mPaddingLeft = padding;
            this.mPaddingRight = padding;
        } else {
            this.mPaddingTop = padding;
            this.mPaddingBottom = padding;
        }
        return this;
    }

    /** 设置分割线左缩进大小 */
    public EqualDividerItemDecoration setPaddingLeft(int paddingLeft) {
        this.mPaddingLeft = paddingLeft;
        return this;
    }

    /** 设置分割线右缩进大小 */
    public EqualDividerItemDecoration setPaddingRight(int paddingRight) {
        this.mPaddingRight = paddingRight;
        return this;
    }

    /** 设置分割线上缩进大小 */
    public EqualDividerItemDecoration setPaddingTop(int paddingTop) {
        this.mPaddingTop = paddingTop;
        return this;
    }

    /** 设置分割线下缩进大小 */
    public EqualDividerItemDecoration setPaddingBottom(int paddingBottom) {
        this.mPaddingBottom = paddingBottom;
        return this;
    }

    /** 设置分割线颜色 */
    public void setColor(int color) {
        mDivider = new ColorDrawable(color);
    }

    /** 获取分割线大小 */
    public int getSize() {
        return mSize;
    }

    /** 获取分割线在列表中的方向 */
    public int getOrientation() {
        return mOrientation;
    }
    //</editor-fold>

    private void checkOrientation(int orientation) {
        if (orientation == RecyclerView.VERTICAL
                || orientation == RecyclerView.HORIZONTAL) {
            return;
        }
        throw new IllegalArgumentException("Orientation must be RecyclerView.HORIZONTAL or RecyclerView.VERTICAL");
    }

    private void checkDrawable(Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//        super.onDraw(c, parent, state);
        if (parent.getLayoutManager() == null || mDivider == null)
            return;

        if (mOrientation == RecyclerView.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        if (isLinearLayoutManager(parent)) {
            getLinearItemOffsets(outRect, parent, position);
        } else if (isGridLayoutManager(parent)) {
            getGridItemOffsets(outRect, parent, position);
        }
    }

    /** 是否是线性布局 */
    protected boolean isLinearLayoutManager(@NonNull RecyclerView parent) {
        return (parent.getLayoutManager() != null && parent.getLayoutManager() instanceof LinearLayoutManager)
                && !isGridLayoutManager(parent);
    }

    /** 是否是表格布局 */
    protected boolean isGridLayoutManager(@NonNull RecyclerView parent) {
        return parent.getLayoutManager() != null && parent.getLayoutManager() instanceof GridLayoutManager;
    }

    /** 是否是瀑布流布局 */
    protected boolean isStaggeredGridLayoutManager(@NonNull RecyclerView parent) {
        return parent.getLayoutManager() != null && parent.getLayoutManager() instanceof StaggeredGridLayoutManager;
    }

    /**
     * 判断当前item是否是第一行
     *
     * @param parent   RecyclerView列表
     * @param position 当前item位置
     */
    protected boolean isFirstRow(@NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return false;

        if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            final int spanCount = manager.getSpanCount();

            if (manager.getOrientation() == RecyclerView.VERTICAL)
                return position < spanCount;
            else
                return position % spanCount == 0;
        } else if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();

            return manager.getOrientation() == RecyclerView.HORIZONTAL || position == 0;
        }

        return false;
    }

    /**
     * 判断当前item是否是最后一行
     *
     * @param parent   RecyclerView列表
     * @param position 当前item位置
     */
    protected boolean isLastRow(@NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return false;

        final int childCount = parent.getAdapter() == null ? 0 : parent.getAdapter().getItemCount();

        if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            final int spanCount = manager.getSpanCount();

            if (manager.getOrientation() == RecyclerView.VERTICAL) {

                int result = childCount % spanCount;
                if (result == 0)
                    return position >= childCount - spanCount;
                else
                    return position >= childCount - result;
            } else {
                return (position + 1) % spanCount == 0;
            }
        } else if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();

            return manager.getOrientation() == RecyclerView.HORIZONTAL      //横向
                    || position == childCount - 1;                          //纵向
        }

        return false;
    }

    /**
     * 判断当前位置的item是否是第一列
     *
     * @param parent   RecyclerView列表
     * @param position 当前item的位置
     */
    protected boolean isFirstColumn(@NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return false;

        if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            final int spanCount = manager.getSpanCount();

            return manager.getOrientation() == RecyclerView.VERTICAL ? position % spanCount == 0 : position < spanCount;
        } else if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();

            return manager.getOrientation() == RecyclerView.VERTICAL || position == 0;
        }

        return false;
    }

    /**
     * 判断当前位置的item是否是最后一列
     *
     * @param parent   RecyclerView列表
     * @param position 当前item的位置
     */
    protected boolean isLastColumn(@NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return false;

        final int childCount = parent.getAdapter() == null ? 0 : parent.getAdapter().getItemCount();

        if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            final int spanCount = manager.getSpanCount();

            if (manager.getOrientation() == RecyclerView.VERTICAL) {
                return (position + 1) % spanCount == 0;
            } else {

                int result = childCount % spanCount;
                return result == 0 ? position >= childCount - spanCount : position >= childCount - result;
            }
        } else if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();

            return manager.getOrientation() == RecyclerView.VERTICAL || position == childCount - 1;
        }

        return false;
    }

    /**
     * 获取当前item所在第几列
     *
     * @param parent   RecyclerView
     * @param position 当前item的位置
     */
    private int getColumnCount(@NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return 0;

        if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
            return manager.getOrientation() == RecyclerView.VERTICAL ? 1 : position;
        } else if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = manager.getSpanCount();

            if (manager.getOrientation() == RecyclerView.VERTICAL) {
                return position % spanCount + 1;
            } else {
                return position / spanCount + 1;
            }
        }

        return 0;
    }

    /**
     * 获取当前item所在第几行
     *
     * @param parent   RecyclerView
     * @param position 当前item的位置
     */
    private int getRowCount(@NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return 0;

        if (isLinearLayoutManager(parent)) {
            LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();

            return manager.getOrientation() == RecyclerView.VERTICAL ? position : 1;
        } else if (isGridLayoutManager(parent)) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = manager.getSpanCount();

            if (manager.getOrientation() == RecyclerView.VERTICAL) {
                return position / spanCount + 1;
            } else {
                return position % spanCount + 1;
            }
        }

        return 0;
    }

    /** 绘制垂直方向上的颜色 */
    private void drawVertical(@NonNull Canvas canvas, @NonNull RecyclerView parent) {
        if (isLinearLayoutManager(parent)) {
            drawLinearVertical(canvas, parent);
        } else if (isGridLayoutManager(parent)) {
            drawGridVertical(canvas, parent);
        } else if (isStaggeredGridLayoutManager(parent)) {
            drawStaggeredVertical(canvas, parent);
        }
    }

    /** 绘制水平方向上的颜色 */
    private void drawHorizontal(@NonNull Canvas canvas, @NonNull RecyclerView parent) {
        if (isLinearLayoutManager(parent)) {
            drawLinearHorizontal(canvas, parent);
        } else if (isGridLayoutManager(parent)) {
            drawGridHorizontal(canvas, parent);
        } else if (isStaggeredGridLayoutManager(parent)) {
            drawStaggeredHorizontal(canvas, parent);
        }
    }

    /** 绘制线性布局垂直方向上的颜色 */
    private void drawLinearVertical(@NonNull Canvas canvas, @NonNull RecyclerView parent) {
        canvas.save();

        int left;
        int right;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int index = 0; index < childCount; index++) {
            View child = parent.getChildAt(index);

            int position = parent.getChildAdapterPosition(child);
            if (isFirstRow(parent, position)) {
                continue;
            }

            parent.getDecoratedBoundsWithMargins(child, mBounds);

            int top = mBounds.top + Math.round(child.getTranslationY());
            int bottom = top + mSize;
            mDivider.setBounds(left + mPaddingLeft, top, right - mPaddingRight, bottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    /** 绘制表格布局垂直方向上的颜色 */
    private void drawGridVertical(@NonNull Canvas canvas, @NonNull RecyclerView parent) {
        canvas.save();

        int left;
        int right;

        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int index = 0; index < childCount; index++) {
            View child = parent.getChildAt(index);
            int position = parent.getChildAdapterPosition(child);

            parent.getDecoratedBoundsWithMargins(child, mBounds);

            int[] size = new int[2];
            getGridHorizontalSideWidth(parent, position, size);

            int topPartHeight = size[0];
            int bottomPartHeight = size[1];

            {
                int top = mBounds.top + Math.round(child.getTranslationY());
                int bottom = top + topPartHeight;
                mDivider.setBounds(left + mPaddingLeft, top, right - mPaddingRight, bottom);
                mDivider.draw(canvas);
            }

            {
                int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                int top = bottom - bottomPartHeight;
                mDivider.setBounds(left + mPaddingLeft, top, right - mPaddingRight, bottom);
                mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }

    /** 绘制瀑布流垂直方向上的颜色 */
    private void drawStaggeredVertical(@NonNull Canvas canvas, @NonNull RecyclerView parent) {
    }

    /** 绘制线性布局水平方向上的颜色 */
    private void drawLinearHorizontal(@NonNull Canvas canvas, @NonNull RecyclerView parent) {
        canvas.save();

        int top;
        int bottom;

        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(),
                    bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        for (int index = 0; index < parent.getChildCount(); index++) {
            View child = parent.getChildAt(index);
            int position = parent.getChildAdapterPosition(child);

            if (isFirstColumn(parent, position)) {
                continue;
            }

            parent.getDecoratedBoundsWithMargins(child, mBounds);

            int left = mBounds.left + Math.round(child.getTranslationX());
            int right = left + mSize;
            mDivider.setBounds(left, top + mPaddingTop, right, bottom - mPaddingBottom);
            mDivider.draw(canvas);
        }
        canvas.restore();
    }

    /** 绘制表格布局水平方向上的颜色 */
    private void drawGridHorizontal(@NonNull Canvas canvas, @NonNull RecyclerView parent) {
        canvas.save();

        int top;
        int bottom;

        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top, parent.getWidth() - parent.getPaddingRight(),
                    bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        for (int index = 0; index < childCount; index++) {
            final View child = parent.getChildAt(index);
            int position = parent.getChildAdapterPosition(child);

            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);

            int[] size = new int[2];
            getGridVerticalSideWidth(parent, position, size);

            int leftPartWidth = size[0];
            int rightPartWidth = size[1];

            {
                //item左侧
                int left = mBounds.left + Math.round(child.getTranslationX());
                int right = left + leftPartWidth;
                mDivider.setBounds(left, top + mPaddingTop, right, bottom - mPaddingBottom);
                mDivider.draw(canvas);
            }

            {
                //item右侧
                int right = mBounds.right + Math.round(child.getTranslationX());
                int left = right - rightPartWidth;
                mDivider.setBounds(left, top + mPaddingTop, right, bottom - mPaddingBottom);
                mDivider.draw(canvas);
            }
        }
    }

    /** 绘制瀑布流布局水平方向上的颜色 */
    private void drawStaggeredHorizontal(@NonNull Canvas canvas, @NonNull RecyclerView parent) {
    }

    /**
     * 计算线性布局的偏移量
     *
     * @param position item所在的位置
     */
    private void getLinearItemOffsets(@NonNull Rect rect, @NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return;

        if (mOrientation != ((LinearLayoutManager) parent.getLayoutManager()).getOrientation()) {
            return;
        }

        if ((isFirstColumn(parent, position) && mOrientation == RecyclerView.HORIZONTAL)
                || (isFirstRow(parent, position) && mOrientation == RecyclerView.VERTICAL)) {
            rect.set(0, 0, 0, 0);
            return;
        }

        if (mOrientation == RecyclerView.VERTICAL)
            rect.set(0, mSize, 0, 0);
        else
            rect.set(mSize, 0, 0, 0);
    }

    /**
     * 计算表格布局的偏移量
     *
     * @param position item所在的位置
     */
    private void getGridItemOffsets(@NonNull Rect rect, @NonNull RecyclerView parent, int position) {
        if (parent.getLayoutManager() == null)
            return;

        GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();

        if (manager.getOrientation() == RecyclerView.VERTICAL) {
            if (mOrientation == RecyclerView.VERTICAL) {
                if (!isFirstRow(parent, position)) {
                    rect.set(0, mSize, 0, 0);
                }
            } else {
                int[] size = new int[2];
                getGridVerticalSideWidth(parent, position, size);
                rect.set(size[0], 0, size[1], 0);
            }
        } else {
            if (mOrientation == RecyclerView.HORIZONTAL) {
                if (!isFirstColumn(parent, position))
                    rect.set(mSize, 0, 0, 0);
            } else {
                int[] size = new int[2];
                getGridHorizontalSideWidth(parent, position, size);
                rect.set(0, size[0], 0, size[1]);
            }
        }
    }

    /**
     * 获取垂直表格列表column左右两边添加分割线时候的大小
     *
     * @param parent   列表
     * @param position item所在位置
     * @param size     存放结果的数组
     */
    private void getGridVerticalSideWidth(@NonNull RecyclerView parent, int position, @NonNull int[] size) {
        if (parent.getLayoutManager() == null || !isGridLayoutManager(parent) || size.length < 2) {
            return;
        }

        GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
        if (manager.getOrientation() == RecyclerView.HORIZONTAL) {
            if (isFirstColumn(parent, position)) {
                size[0] = size[1] = 0;
            } else {
                size[0] = mSize;
                size[1] = 0;
            }
            return;
        }

        final int spanCount = manager.getSpanCount();
        int column = getColumnCount(parent, position);
        int left = (column - 1) * mSize / spanCount;
        int right = mSize - column * mSize / spanCount;

        size[0] = left;
        size[1] = right;
    }

    /**
     * 获取水平表格列表row上下两边添加分割线时候的大小
     *
     * @param parent   列表
     * @param position item所在位置
     * @param size     存放结果的数组
     */
    private void getGridHorizontalSideWidth(@NonNull RecyclerView parent, int position, @NonNull int[] size) {
        if (parent.getLayoutManager() == null || !isGridLayoutManager(parent) || size.length != 2) {
            return;
        }

        GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
        if (manager.getOrientation() == RecyclerView.VERTICAL) {
            if (isFirstRow(parent, position)) {
                size[0] = size[1] = 0;
            } else {
                size[0] = mSize;
                size[1] = 0;
            }
            return;
        }

        final int spanCount = manager.getSpanCount();
        int row = getRowCount(parent, position);
        int top = (row - 1) * mSize / spanCount;
        int bottom = mSize - row * mSize / spanCount;

        size[0] = top;
        size[1] = bottom;
    }

}
