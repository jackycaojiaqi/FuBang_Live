package com.fubang.live.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.et_auth_apply_phone)
    EditText etAuthApplyPhone;
    @BindView(R.id.et_auth_apply_id)
    EditText etAuthApplyId;
    @BindView(R.id.tv_auth_apply_submit)
    TextView tvAuthApplySubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_auth_apply);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_auth_apply_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_auth_apply_submit://支付宝验证身份
                break;
        }
    }
}
