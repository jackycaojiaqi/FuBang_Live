package com.sample.room;


import android.util.Log;

import com.socks.library.KLog;
import com.xlg.android.RoomChannel;
import com.xlg.android.RoomHandler;
import com.xlg.android.protocol.ActWaitMicUserInfo;
import com.xlg.android.protocol.AddClosedFriendInfo;
import com.xlg.android.protocol.AuthorityRejected;
import com.xlg.android.protocol.BigGiftRecord;
import com.xlg.android.protocol.ChestBonusAmount;
import com.xlg.android.protocol.DecoColor;
import com.xlg.android.protocol.DevState;
import com.xlg.android.protocol.DigTreasureResponse;
import com.xlg.android.protocol.ForbidUserChat;
import com.xlg.android.protocol.GiveColorbarInfo;
import com.xlg.android.protocol.GrabRedPagerRequest;
import com.xlg.android.protocol.JoinRoomResponse;
import com.xlg.android.protocol.LocateIPResp;
import com.xlg.android.protocol.LotteryGiftNotice;
import com.xlg.android.protocol.LotteryNotice;
import com.xlg.android.protocol.MicState;
import com.xlg.android.protocol.OpenChestInfo;
import com.xlg.android.protocol.PreTradeGift;
import com.xlg.android.protocol.RedPagerRequest;
import com.xlg.android.protocol.RoomBKGround;
import com.xlg.android.protocol.RoomBaseInfo;
import com.xlg.android.protocol.RoomChatMsg;
import com.xlg.android.protocol.RoomKickoutUserInfo;
import com.xlg.android.protocol.RoomManagerInfo;
import com.xlg.android.protocol.RoomMediaInfo;
import com.xlg.android.protocol.RoomNotice;
import com.xlg.android.protocol.RoomState;
import com.xlg.android.protocol.RoomUserInfo;
import com.xlg.android.protocol.SendSeal;
import com.xlg.android.protocol.SetUserProfileResp;
import com.xlg.android.protocol.SetUserPwdResp;
import com.xlg.android.protocol.SiegeInfo;
import com.xlg.android.protocol.SysCastNotice;
import com.xlg.android.protocol.TransMediaInfo;
import com.xlg.android.protocol.UserAddMicTimeInfo;
import com.xlg.android.protocol.UserAlias;
import com.xlg.android.protocol.UserBankDepositRespInfo;
import com.xlg.android.protocol.UserChestNumInfo;
import com.xlg.android.protocol.UserHeadPic;
import com.xlg.android.protocol.UserPayResponse;
import com.xlg.android.protocol.UserPriority;
import com.xlg.android.protocol.UseridList;
import com.xlg.android.utils.Tools;

import org.simple.eventbus.EventBus;


public class MyRoom implements RoomHandler {
    private RoomChannel channel = new RoomChannel(this);
    private boolean isConnected = false;
    private MicNotify mNotify = null;

    public String videoIP;
    public int videoPort;
    public int videoRand;
    public int videoUID;


    public final int FT_ROOMUSER_STATUS_PUBLIC_MIC = 0x1;

    public MyRoom(MicNotify notify) {
        mNotify = notify;
    }

    public RoomChannel getChannel() {
        return channel;
    }

    public boolean isOK() {
        return isConnected;
    }

    @Override
    public void onConnectSuccessed() {

        isConnected = true;
        // 连接成功
        KLog.e("onConnectSuccessed: 连接成功");
        // 加入房间
        channel.SendHello();
        // 发送连接
        channel.SendJoinRoomReq();
    }

    @Override
    public void onConnectFailed() {
        EventBus.getDefault().post(false, "ConnectFailed");
        KLog.e("onConnectFailed: 连接失败");
    }

    @Override
    public void onJoinRoomResponse(JoinRoomResponse obj) {

        EventBus.getDefault().post(obj, "JoinRoomResponse");
        KLog.e("onJoinRoomResponse: ");
        Tools.PrintObject(obj);
        String str = obj.getMediaserver();
        String ips[] = str.split(";");
        if (ips.length > 0) {
            String s[] = ips[0].split(":");
            if (s.length > 1) {
                videoIP = s[0];
                videoPort = Integer.valueOf(s[1]).intValue();
            }
        }

        videoRand = obj.getReserve1();
    }

    @Override
    public void onRoomUserNotify(RoomUserInfo obj) {

        KLog.e("onRoomUserNotify: ");
        Tools.PrintObject(obj);
        EventBus.getDefault().post(obj, "userList");
    }

    @Override
    public void onGetOpenChestInfoResponse(OpenChestInfo obj) {
        KLog.e("onGetOpenChestInfoResponse: ");
        Tools.PrintObject(obj);
    }

    @Override
    public void onOpenChestNotify(OpenChestInfo obj) {

        PrintUnknown("onOpenChestNotify: ");
    }

