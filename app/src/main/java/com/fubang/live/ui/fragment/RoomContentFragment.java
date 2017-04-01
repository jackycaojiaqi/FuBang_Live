package com.fubang.live.ui.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fubang.live.R;
import com.fubang.live.adapter.EmotionAdapter;
import com.fubang.live.adapter.GiftGridViewAdapter;
import com.fubang.live.base.BaseFragment;
import com.fubang.live.entities.GiftEntity;
import com.fubang.live.entities.RtmpUrlEntity;
import com.fubang.live.presenter.impl.RtmpUrlPresenterImpl;
import com.fubang.live.ui.MainActivity;
import com.fubang.live.util.GiftUtil;
import com.fubang.live.util.GlobalOnItemClickManager;
import com.fubang.live.util.NetUtils;
import com.fubang.live.util.ShareUtil;
import com.fubang.live.util.StartUtil;
import com.fubang.live.view.RtmpUrlView;
import com.fubang.live.widget.MediaController;
import com.fubang.live.widget.SlidingTab.EmotionInputDetector;
import com.fubang.live.widget.SlidingTab.SlidingTabLayout;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.sample.room.MicNotify;
import com.sample.room.RoomMain;
import com.socks.library.KLog;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.WINDOW_SERVICE;
import static com.fubang.live.ui.RoomActivity.is_emoticon_show;

public class RoomContentFragment extends BaseFragment implements MicNotify, RtmpUrlView {
    private static final int MESSAGE_ID_RECONNECTING = 0x01;
    @BindView(R.id.ib_change_orientation)
    ImageButton ibChangeOrientation;
    @BindView(R.id.ib_change_screen)
    ImageButton ibChangeScreen;
    @BindView(R.id.iv_room_anchor_small_pic)
    CircleImageView ivRoomAnchorSmallPic;
    @BindView(R.id.tv_room_anchor_name)
    TextView tvRoomAnchorName;
    @BindView(R.id.tv_room_anchor_online_num)
    TextView tvRoomAnchorOnlineNum;
    @BindView(R.id.iv_room_add_favorite)
    ImageView ivRoomAddFavorite;
    @BindView(R.id.tv_anchor_kbi_num)
    TextView tvAnchorKbiNum;
    @BindView(R.id.tv_anchor_id)
    TextView tvAnchorId;
    @BindView(R.id.iv_room_gift)
    ImageView ivRoomGift;
    @BindView(R.id.iv_room_share)
    ImageView ivRoomShare;
    @BindView(R.id.iv_room_exit)
    ImageView ivRoomExit;
    @BindView(R.id.iv_room_chat)
    ImageView ivRoomChat;
    @BindView(R.id.lv_room_message)
    ListView lvRoomMessage;
    //============================底部输入栏
    @BindView(R.id.room_new_chat_send)
    Button roomChatSend;
    @BindView(R.id.rll_room_input)
    RelativeLayout rllRoomInput;
    @BindView(R.id.chat_new_input_line)
    LinearLayout llRoomInput;
    @BindView(R.id.tv_room_input_close)
    TextView tvRoomInputClose;
    @BindView(R.id.edit_new_text)
    EditText roomMessageEdit;
    @BindView(R.id.emotion_new_button)
    ImageView emotionButton;
    @BindView(R.id.emotion_new_layout)
    RelativeLayout emotionNewLayout;


    private MediaController mMediaController;
    private PLVideoTextureView mVideoView;
    private Toast mToast = null;
    private String mVideoPath = null;
    private int mRotation = 0;
    private int mDisplayAspectRatio = PLVideoTextureView.ASPECT_RATIO_FIT_PARENT; //default
    private View mLoadingView;
    private View mCoverView = null;
    private boolean mIsActivityPaused = true;
    private int mIsLiveStreaming = 1;
    private Context context;
    RoomMain roomMain = new RoomMain(this);
    private int roomId;
    private EmotionInputDetector mDetector;
    private RtmpUrlPresenterImpl presenter;

