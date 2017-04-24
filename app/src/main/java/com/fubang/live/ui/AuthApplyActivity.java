package com.fubang.live.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.api.ApiService;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.callback.StringDialogCallback;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.util.DialogFactory;
import com.fubang.live.util.IdcardUtils;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.socks.library.KLog;


import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by jacky on 17/4/17.
 */
public class AuthApplyActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_auth_apply_name)
    EditText etAuthApplyName;
    @BindView(R.id.et_auth_apply_id)
    EditText etAuthApplyId;
    @BindView(R.id.tv_auth_apply_submit)
    TextView tvAuthApplySubmit;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_auth_apply);
        ButterKnife.bind(this);
        context = this;
    }

    @OnClick({R.id.iv_back, R.id.tv_auth_apply_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_auth_apply_submit://支付宝验证身份
                String user_name = etAuthApplyName.getText().toString().trim();
                String user_id = etAuthApplyId.getText().toString().trim();
                if (user_name.length() < 2) {
                    ToastUtil.show(context, R.string.name_incorrect);
                    return;
                }
                if (user_id.length() != 18) {
                    ToastUtil.show(context, R.string.id_incorrect);
                    return;
                }
                String result = IdcardUtils.toNewIdCard(user_id);
                if (!IdcardUtils.isRealIDCard(user_id)) {
                    ToastUtil.show(context, R.string.id_incorrect);
                    return;
                }

                String url = AppConstant.BASE_URL + AppConstant.MSG_UP_AUTH_INFO;
                OkGo.post(url)//
                        .tag(this)//
                        .params("nuserid", StartUtil.getUserId(context))
                        .params("real_name", user_name)
                        .params("i_d", user_id)
                        .execute(new StringDialogCallback(AuthApplyActivity.this) {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                ToastUtil.show(context, R.string.up_auth_info_success);
                                finish();
                            }
                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                e.printStackTrace();
                                ToastUtil.show(context, R.string.up_auth_info_failure);
                            }
                        });
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
