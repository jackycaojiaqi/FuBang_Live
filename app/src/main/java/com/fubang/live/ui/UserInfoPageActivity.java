package com.fubang.live.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;
import com.xlg.android.protocol.RoomUserInfo;

/**
 * Created by jacky on 2017/4/28.
 */
public class UserInfoPageActivity extends BaseActivity {
    private RoomUserInfo roomUserInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_user_info_page);
        initdate();
        initview();
    }

    private void initdate() {
        roomUserInfo = getIntent().getParcelableExtra(AppConstant.CONTENT);
    }

    private void initview() {
    }
}
