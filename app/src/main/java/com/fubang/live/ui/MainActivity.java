package com.fubang.live.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fubang.live.R;
import com.fubang.live.adapter.FragmentTabAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.iv_main_home_page)
    ImageView ivMainHomePage;
    @BindView(R.id.tv_main_home_page)
    TextView tvMainHomePage;
    @BindView(R.id.ll_main_home_page)
    LinearLayout llMainHomePage;
    @BindView(R.id.iv_main_home_ranking)
    ImageView ivMainHomeRanking;
    @BindView(R.id.tv_main_home_ranking)
    TextView tvMainHomeRanking;
    @BindView(R.id.ll_main_home_ranking)
    LinearLayout llMainHomeRanking;
    @BindView(R.id.iv_main_home_favorite)
    ImageView ivMainHomeFavorite;
    @BindView(R.id.tv_main_home_favorite)
    TextView tvMainHomeFavorite;
    @BindView(R.id.ll_main_home_favorite)
    LinearLayout llMainHomeFavorite;
    @BindView(R.id.iv_main_home_mine)
    ImageView ivMainHomeMine;
    @BindView(R.id.tv_main_home_mine)
    TextView tvMainHomeMine;
    @BindView(R.id.ll_main_home_mine)
    LinearLayout llMainHomeMine;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private FragmentTransaction ft;
    private FragmentTabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initview();
        initdate();
    }


    private void initview() {
    }

    private void initdate() {
    }

    /**
     * 跳转到对应的fragment页面
     *
     * @param i
     */
    private void toSelectFm(int i) {
        Fragment fragment = fragments.get(i);
        ft = tabAdapter.obtainFragmentTransaction(i);
        tabAdapter.getCurrentFragment().onPause(); // 暂停当前tab

        if (fragment.isAdded()) {
            fragment.onResume(); // 启动目标tab的onResume()
        } else {
            ft.add(R.id.main_contaner, fragment);
        }
        tabAdapter.showTab(i); // 显示目标tab
        ft.commitAllowingStateLoss();
    }

    @OnClick({R.id.ll_main_home_page, R.id.ll_main_home_ranking, R.id.ll_main_home_favorite, R.id.ll_main_home_mine})
    void clicks(View view) {
        switch (view.getId()) {
            case R.id.ll_main_home_page:
                ivMainHomePage.setImageResource(R.drawable.home_pressed);
                ivMainHomeRanking.setImageResource(R.drawable.ranking_list);
                ivMainHomeFavorite.setImageResource(R.drawable.follow);
                ivMainHomeMine.setImageResource(R.drawable.me);
                toSelectFm(0);
                break;
            case R.id.ll_main_home_ranking:
                ivMainHomePage.setImageResource(R.drawable.home);
                ivMainHomeRanking.setImageResource(R.drawable.ranking_list_select);
                ivMainHomeFavorite.setImageResource(R.drawable.follow);
                ivMainHomeMine.setImageResource(R.drawable.me);
                toSelectFm(1);
                break;
            case R.id.ll_main_home_favorite:
                ivMainHomePage.setImageResource(R.drawable.home);
                ivMainHomeRanking.setImageResource(R.drawable.ranking_list);
                ivMainHomeFavorite.setImageResource(R.drawable.follow_select);
                ivMainHomeMine.setImageResource(R.drawable.me);
                toSelectFm(2);
                break;
            case R.id.ll_main_home_mine:
                ivMainHomePage.setImageResource(R.drawable.home);
                ivMainHomeRanking.setImageResource(R.drawable.ranking_list);
                ivMainHomeFavorite.setImageResource(R.drawable.follow);
                ivMainHomeMine.setImageResource(R.drawable.me_select);
                toSelectFm(3);
                break;
        }
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
