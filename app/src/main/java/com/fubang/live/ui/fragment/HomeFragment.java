package com.fubang.live.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fubang.live.R;
import com.fubang.live.adapter.MyFragmentPagerAdapter;
import com.fubang.live.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.tb_fm_home_tab)
    TabLayout tbFmHomeTab;
    @BindView(R.id.vp_fm_home_tab)
    ViewPager vpFmHomeTab;

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
        vpFmHomeTab.setOffscreenPageLimit(3);
        vpFmHomeTab.setAdapter(adapter);
        tbFmHomeTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tbFmHomeTab.setupWithViewPager(vpFmHomeTab);
    }


}
