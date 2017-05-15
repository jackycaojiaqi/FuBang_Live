package com.fubang.live.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.EmotionAdapter;
import com.fubang.live.adapter.RoomAudienceAdapter;
import com.fubang.live.adapter.RoomChatAdapter;
import com.fubang.live.adapter.RoomGiftAdapter;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.presenter.impl.UpMicPresenterImpl;
import com.fubang.live.util.ConfigUtils;
import com.fubang.live.util.DialogFactory;
import com.fubang.live.util.FBImage;
import com.fubang.live.util.FileUtils;
import com.fubang.live.util.GlobalOnItemClickManager;
import com.fubang.live.util.LocationUtil;
import com.fubang.live.util.ScreenUtils;
import com.fubang.live.util.ShareUtil;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.StringUtil;
import com.fubang.live.util.ToastUtil;
import com.fubang.live.view.RoomListView;
import com.fubang.live.view.UpMicView;
import com.fubang.live.widget.SlidingTab.EmotionInputDetector;
import com.fubang.live.widget.SlidingTab.SlidingTabLayout;
import com.qiniu.BaseStreamingActivity;
import com.qiniu.CameraPreviewFrameView;
import com.qiniu.filter.IFilter;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.qiniu.pili.droid.streaming.WatermarkSetting;
import com.qiniu.pili.droid.streaming.widget.AspectFrameLayout;
import com.sample.room.MicNotify;
import com.sample.room.RoomMain;
import com.socks.library.KLog;
import com.xlg.android.protocol.BigGiftRecord;
import com.xlg.android.protocol.JoinRoomResponse;
import com.xlg.android.protocol.MicState;
import com.xlg.android.protocol.RoomChatMsg;
import com.xlg.android.protocol.RoomKickoutUserInfo;
import com.xlg.android.protocol.RoomUserInfoNew;

import org.dync.giftlibrary.widget.GiftControl;
import org.dync.giftlibrary.widget.GiftFrameLayout;
import org.dync.giftlibrary.widget.GiftModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

import static android.widget.LinearLayout.HORIZONTAL;
import static com.fubang.live.ui.RoomActivity.is_emoticon_show;

/**
 * Created by jacky on 17/3/27.
 */
public class LiveActivity extends BaseStreamingActivity implements StreamingStateChangedListener, MicNotify, AMapLocationListener {
    private Context context;
    private RoomMain roomMain = new RoomMain(this);
    private EmotionInputDetector mDetector;
    private UpMicPresenterImpl presenter;
    private List<RoomUserInfoNew> list_audience = new ArrayList<>();
    private List<RoomUserInfoNew> list_audience_top = new ArrayList<>();
    private BaseQuickAdapter roomUserAdapter;
    @BindView(R.id.rv_room_audience)
    RecyclerView rvRoomAudience;
    @BindView(R.id.tv_live_audince_num)
    TextView tvLiveAudinceNum;
    @BindView(R.id.iv_live_anchor_pic)
    ImageView ivLiveAnchorPic;
    @BindView(R.id.content)
    FrameLayout FlContent;
    private CustomPopWindow pop_info;
    private CustomPopWindow pop_setting;
    private GiftFrameLayout giftFrameLayout1;
    private GiftFrameLayout giftFrameLayout2;
    private GiftControl giftControl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //获取权限
        if (ContextCompat.checkSelfPermission(LiveActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            PermissionGen.with(LiveActivity.this)
                    .addRequestCode(100)
                    .permissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO)
                    .request();
        } else {
        }

