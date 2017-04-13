package com.fubang.live.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.EmotionAdapter;
import com.fubang.live.adapter.RoomChatAdapter;
import com.fubang.live.adapter.RoomGiftAdapter;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.presenter.impl.UpMicPresenterImpl;
import com.fubang.live.util.GlobalOnItemClickManager;
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
import com.xlg.android.protocol.MicState;
import com.xlg.android.protocol.RoomChatMsg;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

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

import static com.fubang.live.ui.RoomActivity.is_emoticon_show;

/**
 * Created by jacky on 17/3/27.
 */
public class LiveActivity extends BaseStreamingActivity implements StreamingStateChangedListener, MicNotify {
    private Context context;
    private RoomMain roomMain = new RoomMain(this);
    private EmotionInputDetector mDetector;
    private UpMicPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //获取权限
        if (ContextCompat.checkSelfPermission(LiveActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            PermissionGen.with(LiveActivity.this)
                    .addRequestCode(100)
                    .permissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();
        } else {
        }
        //获取权限
        if (ContextCompat.checkSelfPermission(LiveActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            PermissionGen.with(LiveActivity.this)
                    .addRequestCode(200)
                    .permissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();
        } else {
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
                .bindToContent(llRoomInput)
                .bindToEditText(roomMessageEdit)
                .bindToEmotionButton(emotionButton)
                .build();
        setUpEmotionViewPager();

        //设置listadapter
        adapter = new RoomChatAdapter(list_msg, context);
        lvRoomMessage.setAdapter(adapter);
        adapter_gift = new RoomGiftAdapter(list_gift, context);
        lvRoomGift.setAdapter(adapter_gift);


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
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .request();
        } else {
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
                if (list_msg.size() > 200) {//放置消息过多，异常
                    list_msg.clear();
                }
                list_msg.add(msg);//以后消息过多会有问题
                adapter.notifyDataSetChanged();
                lvRoomMessage.setSelection(lvRoomMessage.getCount() - 1);
                lvRoomMessage.setVisibility(View.VISIBLE);
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
            list_gift.add(obj);
            adapter_gift.notifyDataSetChanged();
            lvRoomGift.setSelection(lvRoomGift.getCount() - 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaStreamingManager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // You must invoke pause here.
        mMediaStreamingManager.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        roomMain.getRoom().getChannel().Close();
        EventBus.getDefault().unregister(this);
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
                        rl_live_control.setVisibility(View.VISIBLE);
                        llLiveStart.setVisibility(View.GONE);
                    }
                });
                KLog.e("CONNECTING");
                break;
            case READY:
                KLog.e("READY");
                mIsReady = true;
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

    @OnClick({R.id.iv_live_start, R.id.iv_live_share, R.id.iv_live_exit, R.id.iv_live_chat
            , R.id.tv_room_input_close, R.id.room_new_chat_send})
    public void onViewClicked(View view) {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {
            case R.id.iv_live_start:
                if (mIsReady) {
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
                                ToastUtil.show(context, R.string.auth_upmic);
                            }
                        }

                        @Override
                        public void faidedUpMic(Throwable e) {
                            ToastUtil.show(context, R.string.auth_upmic);
                        }
                    }, Integer.parseInt(StartUtil.getUserId(context)));
                    presenter.getUpMicInfo();
                }

                break;
            case R.id.iv_live_share:
                doShareAction();
                break;
            case R.id.iv_live_exit:
                startActivity(new Intent(context, LiveDoneActivity.class));
                finish();
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
                if (!StringUtil.isEmptyandnull(roomMessageEdit.getText().toString())) {
                    roomMain.getRoom().getChannel().sendChatMsg(0, (byte) 0x00, (byte) 0x00, roomMessageEdit.getText().toString(), StartUtil.getUserId(context), 0);
                    roomMessageEdit.setText("");
                    rllRoomInput.setVisibility(View.GONE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(roomMessageEdit.getWindowToken(), 0);
                    }
                }
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
        final String[] titles = new String[]{"经典", "vip"};
        EmotionAdapter mViewPagerAdapter = new EmotionAdapter(getSupportFragmentManager(), titles);
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.new_pager);
//        if (mViewPager != null) {
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setCurrentItem(0);
//        }
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_new_tabs);
        slidingTabLayout.setCustomTabView(R.layout.widget_tab_indicator, R.id.text);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(context, R.color.colorPrimary));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(mViewPager);

        GlobalOnItemClickManager globalOnItemClickListener = GlobalOnItemClickManager.getInstance();
        globalOnItemClickListener.attachToEditText((EditText) findViewById(R.id.edit_new_text));
    }
}
