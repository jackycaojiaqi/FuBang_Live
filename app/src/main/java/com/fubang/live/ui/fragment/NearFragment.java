package com.fubang.live.ui.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.RoomFavAdapter;
import com.fubang.live.adapter.RoomNearAdapter;
import com.fubang.live.base.BaseFragment;
import com.fubang.live.entities.RoomDistanceEntity;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RoomListEntity;
import com.fubang.live.listener.HidingScrollListener;
import com.fubang.live.listener.UpDownScrollListener;
import com.fubang.live.presenter.impl.RoomListPresenterImpl;
import com.fubang.live.ui.MainActivity;
import com.fubang.live.ui.RoomActivity;
import com.fubang.live.util.LocationUtil;
import com.fubang.live.util.ScreenUtils;
import com.fubang.live.util.StartUtil;
import com.fubang.live.view.RoomListView;
import com.fubang.live.widget.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AMapLocationListener {
    @BindView(R.id.tv_near_filter_name)
    TextView tvNearFilterName;
    @BindView(R.id.rl_near_filter)
    RelativeLayout rlNearFilter;
    @BindView(R.id.rv_near)
    RecyclerView rvNear;
    Unbinder unbinder;
    @BindView(R.id.srl_near)
    SwipeRefreshLayout srlNear;
    private Context context;
    private int count = 10;
    private int page = 1;
    private int group = 0;
    private List<RoomDistanceEntity.RoomlistBean> list = new ArrayList<>();
    private BaseQuickAdapter roomFavAdapter;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_near, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        initview();
        initpermission();
    }

    //定位权限请求成功
    @PermissionSuccess(requestCode = 200)
    public void doSomething() {
        initlocation();
    }

    private void initpermission() {
        //获取权限
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionGen.with(getActivity())
                    .addRequestCode(200)
                    .permissions(
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    .request();
        } else {
            initlocation();
        }
    }

    private void initlocation() {
        mlocationClient = new AMapLocationClient(getActivity());
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
//        mlocationClient.startLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mlocationClient != null) {
            initdate();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.onDestroy();
        }
    }

    private void initview() {
        //=========================recycleview
        roomFavAdapter = new RoomNearAdapter(R.layout.item_room_near, list);
        rvNear.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        roomFavAdapter.openLoadAnimation();
        roomFavAdapter.setAutoLoadMoreSize(5);
        roomFavAdapter.setEnableLoadMore(true);
        roomFavAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, RoomActivity.class);
                intent.putExtra(AppConstant.ROOMID, list.get(position).getRoomid());
                intent.putExtra(AppConstant.ROOMIP, list.get(position).getGateway());
                intent.putExtra(AppConstant.ROOMPWD, list.get(position).getRoompwd());
                startActivity(intent);
            }
        });
        roomFavAdapter.bindToRecyclerView(rvNear);
        roomFavAdapter.setEmptyView(R.layout.empty_view);
        rvNear.setAdapter(roomFavAdapter);
        //=====================下拉刷新
        srlNear.setOnRefreshListener(this);
        //设置样式刷新显示的位置
        srlNear.setProgressViewOffset(true, -10, 50);
    }

    private RoomDistanceEntity roomEntity;

    private void initdate() {
        String url = AppConstant.BASE_URL + AppConstant.MSG_GET_DISTANCE_LIST;
        HttpParams params = new HttpParams();
        params.put("nuserid", StartUtil.getUserId(context));
        params.put(AppConstant.PAGE, page);
        params.put(AppConstant.COUNT, count);
        if (gender_type == 0 || gender_type == 1) {//如果选了性别，则传参数
            params.put("ngender", gender_type);
        }
        OkGo.get(url)//
                .tag(this)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        srlNear.setRefreshing(false);
                        try {
                            roomEntity = new Gson().fromJson(s, RoomDistanceEntity.class);
                            if (roomEntity.getStatus().equals("success")) {
                                list.clear();
                                List<RoomDistanceEntity.RoomlistBean> roomListEntities = roomEntity.getRoomlist();
                                list.addAll(roomListEntities);
                                roomFavAdapter.notifyDataSetChanged();
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            list.clear();
                            roomFavAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();
                        srlNear.setRefreshing(false);
                    }
                });
    }

    private CustomPopWindow pop_info;

    @OnClick(R.id.rl_near_filter)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_near_filter:
                View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_filter_gender, null);
                //处理popWindow 显示内容
                handleLogic(contentView);
                //创建并显示popWindow
                pop_info = new CustomPopWindow.PopupWindowBuilder(getActivity())
                        .setView(contentView)
                        .setOutsideTouchable(false)//是否PopupWindow 以外触摸dissmiss
                        .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                        .setBgDarkAlpha(0.5f) // 控制亮度
                        .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                        .create()
                        .showAtLocation(rlNearFilter, Gravity.CENTER, 0, 0);
                break;
        }
    }

    private int gender_type = -1;// 0全部  1、女性、 2 男性

    private void handleLogic(View contentView) {
        TextView tv_all = (TextView) contentView.findViewById(R.id.pop_filter_tv_all);
        TextView tv_female = (TextView) contentView.findViewById(R.id.pop_filter_tv_female);
        TextView tv_male = (TextView) contentView.findViewById(R.id.pop_filter_tv_male);

        TextView tv_cancle = (TextView) contentView.findViewById(R.id.pop_filter_cancle);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_info.dissmiss();
            }
        });
        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_type = -1;
                pop_info.dissmiss();
                tvNearFilterName.setText("看全部");
                initdate();
            }
        });
        tv_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_type = 0;
                pop_info.dissmiss();
                tvNearFilterName.setText("美女");
                initdate();
            }
        });
        tv_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender_type = 1;
                pop_info.dissmiss();
                tvNearFilterName.setText("帅哥");
                initdate();
            }
        });

    }

    private long before = 0;
    private long after = 0;

    @Override
    public void onRefresh() {
        page = 1;
        before = System.currentTimeMillis();
        if (after == 0) {//第一次先定位
            mlocationClient.startLocation();
        } else {//之后判断间隔时间
            if (mlocationClient != null) {
                if (after - before > 1000000)//100秒后才刷新
                {
                    mlocationClient.startLocation();
                } else {
                    initdate();
                }
            }
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            StartUtil.putLAT(context, String.valueOf(aMapLocation.getLatitude()));
            StartUtil.putLNG(context, String.valueOf(aMapLocation.getLongitude()));
            LocationUtil.uploadLatLng(context, String.valueOf(aMapLocation.getLatitude()),
                    String.valueOf(aMapLocation.getLongitude()));
            initdate();
            after = before;
        }

    }
}
