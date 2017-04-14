package com.fubang.live.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.icu.text.DateFormat;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
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

    public void setAnimaAlpha(final View view) {
        view.setVisibility(View.VISIBLE);
        AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
        animation1.setDuration(30 * 1000);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.setAnimation(animation1);
        animation1.start();
    }


    private View view = null;//存储fragemnt的视图
    private int height = 180;//indicator高低
    public static boolean isIndicatorShow = true;//是否显示
    private boolean isShowing = false;//动画是否正在进行

    public void showIndicator(final View view, Context context) {
        height = ScreenUtils.Dp2Px(context, 62);
        //属性动画translationY
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY",
                0f);
        //持续时间
        animator.setDuration(300);
        //插值器，减速
        animator.setInterpolator(new DecelerateInterpolator(3f));
        //监听器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //不断增加indicator所在viewgroup的高度
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                float v = (Float) animation.getAnimatedValue();
                layoutParams.height = height + (int) v;
                //重新布局
                view.requestLayout();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                //动画开始后设置为true
                isShowing = true;
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束后设置为false
                isShowing = false;
                //显示后设置为已显示
                isIndicatorShow = true;
                super.onAnimationEnd(animation);
            }

        });
        //开始动画
        animator.start();
    }

    public void hideIndicator(final View view, Context context) {
        height = ScreenUtils.Dp2Px(context, 62);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", -height);
        animator.setDuration(300);
        //加速插值器
        animator.setInterpolator(new AccelerateInterpolator(3f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //不断减小indicator所在viewgroup的高度
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                float v = (Float) animation.getAnimatedValue();
                Log.d("TAG", "hide:" + v);
                layoutParams.height = height + (int) v;
                view.requestLayout();
            }
        });
        //同显示
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isShowing = true;
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isShowing = false;
                isIndicatorShow = false;
                super.onAnimationEnd(animation);
            }


        });
        animator.start();

    }
}
