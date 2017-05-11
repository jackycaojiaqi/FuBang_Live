package com.fubang.live.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TableLayout;
import android.widget.TextView;

import com.fubang.live.R;
import com.fubang.live.adapter.MyFragmentPagerAdapter;
import com.fubang.live.adapter.MyMusicPagerAdapter;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.util.ConfigUtils;
import com.fubang.live.util.FileUtils;
import com.fubang.live.util.ScreenUtils;
import com.fubang.live.widget.ClearableEditText;
import com.socks.library.KLog;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by jacky on 2017/5/8.
 */
public class LivePickMusicActivity extends BaseActivity {
    @BindView(R.id.et_search_music_content)
    ClearableEditText etSearchMusicContent;
    @BindView(R.id.tv_search_music_cancle)
    TextView tvSearchMusicCancle;
    @BindView(R.id.tl_live_music)
    TabLayout tlLiveMusic;
    @BindView(R.id.vp_live_music)
    ViewPager vpLiveMusic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigUtils.setStatusBarColor(this, getResources().getColor(R.color.color_music_title));
        setContentView(R.layout.activity_live_pick_music);
        ButterKnife.bind(this);
        initview();
    }


    private void initview() {
        //Fragment+ViewPager+FragmentViewPager组合的使用
        MyMusicPagerAdapter adapter = new MyMusicPagerAdapter(getSupportFragmentManager(),
                this);
        vpLiveMusic.setOffscreenPageLimit(1);
        vpLiveMusic.setAdapter(adapter);
        vpLiveMusic.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tlLiveMusic.setTabMode(TabLayout.MODE_FIXED);
        tlLiveMusic.setupWithViewPager(vpLiveMusic);
        etSearchMusicContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EventBus.getDefault().post(s.toString(), "music_key");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.tv_search_music_cancle)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_search_music_cancle:
                finish();
                break;
        }

    }
}