    private void setOptions(int codecType) {
        AVOptions options = new AVOptions();

        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        // Some optimization with buffering mechanism when be set to 1
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, mIsLiveStreaming);
        if (mIsLiveStreaming == 1) {
            options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        }

        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codecType);

        // whether start play automatically after prepared, default value is 1
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);

        mVideoView.setAVOptions(options);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_video_texture, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //设置表情适配器
        mDetector = EmotionInputDetector.with(getActivity())
                .setEmotionView(emotionNewLayout)
                .bindToContent(llRoomInput)
                .bindToEditText(roomMessageEdit)
                .bindToEmotionButton(emotionButton)
                .build();
        setUpEmotionViewPager();
        mVideoView = (PLVideoTextureView) getView().findViewById(R.id.VideoView);
        mLoadingView = getView().findViewById(R.id.LoadingView);
        mVideoView.setBufferingIndicator(mLoadingView);
        mLoadingView.setVisibility(View.VISIBLE);
        mCoverView = (ImageView) getView().findViewById(R.id.CoverView);
        mVideoView.setCoverView(mCoverView);


        // If you want to fix display orientation such as landscape, you can use the code show as follow
        //
        // if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
        //     mVideoView.setPreviewOrientation(0);
        // }
        // else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
        //     mVideoView.setPreviewOrientation(270);
        // }

        mIsLiveStreaming = getActivity().getIntent().getIntExtra("liveStreaming", 1);

        // 1 -> hw codec enable, 0 -> disable [recommended]
        int codec = getActivity().getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);
        setOptions(codec);

        // You can mirror the display
        // mVideoView.setMirror(true);

        // You can also use a custom `MediaController` widget
        mMediaController = new MediaController(getActivity(), false, mIsLiveStreaming == 1);
//        mVideoView.setMediaController(mMediaController);

        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
