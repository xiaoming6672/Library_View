package com.zhang.library.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.zhang.library.common.view.BaseAppView;
import com.zhang.library.utils.constant.ViewDirection;
import com.zhang.library.utils.context.ResUtils;
import com.zhang.library.utils.context.ViewUtils;
import com.zhang.library.view.databinding.ViewCustomTitleBinding;

/**
 * 自定义标题栏
 *
 * @author ZhangXiaoMing 2023-08-29 22:46 周二
 */
public class XMCustomTitleView extends BaseAppView {

    private ViewCustomTitleBinding mBinding;

    private boolean isAutoFitStatusBar;
    /** 返回图标 */
    private Drawable mBackRes;
    /** 是否隐藏返回键 */
    private boolean isHideBack;
    /** 返回键色调 */
    private ColorStateList mBackTint;
    /** 标题名 */
    private String mTitleName;
    /** 标题字色 */
    private ColorStateList mTitleColor;
    /** 标题字号 */
    private float mTitleSize;
    /** 标题是否加粗 */
    private boolean isTitleBold;
    /** 右侧功能图标 */
    private Drawable mFunctionIcon;
    /** 右侧功能名 */
    private String mFunctionName;
    /** 右侧功能字色 */
    private ColorStateList mFunctionColor;
    /** 右侧功能字号 */
    private float mFunctionSize;
    /** 右侧功能偏移量 */
    private float mFunctionOffset;

    /** 后退按钮点击事件 */
    private View.OnClickListener mBackOnClickListener;
    /** 标题点击事件 */
    private View.OnClickListener mTitleOnClickListener;
    /** 右侧功能点击事件 */
    private View.OnClickListener mFunctionOnClickListener;


    public XMCustomTitleView(@NonNull Context context) {
        super(context);
    }

    public XMCustomTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XMCustomTitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /** 获取View的布局 */
    @Override
    protected int onCreateLayoutId() {
        return R.layout.view_custom_title;
    }

    @Override
    protected void initAttribute(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (isInEditMode())
            ResUtils.set(context.getApplicationContext());

        if (attrs == null)
            return;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.XMCustomTitleView);

        isAutoFitStatusBar = array.getBoolean(R.styleable.XMCustomTitleView_autoFitStatusBar, true);

        mBackRes = array.getDrawable(R.styleable.XMCustomTitleView_backRes);
        isHideBack = array.getBoolean(R.styleable.XMCustomTitleView_hideBack, false);
        mBackTint = array.getColorStateList(R.styleable.XMCustomTitleView_backTint);

        mTitleName = array.getString(R.styleable.XMCustomTitleView_titleName);
        mTitleColor = array.getColorStateList(R.styleable.XMCustomTitleView_titleTextColor);
        mTitleSize = array.getDimension(R.styleable.XMCustomTitleView_titleTextSize, ResUtils.dp2px(18));
        isTitleBold = array.getBoolean(R.styleable.XMCustomTitleView_titleBold, true);

        mFunctionIcon = array.getDrawable(R.styleable.XMCustomTitleView_functionIcon);
        mFunctionName = array.getString(R.styleable.XMCustomTitleView_functionName);
        mFunctionColor = array.getColorStateList(R.styleable.XMCustomTitleView_functionTextColor);
        mFunctionSize = array.getDimension(R.styleable.XMCustomTitleView_functionTextSize, ResUtils.dp2px(14));
        mFunctionOffset = array.getDimension(R.styleable.XMCustomTitleView_functionOffset, ResUtils.dp2px(15));

