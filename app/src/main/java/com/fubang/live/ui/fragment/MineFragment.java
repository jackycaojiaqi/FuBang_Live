package com.fubang.live.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.base.BaseFragment;
import com.fubang.live.entities.UserInfoEntity;
import com.fubang.live.ui.AuthApplyActivity;
import com.fubang.live.ui.HistoryActivity;
import com.fubang.live.ui.LoginActivity;
import com.fubang.live.ui.UserInfoActivity;
import com.fubang.live.util.FBImage;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.StringUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.rll_mian_auth)
    RelativeLayout rllMianAuth;
    @BindView(R.id.rll_mine_history)
    RelativeLayout rllMineHistory;
    Unbinder unbinder;
    @BindView(R.id.iv_mine_bg)
    ImageView ivMineBg;
    @BindView(R.id.iv_mine_message)
    ImageView ivMineMessage;
    @BindView(R.id.iv_mine_edit)
    ImageView ivMineEdit;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.tv_mine_city_and_id)
    TextView tvMineCityAndId;
    @BindView(R.id.tv_mine_fav)
    TextView tvMineFav;
    @BindView(R.id.tv_mine_fans)
    TextView tvMineFans;
    @BindView(R.id.tv_mine_money)
    TextView tvMineMoney;
    @BindView(R.id.tv_mine_auth)
    TextView tvMineAuth;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();

    }

    @Override
    public void onResume() {
        super.onResume();
        initdate();
    }

    UserInfoEntity userEntity;

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
                            //名字
                            tvMineName.setText(userEntity.getInfo().getCalias() + " ");
                            //用户id
                            tvMineCityAndId.setText("常住地 台州" + "   " + "用户id " + StartUtil.getUserId(context));
                            //是否实名认证
                            if (userEntity.getInfo().getState().equals("0")) {
                                tvMineAuth.setText("未实名认证");
                            } else if (userEntity.getInfo().getState().equals("1")) {
                                tvMineAuth.setText("审核中");
                                rllMianAuth.setClickable(false);//通过认证后不用点击
                            } else if (userEntity.getInfo().getState().equals("2")) {
                                tvMineAuth.setText("通过实名认证");
                                rllMianAuth.setClickable(false);//通过认证后不用点击
                            }
                            //粉丝数
                            tvMineFans.setText("粉丝 " + userEntity.getInfo().getGuanzhunum());
                            //关注数
                            tvMineFav.setText("关注 " + userEntity.getInfo().getGuanzhunum());
                            //金币数
                            tvMineMoney.setText(userEntity.getInfo().getNk() + " K币");
                            //直播背景图片
                            if (!StringUtil.isEmptyandnull(userEntity.getInfo().getBphoto())) {
                                KLog.e(AppConstant.BASE_IMG_URL + userEntity.getInfo().getBphoto());
                                StartUtil.putUserPic(context, AppConstant.BASE_IMG_URL + userEntity.getInfo().getBphoto());
                                FBImage.Create(context, AppConstant.BASE_IMG_URL + userEntity.getInfo().getBphoto()).into(ivMineBg);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkGo.getInstance().cancelTag(this);
        unbinder.unbind();
    }

    @OnClick({R.id.rl_setting, R.id.iv_mine_bg, R.id.rll_mian_auth, R.id.rll_mine_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_setting:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.iv_mine_bg:
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                if (userEntity != null) {
                    intent.putExtra(AppConstant.CONTENT, userEntity);
                    startActivity(intent);
                }

                break;
            case R.id.rll_mian_auth:
                startActivity(new Intent(context, AuthApplyActivity.class));
                break;
            case R.id.rll_mine_history:
                startActivity(new Intent(context,HistoryActivity.class));
                break;
        }

    }

}