//        mVideoView.setVideoPath(mVideoPath);
//        mVideoView.start();
        presenter = new RtmpUrlPresenterImpl(this, "10088", "88888");
        presenter.getRtmpUrl();
    }

    @Subscriber(tag = "room_url")
    private void getRoomUrl(String url) {
        mVideoPath = url;
        KLog.e(mVideoPath);
        mVideoView.pause();
        mCoverView.setVisibility(View.VISIBLE);
        sendReconnectMessage();
    }

    @Override
    public void onPause() {
        super.onPause();
        mToast = null;
        mVideoView.pause();
        mIsActivityPaused = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsActivityPaused = false;
        mVideoView.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
        EventBus.getDefault().unregister(this);
    }


    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            boolean isNeedReconnect = false;
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    showToastTips("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    showToastTips("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    showToastTips("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    showToastTips("Connection timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    showToastTips("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    showToastTips("Stream disconnected !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    showToastTips("Network IO Error !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    showToastTips("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    showToastTips("Prepare timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    showToastTips("Read frame timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_HW_DECODE_FAILURE:
                    setOptions(AVOptions.MEDIA_CODEC_SW_DECODE);
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                default:
                    showToastTips("unknown error !");
                    break;
            }
            if (isNeedReconnect) {
                sendReconnectMessage();
            } else {
//                finish();
            }
            // Return true means the error has been handled
            // If return false, then `onCompletion` will be called
            return true;
        }
    };

    private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer plMediaPlayer) {
            showToastTips("Play Completed !");
//            finish();
        }
    };

    private void showToastTips(final String tips) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(getActivity(), tips, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    private void sendReconnectMessage() {
        mLoadingView.setVisibility(View.VISIBLE);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_ID_RECONNECTING), 500);
    }

    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != MESSAGE_ID_RECONNECTING) {
                return;
            }
            if (mIsActivityPaused) {
//                finish();
                return;
            }
            if (!NetUtils.isNetworkAvailable(getActivity())) {
                sendReconnectMessage();
                return;
            }
            mVideoView.setVideoPath(mVideoPath);
            mVideoView.start();
        }
    };
    private InputMethodManager imm;

    @OnClick({R.id.ib_change_orientation, R.id.ib_change_screen, R.id.iv_room_add_favorite,
            R.id.iv_room_gift, R.id.iv_room_share, R.id.iv_room_exit, R.id.iv_room_chat,
            R.id.tv_room_input_close, R.id.room_new_chat_send})
    public void onViewClicked(View view) {
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {
            case R.id.ib_change_orientation:
                mRotation = (mRotation + 90) % 360;
                mVideoView.setDisplayOrientation(mRotation);
                break;
            case R.id.ib_change_screen:
                mDisplayAspectRatio = (mDisplayAspectRatio + 1) % 5;
                mVideoView.setDisplayAspectRatio(mDisplayAspectRatio);
                switch (mVideoView.getDisplayAspectRatio()) {
                    case PLVideoTextureView.ASPECT_RATIO_ORIGIN:
                        showToastTips("Origin mode");
                        break;
                    case PLVideoTextureView.ASPECT_RATIO_FIT_PARENT:
                        showToastTips("Fit parent !");
                        break;
                    case PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT:
                        showToastTips("Paved parent !");
                        break;
                    case PLVideoTextureView.ASPECT_RATIO_16_9:
                        showToastTips("16 : 9 !");
                        break;
                    case PLVideoTextureView.ASPECT_RATIO_4_3:
                        showToastTips("4 : 3 !");
                        break;
                    default:
                        break;
                }
                break;
            case R.id.iv_room_gift:
                showWindow();
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(ivRoomGift);
                }
                break;
            case R.id.iv_room_share:
                doShareAction();
                break;
            case R.id.iv_room_exit:
                getActivity().finish();
                break;
            case R.id.iv_room_chat:
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
            case R.id.lv_room_message:
                break;
            case R.id.tv_room_input_close:
                rllRoomInput.setVisibility(View.GONE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(roomMessageEdit.getWindowToken(), 0);
                }
                break;
            case R.id.room_new_chat_send:
//                roomMain.getRoom().getChannel().sendChatMsg(0, (byte) 0x00, (byte) 0x00, "德玛西亚", "123", 0);
                rllRoomInput.setVisibility(View.GONE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(roomMessageEdit.getWindowToken(), 0);
                }
                break;
            //收藏房间
            case R.id.iv_room_add_favorite:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        roomMain.getRoom().getChannel().followRoom(roomId, Integer.parseInt(StartUtil.getUserId(context)));
                    }
                }).start();
                Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private PopupWindow pop_share;

    /**
     * 处理分享弹窗
     */
    private void doShareAction() {
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.pop_share, null);
        pop_share = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        pop_share.showAtLocation(tvRoomAnchorName, Gravity.TOP, 0, 0);
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

    private EditText giftToUser;
    private PopupWindow popupWindow;
    private List<GiftEntity> gifts = new ArrayList<>();

    //礼物的悬浮框
    private void showWindow() {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_gift_grid, null);
        GridView gridView = (GridView) view.findViewById(R.id.room_gift_list);
        Button giftSendBtn = (Button) view.findViewById(R.id.gift_send_btn);
        final TextView giftName = (TextView) view.findViewById(R.id.gift_name_txt);
        final EditText giftCount = (EditText) view.findViewById(R.id.gift_count);
//        final EditText
        giftToUser = (EditText) view.findViewById(R.id.gift_to_user);
        popupWindow = new PopupWindow(view);
        popupWindow.setFocusable(true);
        gifts.clear();
        gifts.addAll(GiftUtil.getGifts());
        GiftGridViewAdapter giftAdapter = new GiftGridViewAdapter(gifts, getActivity());
        gridView.setAdapter(giftAdapter);
        //选择礼物
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("123",gifts.get(position)+"------------>");
//                giftId = gifts.get(position).getGiftId();
                String name = gifts.get(position).getGiftName();
                giftName.setText(name + "");
//                popupWindow.dismiss();
            }
        });