    @Override
    public void onChestBonusAmountNotify(ChestBonusAmount obj) {

        PrintUnknown("onChestBonusAmountNotify: ");
    }

    @Override
    public void onUserChestChangeNotify(UserChestNumInfo obj) {

        PrintUnknown("onUserChestChangeNotify: ");
    }

    @Override
    public void onJoinRoomError(int err) {

        PrintUnknown("onJoinRoomError: ");
        EventBus.getDefault().post(err, "joinRoomError");
    }

    @Override
    public void onRoomNoticeNotify(RoomNotice[] obj) {

        KLog.e("onRoomNoticeNotify: ");
        for (int i = 0; i < obj.length; i++) {
            Tools.PrintObject(obj[i]);
        }
    }

    @Override
    public void onGetRoomUserListResponse(int g1, RoomUserInfo[] obj) {

        KLog.e("onGetRoomUserListResponse: " + g1);
        EventBus.getDefault().postSticky(obj, "userList");
        for (int i = 0; i < obj.length - 1; i++) {
            Tools.PrintObject(obj[i]);
            Log.d("123", obj[i].getMicindex() + "----------micindex");
            if (0 != (obj[i].getUserstate() & FT_ROOMUSER_STATUS_PUBLIC_MIC)) {
                EventBus.getDefault().post(obj[i], "onMicUser");
                videoUID = obj[i].getUserid();
                KLog.e("===================: find mic: " + videoUID);
                mNotify.onMic(videoIP, videoPort, videoRand, videoUID);
//				}
            }
        }
    }

    private void PrintUnknown(String string) {

        KLog.e("==============================:" + string);
    }

    @Override
    public void onGetRoomMicListResponse(UseridList obj) {

        PrintUnknown("onGetRoomMicListResponse: ");
    }

    @Override
    public void onGetFlygiftListResponse(BigGiftRecord[] obj) {

        KLog.e("onGetFlygiftListResponse: ");
        for (int i = 0; i < obj.length; i++) {
            Tools.PrintObject(obj[i]);
        }
    }

    @Override
    public void onAuthorityRejected(AuthorityRejected obj) {

        PrintUnknown("onAuthorityRejected: ");
    }

    @Override
    public void onSiegeInfoNotify(SiegeInfo obj) {

        KLog.e("onSiegeInfoNotify: ");
        Tools.PrintObject(obj);
    }

    @Override
    public void onKickoutRoomUserNotify(RoomKickoutUserInfo obj) {
        PrintUnknown("onKickoutRoomUserNotify: ");
        Tools.PrintObject(obj);
        EventBus.getDefault().post(obj, "RoomKickoutUserInfo");

    }

    @Override
    public void onChatMsgNotify(RoomChatMsg txt) {

        KLog.e("onChatMsgNotify: " + txt.getContent());
        EventBus.getDefault().post(txt, "RoomChatMsg");
        //txt.getContent 报空指针
        Tools.PrintObject(txt);
        // 回应消息
//         getChannel().sendChatMsg(0, (byte)0, (byte)0, "testChat");
    }

    @Override
    public void onChatMsgError(int err) {

        PrintUnknown("onChatMsgError: ");
    }

    @Override
    public void onUserPayResponse(UserPayResponse obj) {

        PrintUnknown("onUserPayResponse: ");
        Tools.PrintObject(obj);
    }

    @Override
    public void onSendSealNotify(SendSeal obj) {

        PrintUnknown("onSendSealNotify: ");

    }

    @Override
    public void onSetMicStateNotify(MicState obj) {

        PrintUnknown("onSetMicStateNotify: ");
        Tools.PrintObject(obj);
        if (obj.getMicstate() == 1) {
            EventBus.getDefault().post(obj, "upMicState");
        }
        if (obj.getMicstate() == 0) {
            EventBus.getDefault().post(obj, "downMicState");
        }
    }

    @Override
    public void onSetDevStateNotify(DevState obj) {
        KLog.e("onSetDevStateNotify: ");
        Tools.PrintObject(obj);
    }

    @Override
    public void onSyncUserAliasNotify(UserAlias obj) {
        PrintUnknown("onSyncUserAliasNotify: ");
    }

    @Override
    public void onSyncUserHeadPicNotify(UserHeadPic obj) {
        PrintUnknown("onSyncUserHeadPicNotify: ");
    }

    @Override
    public void onSyncDecoColorNotify(DecoColor obj) {

        PrintUnknown("onSyncDecoColorNotify: ");
    }

    @Override
    public void onSetRoomBKgroupNotify(RoomBKGround obj) {

        PrintUnknown("onSetRoomBKgroupNotify: ");
    }

    @Override
    public void onSetRoomBaseInfoResponse() {

        PrintUnknown("onSetRoomBaseInfoResponse: ");
    }

    @Override
    public void onRoomBaseInfoNotify(RoomBaseInfo obj) {

        PrintUnknown("onRoomBaseInfoNotify: ");
    }

