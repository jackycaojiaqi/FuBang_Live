package com.fubang.live.callback;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.fubang.live.R;
import com.fubang.live.util.DialogFactory;
import com.fubang.live.util.ScreenUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）
 * 版    本：1.0
 * 创建日期：2016/4/8
 * 描    述：我的Github地址  https://github.com/jeasonlzy
 * 修订历史：
 * ================================================
 */
public abstract class StringDialogCallback extends StringCallback {

    private static Dialog mDialog = null;
    private Context context;

    public StringDialogCallback(Activity activity) {
        creatRequestDialog(activity, "请求中...", false);
        context = activity;
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        //网络请求前显示对话框
        showRequestDialog(context);
    }

    @Override
    public void onAfter(@Nullable String s, @Nullable Exception e) {
        super.onAfter(s, e);
        //网络请求结束后关闭对话框
        hideRequestDialog();
    }

    public static Dialog creatRequestDialog(final Context context, String tip, boolean isCancel) {

        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_layout);
        Window window = dialog.getWindow();
        dialog.setCancelable(isCancel);
        WindowManager.LayoutParams lp = window.getAttributes();
        int width = ScreenUtils.getScreenWidth(context);
        lp.width = (int) (0.6 * width);
        TextView titleTxtv = (TextView) dialog.findViewById(R.id.tvLoad);
        if (tip == null || tip.length() == 0) {
            titleTxtv.setText(R.string.sending_request);
        } else {
            titleTxtv.setText(tip);
        }

        return dialog;
    }


    public static void showRequestDialog(final Context context) {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = DialogFactory.creatRequestDialog(context, "加载中...", false);
        mDialog.show();
    }

    public static void hideRequestDialog() {
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }
}