//        if (micUsers != null) {
//            for (int i = 0; i < micUsers.size(); i++) {
//                if (micUsers.get(i).getMicindex() == micFlag) {
//                    giftToUser.setText(micUsers.get(i).getUseralias() + "(" + micUsers.get(i).getUserid() + ")");
////                            Log.d("123","toid---"+toid+"toName"+toName);
//                }
//            }
//        }
        //发送礼物
        giftSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toid = -1;
//                toName = "";
////                if (giftToUser.getText().toString().equals("1")){
//                for (int i = 0; i < micUsers.size(); i++) {
//                    if (micUsers.get(i).getMicindex() == micFlag) {
//                        toid = micUsers.get(i).getUserid();
//                        toName = micUsers.get(i).getUseralias();
////                            Log.d("123","toid---"+toid+"toName"+toName);
//                    }
//                }
//                }else if (giftToUser.getText().toString().equals("2")){
//                    for (int i = 0; i < micUsers.size(); i++) {
//                        if (micUsers.get(i).getMicindex()==1){
//                            toid = micUsers.get(i).getUserid();
//                            toName = micUsers.get(i).getUseralias();
////                            Log.d("123","toid---"+toid+"toName"+toName);
//                        }
//                    }
//                }else if (giftToUser.getText().toString().equals("3")){
//                    for (int i = 0; i < micUsers.size(); i++) {
//                        if (micUsers.get(i).getMicindex()==2){
//                            toid = micUsers.get(i).getUserid();
//                            toName = micUsers.get(i).getUseralias();
////                            Log.d("123","toid---"+toid+"toName"+toName);
//                        }
//                    }
//                }else
////                        (giftToUser.getText().toString()!="1" &&giftToUser.getText().toString()!="2"&&giftToUser.getText().toString()!="3")
//                {
//                    Toast.makeText(RoomNewActivity.this, "赠送麦序错误,请重新选择", Toast.LENGTH_SHORT).show();
//                    toid = -1;
//                    toName = "";
//                }
                final int count = Integer.parseInt(giftCount.getText().toString());
//                Log.d("123", "toid--" + toid + "---giftId---" + giftId + "---count---" + count + "---toName---" + toName);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        roomMain.getRoom().getChannel().sendGiftRecord(toid, giftId, count, toName, StartUtil.getUserName(RoomActivity.this));
                    }
                }).start();

                giftName.setText("送给");
                popupWindow.dismiss();
//                sendControl.setVisibility(View.VISIBLE);
            }
        });
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);
//        popupWindow.showAsDropDown(giftImage);
//        popupWindow.showAtLocation(roomInputLinear,Gravity.BOTTOM,0,0);
        WindowManager wm = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        popupWindow.setHeight(height / 2);
        popupWindow.setOutsideTouchable(true);
    }

    /**
     * 表情页面
     */

    private void setUpEmotionViewPager() {
        final String[] titles = new String[]{"经典", "vip"};
        EmotionAdapter mViewPagerAdapter = new EmotionAdapter(getActivity().getSupportFragmentManager(), titles);
        final ViewPager mViewPager = (ViewPager) getActivity().findViewById(R.id.new_pager);
//        if (mViewPager != null) {
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setCurrentItem(0);
//        }
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) getView().findViewById(R.id.sliding_new_tabs);
        slidingTabLayout.setCustomTabView(R.layout.widget_tab_indicator, R.id.text);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(mViewPager);

        GlobalOnItemClickManager globalOnItemClickListener = GlobalOnItemClickManager.getInstance();
        globalOnItemClickListener.attachToEditText((EditText) getActivity().findViewById(R.id.edit_new_text));
    }

    @Override
    public void onMic(String ip, int port, int rand, int uid) {

    }

    @Override
    public void success(RtmpUrlEntity entity) {
        if (entity != null) {
            mVideoPath = entity.getRTMPPlayURL();
            KLog.e(mVideoPath);
            mVideoView.setVideoPath(mVideoPath);
            mVideoView.start();
        }

    }

    @Override
    public void faided() {

    }
}