    @Override
    public void onSetRoomManagersResponse() {

        PrintUnknown("onSetRoomManagersResponse: ");
    }

    @Override
    public void onSetRoomManagersNotify(RoomManagerInfo obj) {

        PrintUnknown("onSetRoomManagersNotify: ");
    }

    @Override
    public void onRoomMediaURLNotify(RoomMediaInfo obj) {

        PrintUnknown("onRoomMediaURLNotify: ");
    }

    @Override
    public void onSetRoomRunStateNotify(RoomState obj) {

        PrintUnknown("onSetRoomRunStateNotify: ");
    }

    @Override
    public void onSetUserPriorityResponse() {

        PrintUnknown("onSetUserPriorityResponse: ");
    }

    @Override
    public void onSetUserProfileResponse(SetUserProfileResp obj) {

        PrintUnknown("onSetUserProfileResponse: ");
    }

    @Override
    public void onSetUserPwdRepsonse(SetUserPwdResp obj) {

        PrintUnknown("onSetUserPwdRepsonse: ");
    }

    @Override
    public void onSetUserPwdError() {

        PrintUnknown("onSetUserPwdError: ");
    }

    @Override
    public void onSetUserPriorityNotify(UserPriority obj) {

        PrintUnknown("onSetUserPriorityNotify: ");
    }

    @Override
    public void onTransMediaRequest(TransMediaInfo obj) {

        KLog.e("onTransMediaRequest: ");
        Tools.PrintObject(obj);
    }

    @Override
    public void onTransMediaResponse(TransMediaInfo obj) {

        PrintUnknown("onTransMediaResponse: ");
    }

    @Override
    public void onTransMediaError(TransMediaInfo obj) {

        PrintUnknown("onTransMediaError: ");
    }

    @Override
    public void onRoomUserKeepAliveResponse() {

        KLog.e("onRoomUserKeepAliveResponse: 心跳回应");
    }

    @Override
    public void onForbidUserChatNotify(ForbidUserChat obj) {

        PrintUnknown("onForbidUserChatNotify: ");
    }

    @Override
    public void onTradeGiftResponse() {

        PrintUnknown("onTradeGiftResponse: ");
    }

    @Override
    public void onTradeGiftError(int i) {//504金币不足
        PrintUnknown("onTradeGiftError: ");
        KLog.e(i);
    }

    @Override
    public void onTradeGiftNotify(BigGiftRecord obj) {

        PrintUnknown("onTradeGiftNotify: ");
        Tools.PrintObject(obj);
        EventBus.getDefault().post(obj, "BigGiftRecord");
    }

    @Override
    public void onLotteryGiftNotify(LotteryGiftNotice obj) {

        PrintUnknown("onLotteryGiftNotify: ");
    }

    @Override
    public void onLotteryNotify(LotteryNotice obj) {

        PrintUnknown("onLotteryNotify: ");
    }

    @Override
    public void onSysCastNotify(SysCastNotice obj) {

        PrintUnknown("onSysCastNotify: ");
    }

    @Override
    public void onLocateUserIPResponse(LocateIPResp obj) {

        PrintUnknown("onLocateUserIPResponse: ");
    }

    @Override
    public void onGiveColorbarNotify(GiveColorbarInfo obj) {

        PrintUnknown("onGiveColorbarNotify: ");
    }

    @Override
    public void onAddMicTimeNotify(UserAddMicTimeInfo obj) {

        PrintUnknown("onAddMicTimeNotify: ");
    }

    @Override
    public void onActWaitMicUserNotify(ActWaitMicUserInfo obj) {

        PrintUnknown("onActWaitMicUserNotify: ");
    }

    @Override
    public void onAddClosedFriendNotify(AddClosedFriendInfo obj) {

        PrintUnknown("onAddClosedFriendNotify: ");
    }

    @Override
    public void onBankDepositResponse(UserBankDepositRespInfo obj) {

        PrintUnknown("onBankDepositResponse: ");
    }

    @Override
    public void onBankDepositError() {

        PrintUnknown("onBankDepositError: ");
    }

    @Override
    public void onDoNotReachRoomServer() {

        PrintUnknown("onDoNotReachRoomServer: ");
    }

    @Override
    public void onDigTreasureResponse(DigTreasureResponse obj) {

        PrintUnknown("onDigTreasureResponse: ");
    }

    @Override
    public void onRedPagerResponse(RedPagerRequest obj) {

        PrintUnknown("onRedPagerResponse: ");
    }

    @Override
    public void onRedPagerError(int i) {

        PrintUnknown("onRedPagerError: ");
    }

    @Override
    public void onGrabRedPagerResponse(GrabRedPagerRequest obj) {

        PrintUnknown("onGrabRedPagerResponse: ");
    }

    @Override
    public void onPreTradeGiftResponse(PreTradeGift obj) {

        PrintUnknown("onPreTradeGiftResponse: ");
    }

    @Override
    public void onDisconnected() {

        isConnected = false;
    }

}
