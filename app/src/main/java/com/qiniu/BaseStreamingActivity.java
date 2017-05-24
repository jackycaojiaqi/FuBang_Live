package com.qiniu;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.util.Config;
import com.fubang.live.widget.ClearableEditText;
import com.qiniu.gles.FBO;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.AudioSourceCallback;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting.CAMERA_FACING_ID;
import com.qiniu.pili.droid.streaming.FrameCapturedCallback;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.MicrophoneStreamingSetting;
import com.qiniu.pili.droid.streaming.StreamStatusCallback;
import com.qiniu.pili.droid.streaming.StreamingPreviewCallback;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingSessionListener;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.qiniu.pili.droid.streaming.SurfaceTextureCallback;
import com.qiniu.pili.droid.streaming.av.common.PLFourCC;
import com.qiniu.ui.RotateLayout;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.wcy.lrcview.LrcView;

public class BaseStreamingActivity extends BaseActivity {

    private static final String TAG = "StreamingBaseActivity";

    private static final int ZOOM_MINIMUM_WAIT_MILLIS = 33; //ms

    protected static final int MSG_START_STREAMING = 0;
    protected static final int MSG_STOP_STREAMING = 1;
    protected static final int MSG_SET_ZOOM = 2;
    protected static final int MSG_MUTE = 3;
    protected static final int MSG_FB = 4;
    protected static final int MSG_PREVIEW_MIRROR = 5;
    protected static final int MSG_ENCODING_MIRROR = 6;
    @BindView(R.id.et_live_title)
    protected EditText etLiveTitle;
    @BindView(R.id.iv_live_start)
    protected ImageView ivLiveStart;
    @BindView(R.id.ll_live_start)
    protected RelativeLayout llLiveStart;
    @BindView(R.id.iv_live_share)
    protected ImageView ivLiveShare;
    @BindView(R.id.iv_live_exit)
    protected ImageView ivLiveExit;
    @BindView(R.id.iv_live_cancle_top)
    protected ImageView ivLiveCancleTop;
    @BindView(R.id.iv_live_chat)
    protected ImageView ivLiveChat;
    @BindView(R.id.iv_live_music)
    protected ImageView ivLiveMusic;
    @BindView(R.id.iv_live_setting)
    protected ImageView ivLiveSetting;
    @BindView(R.id.tv_live_title)
    protected TextView tvLiveTitle;

    //礼物聊天列表
    @BindView(R.id.lv_room_message)
    protected ListView lvRoomMessage;
    @BindView(R.id.lv_room_gift)
    protected ListView lvRoomGift;
    //============================底部输入栏
    @BindView(R.id.room_new_chat_send)
    protected Button roomChatSend;
    @BindView(R.id.rll_room_input)
    protected RelativeLayout rllRoomInput;
    @BindView(R.id.chat_new_input_line)
    protected LinearLayout llRoomInput;
    @BindView(R.id.tv_room_input_close)
    protected TextView tvRoomInputClose;
    @BindView(R.id.edit_new_text)
    protected EditText roomMessageEdit;
    @BindView(R.id.emotion_new_button)
    protected ImageView emotionButton;
    @BindView(R.id.emotion_new_layout)
    protected RelativeLayout emotionNewLayout;
    //===============改变标题布局
    @BindView(R.id.et_live_change_title)
    protected ClearableEditText etLiveChangeTitle;
    @BindView(R.id.tv_live_change_title_submit)
    protected TextView tvLiveChangeTitleSubmit;
    @BindView(R.id.rll_live_change_title)
    protected RelativeLayout rllLiveChangeTitle;
    @BindView(R.id.iv_live_change_title_cancle)
    protected ImageView ivLiveChangeTitleCancle;
    //====================歌词布局
    @BindView(R.id.rll_live_lrc)
    protected RelativeLayout rllLiveLrc;
    @BindView(R.id.lrc_live)
    protected LrcView lrcLive;
    @BindView(R.id.tv_live_lrc_cancle)
    protected TextView tvLiveLrcCancle;


    protected RelativeLayout rl_live_control;

    private Context mContext;

    protected Button mShutterButton;
    private Button mMuteButton;
    private Button mTorchBtn;
    private Button mCameraSwitchBtn;
    private Button mCaptureFrameBtn;
    private Button mEncodingOrientationSwitcherBtn;
    private Button mFaceBeautyBtn;
    private RotateLayout mRotateLayout;

    protected TextView mLogTextView;
    protected TextView mStatusTextView;
    protected TextView mStatView;

    protected boolean mShutterButtonPressed = false;
    private boolean mIsTorchOn = false;
    private boolean mIsNeedMute = false;
    private boolean mIsNeedFB = false;
    private boolean mIsEncOrientationPort = true;
    private boolean mIsPreviewMirror = false;
    private boolean mIsEncodingMirror = false;

    private String mStatusMsgContent;
    private String mLogContent = "\n";

    protected MediaStreamingManager mMediaStreamingManager;
    protected CameraStreamingSetting mCameraStreamingSetting;
    protected MicrophoneStreamingSetting mMicrophoneStreamingSetting;
    protected StreamingProfile mProfile;
    protected JSONObject mJSONObject;
    private boolean mOrientationChanged = false;

    protected boolean mIsReady = false;

    private int mCurrentZoom = 0;
    private int mMaxZoom = 0;

    private FBO mFBO = new FBO();


    private int mCurrentCamFacingIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Config.SCREEN_ORIENTATION == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            mIsEncOrientationPort = true;
        } else if (Config.SCREEN_ORIENTATION == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mIsEncOrientationPort = false;
        }
        setRequestedOrientation(Config.SCREEN_ORIENTATION);
        setContentView(R.layout.activity_camera_streaming);
        ButterKnife.bind(this);
        rl_live_control = (RelativeLayout) findViewById(R.id.rl_live_control);

//        SharedLibraryNameHelper.getInstance().renameSharedLibrary(
//                SharedLibraryNameHelper.PLSharedLibraryType.PL_SO_TYPE_AAC,
//                getApplicationInfo().nativeLibraryDir + "/libpldroid_streaming_aac_encoder_v7a.so");
//
//        SharedLibraryNameHelper.getInstance().renameSharedLibrary(
//                SharedLibraryNameHelper.PLSharedLibraryType.PL_SO_TYPE_CORE, "pldroid_streaming_core");
//
//        SharedLibraryNameHelper.getInstance().renameSharedLibrary(
//                SharedLibraryNameHelper.PLSharedLibraryType.PL_SO_TYPE_H264, "pldroid_streaming_h264_encoder_v7a");

        mContext = this;
    }
}
