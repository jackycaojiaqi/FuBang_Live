package com.xlg.android;

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

// 各个事件回调函数

public interface RoomHandler {
	// 连接成功
	public void onConnectSuccessed();
	// 连接失败
	public void onConnectFailed();
	// 加入房间回调
	public void onJoinRoomResponse(JoinRoomResponse obj);
	
	public void onRoomUserNotify(RoomUserInfo obj);
	
	public void onGetOpenChestInfoResponse(OpenChestInfo obj);
	
	public void onOpenChestNotify(OpenChestInfo obj);
	
	public void onChestBonusAmountNotify(ChestBonusAmount obj);
	
	public void onUserChestChangeNotify(UserChestNumInfo obj);
	
	public void onJoinRoomError(int err);
	
	public void onRoomNoticeNotify(RoomNotice[] obj);
	
	public void onGetRoomUserListResponse(int g1, RoomUserInfo[] userList);
	
	public void onGetRoomMicListResponse(UseridList obj);
	
	public void onGetFlygiftListResponse(BigGiftRecord[] obj);
	
	public void onAuthorityRejected(AuthorityRejected obj);
	
	public void onSiegeInfoNotify(SiegeInfo obj);
	
	public void onKickoutRoomUserNotify(RoomKickoutUserInfo obj);
	
	public void onChatMsgNotify(RoomChatMsg txt);
	
	public void onChatMsgError(int err);
	
	public void onUserPayResponse(UserPayResponse obj);
	
	public void onSendSealNotify(SendSeal obj);
	
	public void onSetMicStateNotify(MicState obj);
	
	public void onSetDevStateNotify(DevState obj);
	
	public void onSyncUserAliasNotify(UserAlias obj);
	
	public void onSyncUserHeadPicNotify(UserHeadPic obj);
	
	public void onSyncDecoColorNotify(DecoColor obj);
	
	public void onSetRoomBKgroupNotify(RoomBKGround obj);
	
	public void onSetRoomBaseInfoResponse();
	
	public void onRoomBaseInfoNotify(RoomBaseInfo obj);
	
	public void onSetRoomManagersResponse();
	
	public void onSetRoomManagersNotify(RoomManagerInfo obj);
	
	public void onRoomMediaURLNotify(RoomMediaInfo obj);
	
	public void onSetRoomRunStateNotify(RoomState obj);
	
	public void onSetUserPriorityResponse();
	
	public void onSetUserProfileResponse(SetUserProfileResp obj);
	
	public void onSetUserPwdRepsonse(SetUserPwdResp obj);
	
	public void onSetUserPwdError();
	
	public void onSetUserPriorityNotify(UserPriority obj);
	
	public void onTransMediaRequest(TransMediaInfo obj);
	
	public void onTransMediaResponse(TransMediaInfo obj);
	
	public void onTransMediaError(TransMediaInfo obj);
	
	// 心跳回应
	public void onRoomUserKeepAliveResponse();
	
	public void onForbidUserChatNotify(ForbidUserChat obj);
	
	public void onTradeGiftResponse();
	
	public void onTradeGiftError(int i);
	
	public void onTradeGiftNotify(BigGiftRecord obj);
	
	public void onLotteryGiftNotify(LotteryGiftNotice obj);
	
	public void onLotteryNotify(LotteryNotice obj);
	
	public void onSysCastNotify(SysCastNotice obj);
	
	public void onLocateUserIPResponse(LocateIPResp obj);
	
	public void onGiveColorbarNotify(GiveColorbarInfo obj);
	
	public void onAddMicTimeNotify(UserAddMicTimeInfo obj);
	
	public void onActWaitMicUserNotify(ActWaitMicUserInfo obj);
	
	public void onAddClosedFriendNotify(AddClosedFriendInfo obj);
	
	public void onBankDepositResponse(UserBankDepositRespInfo obj);
	
	public void onBankDepositError();
	
	public void onDoNotReachRoomServer();
	
	public void onDigTreasureResponse(DigTreasureResponse obj);
	
	public void onRedPagerResponse(RedPagerRequest obj);
	
	public void onRedPagerError(int i);
	
	public void onGrabRedPagerResponse(GrabRedPagerRequest obj);
	
	public void onPreTradeGiftResponse(PreTradeGift obj);
	
	// 连接断开
	public void onDisconnected();
}
