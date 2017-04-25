package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.callback.StringDialogCallback;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.StringUtil;
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
 * Created by jacky on 2017/4/24.
 */
public class EditInfoctivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.et_edit_content)
    EditText etEditContent;
    @BindView(R.id.tv_edit_content_length)
    TextView tvEditContentLength;
    @BindView(R.id.rll_input_content)
    RelativeLayout rllInputContent;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.ll_select_content)
    LinearLayout llSelectContent;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    private Context context;
    private boolean is_male = true;
    private int intent_type = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_editinfo);
        ButterKnife.bind(this);
        context = this;
        initview();
    }

    private void initview() {
        tvTitle.setText("修改信息");
        tvSubmit.setVisibility(View.VISIBLE);
        String type = getIntent().getStringExtra("type");
        String content = getIntent().getStringExtra("content");
        KLog.e(type + " " + content);
        if (type.equals("name")) {
            rllInputContent.setVisibility(View.VISIBLE);
            llSelectContent.setVisibility(View.GONE);
            setEditTextMaxLength(8);
            tvEditContentLength.setText("0/8");
            if (!StringUtil.isEmptyandnull(content))
                etEditContent.setText(content + " ");
            intent_type = 1;
        } else if (type.equals("sign")) {
            rllInputContent.setVisibility(View.VISIBLE);
            llSelectContent.setVisibility(View.GONE);
            setEditTextMaxLength(30);
            tvEditContentLength.setText("0/30");
            if (!StringUtil.isEmptyandnull(content))
                etEditContent.setText(content + " ");
            intent_type = 2;
        } else if (type.equals("gender")) {
            rllInputContent.setVisibility(View.GONE);
            llSelectContent.setVisibility(View.VISIBLE);
            intent_type = 3;
            if (!StringUtil.isEmptyandnull(content)) {
                KLog.e(content);
                if (content.equals("0")) {
                    rbFemale.setChecked(true);
                    is_male = false;
                } else if (content.equals("1")) {
                    rbMale.setChecked(true);
                    is_male = true;
                }
            }

            rbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    is_male = isChecked;
                }
            });
            rbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    is_male = !isChecked;
                }
            });
        }

        etEditContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (intent_type == 1) {
                    tvEditContentLength.setText(s.length() + "/8");
                } else if (intent_type == 2) {
                    tvEditContentLength.setText(s.length() + "/30");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                doSubmit(intent_type);
                break;
        }
    }

    private void doSubmit(final int intent_type) {
        final String content = etEditContent.getText().toString().toString();

        if (content.length() == 0 && intent_type != 3) {
            ToastUtil.show(context, R.string.content_not_null);
            return;
        }
        String url = AppConstant.BASE_URL + AppConstant.MSG_MODIFY_USER_INFO;
        Map<String, String> params = new HashMap<>();
        params.put("nuserid", StartUtil.getUserId(context));
        if (intent_type == 1) {
            KLog.e(content);
            params.put("calias", content);
        } else if (intent_type == 2) {
            params.put("cidiograph", content);
        } else if (intent_type == 3) {
            if (is_male) {
                KLog.e(is_male);
                params.put("ngender", "1");//		性别（0－女，1－男，2－未知）
            } else {
                KLog.e(is_male);
                params.put("ngender", "0");//
            }

        }
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
                                if (intent_type == 1) {
                                    Intent intent = new Intent();
                                    intent.putExtra("name", content);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else if (intent_type == 3) {
                                    Intent intent = new Intent();
                                    intent.putExtra("gender", is_male ? "1" : "0");
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else if (intent_type == 2) {
                                    Intent intent = new Intent();
                                    intent.putExtra("sign", content);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
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
    }

    public void setEditTextMaxLength(int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        etEditContent.setFilters(FilterArray);
    }
}
