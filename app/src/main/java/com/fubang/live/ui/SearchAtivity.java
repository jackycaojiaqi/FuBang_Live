package com.fubang.live.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 17/3/28.
 */
public class SearchAtivity extends BaseActivity {
    @BindView(R.id.et_search_content)
    EditText etSearchContent;
    @BindView(R.id.tv_search_cancle)
    TextView tvSearchCancle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initview();
    }

    private void initview() {

    }

    @OnClick(R.id.tv_search_cancle)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_search_cancle:
                finish();
                break;
        }
    }
}
