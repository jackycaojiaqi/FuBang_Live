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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.fubang.live.R;
import com.fubang.live.adapter.FragmentTabAdapter;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.RtmpUrlEntity;
import com.fubang.live.modle.impl.RtmpUrlModelImpl;
import com.fubang.live.presenter.impl.RtmpUrlPresenterImpl;
import com.fubang.live.ui.fragment.FollowFragment;
import com.fubang.live.ui.fragment.HomeFragment;
import com.fubang.live.ui.fragment.MineFragment;
import com.fubang.live.ui.fragment.NearFragment;
import com.fubang.live.util.Config;
import com.fubang.live.util.ToastUtil;
import com.fubang.live.view.RtmpUrlView;
import com.socks.library.KLog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends BaseActivity implements RtmpUrlView, AMapLocationListener {
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
    private RtmpUrlPresenterImpl presenter;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient;

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

    private void initlocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @PermissionSuccess(requestCode = 200)
    public void doSomething() {
        initlocation();
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
        //获取权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            KLog.e("333");
            PermissionGen.with(MainActivity.this)
                    .addRequestCode(200)
                    .permissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();
        } else {
            initlocation();
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
                presenter = new RtmpUrlPresenterImpl(MainActivity.this, "10088", "88888");
                presenter.getRtmpUrl();

            }
        });
        iv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter = new RtmpUrlPresenterImpl(MainActivity.this, "10088", "88888");
                presenter.getRtmpUrl();
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

    @Override
    public void success(RtmpUrlEntity entity) {
        if (entity != null) {
            Intent intent = new Intent(context, LiveActivity.class);
            intent.putExtra(Config.EXTRA_KEY_PUB_URL,
                    entity.getPublishUrl());
            startActivity(intent);
            pop_main.dismiss();
        }

    }

    @Override
    public void faided() {
        ToastUtil.show(context, R.string.net_error);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        KLog.e(aMapLocation.getCity());
        KLog.e(aMapLocation.getAddress());
    }
}