        array.recycle();
    }

    /** 初始化控件 */
    @Override
    protected void initView() {
        mBinding = ViewCustomTitleBinding.bind(this);

        mBinding.ivBack.setOnClickListener(v -> {
            if (mBackOnClickListener != null)
                mBackOnClickListener.onClick(v);
        });
        mBinding.tvTitleName.setOnClickListener(v -> {
            if (mTitleOnClickListener != null)
                mTitleOnClickListener.onClick(v);
        });
        mBinding.ivFunctionIcon.setOnClickListener(v -> {
            if (mFunctionOnClickListener != null)
                mFunctionOnClickListener.onClick(v);
        });
        mBinding.tvFunctionName.setOnClickListener(v -> {
            if (mFunctionOnClickListener != null)
                mFunctionOnClickListener.onClick(v);
        });
    }

    /** 初始化数据 */
    @Override
    protected void initData() {
        if (isAutoFitStatusBar)
            ViewUtils.fitsSystemWindowsByPaddingTop(this);

        if (mBackRes != null)
            mBinding.ivBack.setImageDrawable(mBackRes);
        if (mBackTint != null)
            mBinding.ivBack.setImageTintList(mBackTint);
        ViewUtils.setViewVisibleOrGone(mBinding.ivBack, !isHideBack);

        mBinding.tvTitleName.setText(mTitleName);
        if (mTitleColor != null)
            mBinding.tvTitleName.setTextColor(mTitleColor);
        if (mTitleSize > 0)
            mBinding.tvTitleName.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
        mBinding.tvTitleName.setTypeface(isTitleBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);

        if (mFunctionIcon != null) {
            mBinding.ivFunctionIcon.setVisibility(VISIBLE);
            mBinding.tvFunctionName.setVisibility(GONE);

            mBinding.ivFunctionIcon.setImageDrawable(mFunctionIcon);
        } else if (!TextUtils.isEmpty(mFunctionName)) {
            mBinding.ivFunctionIcon.setVisibility(GONE);
            mBinding.tvFunctionName.setVisibility(VISIBLE);

            mBinding.tvFunctionName.setText(mFunctionName);
            if (mFunctionColor != null)
                mBinding.tvFunctionName.setTextColor(mFunctionColor);
            if (mFunctionSize > 0)
                mBinding.tvFunctionName.setTextSize(TypedValue.COMPLEX_UNIT_PX, mFunctionSize);
        } else {
            mBinding.ivFunctionIcon.setVisibility(GONE);
            mBinding.tvFunctionName.setVisibility(GONE);
        }

        if (mFunctionOffset > 0) {
            ViewUtils.setMarginValue(mBinding.ivFunctionIcon, (int) mFunctionOffset, ViewDirection.RIGHT);
            ViewUtils.setMarginValue(mBinding.tvFunctionName, (int) mFunctionOffset, ViewDirection.RIGHT);
        }
    }


    /**
     * 设置返回键图标资源
     *
     * @param resId 资源id
     */
    public XMCustomTitleView setBackRes(@DrawableRes int resId) {
        mBinding.ivBack.setImageResource(resId);
        return this;
    }

    /**
     * 设置返回键图标
     *
     * @param drawable drawable
     */
    public XMCustomTitleView setBackRes(@Nullable Drawable drawable) {
        mBackRes = drawable;
        mBinding.ivBack.setImageDrawable(drawable);
        return this;
    }

    /**
     * 设置标题名
     *
     * @param resId 资源id
     */
    public XMCustomTitleView setTitleName(@StringRes int resId) {
        mBinding.tvTitleName.setText(resId);
        return this;
    }

    /**
     * 设置标题名
     *
     * @param name 名称
     */
    public XMCustomTitleView setTitleName(String name) {
        this.mTitleName = name;
        mBinding.tvTitleName.setText(name);
        return this;
    }

    /**
     * 设置标题字色
     *
     * @param color 颜色
     */
    public XMCustomTitleView setTitleTextColor(int color) {
        mTitleColor = ColorStateList.valueOf(color);
        mBinding.tvTitleName.setTextColor(mTitleColor);
        return this;
    }

    /**
     * 设置标题字色
     *
     * @param color 颜色
     */
    public XMCustomTitleView setTitleTextColor(ColorStateList color) {
        mTitleColor = color;
        mBinding.tvTitleName.setTextColor(mTitleColor);
        return this;
    }

    /**
     * 设置标题字号
     *
     * @param px 字号
     */
    public XMCustomTitleView setTitleTextSize(float px) {
        mTitleSize = px;
        mBinding.tvTitleName.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
        return this;
    }

    /**
     * 设置标题是否加粗显示
     *
     * @param bold 是否加粗
     */
    public XMCustomTitleView setTitleBold(boolean bold) {
        isTitleBold = bold;
        mBinding.tvTitleName.setTypeface(isTitleBold ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
        return this;
    }

    /**
     * 设置右侧功能图标资源id
     *
     * @param resId 资源id
     */
    public XMCustomTitleView setFunctionIcon(@DrawableRes int resId) {
        mBinding.ivFunctionIcon.setImageResource(resId);

        mBinding.ivFunctionIcon.setVisibility(VISIBLE);
        mBinding.tvFunctionName.setVisibility(GONE);

        return this;
    }

    /**
     * 设置右侧功能图标
     *
     * @param drawable 图标
     */
    public XMCustomTitleView setFunctionIcon(@Nullable Drawable drawable) {
        mFunctionIcon = drawable;
        mBinding.ivFunctionIcon.setImageDrawable(drawable);

        mBinding.ivFunctionIcon.setVisibility(VISIBLE);
        mBinding.tvFunctionName.setVisibility(GONE);

        return this;
    }

    /**
     * 设置右侧功能名
     *
     * @param resId 资源id
     */
    public XMCustomTitleView setFunctionName(@StringRes int resId) {
        mBinding.tvFunctionName.setText(resId);

        mBinding.ivFunctionIcon.setVisibility(GONE);
        mBinding.tvFunctionName.setVisibility(VISIBLE);

        return this;
    }

    /**
     * 设置右侧功能名
     *
     * @param name 名称
     */
    public XMCustomTitleView setFunctionName(String name) {
        mFunctionName = name;
        mBinding.tvFunctionName.setText(mFunctionName);

        mBinding.ivFunctionIcon.setVisibility(GONE);
        mBinding.tvFunctionName.setVisibility(VISIBLE);

        return this;
    }

    /**
     * 设置右侧功能字色
     *
     * @param color 颜色
     */
    public XMCustomTitleView setFunctionTextColor(int color) {
        mFunctionColor = ColorStateList.valueOf(color);
        mBinding.tvFunctionName.setTextColor(mFunctionColor);
        return this;
    }

    /**
     * 设置右侧功能颜色
     *
     * @param color 颜色
     */
    public XMCustomTitleView setFunctionTextColor(ColorStateList color) {
        mFunctionColor = color;
        mBinding.tvFunctionName.setTextColor(mFunctionColor);
        return this;
    }

    /**
     * 设置右侧功能字号
     *
     * @param px 字号
     */
    public XMCustomTitleView setFunctionTextSize(float px) {
        mFunctionSize = px;
        mBinding.tvFunctionName.setTextSize(TypedValue.COMPLEX_UNIT_PX, mFunctionSize);
        return this;
    }

    /**
     * 设置右侧功能偏移量
     *
     * @param offset 偏移量
     */
    public XMCustomTitleView setFunctionOffset(float offset) {
        mFunctionOffset = offset;
        ViewUtils.setMarginValue(mBinding.ivFunctionIcon, (int) mFunctionOffset, ViewDirection.RIGHT);
        ViewUtils.setMarginValue(mBinding.tvFunctionName, (int) mFunctionOffset, ViewDirection.RIGHT);
        return this;
    }

    /**
     * 设置返回键点击事件
     *
     * @param listener 监听器
     */
    public XMCustomTitleView setBackOnClickListener(View.OnClickListener listener) {
        mBackOnClickListener = listener;
        return this;
    }

    /**
     * 设置标题点击事件
     *
     * @param listener 监听器
     */
    public XMCustomTitleView setTitleOnClickListener(View.OnClickListener listener) {
        mTitleOnClickListener = listener;
        return this;
    }

    /**
     * 设置功能按钮点击事件
     *
     * @param listener 监听器
     */
    public XMCustomTitleView setFunctionOnClickListener(View.OnClickListener listener) {
        mFunctionOnClickListener = listener;
        return this;
    }
}
