package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.util.ShareUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jacky on 17/4/1.
 */
public class LiveDoneActivity extends BaseActivity {


    @BindView(R.id.iv_live_done_pic)
    CircleImageView ivLiveDonePic;
    @BindView(R.id.tv_live_done_name)
    TextView tvLiveDoneName;
    @BindView(R.id.tv_live_done_people_see_num)
    TextView tvLiveDonePeopleSeeNum;
    @BindView(R.id.tv_live_done_kbi_get)
    TextView tvLiveDoneKbiGet;
    @BindView(R.id.iv_live_done_share_sina)
    ImageView ivLiveDoneShareSina;
    @BindView(R.id.iv_live_done_share_qq)
    ImageView ivLiveDoneShareQq;
    @BindView(R.id.iv_live_done_share_wechat)
    ImageView ivLiveDoneShareWechat;
    @BindView(R.id.iv_live_done_share_wechat_circle)
    ImageView ivLiveDoneShareWechatCircle;
    @BindView(R.id.iv_live_done_back)
    ImageView ivLiveDoneBack;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_done);
        context = this;
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_live_done_share_sina, R.id.iv_live_done_share_qq, R.id.iv_live_done_share_wechat, R.id.iv_live_done_share_wechat_circle, R.id.iv_live_done_back})
    public void onViewClicked(View view) {
        Platform plat;
        switch (view.getId()) {
            case R.id.iv_live_done_share_sina:
                plat = ShareSDK.getPlatform(SinaWeibo.NAME);
                ShareUtil.getInstance().showShareNew(context, plat);
                break;
            case R.id.iv_live_done_share_qq:
                plat = ShareSDK.getPlatform(QQ.NAME);
                ShareUtil.getInstance().showShareNew(context, plat);
                break;
            case R.id.iv_live_done_share_wechat:
                plat = ShareSDK.getPlatform(Wechat.NAME);
                ShareUtil.getInstance().showShareNew(context, plat);
                break;
            case R.id.iv_live_done_share_wechat_circle:
                plat = ShareSDK.getPlatform(WechatMoments.NAME);
                ShareUtil.getInstance().showShareNew(context, plat);
                break;
            case R.id.iv_live_done_back:
                startActivity(new Intent(context, MainActivity.class));
                finish();
                break;
        }
    }
}
