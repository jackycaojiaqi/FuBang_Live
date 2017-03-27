package com.fubang.live.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fubang.live.R;
import com.fubang.live.adapter.MyRoomFragmentVerticalPagerAdapter;
import com.fubang.live.base.BaseActivity;

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
    }
}
