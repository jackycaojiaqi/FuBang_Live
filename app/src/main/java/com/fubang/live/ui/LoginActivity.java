package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 17/3/24.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.rl_login_wechat)
    RelativeLayout rlLoginWechat;
    @BindView(R.id.rl_login_qq)
    RelativeLayout rlLoginQq;
    @BindView(R.id.rl_login_sina)
    RelativeLayout rlLoginSina;
    @BindView(R.id.rl_login_phone)
    RelativeLayout rlLoginPhone;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context = this;
    }

    @OnClick({R.id.rl_login_wechat, R.id.rl_login_qq, R.id.rl_login_sina, R.id.rl_login_phone})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_login_wechat:
                intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_login_qq:
                intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_login_sina:
                intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_login_phone:
                intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
