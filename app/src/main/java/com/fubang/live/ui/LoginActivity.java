package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.UserEntity;
import com.fubang.live.util.StartUtil;
import com.sample.login.LoginMain;
import com.socks.library.KLog;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.utils.Tools;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

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
    private int flag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        context = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.rl_login_wechat, R.id.rl_login_qq, R.id.rl_login_sina, R.id.rl_login_phone})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rl_login_wechat:
                flag = 3;
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                wechat.setPlatformActionListener(this);
                wechat.showUser(null);//授权并获取用户信息
                //移除授权
                wechat.removeAccount(true);
                break;
            case R.id.rl_login_qq:
                flag = 2;
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
//                StartUtil.editInfo(this, userName, userId + "", userIcon, "123");
                intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }

    private String userIcon;
    private String userId = "18888777";
    private String userName = "小新";

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        KLog.e("arg1:" + i);
        if (i == Platform.ACTION_USER_INFOR) {
            KLog.e("arg1:" + i);
            PlatformDb platDB = platform.getDb();//获取数平台数据DB
            //通过DB获取各种数据
            String token = platDB.getToken();
            String userGender = platDB.getUserGender();
            userIcon = platDB.getUserIcon();
            userId = platDB.getUserId();
            userName = platDB.getUserName();
            EventBus.getDefault().post(new UserEntity(userIcon, userId, userName), "UserInfo");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new LoginMain(0, "", userId, flag, flag, LoginActivity.this).start(0, "", flag, userId, flag, LoginActivity.this);
                }
            }).start();
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    @Subscriber(tag = "login_success")
    public void loginSuccess(LogonResponse res) {
//        flag = 0;
        Tools.PrintObject(res);
        StartUtil.putCount(LoginActivity.this, flag);
//        Log.d("123",flag+"flag");
        if (res != null) {
            if (flag == 2 || flag == 3) {
//                Log.d("123",userName+userIcon);
                StartUtil.editInfo(this, userName, res.getUserid() + "", userIcon, res.getCuserpwd());
            } else {
//                Log.d("123",res.getHeadpic()+"lenth");
                if (res.getHeadpic() > 15) {
                    StartUtil.editInfo(this, res.getCalias(), res.getUserid() + "", res.getHeadpic() + "", res.getCuserpwd());
                } else {
//                    Log.d("123","不是吧这走?");
                    StartUtil.editInfo(this, res.getCalias(), res.getUserid() + "", "head" + res.getHeadpic(), res.getCuserpwd());
                }
            }
            StartUtil.putVersion(this, res.getNverison() + "");
            StartUtil.editUserInfo(this, res.getNlevel() + "", res.getNdeposit() + "", res.getNk() + "", res.getNb() + "", res.getCidiograph());
            startActivity(new Intent(context, MainActivity.class));
        }
    }
}

