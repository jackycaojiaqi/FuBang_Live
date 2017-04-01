package com.fubang.live.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.fubang.live.R;
import com.fubang.live.adapter.FragmentTabAdapter;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.ui.fragment.FollowFragment;
import com.fubang.live.ui.fragment.HomeFragment;
import com.fubang.live.ui.fragment.MineFragment;
import com.fubang.live.ui.fragment.NearFragment;
import com.fubang.live.util.Config;
import com.socks.library.KLog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends BaseActivity {
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
    @BindView(R.id.iv_main_home_live)
    ImageView ivMainHomeLive;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private FragmentTransaction ft;
    private FragmentTabAdapter tabAdapter;
    private PopupWindow pop_main;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        initview();
        initdate();
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething() {
        KLog.e("333");
    }

    private void initview() {
        //获取权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            KLog.e("111");
            PermissionGen.with(MainActivity.this)
                    .addRequestCode(100)
                    .permissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();
        } else {
            KLog.e("222");
        }
        fragments.add(0, new HomeFragment());
        fragments.add(1, new NearFragment());
        fragments.add(2, new FollowFragment());
        fragments.add(3, new MineFragment());
        tabAdapter = new FragmentTabAdapter(this, fragments, R.id.main_contaner, null);
        //默认显示首页
        toSelectFm(0);
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

    @OnClick({R.id.ll_main_home_page, R.id.ll_main_home_ranking, R.id.ll_main_home_favorite, R.id.ll_main_home_mine
            , R.id.iv_main_home_live})
    void clicks(View view) {
        switch (view.getId()) {
            case R.id.ll_main_home_page:
                ivMainHomePage.setImageResource(R.drawable.ic_main_home);
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
                ivMainHomeMine.setImageResource(R.drawable.ic_main_me);
                toSelectFm(3);
                break;
            case R.id.iv_main_home_live:
                ShowPopAction();
                break;
        }
    }

    /**
     * 处理分享弹窗
     */
    private void ShowPopAction() {
        View popupView = getLayoutInflater().inflate(R.layout.pop_main, null);
        pop_main = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop_main.showAtLocation(llMainHomeFavorite, Gravity.TOP, 0, 0);
        pop_main.setAnimationStyle(R.style.take_photo_anim);
        ImageView iv_cancle = (ImageView) popupView.findViewById(R.id.tv_main_cancle);
        ImageView iv_live = (ImageView) popupView.findViewById(R.id.iv_main_goto_live);
        ImageView iv_video = (ImageView) popupView.findViewById(R.id.iv_main_goto_video);
        iv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_main.dismiss();
            }
        });
        iv_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LiveActivity.class);
                intent.putExtra(Config.EXTRA_KEY_PUB_URL,
                        "rtmp://pili-publish.fbyxsp.com/wanghong/wh_10088_58888?e=1491016444&token=rgNGguFNzZb47-3LXCxtm4H5iMjbMG-5dhhOR512:TgnVOQSwRYif9kD-9sItlxgqso0=");
                startActivity(intent);
                pop_main.dismiss();
            }
        });
        iv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LiveActivity.class);
                intent.putExtra(Config.EXTRA_KEY_PUB_URL,
                        "rtmp://pili-publish.fbyxsp.com/wanghong/wh_10088_58888?e=1491016444&token=rgNGguFNzZb47-3LXCxtm4H5iMjbMG-5dhhOR512:TgnVOQSwRYif9kD-9sItlxgqso0=");
                startActivity(intent);
                pop_main.dismiss();
            }
        });

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
