package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.AdEntity;
import com.fubang.live.entities.MusicListEntity;
import com.fubang.live.entities.UserInfoEntity;
import com.fubang.live.util.FBImage;
import com.fubang.live.util.LiteOrmDBUtil;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.StringUtil;
import com.fubang.live.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
//import com.hyphenate.chat.EMClient;
//import com.hyphenate.util.EasyUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.socks.library.KLog;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jacky on 17/3/28.
 */
public class WelcomeActivity extends BaseActivity {
    private Context context;
    private AdEntity adEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            //结束你的activity
            finish();
            return;
        }
//        //获取回话信息
//        EMClient.getInstance().chatManager().loadAllConversations();
//        EMClient.getInstance().groupManager().loadAllGroups();

        setTranslucentStatus();
        setContentView(R.layout.activity_welcome);
        context = this;
        final String userid = StartUtil.getUserId(context);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String url = AppConstant.BASE_URL + AppConstant.MSG_GET_AD_INFO;
                OkGo.get(url)//
                        .tag(this)//
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    adEntity = new Gson().fromJson(s, AdEntity.class);
                                    if (adEntity != null) {
                                        if (adEntity.getPic_list() != null) {

                                            LiteOrmDBUtil.deleteAll(AdEntity.PicListBean.class);//删除旧的
                                            LiteOrmDBUtil.insertAll(adEntity.getPic_list());//插入新的广告实体
                                            List<AdEntity.PicListBean> listBeen = LiteOrmDBUtil.getQueryAll(AdEntity.PicListBean.class);
                                            if (!StringUtil.isEmptyandnull(userid)) {
                                                startActivity(new Intent(context, MainActivity.class));

                                                finish();
                                            } else {
                                                startActivity(new Intent(context, LoginActivity.class));
                                                finish();
                                            }
                                        }
                                    }

                                } catch (JsonSyntaxException e) {
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                e.printStackTrace();
                                ToastUtil.show(context, R.string.net_error);
                            }
                        });
            }
        }, 1000);
        if (!StringUtil.isEmptyandnull(StartUtil.getUserId(context))) {//获取直播类型
            if (StartUtil.getLiveType(context).equals("0")) {
                initdate();
            }
        }
    }

    private UserInfoEntity userEntity;

    private void initdate() {
        String url = AppConstant.BASE_URL + AppConstant.MSG_GET_USER_INFO;
        OkGo.get(url)//
                .tag(this)//
                .params("nuserid", StartUtil.getUserId(context))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        userEntity = new Gson().fromJson(s, UserInfoEntity.class);
                        if (userEntity.getStatus().equals("success")) {
                            if (userEntity.getInfo().getType() == 0) {
                                StartUtil.putLiveType(context, "0");
                            } else if (userEntity.getInfo().getType() > 0) {
                                StartUtil.putLiveType(context, String.valueOf(userEntity.getInfo().getType()));
                            }
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();
                    }
                });
    }

}
