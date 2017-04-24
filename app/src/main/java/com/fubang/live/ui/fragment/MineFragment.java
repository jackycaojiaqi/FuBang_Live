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

import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.base.BaseFragment;
import com.fubang.live.callback.StringDialogCallback;
import com.fubang.live.ui.AuthActivity;
import com.fubang.live.ui.AuthApplyActivity;
import com.fubang.live.ui.LoginActivity;
import com.fubang.live.ui.UserInfoActivity;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.socks.library.KLog;

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
    Unbinder unbinder;
    @BindView(R.id.iv_mine_bg)
    ImageView ivMineBg;
    @BindView(R.id.iv_mine_message)
    ImageView ivMineMessage;
    @BindView(R.id.iv_mine_edit)
    ImageView ivMineEdit;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.iv_mine_city_and_id)
    TextView ivMineCityAndId;
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
        initdate();
    }

    private void initdate() {
        String url = AppConstant.BASE_URL + AppConstant.MSG_GET_USER_INFO;
        OkGo.get(url)//
                .tag(this)//
                .params("nuserid", StartUtil.getUserId(context))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        KLog.json(s);
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
        unbinder.unbind();
    }

    @OnClick({R.id.rl_setting, R.id.iv_mine_bg, R.id.rll_mian_auth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_setting:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.iv_mine_bg:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.rll_mian_auth:
                startActivity(new Intent(context, AuthApplyActivity.class));
                break;
        }

    }

}
