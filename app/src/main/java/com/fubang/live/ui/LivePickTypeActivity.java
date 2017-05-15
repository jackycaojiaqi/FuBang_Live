package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.callback.StringDialogCallback;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.ToastUtil;
import com.lzy.okgo.OkGo;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jacky on 2017/5/15.
 */
public class LivePickTypeActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rb_type_talent)
    RadioButton rbTypeTalent;
    @BindView(R.id.rb_type_male)
    RadioButton rbTypeMale;
    @BindView(R.id.rb_type_singer)
    RadioButton rbTypeSinger;
    @BindView(R.id.rb_type_female)
    RadioButton rbTypeFemale;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private String type = "才艺";
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_live_pick_type);
        ButterKnife.bind(this);
        context = this;
        initview();
    }

    private void initview() {
        back(ivBack);
        setTitle("选择直播类型");
        tvSubmit.setVisibility(View.VISIBLE);
        rbTypeTalent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "才艺";
                rbTypeTalent.setChecked(true);
                rbTypeMale.setChecked(false);
                rbTypeSinger.setChecked(false);
                rbTypeFemale.setChecked(false);
            }
        });
        rbTypeMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "帅哥";
                rbTypeTalent.setChecked(false);
                rbTypeMale.setChecked(true);
                rbTypeSinger.setChecked(false);
                rbTypeFemale.setChecked(false);
            }
        });
        rbTypeSinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "好声音";
                rbTypeTalent.setChecked(false);
                rbTypeMale.setChecked(false);
                rbTypeSinger.setChecked(true);
                rbTypeFemale.setChecked(false);
            }
        });
        rbTypeFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "美女";
                rbTypeTalent.setChecked(false);
                rbTypeMale.setChecked(false);
                rbTypeSinger.setChecked(false);
                rbTypeFemale.setChecked(true);
            }
        });
    }

    @OnClick(R.id.tv_submit)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                String url = AppConstant.BASE_URL + AppConstant.MSG_MODIFY_USER_INFO;
                Map<String, String> params = new HashMap<>();
                params.put("nuserid", StartUtil.getUserId(context));
                params.put("type", type);
                OkGo.post(url)
                        .tag(this)
                        .params(params)
                        .execute(new StringDialogCallback(this) {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject object = new JSONObject(s);
                                    String states = object.getString("status");
                                    if (states.equals("success")) {
                                        ToastUtil.show(context, R.string.modify_success);
                                        Intent intent = new Intent();
                                        intent.putExtra("type", type);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    } else {
                                        ToastUtil.show(context, R.string.modify_failed);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                e.printStackTrace();
                            }
                        });
                break;
        }
    }
}
