package com.zhang.library.view.layout;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 适配系统状态栏风格
 *
 * @author ZhangXiaoMing 2024-04-14 22:10 周日
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({
        FitsSystemStyle.NONE,
        FitsSystemStyle.PADDING,
        FitsSystemStyle.MARGIN,
})
public @interface FitsSystemStyle {

    int NONE = 0;
    int PADDING = 1;
    int MARGIN = 2;
}
