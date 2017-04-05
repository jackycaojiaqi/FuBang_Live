package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.StringUtil;

/**
 * Created by jacky on 17/3/28.
 */
public class WelcomeActivity extends BaseActivity {
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_welcome);
        context = this;
        String userid = StartUtil.getUserId(context);
        if (!StringUtil.isEmptyandnull(userid)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                }
            }, 2000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(context, LoginActivity.class));
                    finish();
                }
            }, 2000);
        }

    }
}
