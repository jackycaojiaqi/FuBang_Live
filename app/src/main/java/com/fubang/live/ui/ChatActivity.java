package com.fubang.live.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;

import com.fubang.live.R;
import com.fubang.live.adapter.MyChatPagerAdapter;
import com.fubang.live.adapter.MyMusicPagerAdapter;
import com.fubang.live.base.BaseActivity;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jacky on 2017/5/22.
 */
public class ChatActivity extends BaseActivity {
    @BindView(R.id.tl_chat)
    TabLayout tlChat;
    @BindView(R.id.vp_chat)
    ViewPager vpChat;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {
        back(ivBack);
        //Fragment+ViewPager+FragmentViewPager组合的使用
        MyChatPagerAdapter adapter = new MyChatPagerAdapter(getSupportFragmentManager(),
                this);
        vpChat.setOffscreenPageLimit(1);
        vpChat.setAdapter(adapter);
        vpChat.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        tlChat.setTabMode(TabLayout.MODE_FIXED);
        tlChat.setupWithViewPager(vpChat);
    }
}