        //获取权限
        if (ContextCompat.checkSelfPermission(LiveActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            PermissionGen.with(LiveActivity.this)
                    .addRequestCode(200)
                    .permissions(
                            Manifest.permission.RECORD_AUDIO)
                    .request();
        } else {
        }
        if (ContextCompat.checkSelfPermission(LiveActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionGen.with(LiveActivity.this)
                    .addRequestCode(300)
                    .permissions(
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                    .request();
        } else {
            initlocation();
        }
        EventBus.getDefault().register(this);
//        setContentView(R.layout.activity_live);
        AspectFrameLayout afl = (AspectFrameLayout) findViewById(R.id.cameraPreview_afl);
        afl.setShowMode(AspectFrameLayout.SHOW_MODE.FULL);
        CameraPreviewFrameView cameraPreviewFrameView =
                (CameraPreviewFrameView) findViewById(R.id.cameraPreview_surfaceView);
        cameraPreviewFrameView.setListener(this);
//        WatermarkSetting watermarksetting = new WatermarkSetting(this);
//        watermarksetting.setResourceId(R.mipmap.ic_launcher)
//                .setAlpha(100)
//                .setSize(WatermarkSetting.WATERMARK_SIZE.SMALL)
//                .setCustomPosition(0.5f, 0.5f);
        mMediaStreamingManager = new MediaStreamingManager(this, afl, cameraPreviewFrameView,
                AVCodecType.SW_VIDEO_WITH_SW_AUDIO_CODEC); // sw codec
//        mMediaStreamingManager.prepare(mCameraStreamingSetting, mMicrophoneStreamingSetting, watermarksetting, mProfile, new PreviewAppearance(0.0f, 0.0f, 0.5f, 0.5f, PreviewAppearance.ScaleType.FIT));
        mMediaStreamingManager.prepare(mCameraStreamingSetting, mMicrophoneStreamingSetting, mProfile);

        mMediaStreamingManager.setStreamingStateListener(this);
        mMediaStreamingManager.setSurfaceTextureCallback(this);
        mMediaStreamingManager.setStreamingSessionListener(this);
//        mMediaStreamingManager.setNativeLoggingEnabled(false);
        mMediaStreamingManager.setStreamStatusCallback(this);
        mMediaStreamingManager.setAudioSourceCallback(this);
        // update the StreamingProfile
//        mProfile.setStream(new StreamingProfile.Stream(mJSONObject1));
//        mMediaStreamingManager.setStreamingProfile(mProfile);
        setFocusAreaIndicator();
        //设置表情适配器
        mDetector = EmotionInputDetector.with(LiveActivity.this)
                .setEmotionView(emotionNewLayout)
                .bindToContent(emotionNewLayout)
                .bindToEditText(roomMessageEdit)
                .bindToEmotionButton(emotionButton)
                .build();
        setUpEmotionViewPager();

        //设置listadapter
        adapter = new RoomChatAdapter(list_msg, context);
        lvRoomMessage.setAdapter(adapter);
        adapter_gift = new RoomGiftAdapter(list_gift, context);
        lvRoomGift.setAdapter(adapter_gift);

        roomUserAdapter = new RoomAudienceAdapter(R.layout.item_audience_room, list_audience);
        rvRoomAudience.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL, false));
        roomUserAdapter.openLoadAnimation();
        roomUserAdapter.setAutoLoadMoreSize(5);
        roomUserAdapter.setEnableLoadMore(true);
        roomUserAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                View contentView = LayoutInflater.from(context).inflate(R.layout.pop_user_info, null);
                //处理popWindow 显示内容
                handleLogic(contentView, list_audience.get(position));
                //创建并显示popWindow
                pop_info = new CustomPopWindow.PopupWindowBuilder(context)
                        .setView(contentView)
                        .setOutsideTouchable(false)//是否PopupWindow 以外触摸dissmiss
                        .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                        .setBgDarkAlpha(0.5f) // 控制亮度
                        .size(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.Dp2Px(context, 620))//显示大小
                        .create()
                        .showAtLocation(rvRoomAudience, Gravity.CENTER, 0, 0);
            }
        });
        rvRoomAudience.setAdapter(roomUserAdapter);

        //礼物连击
        giftFrameLayout1 = (GiftFrameLayout) findViewById(R.id.gift_layout1);
        giftFrameLayout2 = (GiftFrameLayout) findViewById(R.id.gift_layout2);
        giftControl = new GiftControl(giftFrameLayout1, giftFrameLayout2);
    }

    private void handleLogic(View contentView, final RoomUserInfoNew roomUserInfo) {
        //设置pop监听事件
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop_info != null) {
                    pop_info.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.iv_pop_cancle:
                        pop_info.dissmiss();
                        break;
                    case R.id.tv_pop_fav:
                        //加入收藏操作
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                roomMain.getRoom().getChannel().followAnchor(Integer.parseInt(StartUtil.getUserId(context)), Integer.parseInt(StartUtil.getUserId(context)));
                            }
                        }).start();
                        ToastUtil.show(context, R.string.add_fav_success);
                        break;
                    case R.id.tv_pop_goto_user_info_page:
                        Intent intent = new Intent(context, UserInfoPageActivity.class);
                        intent.putExtra(AppConstant.ROOMID, String.valueOf(roomUserInfo.getUserid()));
                        startActivity(intent);
                        break;
                }
            }
        };
        contentView.findViewById(R.id.iv_pop_cancle).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_pop_fav).setOnClickListener(listener);
        contentView.findViewById(R.id.tv_pop_goto_user_info_page).setOnClickListener(listener);
        //填充数据
        ((TextView) contentView.findViewById(R.id.tv_pop_user_id)).setText(roomUserInfo.getUserid() + " ");//用户id
        ((TextView) contentView.findViewById(R.id.tv_pop_user_name)).setText(roomUserInfo.getAlias() + " ");//用户名字
        FBImage.Create(context, AppConstant.BASE_IMG_URL + roomUserInfo.getCphoto())
                .into(((ImageView) contentView.findViewById(R.id.iv_pop_user_pic)));//头像
    }

    /**
     * 处理setting弹窗
     *
     * @param contentView
     */
    private boolean is_mirror = false;

    private void handleSettingView(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_pop_setting_title://修改标题操作
                        rllLiveChangeTitle.setVisibility(View.VISIBLE);
                        etLiveChangeTitle.setFocusable(true);
                        tvLiveChangeTitleSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                room_title = etLiveChangeTitle.getText().toString().trim();
                                tvLiveTitle.setText("直播标题：" + room_title);
                                rllLiveChangeTitle.setVisibility(View.GONE);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        roomMain.getRoom().getChannel().forTitle(room_title);
                                    }
                                }).start();
                                etLiveChangeTitle.setText("");
                            }
                        });
                        ivLiveChangeTitleCancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rllLiveChangeTitle.setVisibility(View.GONE);
                            }
                        });
                        pop_setting.dissmiss();
                        break;
                    case R.id.ll_pop_setting_share:
                        doShareAction();
                        pop_setting.dissmiss();
                        break;
                    case R.id.ll_pop_setting_camera_change:
                        mHandler.removeCallbacks(mSwitcher);
                        mHandler.postDelayed(mSwitcher, 100);
                        pop_setting.dissmiss();
                        break;
                    case R.id.ll_pop_setting_beautify:
                        if (!mHandler.hasMessages(MSG_FB)) {
                            mHandler.sendEmptyMessage(MSG_FB);
                        }
                        pop_setting.dissmiss();
                        break;
                    case R.id.ll_pop_setting_mirror:
                        is_mirror = !is_mirror;
                        if (!is_mirror) {
                            ToastUtil.show(context, R.string.mirror_same);
                        } else {
                            ToastUtil.show(context, R.string.mirror_not_same);
                        }
                        if (!mHandler.hasMessages(MSG_PREVIEW_MIRROR)) {
                            mHandler.sendEmptyMessage(MSG_PREVIEW_MIRROR);
                        }
                        pop_setting.dissmiss();
                        break;
                }
            }
        };
        contentView.findViewById(R.id.ll_pop_setting_title).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_pop_setting_share).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_pop_setting_camera_change).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_pop_setting_beautify).setOnClickListener(listener);
        contentView.findViewById(R.id.ll_pop_setting_mirror).setOnClickListener(listener);
    }

    private List<RoomChatMsg> list_msg = new ArrayList<>();
    private List<BigGiftRecord> list_gift = new ArrayList<>();
    private RoomChatAdapter adapter;
    private RoomGiftAdapter adapter_gift;

    @PermissionFail(requestCode = 100)
    public void Permission100Fail() {
        //获取权限
        if (ContextCompat.checkSelfPermission(LiveActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            PermissionGen.with(LiveActivity.this)
                    .addRequestCode(100)
                    .permissions(
                            Manifest.permission.CAMERA
                    )
                    .request();
        } else {
        }

    }

    @PermissionFail(requestCode = 200)
    public void Permission200Fail() {
        //获取权限
        if (ContextCompat.checkSelfPermission(LiveActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            PermissionGen.with(LiveActivity.this)
                    .addRequestCode(200)
                    .permissions(
                            Manifest.permission.RECORD_AUDIO
                    )
                    .request();
        } else {
        }
    }

    @PermissionSuccess(requestCode = 300)
    public void Permission300Fail() {
        initlocation();
    }

    private RoomUserInfoNew userInfoAnchor;

    //接收主播信息
    @Subscriber(tag = "onMicUser")
    public void AnchorInfo(RoomUserInfoNew obj) {
        userInfoAnchor = obj;
        if (!StringUtil.isEmptyandnull(obj.getCphoto())) {
            FBImage.Create(context, AppConstant.BASE_IMG_URL + userInfoAnchor.getCphoto()).into(ivLiveAnchorPic);
        }
    }

    //接收服务器发送的消息更新列表
    @Subscriber(tag = "RoomChatMsg")
    public void getRoomChatMsg(RoomChatMsg msg) {
        KLog.e(msg.getContent() + " ");
        if (msg.getMsgtype() == 0) {
            if (msg.getIsprivate() == 0) {
                //("<b><FONT style=\"FONT-FAMILY:宋体;FONT-SIZE:17px; COLOR:#FF0000\">/mr599</FONT></b>")) {
                //<b><FONT style="FONT-FAMILY:宋体;FONT-SIZE:17px; COLOR:#FF0000">/mr599</FONT></b>
//                EventBus.getDefault().post(msg,"CommonMsg");
//                listView.setSelection(listView.getCount() - 1);
                if (list_msg.size() > 100) {//放置消息过多，异常
                    list_msg.clear();
                }
                //计算等级---不是主播
                for (RoomUserInfoNew roomUserInfoNew : list_audience) {
                    if (roomUserInfoNew.getUserid() == msg.getSrcid()) {
                        if (!StringUtil.isEmptyandnull(roomUserInfoNew.getExpend())) {
                            int level = (Integer.parseInt(roomUserInfoNew.getExpend()) / 100);
                            if (level >= 100) {
                                level = 100;
                            }
                            if (level == 0) {
                                level = 1;
                            }
                            msg.setUser_level(level);
                        }
                    }
                }
                //是主播
                if (userInfoAnchor != null) {
                    if (userInfoAnchor.getUserid() == msg.getSrcid()) {
                        if (!StringUtil.isEmptyandnull(userInfoAnchor.getExpend())) {
                            int level = (Integer.parseInt(userInfoAnchor.getExpend()) / 100);
                            if (level >= 100) {
                                level = 100;
                            }
                            if (level == 0) {
                                level = 1;
                            }
                            msg.setUser_level(level);
                        }
                    }
                }
                list_msg.add(msg);//以后消息过多会有问题
                adapter.notifyDataSetChanged();
                lvRoomMessage.setSelection(lvRoomMessage.getCount() - 1);
                setAnimaAlpha(lvRoomMessage);
            }
        }
//        if(msg.getMsgtype() == 0) {
//            if (msg.getIsprivate() == 1) {
//                if (msg.getToid() == sendToUser.getUserid() || msg.getToid() == Integer.parseInt(StartUtil.getUserId(this))) {
//                    EventBus.getDefault().post(msg,"PersonMsg");
//                }
//            }
//        }
        if (msg.getMsgtype() == 12 && msg.getSrcid() == 2) {

            Spanned spanned = Html.fromHtml(msg.getContent());
            Log.d("123", spanned + "");
        }

    }

    //接收礼物消息更新
    @Subscriber(tag = "BigGiftRecord")
    public void getGiftRecord(BigGiftRecord obj) {
        int count = obj.getCount();
        if (count != 0) {
            for (RoomUserInfoNew roomUserInfo : list_audience) {
                if (roomUserInfo.getUserid() == obj.getSrcid()) {//判断观众和发送者id是否一致   确定那一个人发的，从而去除头像
                    giftControl.loadGift(new GiftModel(String.valueOf(obj.getGiftid()), "送出礼物：", obj.getCount(),
                            String.valueOf(obj.getGiftid()), String.valueOf(obj.getSrcid()), obj.getSrcalias(),
                            AppConstant.BASE_IMG_URL + roomUserInfo.getCphoto(), System.currentTimeMillis()));
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaStreamingManager.resume();
    }

    private boolean is_pause = false;

    @Override
    protected void onPause() {
        super.onPause();
        // You must invoke pause here.
        is_pause = true;
        mMediaStreamingManager.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (roomMain.getRoom() != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    roomMain.getRoom().getChannel().kickOutRoom(Integer.parseInt(StartUtil.getUserId(context)));//将自己提出房间
                    roomMain.getRoom().getChannel().Close();
                }
            }).start();
        }
        //销毁动画
        if (giftControl != null) {
            giftControl.cleanAll();
        }
        //音乐播放销毁
        handler.removeCallbacks(runnable);
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        EventBus.getDefault().unregister(this);
    }

    private String room_title;

    //主播加入房间后上麦、并且发送标题
    @Subscriber(tag = "JoinRoomResponse")
    public void JoinRoomResponse(JoinRoomResponse obj) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                roomMain.getRoom().getChannel().sendUpMic();
                roomMain.getRoom().getChannel().forTitle(room_title);

            }
        }).start();

    }


    //接收观众列表信息
    @Subscriber(tag = "userList")
    public void AudienceInfo(List<RoomUserInfoNew> obj) {
        list_audience.clear();
        for (int i = 0; i < obj.size(); i++) {
            list_audience.add(obj.get(i));
        }
        if (list_audience.size() > 20) {
            list_audience_top = list_audience.subList(0, 19);
        } else {
            list_audience_top = list_audience;
        }
        //根据expend经验值，来重新排序。
        Collections.sort(list_audience_top, new Comparator<RoomUserInfoNew>() {
            @Override
            public int compare(RoomUserInfoNew o1, RoomUserInfoNew o2) {
                return Integer.parseInt(o1.getExpend()) > Integer.parseInt(o2.getExpend()) ? -1 : 1;
            }
        });
        roomUserAdapter.setNewData
                (list_audience_top);
        tvLiveAudinceNum.setText(list_audience.size() + " ");//房间观众数量

        //===================首次进入房间，聊天列表会有文字提示
        RoomChatMsg msg = new RoomChatMsg();
        msg.setType(1);
        list_msg.clear();
        list_msg.add(msg);
        adapter.notifyDataSetChanged();
        lvRoomMessage.setSelection(lvRoomMessage.getCount() - 1);
        setAnimaAlpha(lvRoomMessage);
    }

    //用户离开房间回调
    @Subscriber(tag = "RoomKickoutUserInfo")

    public void RoomKickoutUserInfo(RoomKickoutUserInfo obj) {
        int to_id = obj.getToid();
        for (int i = 0; i < list_audience.size(); i++) {
            if (list_audience.get(i).getUserid() == to_id) {
                list_audience.remove(i);
            }
        }
        if (list_audience.size() > 20) {
            list_audience_top = list_audience.subList(0, 19);
        } else {
            list_audience_top = list_audience;
        }
        //根据expend经验值，来重新排序。
        Collections.sort(list_audience_top, new Comparator<RoomUserInfoNew>() {
            @Override
            public int compare(RoomUserInfoNew o1, RoomUserInfoNew o2) {
                return Integer.parseInt(o1.getExpend()) > Integer.parseInt(o2.getExpend()) ? -1 : 1;
            }
        });
        roomUserAdapter.setNewData(list_audience_top);
        roomUserAdapter.notifyDataSetChanged();
        tvLiveAudinceNum.setText(list_audience.size() + " ");//房间观众数量
    }

    //用户加入房间回调
    @Subscriber(tag = "onRoomUserNotify")
    public void onRoomUserNotify(RoomUserInfoNew obj) {
        list_audience.add(obj);
        if (list_audience.size() > 20) {
            list_audience_top = list_audience.subList(0, 19);
        } else {
            list_audience_top = list_audience;
        }
        //根据expend经验值，来重新排序。
        Collections.sort(list_audience_top, new Comparator<RoomUserInfoNew>() {
            @Override
            public int compare(RoomUserInfoNew o1, RoomUserInfoNew o2) {
                return Integer.parseInt(o1.getExpend()) > Integer.parseInt(o2.getExpend()) ? -1 : 1;
            }
        });
        roomUserAdapter.setNewData(list_audience_top);
        tvLiveAudinceNum.setText(list_audience.size() + " ");//房间观众数量

        //============================等级大于30进入房间聊天列表会有通知
        RoomChatMsg msg = new RoomChatMsg();
        if (!StringUtil.isEmptyandnull(obj.getExpend())) {
            int level = (Integer.parseInt(obj.getExpend()) / 100);
            if (level >= 30) {
                msg.setType(2);
                msg.setSrcalias(obj.getAlias());
                list_msg.add(msg);
                adapter.notifyDataSetChanged();
                lvRoomMessage.setSelection(lvRoomMessage.getCount() - 1);
                setAnimaAlpha(lvRoomMessage);
            }
        }
    }

    @Override
    public void onStateChanged(StreamingState streamingState, Object o) {
        switch (streamingState) {
            case PREPARING:
                KLog.e("PREPARING");
                break;
            case CONNECTING:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogFactory.hideRequestDialog();
                        rl_live_control.setVisibility(View.VISIBLE);
                        lvRoomMessage.setVisibility(View.VISIBLE);
                        llLiveStart.setVisibility(View.GONE);
                    }
                });
                KLog.e("CONNECTING");
                break;
            case READY:
                KLog.e("READY");
                mIsReady = true;
                if (is_pause) {//如果是调用onpause  再onresume，需要重新上传视频流
                    startStreaming();
                    is_pause = false;
                }
                break;
            case STREAMING:
                KLog.e("STREAMING");
                // The av packet had been sent.
                break;
            case SHUTDOWN:
                KLog.e("SHUTDOWN");
                // The streaming had been finished.
                break;
            case IOERROR:
                KLog.e("IOERROR");
                // Network connect error.
                break;
            case OPEN_CAMERA_FAIL:
                KLog.e("OPEN_CAMERA_FAIL");
                // Failed to open camera.
                break;
            case DISCONNECTED:
                KLog.e("DISCONNECTED");
                break;
        }
    }

    private InputMethodManager imm;
    private String ip;
    private int port;
    private View contentView_setting;
    private int windowPos[];
    private int REQUEST_CODE_MUSIC = 0X008;
    private MaterialDialog dialog;

    @OnClick({R.id.iv_live_start, R.id.iv_live_share, R.id.iv_live_exit, R.id.iv_live_chat
            , R.id.tv_room_input_close, R.id.room_new_chat_send, R.id.iv_live_music
            , R.id.iv_live_cancle_top, R.id.iv_live_setting, R.id.tv_live_lrc_cancle})
    public void onViewClicked(View view) {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {
            case R.id.iv_live_start:
                room_title = etLiveTitle.getText().toString().trim();
                if (!StringUtil.isEmptyandnull(room_title)) {
                    tvLiveTitle.setText("直播标题：" + room_title);
                }
                if (mIsReady) {
                    DialogFactory.showRequestDialog(context);
                    //上麦presenter
                    presenter = new UpMicPresenterImpl(new UpMicView() {
                        @Override
                        public void successUpMic(RoomEntity entity) {
                            if (entity.getRoomlist().size() > 0) {
                                final String roomPwd = entity.getRoomlist().get(0).getRoompwd();
                                String roomIp = entity.getRoomlist().get(0).getGateway();
                                String roomId = entity.getRoomlist().get(0).getRoomid();
                                String[] Ips = roomIp.split(";");
                                String[] ports = Ips[0].split(":");
                                ip = ports[0];
                                port = Integer.parseInt(ports[1]);
                                KLog.e(roomId + " " + ip + " " + port + " " + roomPwd);
                                //连接房间
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        roomMain.Start(Integer.parseInt(StartUtil.getUserId(context)), Integer.parseInt(StartUtil.getUserId(context)), StartUtil.getUserPwd(context), ip, port, roomPwd);
                                    }
                                }).start();
                                startStreaming();
                            } else {
                                DialogFactory.hideRequestDialog();
                                startActivity(new Intent(context, AuthApplyActivity.class));
                                ToastUtil.show(context, R.string.auth_upmic);
                                finish();
                            }
                        }

                        @Override
                        public void faidedUpMic(Throwable e) {
                            DialogFactory.hideRequestDialog();
                            startActivity(new Intent(context, AuthApplyActivity.class));
                            ToastUtil.show(context, R.string.auth_upmic);
                            finish();
                        }
                    }, Integer.parseInt(StartUtil.getUserId(context)));
                    presenter.getUpMicInfo();
                }

                break;
            case R.id.iv_live_share:
                doShareAction();
                break;
            case R.id.iv_live_exit:
                MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                        .title(R.string.sure_to_quit_live)
                        .positiveText(R.string.sure)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                startActivity(new Intent(context, LiveDoneActivity.class));
                                finish();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        })
                        .negativeText(R.string.cancle);
                dialog = builder.build();
                dialog.show();

                break;
            case R.id.iv_live_chat:
                rllRoomInput.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!is_emoticon_show) {
                            if (imm != null) {
                                imm.showSoftInput(roomMessageEdit, 0);
                            }
                        }
                    }
                }, 200);
                break;
            case R.id.tv_room_input_close:
                rllRoomInput.setVisibility(View.GONE);
                break;
            case R.id.room_new_chat_send:
                final String msg = roomMessageEdit.getText().toString();
                if (!StringUtil.isEmptyandnull(msg)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            roomMain.getRoom().getChannel().sendChatMsg(0, (byte) 0x00, (byte) 0x00, msg, StartUtil.getUserName(context), 0);
                        }
                    }).start();
                    roomMessageEdit.setText("");
                    rllRoomInput.setVisibility(View.GONE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(roomMessageEdit.getWindowToken(), 0);
                    }
                }
                break;
            case R.id.iv_live_music:
                startActivityForResult(new Intent(context, LivePickMusicActivity.class), REQUEST_CODE_MUSIC);
                break;
            case R.id.iv_live_cancle_top:
                finish();
                break;
            case R.id.iv_live_setting:
                if (contentView_setting != null) {
                    if (pop_setting != null) {
                        pop_setting.dissmiss();
                        pop_setting.showAtLocation(contentView_setting, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
                    }
                } else {
                    contentView_setting = LayoutInflater.from(context).inflate(R.layout.pop_live_setting, null);
                    windowPos = ConfigUtils.calculatePopWindowPos(ivLiveSetting, contentView_setting);
//                    int xOff = 20;// 可以自己调整偏移
//                    windowPos[0] -= xOff;
                    //处理popWindow 显示内容
                    handleSettingView(contentView_setting);
                    //创建并显示popWindow
                    pop_setting = new CustomPopWindow.PopupWindowBuilder(context)
                            .setView(contentView_setting)
                            .setOutsideTouchable(false)//是否PopupWindow 以外触摸dissmiss
                            .enableBackgroundDark(false) //弹出popWindow时，背景是否变暗
                            .create()
                            .showAtLocation(contentView_setting, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
                }
                break;
            case R.id.tv_live_lrc_cancle:
                mediaPlayer.pause();
                lrcLive.onDrag(0);
                rllLiveLrc.setVisibility(View.GONE);
                break;
        }
    }

    private PopupWindow pop_share;

    /**
     * 处理分享弹窗
     */

    private void doShareAction() {
        View popupView = getLayoutInflater().inflate(R.layout.pop_share, null);
        pop_share = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop_share.showAtLocation(ivLiveStart, Gravity.TOP, 0, 0);
        TextView tv_cancle = (TextView) popupView.findViewById(R.id.tv_share_cancle);
        LinearLayout ll_wechat = (LinearLayout) popupView.findViewById(R.id.ll_share_wechat);
        LinearLayout ll_wechat_circle = (LinearLayout) popupView.findViewById(R.id.ll_share_wechat_circle);
        LinearLayout ll_qq = (LinearLayout) popupView.findViewById(R.id.ll_share_wechat_qq);
        LinearLayout ll_sina = (LinearLayout) popupView.findViewById(R.id.ll_share_sina);
        ll_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform plat = ShareSDK.getPlatform(Wechat.NAME);
                ShareUtil.getInstance().showShareNew(context, plat);
                pop_share.dismiss();
            }
        });
        ll_wechat_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform plat = ShareSDK.getPlatform(WechatMoments.NAME);
                ShareUtil.getInstance().showShareNew(context, plat);
                pop_share.dismiss();
            }
        });
        ll_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform plat = ShareSDK.getPlatform(QQ.NAME);
                ShareUtil.getInstance().showShareNew(context, plat);
                pop_share.dismiss();
            }
        });
        ll_sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform plat = ShareSDK.getPlatform(SinaWeibo.NAME);
                ShareUtil.getInstance().showShareNew(context, plat);
                pop_share.dismiss();
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_share.dismiss();
            }
        });
    }

    @Override
    public void onMic(String ip, int port, int rand, int uid) {

    }

    /**
     * 表情页面
     */

    private void setUpEmotionViewPager() {
        final String[] titles = new String[]{"经典"};
        EmotionAdapter mViewPagerAdapter = new EmotionAdapter(getSupportFragmentManager(), titles);
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.new_pager);
//        if (mViewPager != null) {
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);
//        }
//        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_new_tabs);
//        slidingTabLayout.setCustomTabView(R.layout.widget_tab_indicator, R.id.text);
//        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(context, R.color.colorPrimary));
//        slidingTabLayout.setDistributeEvenly(true);
//        slidingTabLayout.setViewPager(mViewPager);
        GlobalOnItemClickManager globalOnItemClickListener = GlobalOnItemClickManager.getInstance();
        globalOnItemClickListener.attachToEditText((EditText) findViewById(R.id.edit_new_text));
    }

    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_MUSIC) {
                rllLiveLrc.setVisibility(View.VISIBLE);
                String music_id = data.getStringExtra("music_id");
                File file_lrc = new File(FileUtils.getLrcFiles() + music_id + ".lrc");
                if (file_lrc.exists()) {
                    lrcLive.loadLrc(file_lrc);//设置歌词资源
                }
                File file_music = new File(FileUtils.getMusicFiles() + music_id + ".mp3");

                if (file_music.exists()) {
                    try {
                        mediaPlayer.reset();
                        String path = file_music.getAbsolutePath();
                        KLog.e(path);
                        mediaPlayer.setDataSource(path);//设置MP3路径
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mediaPlayer.start();
                                handler.post(runnable);
                            }
                        });
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Handler handler = new Handler();
    /**
     * 根据MP3播放进度时事同步歌词组件
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying()) {
                long time = mediaPlayer.getCurrentPosition();
                lrcLive.updateTime(time);
            }
            handler.postDelayed(this, 200);
        }
    };
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient;

    private void initlocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (StringUtil.isEmptyandnull(StartUtil.getCity(context))) {//为空表示用户没有手动设置区域，则定位填充数据，如果有数据，则不填充
                StartUtil.putCity(context, aMapLocation.getCity());
            }
            StartUtil.putLAT(context, String.valueOf(aMapLocation.getLatitude()));
            StartUtil.putLNG(context, String.valueOf(aMapLocation.getLongitude()));
            LocationUtil.uploadLatLng(context, String.valueOf(aMapLocation.getLatitude()),
                    String.valueOf(aMapLocation.getLongitude()));
        }
    }
}
