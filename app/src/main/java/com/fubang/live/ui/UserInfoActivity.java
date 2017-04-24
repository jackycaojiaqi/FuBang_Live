package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.callback.StringDialogCallback;
import com.fubang.live.util.FileUtils;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.SystemStatusManager;
import com.fubang.live.util.ToastUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jacky on 17/3/30.
 */
public class UserInfoActivity extends TakePhotoActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_user_info_pic)
    ImageView ivUserInfoPic;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_user_info_pic)
    RelativeLayout rlUserInfoPic;
    @BindView(R.id.rl_user_info_name)
    RelativeLayout rlUserInfoName;
    @BindView(R.id.rl_user_info_gender)
    RelativeLayout rlUserInfoGender;
    @BindView(R.id.rl_user_info_sign)
    RelativeLayout rlUserInfoSign;
    private Context context;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    Uri imageUri;
    private String name;
    private String sign;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        context = this;
        initview();
        initdate();
    }



    private void initview() {
        tvTitle.setText("编辑资料");
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

    @OnClick({R.id.rl_user_info_pic, R.id.iv_back,R.id.rl_user_info_name,
            R.id.rl_user_info_gender, R.id.rl_user_info_sign})
    public void onViewClicked(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_user_info_pic:
                ShowPopAction();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_user_info_name:
                intent = new Intent(context,EditInfoctivity.class);
                intent.putExtra("type","name");
                intent.putExtra("content",name);
                startActivity(intent);
                break;
            case R.id.rl_user_info_gender:
                intent = new Intent(context,EditInfoctivity.class);
                intent.putExtra("type","gender");
                startActivity(intent);
                break;
            case R.id.rl_user_info_sign:
                intent = new Intent(context,EditInfoctivity.class);
                intent.putExtra("type","sign");
                intent.putExtra("content",sign);
                startActivity(intent);
                break;
        }
    }

    private PopupWindow pop_pic;

    /**
     * 处理拍照弹窗
     */
    private void ShowPopAction() {
        final View popupView = getLayoutInflater().inflate(R.layout.pop_user_pic, null);
        pop_pic = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop_pic.showAtLocation(tvTitle, Gravity.CENTER_HORIZONTAL, 0, 0);
        pop_pic.setAnimationStyle(R.style.take_photo_anim);
        pop_pic.setOutsideTouchable(false);
        ImageView iv_cancle = (ImageView) popupView.findViewById(R.id.tv_user_info_pic_cancle);
        TextView tv_album = (TextView) popupView.findViewById(R.id.tv_user_info_pic_form_album);
        TextView tv_camera = (TextView) popupView.findViewById(R.id.tv_user_info_pic_form_camera);
        iv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_pic.dismiss();
            }
        });
        tv_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(FileUtils.getTempFiles() + System.currentTimeMillis() + "tempalbum.jpg");
                if (file.exists()) {
                    file.delete();
                }
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                imageUri = Uri.fromFile(file);
                getTakePhoto().onPickFromGalleryWithCrop(imageUri, getCropOptions());
                pop_pic.dismiss();
            }
        });
        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(FileUtils.getTempFiles() + System.currentTimeMillis() + "tempcamera.jpg");
                if (file.exists()) {
                    file.delete();
                }
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                imageUri = Uri.fromFile(file);
                getTakePhoto().onPickFromCaptureWithCrop(imageUri, getCropOptions());
                pop_pic.dismiss();
            }
        });
    }

    @Override
    public void takeCancel() {
        KLog.e("takeCancel");
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        KLog.e("takeFail");
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        KLog.e("takeSuccess");
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        KLog.e(images.size());
        if (images.size() > 0) {
            KLog.e(images.get(0).getOriginalPath());
            Picasso.with(context).load(new File(images.get(0).getOriginalPath())).fit().into(ivUserInfoPic);
            //上传图片
            String url = AppConstant.BASE_URL + AppConstant.MSG_MODIFY_USER_PIC;
            OkGo.post(url)//上传头像
                    .tag(this)//
                    .params("nuserid", StartUtil.getUserId(context))
                    .params("lanwei", "cphoto")
                    .params("file", new File(images.get(0).getOriginalPath()))
                    .execute(new StringDialogCallback(this) {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            ToastUtil.show(context, R.string.up_auth_info_success);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            e.printStackTrace();
                            ToastUtil.show(context, R.string.up_auth_info_failure);
                        }
                    });
            OkGo.post(url)//上传直播背景
                    .tag(this)
                    .params("nuserid", StartUtil.getUserId(context))
                    .params("lanwei", "bphoto")
                    .params("file", new File(images.get(0).getOriginalPath()))
                    .execute(new StringDialogCallback(this) {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            ToastUtil.show(context, R.string.up_auth_info_success);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            e.printStackTrace();
                            ToastUtil.show(context, R.string.up_auth_info_failure);
                        }
                    });
        }
    }

    private CropOptions getCropOptions() {
        int height = Integer.parseInt("1000");
        int width = Integer.parseInt("1000");
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);
        builder.setOutputX(width).setOutputY(height);
        return builder.create();
    }

    /**
     * 设置状态栏背景状态
     */
    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemStatusManager tintManager = new SystemStatusManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(0);//状态栏无背景

    }

}
