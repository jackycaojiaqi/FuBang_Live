package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;
import com.socks.library.KLog;

import org.simple.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by jacky on 17/3/24.
 */
public class LoginActivity extends BaseActivity implements PlatformActionListener {
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
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                wechat.setPlatformActionListener(this);
                wechat.showUser(null);//授权并获取用户信息
                //移除授权
                wechat.removeAccount(true);
                break;
            case R.id.rl_login_qq:
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.setPlatformActionListener(this);
                qq.showUser(null);//授权并获取用户信息
                //移除授权
                qq.removeAccount(true);
                break;
            case R.id.rl_login_sina:
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                sina.setPlatformActionListener(this);
                sina.showUser(null);//授权并获取用户信息
                //移除授权
                sina.removeAccount(true);
                break;
            case R.id.rl_login_phone:
                intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        KLog.e("arg1:" + i);
        if (i == Platform.ACTION_USER_INFOR) {
            KLog.e("arg1:" + i);
            PlatformDb platDB = platform.getDb();//获取数平台数据DB
            //通过DB获取各种数据
            String token = platDB.getToken();
            KLog.e("token:" + token);

            String userGender = platDB.getUserGender();
            KLog.e("userGender:" + userGender);
            String userIcon = platDB.getUserIcon();
            KLog.e("userIcon:" + userIcon);
            String userId = platDB.getUserId();
            String userName = platDB.getUserName();
            KLog.e("userName:" + userName);
//                            EventBus.getDefault().post(new UserEntity(userIcon, userId, userName), "UserInfo");
//                            StartUtil.putQQid(this, userId);
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    new LoginMain(0, "", userId, flag, flag, LoginActivity.this).start(0, "", flag, userId, flag, LoginActivity.this);
//                                }
//                            }).start();
//                    startActivity(MainActivity_.intent(LoginActivity.this).extra("flag",flag).get());
        }


    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

}

