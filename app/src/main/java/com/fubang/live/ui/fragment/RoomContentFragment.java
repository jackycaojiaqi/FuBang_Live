package com.fubang.live.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.EmotionAdapter;
import com.fubang.live.adapter.GiftGridViewAdapter;
import com.fubang.live.adapter.RoomAudienceAdapter;
import com.fubang.live.adapter.RoomChatAdapter;
import com.fubang.live.adapter.RoomGiftAdapter;
import com.fubang.live.base.BaseFragment;
import com.fubang.live.entities.GiftEntity;
import com.fubang.live.entities.RtmpUrlEntity;
import com.fubang.live.presenter.impl.RtmpUrlPresenterImpl;
import com.fubang.live.ui.LiveDoneActivity;
import com.fubang.live.ui.PayActivity;
import com.fubang.live.ui.RoomActivity;
import com.fubang.live.ui.UserInfoPageActivity;
import com.fubang.live.util.ConfigUtils;
import com.fubang.live.util.FBImage;
import com.fubang.live.util.GiftUtil;
import com.fubang.live.util.GlobalOnItemClickManager;
import com.fubang.live.util.NetUtils;
import com.fubang.live.util.ScreenUtils;
import com.fubang.live.util.ShareUtil;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.StringUtil;
import com.fubang.live.util.ToastUtil;
import com.fubang.live.view.RtmpUrlView;
import com.fubang.live.widget.DivergeView;
import com.fubang.live.widget.MediaController;
import com.fubang.live.widget.SlidingTab.EmotionInputDetector;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.sample.room.MicNotify;
import com.sample.room.RoomMain;
import com.socks.library.KLog;
import com.xlg.android.protocol.BigGiftRecord;
import com.xlg.android.protocol.JoinRoomResponse;
import com.xlg.android.protocol.RoomChatMsg;
import com.xlg.android.protocol.RoomKickoutUserInfo;
import com.xlg.android.protocol.RoomUserInfoNew;

import org.dync.giftlibrary.widget.GiftControl;
import org.dync.giftlibrary.widget.GiftFrameLayout;
import org.dync.giftlibrary.widget.GiftModel;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import static android.widget.LinearLayout.HORIZONTAL;
import static com.fubang.live.ui.RoomActivity.is_emoticon_show;

public class RoomContentFragment extends BaseFragment implements MicNotify, RtmpUrlView {
    private static final int MESSAGE_ID_RECONNECTING = 0x01;
    @BindView(R.id.content)
    RelativeLayout viewContent;
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
    @BindView(R.id.lv_room_gift)
    ListView lvRoomGift;
    @BindView(R.id.rv_room_audience)
    RecyclerView rvRoomAudience;
    //============================
    @BindView(R.id.rll_content_view)
    RelativeLayout rllContentView;
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
    @BindView(R.id.divergeView)
    com.fubang.live.widget.DivergeView DivergeView;
    @BindView(R.id.iv_room_content_clear)
    ImageView ivRoomContentClear;

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
    private EmotionInputDetector mDetector;
    private RtmpUrlPresenterImpl presenter;
    private String roomIp, ip, roomPwd;
    private int roomId, port;
    private List<RoomUserInfoNew> list_audience = new ArrayList<>();
    private List<RoomUserInfoNew> list_audience_top = new ArrayList<>();
    private BaseQuickAdapter roomUserAdapter;
    private PowerManager.WakeLock mWakeLock;
    private CustomPopWindow pop_info;
    private GiftFrameLayout giftFrameLayout1;
    private GiftFrameLayout giftFrameLayout2;
    private GiftControl giftControl;

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
        roomPwd = getArguments().getString(AppConstant.ROOMPWD);
        roomIp = getArguments().getString(AppConstant.ROOMIP);
        roomId = Integer.parseInt(getArguments().getString(AppConstant.ROOMID));
        String[] Ips = roomIp.split(";");
        String[] ports = Ips[0].split(":");
        ip = ports[0];
        port = Integer.parseInt(ports[1]);
        KLog.e(roomId + " " + ip + " " + port + " " + roomPwd);
        //连接房间
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomMain.Start(roomId, Integer.parseInt(StartUtil.getUserId(getActivity())), StartUtil.getUserPwd(getActivity()), ip, port, roomPwd);
            }
        }).start();
