package com.fubang.live.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.AuthResult;
import com.alipay.PayDemoActivity;
import com.alipay.PayResult;
import com.alipay.sdk.app.PayTask;
import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;

import org.simple.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/5/17.
 */
public class PayActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_pay_nk_num)
    TextView tvPayNkNum;
    @BindView(R.id.tv_pay_1)
    TextView tvPay1;
    @BindView(R.id.tv_pay_6)
    TextView tvPay6;
    @BindView(R.id.tv_pay_30)
    TextView tvPay30;
    @BindView(R.id.tv_pay_98)
    TextView tvPay98;
    @BindView(R.id.tv_pay_298)
    TextView tvPay298;
    private static final int SDK_PAY_FLAG = 1;
    private long nk_num = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        nk_num = getIntent().getLongExtra("nk_num", 0);
        initview();
    }

    private void initview() {
        back(ivBack);
        setText(tvTitle, "金币充值");
        tvPayNkNum.setText(nk_num + " ");
    }

    @OnClick({R.id.tv_pay_1, R.id.tv_pay_6, R.id.tv_pay_30, R.id.tv_pay_98, R.id.tv_pay_298})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pay_1:
                doPayAction(1);
                break;
            case R.id.tv_pay_6:
                doPayAction(6);
                break;
            case R.id.tv_pay_30:
                doPayAction(30);
                break;
            case R.id.tv_pay_98:
                doPayAction(98);
                break;
            case R.id.tv_pay_298:
                doPayAction(298);
                break;
        }
    }

    private void doPayAction(int num) {
        //这里获取订单号


        //获取订单号之后支付
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(" orderinfo", true);//========获取到的订单信息
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        //通知php支付成功


                        //发送eventbus 消息通知房间更新金币数量
                        //nk_num+充值数量
                        EventBus.getDefault().post(nk_num, "onPaySuccess");

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }

        ;
    };
}
