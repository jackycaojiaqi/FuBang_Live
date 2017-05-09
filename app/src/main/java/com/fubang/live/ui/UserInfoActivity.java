package com.fubang.live.ui;

import android.content.ClipboardManager;
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
import com.fubang.live.entities.UserInfoEntity;
import com.fubang.live.util.FBImage;
import com.fubang.live.util.FileUtils;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.StringUtil;
import com.fubang.live.util.SystemStatusManager;
import com.fubang.live.util.ToastUtil;
import com.fubang.live.widget.addrpicker.AddressPickTask;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.lzy.okgo.OkGo;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
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
    @BindView(R.id.tv_user_info_name)
    TextView tvUserInfoName;
    @BindView(R.id.tv_user_info_gender)
    TextView tvUserInfoGender;
    @BindView(R.id.tv_user_info_id)
    TextView tvUserInfoId;
    @BindView(R.id.rl_user_info_id)
    RelativeLayout rlUserInfoId;
    @BindView(R.id.tv_user_info_addr)
    TextView tvUserInfoAddr;
    @BindView(R.id.rl_user_info_addr)
    RelativeLayout rlUserInfoAddr;
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
        tvTitle.setText("个人中心");
    }

    private UserInfoEntity userInfoEntity;

    private void initdate() {
        userInfoEntity = getIntent().getParcelableExtra(AppConstant.CONTENT);
        if (userInfoEntity.getStatus().equals("success")) {
            //头像
            if (!StringUtil.isEmptyandnull(userInfoEntity.getInfo().getCphoto())) {
                FBImage.Create(context, AppConstant.BASE_IMG_URL + userInfoEntity.getInfo().getCphoto()).into(ivUserInfoPic);
            }
            //名字
            tvUserInfoName.setText(userInfoEntity.getInfo().getCalias() + " ");
            //性别
            if (!StringUtil.isEmptyandnull(userInfoEntity.getInfo().getGender())) {
                if (userInfoEntity.getInfo().getGender().equals("1")) {
                    tvUserInfoGender.setText(R.string.male);
                } else {
                    tvUserInfoGender.setText(R.string.female);
                }

            } else {
                tvUserInfoGender.setText(R.string.unknow);
            }
            //富邦号
            tvUserInfoId.setText(StartUtil.getUserId(context) + " ");
            //地址
            if (StringUtil.isEmptyandnull(userInfoEntity.getInfo().getLocation())) {
                tvUserInfoAddr.setText(StartUtil.getCity(context) + " ");
            } else {
                tvUserInfoAddr.setText(userInfoEntity.getInfo().getLocation() + " ");
            }

        }
    }

    private final int MSG_MODIFY_INFO_NAME = 0X0011;
    private final int MSG_MODIFY_INFO_GENDER = 0X0012;
    private final int MSG_MODIFY_INFO_SIGN = 0X0013;

    @OnClick({R.id.rl_user_info_pic, R.id.iv_back, R.id.rl_user_info_name,
            R.id.rl_user_info_gender, R.id.rl_user_info_sign, R.id.rl_user_info_id
            , R.id.rl_user_info_addr})
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
                intent = new Intent(context, EditInfoctivity.class);
                intent.putExtra("type", "name");
                intent.putExtra("content", userInfoEntity.getInfo().getCalias());
                startActivityForResult(intent, MSG_MODIFY_INFO_NAME);
                break;
            case R.id.rl_user_info_gender:
                intent = new Intent(context, EditInfoctivity.class);
                intent.putExtra("type", "gender");
                intent.putExtra("content", userInfoEntity.getInfo().getGender());
                startActivityForResult(intent, MSG_MODIFY_INFO_GENDER);
                break;
            case R.id.rl_user_info_sign:
                intent = new Intent(context, EditInfoctivity.class);
                intent.putExtra("type", "sign");
                intent.putExtra("content", userInfoEntity.getInfo().getCidiograph());
                startActivityForResult(intent, MSG_MODIFY_INFO_SIGN);
                break;
            case R.id.rl_user_info_id:
                // 得到剪贴板管理器
                ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(StartUtil.getUserId(context));
                ToastUtil.show(context, R.string.copy_success);
                break;
            case R.id.rl_user_info_addr:
                //选择地址组件
                AddressPickTask task = new AddressPickTask(this);
                task.setHideCounty(true);
                task.setCallback(new AddressPickTask.Callback() {
                    @Override
                    public void onAddressInitFailed() {
                        ToastUtil.show(context, "数据初始化失败");
                    }

                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        tvUserInfoAddr.setText(city.getAreaName());
                        StartUtil.putCity(context, city.getAreaName());
                        doSubmit(city.getAreaName());
                    }
                });
                task.execute("浙江", "台州");
                break;
        }
    }

    private void doSubmit(final String city) {
        String url = AppConstant.BASE_URL + AppConstant.MSG_MODIFY_USER_INFO;
        Map<String, String> params = new HashMap<>();
        params.put("nuserid", StartUtil.getUserId(context));
        params.put("location", city);
        OkGo.post(url)
                .tag(this)
                .params(params)
                .execute(new StringDialogCallback(UserInfoActivity.this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        KLog.e("上传城市成功");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();
                    }
                });
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
                            ToastUtil.show(context, R.string.up_auth_pic_success);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            e.printStackTrace();
                            ToastUtil.show(context, R.string.up_auth_pic_failure);
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
        int height = Integer.parseInt("500");
        int width = Integer.parseInt("600");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == MSG_MODIFY_INFO_NAME) {
                String name = data.getStringExtra("name");
                if (!StringUtil.isEmptyandnull(name)) {
                    tvUserInfoName.setText(name);
                    userInfoEntity.getInfo().setCalias(name);
                }
                KLog.e(name);
            } else if (requestCode == MSG_MODIFY_INFO_GENDER) {
                String gender = data.getStringExtra("gender");

                if (!StringUtil.isEmptyandnull(gender)) {
                    if (gender.equals("0")) {
                        tvUserInfoGender.setText(R.string.female);
                    } else if (gender.equals("1")) {
                        tvUserInfoGender.setText(R.string.male);
                    }
                    userInfoEntity.getInfo().setGender(gender);
                }
                KLog.e(gender);
            } else if (requestCode == MSG_MODIFY_INFO_SIGN) {
                String sign = data.getStringExtra("sign");
                if (!StringUtil.isEmptyandnull(sign)) {
                    userInfoEntity.getInfo().setCidiograph(sign);
                }
                KLog.e(sign);
            }
        }
    }
}
