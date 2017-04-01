package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fubang.live.R;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.util.ShareUtil;
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
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by jacky on 17/3/27.
 */
public class LiveActivity extends BaseStreamingActivity implements StreamingStateChangedListener {
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
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

    @OnClick({R.id.iv_live_start, R.id.iv_live_share, R.id.iv_live_exit, R.id.iv_live_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_live_start:
                KLog.e("1111");
                if (mIsReady) {
                    KLog.e("1111");
                    startStreaming();
                }
                break;
            case R.id.iv_live_share:
                doShareAction();
                break;
            case R.id.iv_live_exit:
                startActivity(new Intent(context,LiveDoneActivity.class));
                finish();
                break;
            case R.id.iv_live_chat:
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
            }
        });
        ll_wechat_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform plat = ShareSDK.getPlatform(WechatMoments.NAME);
                ShareUtil.getInstance().showShareNew(context, plat);
            }
        });
        ll_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform plat = ShareSDK.getPlatform(QQ.NAME);
                ShareUtil.getInstance().showShareNew(context, plat);
            }
        });
        ll_sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform plat = ShareSDK.getPlatform(SinaWeibo.NAME);
                ShareUtil.getInstance().showShareNew(context, plat);
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_share.dismiss();
            }
        });
    }

}
