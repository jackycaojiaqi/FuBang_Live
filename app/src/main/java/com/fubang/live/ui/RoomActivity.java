package com.fubang.live.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.fubang.live.R;
import com.fubang.live.adapter.MyRoomFragmentVerticalPagerAdapter;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.RtmpUrlEntity;
import com.fubang.live.presenter.impl.RtmpUrlPresenterImpl;
import com.fubang.live.util.StringUtil;
import com.fubang.live.util.ToastUtil;
import com.fubang.live.view.RtmpUrlView;
import com.socks.library.KLog;

import org.simple.eventbus.EventBus;

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
    @BindView(R.id.vp_room)
    VerticalViewPager dvpRoom;
    private Context context;
    public static boolean is_emoticon_show = false;
    private RtmpUrlPresenterImpl presenter;
    private String rtmp_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);
        context = this;
        initdate();
    }

    private void initdate() {
        presenter = new RtmpUrlPresenterImpl(RoomActivity.this, "10088", "88888");
        presenter.getRtmpUrl();
    }

    private void initview() {
        dvpRoom.setAdapter(new MyRoomFragmentVerticalPagerAdapter(getSupportFragmentManager(), context));
        dvpRoom.setPageTransformer(false, new DefaultTransformer());
        dvpRoom.setOffscreenPageLimit(3);
        dvpRoom.setCurrentItem(1);
        dvpRoom.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
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
