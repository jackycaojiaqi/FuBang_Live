package com.fubang.live.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.fubang.live.R;
import com.fubang.live.adapter.MyRoomFragmentVerticalPagerAdapter;
import com.fubang.live.base.BaseActivity;
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
public class RoomActivity extends BaseActivity {
    @BindView(R.id.vp_room)
    VerticalViewPager dvpRoom;
    private Context context;
    public static boolean is_emoticon_show = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);
        context = this;
        initview();

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
                            EventBus.getDefault().post("rtmp://pili-live-rtmp.fbyxsp.com/wanghong/wh_10088_58888", "room_url");
                            dvpRoom.setCurrentItem(1, true);
                        }
                    }, 400);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
