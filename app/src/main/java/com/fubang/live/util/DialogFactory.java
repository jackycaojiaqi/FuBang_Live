package com.fubang.live.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fubang.live.R;


public class DialogFactory {
    private static Dialog mDialog = null;
    private static Dialog mPhoneDialog = null;
    private static String select_phone = "";
    private static Dialog mDialog_favorite = null;

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

    public static void showRequestDialog(final Context context, String tip) {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = DialogFactory.creatRequestDialog(context, tip, false);
        mDialog.show();
    }

    public static void showRequestDialog(final Context context, String tip, boolean isCancel) {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = DialogFactory.creatRequestDialog(context, tip, isCancel);
        mDialog.show();
    }

    public static void showRequestDialog(final Context context, boolean isCancel) {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = DialogFactory.creatRequestDialog(context, "加载中...", isCancel);
        mDialog.show();
    }

    public static void ToastDialog(Context context, String title, String msg) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(msg)
                .setPositiveButton("确定", null).create().show();
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }



    public static void hideRequestDialog() {
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    public static void hideFavoriteDialog() {
        if (mDialog_favorite != null) {
            if (mDialog_favorite.isShowing()) {
                mDialog_favorite.dismiss();
            }
        }
    }

//    public static void showPhoneDialog(final Context context,
//                                       String[] phone_number) {
//        if (mPhoneDialog != null) {
//            mPhoneDialog.dismiss();
//            mPhoneDialog = null;
//        }
//        mPhoneDialog = DialogFactory.creatPhoneDialog(context, phone_number);
//        mPhoneDialog.show();
//    }
//
//    public static Dialog creatPhoneDialog(final Context context,
//                                          final String[] phone_number) {
//        select_phone = phone_number[0];
//        Dialog dialog = new AlertDialog.Builder(context)
//                .setTitle("求助")
//                // .setIcon(R.drawable.ic_launcher)
//                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                })
//                .setNegativeButton("拨打", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(Intent.ACTION_CALL, Uri
//                                .parse("tel:" + select_phone));
//                        context.startActivity(intent);
//
//                    }
//                })
//                .setSingleChoiceItems(phone_number, 0,
//                        new DialogInterface.OnClickListener() {
//                            // 上边第一个表示的是公司数组列表，第二个参数表示默认选择的，第三个监听器
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                                // chNum=which;
//                                select_phone = phone_number[which];
//                                // HomeActivity.this.company.setText("公司:"+HomeActivity.this.getResources().getStringArray(R.array.company)[which]);
//                                // HomeActivity.this.ceo.setText("ceo:"+HomeActivity.this.getResources().getStringArray(R.array.ceo)[which]);
//                                // HomeActivity.this.file.setText("行业:"+HomeActivity.this.getResources().getStringArray(R.array.file)[which]);
//                            }
//                        }).create();
//        dialog.show();
//
//        return dialog;
//    }

    /**
     * 创建普通对话框
     *
     * @param ctx      上下文 必填
     * @param iconId   图标，如：R.drawable.icon 必填
     * @param title    标题 必填
     * @param message  显示内容 必填
     * @param btnName  按钮名称 必填
     * @param listener 监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
    public static Dialog createNormalDialog(Context ctx,
                                            int iconId,
                                            String title,
                                            String message,
                                            String btnName,
                                            DialogInterface.OnClickListener listener) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        // 设置对话框的图标
        builder.setIcon(iconId);
        // 设置对话框的标题
        builder.setTitle(title);
        // 设置对话框的显示内容
        builder.setMessage(message);
        // 添加按钮，android.content.DialogInterface.OnClickListener.OnClickListener
        builder.setPositiveButton(btnName, listener);
        // 创建一个普通对话框
        dialog = builder.create();
        return dialog;
    }


    /**
     * 创建列表对话框
     *
     * @param ctx      上下文 必填
     * @param iconId   图标，如：R.drawable.icon 必填
     * @param title    标题 必填
     * @param itemsId  字符串数组资源id 必填
     * @param listener 监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
    public static Dialog createListDialog(Context ctx,
                                          int iconId,
                                          String title,
                                          int itemsId,
                                          DialogInterface.OnClickListener listener) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        // 设置对话框的图标
        builder.setIcon(iconId);
        // 设置对话框的标题
        builder.setTitle(title);
        // 添加按钮，android.content.DialogInterface.OnClickListener.OnClickListener
        builder.setItems(itemsId, listener);
        // 创建一个列表对话框
        dialog = builder.create();
        return dialog;
    }

    /**
     * 创建单选按钮对话框
     *
     * @param ctx       上下文 必填
     * @param iconId    图标，如：R.drawable.icon 必填
     * @param title     标题 必填
     * @param itemsId   字符串数组资源id 必填
     * @param listener  单选按钮项监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @param btnName   按钮名称 必填
     * @param listener2 按钮监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
    public static Dialog createRadioDialog(Context ctx,
                                           int iconId,
                                           String title,
                                           int itemsId,
                                           DialogInterface.OnClickListener listener,
                                           String btnName,
                                           DialogInterface.OnClickListener listener2) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        // 设置对话框的图标
        builder.setIcon(iconId);
        // 设置对话框的标题
        builder.setTitle(title);
        // 0: 默认第一个单选按钮被选中
        builder.setSingleChoiceItems(itemsId, 0, listener);
        // 添加一个按钮
        builder.setPositiveButton(btnName, listener2);
        // 创建一个单选按钮对话框
        dialog = builder.create();
        return dialog;
    }


    /**
     * 创建复选对话框
     *
     * @param ctx       上下文 必填
     * @param iconId    图标，如：R.drawable.icon 必填
     * @param title     标题 必填
     * @param itemsId   字符串数组资源id 必填
     * @param flags     初始复选情况 必填
     * @param listener  单选按钮项监听器，需实现android.content.DialogInterface.OnMultiChoiceClickListener接口 必填
     * @param btnName   按钮名称 必填
     * @param listener2 按钮监听器，需实现android.content.DialogInterface.OnClickListener接口 必填
     * @return
     */
    public static Dialog createCheckBoxDialog(Context ctx,
                                              int iconId,
                                              String title,
                                              int itemsId,
                                              boolean[] flags,
                                              DialogInterface.OnMultiChoiceClickListener listener,
                                              String btnName,
                                              DialogInterface.OnClickListener listener2) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        // 设置对话框的图标
        builder.setIcon(iconId);
        // 设置对话框的标题
        builder.setTitle(title);
        builder.setMultiChoiceItems(itemsId, flags, listener);
        // 添加一个按钮
        builder.setPositiveButton(btnName, listener2);
        // 创建一个复选对话框
        dialog = builder.create();
        return dialog;
    }

    protected void dialog(final Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("确认退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((Activity) ctx).finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
