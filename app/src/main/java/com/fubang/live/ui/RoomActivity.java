package com.fubang.live.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.MyRoomFragmentVerticalPagerAdapter;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.RtmpUrlEntity;
import com.fubang.live.presenter.impl.RtmpUrlPresenterImpl;
import com.fubang.live.ui.fragment.FollowFragment;
import com.fubang.live.ui.fragment.GameFragment;
import com.fubang.live.ui.fragment.HotFragment;
import com.fubang.live.ui.fragment.NearFragment;
import com.fubang.live.ui.fragment.RoomContentFragment;
import com.fubang.live.ui.fragment.RoomNoContentFragment;
import com.fubang.live.ui.fragment.VideoFragment;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.StringUtil;
import com.fubang.live.util.ToastUtil;
import com.fubang.live.view.RtmpUrlView;
import com.socks.library.KLog;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kaelaela.verticalviewpager.VerticalViewPager;
import me.kaelaela.verticalviewpager.transforms.DefaultTransformer;
import me.kaelaela.verticalviewpager.transforms.StackTransformer;
import me.kaelaela.verticalviewpager.transforms.ZoomOutTransformer;

/**
 * Created by jacky on 17/3/27.
 */
public class RoomActivity extends BaseActivity implements RtmpUrlView {
    public static VerticalViewPager dvpRoom;
    private Context context;
    public static boolean is_emoticon_show = false;
    private RtmpUrlPresenterImpl presenter;
    private String rtmp_url;
    private String roomIp, ip, port, roomPwd;
    private String roomId;
    private List<Fragment> list_fragment = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);
        dvpRoom = (VerticalViewPager) findViewById(R.id.vp_room);

        roomId = getIntent().getStringExtra(AppConstant.ROOMID);
        context = this;
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.ROOMPWD, getIntent().getStringExtra(AppConstant.ROOMPWD));
        bundle.putString(AppConstant.ROOMIP, getIntent().getStringExtra(AppConstant.ROOMIP));
        bundle.putString(AppConstant.ROOMID, getIntent().getStringExtra(AppConstant.ROOMID));
        RoomNoContentFragment roomNoContentFragment = new RoomNoContentFragment();
        RoomContentFragment roomContentFragment = new RoomContentFragment();
        RoomNoContentFragment roomNoContentFragment2 = new RoomNoContentFragment();
        roomContentFragment.setArguments(bundle);
        roomNoContentFragment.setArguments(bundle);
        roomNoContentFragment2.setArguments(bundle);
        list_fragment.add(roomNoContentFragment);
        list_fragment.add(roomContentFragment);
        list_fragment.add(roomNoContentFragment2);
        initdate();
    }

    private void initdate() {
        presenter = new RtmpUrlPresenterImpl(RoomActivity.this, roomId, roomId);
        presenter.getRtmpUrl();
    }

    private void initview() {
        dvpRoom.setAdapter(new MyRoomFragmentVerticalPagerAdapter(getSupportFragmentManager(), list_fragment, context));
        dvpRoom.setPageTransformer(false, new DefaultTransformer());
        dvpRoom.setOffscreenPageLimit(3);
        dvpRoom.setCurrentItem(1);
        dvpRoom.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                //这里要做前后房间切换的处理，现在没有做
                KLog.e("onPageSelected:" + position);
                if (position != 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!StringUtil.isEmptyandnull(rtmp_url)) {
                                EventBus.getDefault().post(rtmp_url, "room_url");
                                dvpRoom.setCurrentItem(1, true);
                            }
                        }
                    }, 400);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void success(RtmpUrlEntity entity) {
        if (entity != null) {
            rtmp_url = entity.getRTMPPlayURL();
            KLog.e(rtmp_url);
        }
        initview();
    }

    @Override
    public void faided() {
        ToastUtil.show(context, R.string.net_error);
    }


}
