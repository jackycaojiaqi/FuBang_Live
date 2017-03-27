package com.fubang.live.base;

import android.content.Context;
import android.icu.text.DateFormat;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.fubang.live.util.ScreenUtils;

import java.lang.reflect.Field;

/**
 * Created by jacky on 17/3/23.
 */
public class BaseFragment extends Fragment {
    /**
     * 通过反射修改TabLayout Indicator的宽度（仅在Android 4.2及以上生效）
     */
    public void setUpIndicatorWidth(Context context, TabLayout tabLayout) {
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout layout = null;
        try {
            if (tabStrip != null) {
                layout = (LinearLayout) tabStrip.get(tabLayout);
            }
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setPadding(ScreenUtils.Dp2Px(context, 10f), 0, ScreenUtils.Dp2Px(context, 10f), 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.setMargins(ScreenUtils.Dp2Px(context, 10f), 0, ScreenUtils.Dp2Px(context, 10f), 0);
                }
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
