package com.fubang.live.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fubang.live.ui.fragment.FemaleFragment;
import com.fubang.live.ui.fragment.FollowFragment;
import com.fubang.live.ui.fragment.GameFragment;
import com.fubang.live.ui.fragment.HotFragment;
import com.fubang.live.ui.fragment.MaleFragment;
import com.fubang.live.ui.fragment.NearFragment;
import com.fubang.live.ui.fragment.SingerFragment;
import com.fubang.live.ui.fragment.TalentFragment;
import com.fubang.live.ui.fragment.VideoFragment;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * Created by jacky on 17/3/17.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public final int COUNT = 7;
    private String[] titles = new String[]{"关注", "热门", "附近", "才艺", "好声音","帅哥","美女"};
    private Context context;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FollowFragment();//关注
        } else if (position == 1) {
            return new HotFragment();//热门
        } else if (position == 2) {
            return new NearFragment();//附近
        } else if (position == 3) {
            return new TalentFragment();//才艺
        } else if (position == 4) {
            return new SingerFragment();//好声音
        } else if (position == 5) {
            return new MaleFragment();//
        } else if (position == 6) {
            return new FemaleFragment();
        }
        return new FollowFragment();
    }


    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
