package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.UserInfoEntity;
import com.fubang.live.util.FBImage;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.StringUtil;
import com.fubang.live.util.ToastUtil;
import com.fubang.live.widget.NestedScrollView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.socks.library.KLog;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static android.view.View.VISIBLE;

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
    @BindView(R.id.iv_user_info_gender)
    ImageView ivUserInfoGender;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.sv_root)
    NestedScrollView svRoot;

    private UserInfoEntity userEntity;
    private Context context;
    private String roomId, roomIp, ip, roomPwd;
    /**
     * 移除并放大的模块, 悬浮模块(标题),　内容中的标题
     */
    private View rl_top;
    /**
     * 有反弹的滑动控件
     */
    private NestedScrollView sv_root;
    /**
     * 滑动组中的内容
     */
    private LinearLayout ll_content;

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

    private boolean is_up_mic = false;

    private void initdate() {
        roomPwd = getIntent().getStringExtra(AppConstant.ROOMPWD);
        roomIp = getIntent().getStringExtra(AppConstant.ROOMIP);
        roomId = getIntent().getStringExtra(AppConstant.ROOMID);
        KLog.e(roomId);
        String url = AppConstant.BASE_URL + AppConstant.MSG_GET_USER_INFO;
        OkGo.get(url)//
                .tag(this)//
                .params("nuserid", roomId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            userEntity = new Gson().fromJson(s, UserInfoEntity.class);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                        if (userEntity.getStatus().equals("success")) {
                            if (userEntity.getInfo() != null) {
                                //名字
                                tvUserInfoName.setText(userEntity.getInfo().getCalias() + " ");
                                //城市+id
                                if (StringUtil.isEmptyandnull(userEntity.getInfo().getLocation())) {
                                    tvUserInfoCityAndId.setText("常住地 " + StartUtil.getCity(context) + "   " + "用户id " + StartUtil.getUserId(context));
                                } else {
                                    tvUserInfoCityAndId.setText("常住地 " + userEntity.getInfo().getLocation() + "   " + "用户id " + StartUtil.getUserId(context));
                                }
                                //签名
                                if (!StringUtil.isEmptyandnull(userEntity.getInfo().getCidiograph())) {
                                    tvUserInfoSign.setText(userEntity.getInfo().getCidiograph());
                                }
                                //性别
                                if (userEntity.getInfo().getGender().equals("0")) {
                                    ivUserInfoGender.setImageResource(R.drawable.ic_female_select);
                                } else {
                                    ivUserInfoGender.setImageResource(R.drawable.ic_male_select);
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
                                    llUserInfoGotoRoom.setVisibility(VISIBLE);
                                    is_up_mic = true;
                                } else {
                                    is_up_mic = false;
                                }
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

    private int scrollY_new;

    private void initview() {
        tvTitle.setText("用户信息");
        rl_top = findViewById(R.id.rl_top);
        sv_root = (NestedScrollView) findViewById(R.id.sv_root);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        sv_root.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                KLog.e(scrollY + " " + oldScrollY);
                if (scrollY >= 0) {//往上滑动
                    int height = rl_top.getHeight();
                    if (height != ll_content.getPaddingTop()) {//如果滑动时高度有误先矫正高度
                        ViewGroup.LayoutParams layoutParams = rl_top.getLayoutParams();
                        layoutParams.height = ll_content.getPaddingTop();
                        rl_top.setLayoutParams(layoutParams);
                    }
//                    boolean overTitle = scrollY >= height;
//                    rl_top.setVisibility(overTitle ? View.GONE : VISIBLE);
                    rl_top.scrollTo(0, scrollY / 3);
                    //如果滑动到top布局一半 隐藏
                    if (scrollY >= (height / 6 * 2)) {
                        llUserInfoGotoRoom.setVisibility(View.GONE);
                    }
                } else {//下拉滑动
//                    rl_top.setVisibility(VISIBLE);
                    rl_top.scrollTo(0, 0);//不能有滑动偏移
                    ViewGroup.LayoutParams layoutParams = rl_top.getLayoutParams();
                    layoutParams.height = ll_content.getPaddingTop() - scrollY;
                    rl_top.setLayoutParams(layoutParams);
                }
                if (scrollY_new > scrollY) {//确保向下滑
                    if (scrollY < 200) {
                        if (is_up_mic) {//是否上麦
                            llUserInfoGotoRoom.setVisibility(View.VISIBLE);
                        }
                    }
                }
                scrollY_new = scrollY;
            }
        });
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
                KLog.e("ll_user_info_goto_room");
                intent = new Intent(context, RoomActivity.class);
                intent.putExtra(AppConstant.ROOMID, roomId);
                intent.putExtra(AppConstant.ROOMIP, roomIp);
                intent.putExtra(AppConstant.ROOMPWD, roomPwd);
                startActivity(intent);
                break;
        }
    }
}