//        //设置表情适配器
        mDetector = EmotionInputDetector.with(getActivity())
                .setEmotionView(emotionNewLayout)
                .bindToContent(emotionNewLayout)
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
        presenter = new RtmpUrlPresenterImpl(this, String.valueOf(roomId), String.valueOf(roomId));
        presenter.getRtmpUrl();
        //设置listadapter
        adapter_message = new RoomChatAdapter(list_msg, context);
        lvRoomMessage.setAdapter(adapter_message);
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
                View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_user_info, null);
                //处理popWindow 显示内容
                handleLogic(contentView, list_audience.get(position));
                //创建并显示popWindow
                pop_info = new CustomPopWindow.PopupWindowBuilder(getActivity())
                        .setView(contentView)
                        .setOutsideTouchable(false)//是否PopupWindow 以外触摸dissmiss
                        .size(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.Dp2Px(context, 620))//显示大小
                        .create()
                        .showAtLocation(rvRoomAudience, Gravity.CENTER, 0, 0);
            }
        });
        rvRoomAudience.setAdapter(roomUserAdapter);
        //爱心赛贝尔曲线
        initDivergeView();
        //屏幕常亮
        PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
        //听众列表和viewpager滑动冲突
        rvRoomAudience.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RoomActivity.dvpRoom.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        //礼物连击
        giftFrameLayout1 = (GiftFrameLayout) getView().findViewById(R.id.gift_layout1);
        giftFrameLayout2 = (GiftFrameLayout) getView().findViewById(R.id.gift_layout2);
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
                                if (roomMain.getRoom().isOK())
                                roomMain.getRoom().getChannel().followAnchor(roomId, Integer.parseInt(StartUtil.getUserId(context)));
                            }
                        }).start();
                        ToastUtil.show(context, R.string.add_fav_success);
                        break;
                    case R.id.tv_pop_goto_user_info_page:
                        Intent intent = new Intent(context, UserInfoPageActivity.class);
                        intent.putExtra(AppConstant.ROOMID, String.valueOf(roomUserInfo.getUserid()));
                        intent.putExtra(AppConstant.ROOMIP, roomIp);
                        intent.putExtra(AppConstant.ROOMPWD, roomPwd);
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
        FBImage.Create(getActivity(), AppConstant.BASE_IMG_URL + roomUserInfo.getCphoto())
                .into(((ImageView) contentView.findViewById(R.id.iv_pop_user_pic)));//头像
    }

    private ArrayList<Bitmap> mList;

    private void initDivergeView() {
        mList = new ArrayList<>();
        mList.add(((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.ic_praise_sm5, null)).getBitmap());
        DivergeView.post(new Runnable() {
            @Override
            public void run() {
                DivergeView.setEndPoint(new PointF(DivergeView.getMeasuredWidth() / 2, 0));
                DivergeView.setDivergeViewProvider(new Provider());
            }
        });
        DivergeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handle.sendEmptyMessage(SEND_LIKE_ANIM);
            }
        });

    }

    private final int SEND_LIKE_ANIM = 12;
    Handler handle = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SEND_LIKE_ANIM:
                    DivergeView.startDiverges(0);
                    break;
            }
        }
    };

    class Provider implements DivergeView.DivergeViewProvider {

        @Override
        public Bitmap getBitmap(Object obj) {
            return mList == null ? null : mList.get((int) obj);
        }
    }

    private long nk_num;//用户金币总量


    //充值金币成功后，
    @Subscriber(tag = "onPaySuccess")
    private void onPaySuccess(long nk) {
        nk_num = nk;
        if (tv_nk_num != null) {
            tv_nk_num.setText("金币余额 " + nk_num);
        }
    }

    //用户第一次加入房间，获取金币数量
    @Subscriber(tag = "JoinRoomResponse")
    private void JoinRoomResponse(JoinRoomResponse userinfo) {
        nk_num = userinfo.getNk();
    }

    //在用户信息界面发送的关注指令，在房间中操作
    @Subscriber(tag = "add_fav")
    private void addFav(final int user_id) {
        //加入收藏操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                KLog.e("addFav");
                if (roomMain.getRoom().isOK())
                roomMain.getRoom().getChannel().followAnchor(user_id, Integer.parseInt(StartUtil.getUserId(context)));
            }
        }).start();
    }

    @Subscriber(tag = "room_url")
    private void getRoomUrl(String url) {
        mVideoPath = url;
        KLog.e(mVideoPath);
        mVideoView.pause();
        mCoverView.setVisibility(View.VISIBLE);
        sendReconnectMessage();
    }

    private List<RoomChatMsg> list_msg = new ArrayList<>();
    private List<BigGiftRecord> list_gift = new ArrayList<>();
    private RoomChatAdapter adapter_message;
    private RoomGiftAdapter adapter_gift;

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
                        if (!StringUtil.isEmptyandnull(roomUserInfoNew.getNb())) {
                            int level = (Integer.parseInt(roomUserInfoNew.getNb()) / 100);
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
                        if (!StringUtil.isEmptyandnull(userInfoAnchor.getNb())) {
                            int level = (Integer.parseInt(userInfoAnchor.getNb()) / 100);
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
                adapter_message.notifyDataSetChanged();
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

    //接收帐号踢出房间信息 ，提示重新登录
    @Subscriber(tag = "RoomKickoutUserInfo")
    public void KickOutRoom(RoomKickoutUserInfo obj) {
        KLog.e(obj.getReasonid());
        if (obj.getReasonid() == 522) {
//            ToastUtil.show(getActivity().getApplicationContext(), R.string.kickout);
//            getActivity().finish();
        } else if (obj.getReasonid() == 701) {
//            ToastUtil.show(getActivity().getApplicationContext(), R.string.in_room_time_out);
//            getActivity().finish();、
        }
    }

    private RoomUserInfoNew userInfoAnchor;

    //接收主播信息
    @Subscriber(tag = "onMicUser")
    public void AnchorInfo(RoomUserInfoNew obj) {
        userInfoAnchor = obj;
        KLog.e(obj.getUserid());
//        tvRoomAnchorName.setText(obj.getUseralias());//主播名字
        tvAnchorId.setText(obj.getUserid() + " ");//主播ID
//        if (!StringUtil.isEmptyandnull(obj.getNb())) {
//            if (Double.parseDouble(obj.getNb()) > 999999999) {//需要转成小数点
//                KLog.e(obj.getNb());
//                float nk = Float.parseFloat(obj.getNb()) / 1000000000;
//                BigDecimal b = new BigDecimal(nk);
//                float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
//                tvAnchorKbiNum.setText(f1 + "亿");//主播K币
//            } else if (Double.parseDouble(obj.getNb()) > 9999999) {
//                float nk = Float.parseFloat(obj.getNb()) / 10000000;
//                BigDecimal b = new BigDecimal(nk);
//                float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
//                tvAnchorKbiNum.setText(f1 + "百万");//主播K币
//            } else {
//                tvAnchorKbiNum.setText(obj.getNb() + "");//主播K币
//            }
//        }
        if (!StringUtil.isEmptyandnull(obj.getCphoto())) {
            FBImage.Create(context, AppConstant.BASE_IMG_URL + obj.getCphoto()).into(ivRoomAnchorSmallPic);
        }
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
        tvRoomAnchorOnlineNum.setText(list_audience.size() + " ");//房间观众数量
        //===================首次进入房间，聊天列表会有文字提示
        RoomChatMsg msg = new RoomChatMsg();
        msg.setType(1);
        list_msg.clear();
        list_msg.add(msg);
        adapter_message.notifyDataSetChanged();
        lvRoomMessage.setSelection(lvRoomMessage.getCount() - 1);
        setAnimaAlpha(lvRoomMessage);
        //本人已经关注 则取消关注按钮
        for (RoomUserInfoNew roomUserInfoNew : list_audience) {
            if (roomUserInfoNew.getUserid() == Integer.parseInt(StartUtil.getUserId(context))) {
                if (roomUserInfoNew.getMyfavorite() == 0) {
                    ivRoomAddFavorite.setVisibility(View.VISIBLE);
                } else if (roomUserInfoNew.getMyfavorite() == 1) {
                    ivRoomAddFavorite.setVisibility(View.GONE);
                }
            }
        }

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
        tvRoomAnchorOnlineNum.setText(list_audience.size() + " ");//房间观众数量
    }

    //用户加入房间回调
    @Subscriber(tag = "onRoomUserNotify")
    public void onRoomUserNotify(RoomUserInfoNew obj) {
        list_audience.add(obj);
        //先排序
        Collections.sort(list_audience, new Comparator<RoomUserInfoNew>() {
            @Override
            public int compare(RoomUserInfoNew o1, RoomUserInfoNew o2) {
                return Integer.parseInt(o1.getExpend()) > Integer.parseInt(o2.getExpend()) ? -1 : 1;
            }
        });
        //取前20
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
        tvRoomAnchorOnlineNum.setText(list_audience.size() + " ");//房间观众数量

        //============================等级大于30进入房间聊天列表会有通知
        RoomChatMsg msg = new RoomChatMsg();
        if (!StringUtil.isEmptyandnull(obj.getNb())) {
            int level = (Integer.parseInt(obj.getNb()) / 100);
            if (level >= 30) {
                msg.setType(2);
                msg.setSrcalias(obj.getAlias());
                list_msg.add(msg);
                adapter_message.notifyDataSetChanged();
                lvRoomMessage.setSelection(lvRoomMessage.getCount() - 1);
                setAnimaAlpha(lvRoomMessage);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mToast = null;
        mWakeLock.release();
        mVideoView.pause();
        mIsActivityPaused = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mWakeLock.acquire();
        mIsActivityPaused = false;
        mVideoView.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (roomMain.getRoom() != null) {
                    roomMain.getRoom().getChannel().kickOutRoom(Integer.parseInt(StartUtil.getUserId(context)));//将自己提出房间
                    roomMain.getRoom().getChannel().Close();
                }
            }
        }).start();
        //销毁动画
        if (giftControl != null) {
            giftControl.cleanAll();
        }
        handle.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);
    }


    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            boolean isNeedReconnect = false;
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    KLog.e("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    KLog.e("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    KLog.e("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    KLog.e("Connection timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    KLog.e("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    KLog.e("Stream disconnected !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    KLog.e("Network IO Error !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    KLog.e("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    KLog.e("Prepare timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    KLog.e("Read frame timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_HW_DECODE_FAILURE:
                    setOptions(AVOptions.MEDIA_CODEC_SW_DECODE);
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                default:
                    KLog.e("unknown error !");
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
            KLog.e("Play Completed !");
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
    private boolean is_show_content_view = true;
    private MaterialDialog dialog;

    @OnClick({R.id.ib_change_orientation, R.id.ib_change_screen, R.id.iv_room_add_favorite,
            R.id.iv_room_gift, R.id.iv_room_share, R.id.iv_room_exit, R.id.iv_room_chat
            , R.id.room_new_chat_send, R.id.tv_room_input_close, R.id.iv_room_content_clear
            , R.id.iv_room_anchor_small_pic})
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
                        KLog.e("Origin mode");
                        break;
                    case PLVideoTextureView.ASPECT_RATIO_FIT_PARENT:
                        KLog.e("Fit parent !");
                        break;
                    case PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT:
                        KLog.e("Paved parent !");
                        break;
                    case PLVideoTextureView.ASPECT_RATIO_16_9:
                        KLog.e("16 : 9 !");
                        break;
                    case PLVideoTextureView.ASPECT_RATIO_4_3:
                        KLog.e("4 : 3 !");
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
                    int windowPos[] = ConfigUtils.calculatePopWindowPos(ivRoomExit, view_pop_gift);
                    popupWindow.showAtLocation(viewContent, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
                }
                break;
            case R.id.iv_room_share:
                doShareAction();
                break;
            case R.id.tv_room_input_close:
                rllRoomInput.setVisibility(View.GONE);
                break;
            case R.id.iv_room_exit:
                MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                        .title(R.string.sure_to_quit_live)
                        .positiveText(R.string.sure)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                getActivity().finish();
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
            case R.id.room_new_chat_send:
                final String msg = roomMessageEdit.getText().toString();
                if (!StringUtil.isEmptyandnull(roomMessageEdit.getText().toString())) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (roomMain.getRoom().isOK())
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
            //收藏房间
            case R.id.iv_room_add_favorite:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (roomMain.getRoom().isOK())
                        roomMain.getRoom().getChannel().followAnchor(roomId, Integer.parseInt(StartUtil.getUserId(context)));
                    }
                }).start();
                ivRoomAddFavorite.setVisibility(View.GONE);
                Toast.makeText(context, R.string.add_fav_success, Toast.LENGTH_SHORT).show();
                break;
            //清除房间的页面
            case R.id.iv_room_content_clear:
                is_show_content_view = !is_show_content_view;
                if (is_show_content_view) {
                    ivRoomContentClear.setImageResource(R.drawable.ic_room_content_show);
                    rllContentView.setVisibility(View.VISIBLE);
                } else {
                    ivRoomContentClear.setImageResource(R.drawable.ic_room_centent_clear);
                    rllContentView.setVisibility(View.GONE);
                }
                break;
            //点击主播头像显示弹窗
            case R.id.iv_room_anchor_small_pic:
                if (userInfoAnchor != null) {
                    View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_user_info, null);
                    //处理popWindow 显示内容
                    handleLogic(contentView, userInfoAnchor);
                    //创建并显示popWindow
                    pop_info = new CustomPopWindow.PopupWindowBuilder(getActivity())
                            .setView(contentView)
                            .setOutsideTouchable(false)//是否PopupWindow 以外触摸dissmiss
                            .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                            .setBgDarkAlpha(0.5f) // 控制亮度
                            .size(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.Dp2Px(context, 620))//显示大小
                            .create()
                            .showAtLocation(rvRoomAudience, Gravity.CENTER, 0, 0);
                }
                break;
        }
    }

    private PopupWindow pop_share;

    /**
     * 处理分享弹窗
     */
    private void doShareAction() {
        final View popupView = getActivity().getLayoutInflater().inflate(R.layout.pop_share, null);
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

    private TextView giftToUser;
    private PopupWindow popupWindow;
    private List<GiftEntity> gifts = new ArrayList<>();
    private int giftId;
    private View view_pop_gift;
    private int gift_position = -1;
    private TextView tv_nk_num;

    //礼物的悬浮框
    private void showWindow() {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view_pop_gift = layoutInflater.inflate(R.layout.pop_gift_grid, null);
        GridView gridView = (GridView) view_pop_gift.findViewById(R.id.room_gift_list);
        Button giftSendBtn = (Button) view_pop_gift.findViewById(R.id.gift_send_btn);
        final TextView giftName = (TextView) view_pop_gift.findViewById(R.id.gift_name_txt);
        LinearLayout goto_pay = (LinearLayout) view_pop_gift.findViewById(R.id.ll_room_goto_pay);
        tv_nk_num = (TextView) view_pop_gift.findViewById(R.id.tv_room_nk_num);
        final EditText giftCount = (EditText) view_pop_gift.findViewById(R.id.gift_count);
//        final EditText
        giftToUser = (TextView) view_pop_gift.findViewById(R.id.gift_to_user);
        popupWindow = new PopupWindow(view_pop_gift);
        popupWindow.setFocusable(true);
        gifts.clear();
        gifts.addAll(GiftUtil.getGifts());
        GiftGridViewAdapter giftAdapter = new GiftGridViewAdapter(gifts, getActivity());
        gridView.setAdapter(giftAdapter);
        //选择礼物
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                giftId = gifts.get(position).getGiftId();
                String name = gifts.get(position).getGiftName();
                giftName.setText(name + " 送给");
                gift_position = position;
            }
        });
        //发送礼物
        giftSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (giftId == -1) {
                    ToastUtil.show(context, R.string.please_select_a_gift);
                    return;
                }
                final int count = Integer.parseInt(giftCount.getText().toString());
                if (nk_num < (gifts.get(gift_position).getPrice() * count)) {
                    ToastUtil.show(context, R.string.no_more_nk);
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (roomMain.getRoom().isOK())
                            roomMain.getRoom().getChannel().sendGiftRecord(Integer.parseInt(StartUtil.getUserId(context)), roomId, giftId, count, userInfoAnchor.getAlias(), StartUtil.getUserName(context));
                    }
                }).start();
                giftName.setText("送给");
                popupWindow.dismiss();
//                sendControl.setVisibility(View.VISIBLE);
            }
        });
        goto_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PayActivity.class);
                intent.putExtra("nk_num", nk_num);
                startActivity(intent);
            }
        });
        tv_nk_num.setText("余额:" + nk_num);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0x00ffffff);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                giftId = -1;
            }
        });
    }

    /**
     * 表情页面
     */

    private void setUpEmotionViewPager() {
        final String[] titles = new String[]{"经典"};
        EmotionAdapter mViewPagerAdapter = new EmotionAdapter(getActivity().getSupportFragmentManager(), titles);
        final ViewPager mViewPager = (ViewPager) getActivity().findViewById(R.id.new_pager);
//        if (mViewPager != null) {
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setCurrentItem(0);
//        }
//        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) getView().findViewById(R.id.sliding_new_tabs);
//        slidingTabLayout.setCustomTabView(R.layout.widget_tab_indicator, R.id.text);
//        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
//        slidingTabLayout.setDistributeEvenly(true);
//        slidingTabLayout.setViewPager(mViewPager);

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

}
