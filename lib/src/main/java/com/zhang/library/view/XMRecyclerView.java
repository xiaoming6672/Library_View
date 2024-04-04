package com.zhang.library.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.zhang.library.utils.context.ContextUtils;

/**
 * 自定义RecyclerView
 *
 * @author ZhangXiaoMing 2021-01-05 16:07 星期二
 */
public class XMRecyclerView extends RecyclerView {

    public XMRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public XMRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.recyclerViewStyle);
    }

    public XMRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode())
            ContextUtils.set(context.getApplicationContext());

        init();
    }

    private void init() {
        if (getLayoutManager() == null)
            setLinearLayoutManager(VERTICAL);
    }

    //<editor-fold desc="Getter and Setter of LayoutManger">

    /** 获取线性布局管理器 */
    public LinearLayoutManager getLinearLayoutManager() {
        LayoutManager manager = getLayoutManager();

        return manager instanceof LinearLayoutManager ? ((LinearLayoutManager) manager) : null;
    }

    /**
     * 设置线性布局管理器
     *
     * @param orientation 布局管理器方向
     *
     * @see #VERTICAL
     * @see #HORIZONTAL
     */
    public void setLinearLayoutManager(@Orientation int orientation) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(orientation);

        setLayoutManager(manager);
    }

    /** 获取表格布局管理器 */
    public GridLayoutManager getGridLayoutManager() {
        LayoutManager manager = getLayoutManager();

        return manager instanceof GridLayoutManager ? ((GridLayoutManager) manager) : null;
    }

    /**
     * 设置表格布局管理器
     *
     * @param orientation 布局管理器方向
     * @param spanCount   列数
     *
     * @see #VERTICAL
     * @see #HORIZONTAL
     */
    public void setGridLayoutManager(@Orientation int orientation, int spanCount) {
        GridLayoutManager manager = new GridLayoutManager(getContext(), spanCount);
        manager.setOrientation(orientation);

        setLayoutManager(manager);
    }

    /** 获取瀑布流布局管理器 */
    public StaggeredGridLayoutManager getStaggeredGridLayoutManager() {
        LayoutManager manager = getLayoutManager();

        return manager instanceof StaggeredGridLayoutManager ? ((StaggeredGridLayoutManager) manager) : null;
    }

    /**
     * 设置瀑布流布局管理器
     *
     * @param orientation 布局管理器方向
     * @param spanCount   列数
     *
     * @see #VERTICAL
     * @see #HORIZONTAL
     */
    public void setStaggeredGridLayoutManager(@Orientation int orientation, int spanCount) {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(spanCount, orientation);

        setLayoutManager(manager);
    }
    //</editor-fold>

    //<editor-fold desc="ItemDecoration">

    /**
     * 添加透明分割线
     *
     * @param orientation 列表方向
     * @param size        分割线大小
     *
     * @see #VERTICAL
     * @see #HORIZONTAL
     */
    public void addTransparentDivider(@Orientation int orientation, int size) {
        addDivider(orientation, size, Color.TRANSPARENT);
    }

    /**
     * 添加分割线
     *
     * @param orientation 列表方向
     * @param size        分割线大小
     * @param color       分割线颜色
     *
     * @see #VERTICAL
     * @see #HORIZONTAL
     */
    public void addDivider(@Orientation int orientation, int size, int color) {
        addDivider(orientation, size, color, 0);
    }

    /**
     * 添加分割线
     *
     * @param orientation 列表方向
     * @param size        分割线大小
     * @param color       分割线颜色
     * @param padding     分割线缩进大小
     *
     * @see #VERTICAL
     * @see #HORIZONTAL
     */
    public void addDivider(@Orientation int orientation, int size, int color, int padding) {
        XMEqualDividerItemDecoration decoration = new XMEqualDividerItemDecoration(orientation, size, color);
        decoration.setPadding(padding);

        addItemDecoration(decoration);
    }

    /**
     * 添加分割线
     *
     * @param orientation 列表方向
     * @param drawable    分割线图案
     *
     * @see #VERTICAL
     * @see #HORIZONTAL
     */
    public void addDivider(@Orientation int orientation, Drawable drawable) {
        XMEqualDividerItemDecoration decoration = new XMEqualDividerItemDecoration(orientation, drawable);

        addItemDecoration(decoration);
    }

    /**
     * 添加分割线
     *
     * @param orientation 列表方向
     * @param drawable    分割线图案
     * @param padding     分割线缩进大小
     *
     * @see #VERTICAL
     * @see #HORIZONTAL
     */
    public void addDivider(@Orientation int orientation, Drawable drawable, int padding) {
        XMEqualDividerItemDecoration decoration = new XMEqualDividerItemDecoration(orientation, drawable);
        decoration.setPadding(padding);

        addItemDecoration(decoration);
    }
    //</editor-fold>

    /** 获取列表中数据数量 */
    public final <VH extends ViewHolder> int getItemCount() {
        return getAdapter() == null ? 0 : getAdapter().getItemCount();
    }

    /** 滑动到顶部 */
    public final void scrollToHeader() {
        scrollToPosition(0);
    }

    /** 滑动到顶部，展现滑动过程 */
    public final void smoothScrollToHeader() {
        smoothScrollToPosition(0);
    }

    /** 滑动到底部 */
    public final void scrollToFooter() {
        int count = getItemCount();

        if (count > 0)
            scrollToPosition(count - 1);
    }

    /** 滑动到底部，展示滑动过程 */
    public final void smoothScrollToFooter() {
        int count = getItemCount();

        if (count > 0)
            smoothScrollToPosition(count - 1);
    }
}
