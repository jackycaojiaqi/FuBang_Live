package com.fubang.live.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;

/**
 * Created by jacky on 17/3/28.
 */
public class SearchAtivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_search);
    }
}
