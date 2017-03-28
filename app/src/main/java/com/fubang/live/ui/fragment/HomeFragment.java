package com.fubang.live.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fubang.live.R;
import com.fubang.live.adapter.MyFragmentPagerAdapter;
import com.fubang.live.base.BaseFragment;
import com.fubang.live.ui.SearchAtivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.tb_fm_home_tab)
    TabLayout tbFmHomeTab;
    @BindView(R.id.vp_fm_home_tab)
    ViewPager vpFmHomeTab;
    @BindView(R.id.iv_home_search)
    ImageView ivHomeSearch;
    @BindView(R.id.iv_home_message)
    ImageView ivHomeMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Fragment+ViewPager+FragmentViewPager组合的使用
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
                getActivity());
        vpFmHomeTab.setOffscreenPageLimit(1);
        vpFmHomeTab.setAdapter(adapter);
        tbFmHomeTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tbFmHomeTab.setupWithViewPager(vpFmHomeTab);
    }


    @OnClick({R.id.iv_home_search, R.id.iv_home_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home_search:
                startActivity(new Intent(getActivity(), SearchAtivity.class));
                break;
            case R.id.iv_home_message:
                break;
        }
    }
}
