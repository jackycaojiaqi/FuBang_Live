package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.UserInfoEntity;
import com.fubang.live.util.FBImage;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.StringUtil;
import com.fubang.live.util.ToastUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.socks.library.KLog;
import com.xlg.android.protocol.RoomUserInfo;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jacky on 2017/4/28.
 */
public class UserInfoPageActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_user_info_bg)
    ImageView ivUserInfoBg;
    @BindView(R.id.tv_user_info_name)
    TextView tvUserInfoName;
    @BindView(R.id.tv_user_info_city_and_id)
    TextView tvUserInfoCityAndId;
    @BindView(R.id.tv_user_info_fav)
    TextView tvUserInfoFav;
    @BindView(R.id.tv_user_info_fans)
    TextView tvUserInfoFans;
    @BindView(R.id.tv_user_info_sign)
    TextView tvUserInfoSign;
    @BindView(R.id.ll_user_info_fav)
    LinearLayout llUserInfoFav;
    @BindView(R.id.ll_user_info_message)
    LinearLayout llUserInfoMessage;
    @BindView(R.id.ll_user_info_goto_room)
    LinearLayout llUserInfoGotoRoom;

    private UserInfoEntity userEntity;
    private Context context;
    private String roomId,roomIp, ip, roomPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_user_info_page);
        ButterKnife.bind(this);
        context = this;
        initview();
        initdate();
    }

    private void initdate() {
        roomPwd = getIntent().getStringExtra(AppConstant.ROOMPWD);
        roomIp = getIntent().getStringExtra(AppConstant.ROOMIP);
        roomId = getIntent().getStringExtra(AppConstant.ROOMID);
        String url = AppConstant.BASE_URL + AppConstant.MSG_GET_USER_INFO;
        OkGo.get(url)//
                .tag(this)//
                .params("nuserid",roomId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        userEntity = new Gson().fromJson(s, UserInfoEntity.class);
                        if (userEntity.getStatus().equals("success")) {
                            //名字
                            tvUserInfoName.setText(userEntity.getInfo().getCalias() + " ");
                            //用户id
                            tvUserInfoCityAndId.setText("常住地 台州" + "   " + "用户id " + StartUtil.getUserId(context));
                            //签名
                            if (!StringUtil.isEmptyandnull(userEntity.getInfo().getCidiograph())) {
                                tvUserInfoSign.setText(userEntity.getInfo().getCidiograph());
                            }
                            //粉丝数
                            tvUserInfoFans.setText("粉丝 " + userEntity.getInfo().getGuanzhunum());
                            //关注数
                            tvUserInfoFav.setText("关注 " + userEntity.getInfo().getGuanzhunum());
                            //直播背景图片
                            if (!StringUtil.isEmptyandnull(userEntity.getInfo().getBphoto())) {
                                KLog.e(AppConstant.BASE_IMG_URL + userEntity.getInfo().getBphoto());
                                StartUtil.putUserPic(context, AppConstant.BASE_IMG_URL + userEntity.getInfo().getBphoto());
                                FBImage.Create(context, AppConstant.BASE_IMG_URL + userEntity.getInfo().getBphoto()).into(ivUserInfoBg);
                            }
                            //用户上麦状态
                            if (userEntity.getInfo().getMicstate().equals("1")) {
                                llUserInfoGotoRoom.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();
                    }
                });
    }

    private void initview() {
        tvTitle.setText("用户信息");
    }

    @OnClick({R.id.iv_back, R.id.tv_user_info_fav, R.id.tv_user_info_fans, R.id.ll_user_info_fav, R.id.ll_user_info_message
            , R.id.ll_user_info_goto_room})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_user_info_fav:
                intent = new Intent(context, FavListActivity.class);
                intent.putExtra(AppConstant.USER_ID, StartUtil.getUserId(context));
                startActivity(intent);
                break;
            case R.id.tv_user_info_fans:
                break;
            case R.id.ll_user_info_fav:
                EventBus.getDefault().post(roomId, "add_fav");
                ToastUtil.show(context, R.string.add_fav_success);
                break;
            case R.id.ll_user_info_message:
                break;
            case R.id.ll_user_info_goto_room:
                intent = new Intent(context, RoomActivity.class);
                intent.putExtra(AppConstant.ROOMID, roomId);
                intent.putExtra(AppConstant.ROOMIP, roomIp);
                intent.putExtra(AppConstant.ROOMPWD, roomPwd);
                startActivity(intent);
                break;
        }
    }
}
