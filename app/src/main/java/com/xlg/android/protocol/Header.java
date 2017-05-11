package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

public class Header {
    public static final int SIZE_HEADER = 16;

    public static final int OFFSET_LENGTH = 0;
    public static final int OFFSET_VERSION = 4;
    public static final int OFFSET_RESERVE = 4;
    public static final int OFFSET_CMD1 = 5;
    public static final int OFFSET_CMD2 = 6;
    public static final int OFFSET_CMD3 = 7;
    public static final int OFFSET_TIMER = 8;

    public static final byte MessageType_mxpLogonRequest = (byte) 0x1; // 1;
    public static final byte MessageType_mxpLogonResponse = (byte) 0x2; // 2;
    public static final byte MessageType_mxpLogonError = (byte) 0x3; // 3;
    public static final byte MessageType_mxpJoinRoomRequest = (byte) 4; // 4
    public static final byte MessageType_mxpJoinRoomError = (byte) 0x5; // 5;

    public static final byte MessageType_mxpJoinRoomResponse = (byte) 0x6; // 6
    public static final byte MessageType_mxpDoNotReachRoomServer = (byte) (6 + 2); // 8
    public static final byte MessageType_mxpGetRoomUserListResponse = (byte) (6 + 4); // 10
    public static final byte MessageType_mxpRoomUserNotify = (byte) (6 + 5); // 11
    public static final byte MessageType_mxpKickoutRoomUserRequest = (byte) (6 + 6);//12
    public static final byte MessageType_mxpKickoutRoomUserNotify = (byte) (6 + 9); // 15
    public static final byte MessageType_mxpGetFlygiftListResponse = (byte) (6 + 10); // 16
    public static final byte MessageType_mxpChatMsgRequest = (byte) (6 + 11); // 17
    public static final byte MessageType_mxpChatMsgError = (byte) (6 + 12); // 18
    public static final byte MessageType_mxpChatMsgNotify = (byte) (6 + 13); // 19
    public static final byte MessageType_mxpTradeGiftRequest = (byte) (6 + 14);//20
    public static final byte MessageType_mxpTradeGiftResponse = (byte) (6 + 15); // 21
    public static final byte MessageType_mxpTradeGiftError = (byte) (6 + 16); // 22
    public static final byte MessageType_mxpTradeGiftNotify = (byte) (6 + 17); // 23
    public static final byte MessageType_mxpUserPayResponse = (byte) (6 + 19); // 25
    public static final byte MessageType_mxpSetUserProfileResponse = (byte) (6 + 24); // 30
    public static final byte MessageType_mxpSetRoomBaseInfoRequest = (byte) (32); // 32
    public static final byte MessageType_mxpSetRoomBaseInfoResponse = (byte) (6 + 27); // 33
    public static final byte MessageType_mxpSetRoomBaseInfoError = (byte) (34); // 34
    public static final byte MessageType_mxpSetRoomBaseInfoNotify = (byte) (6 + 29); // 35
    public static final byte MessageType_mxpUpdateRoomBaseInfoNotify = (byte) (6 + 30); // 36
    public static final byte MessageType_mxpSetRoomManagersResponse = (byte) (6 + 32); // 38
    public static final byte MessageType_mxpSetRoomManagersNotify = (byte) (6 + 34); // 40
    public static final byte MessageType_mxpSetRoomMediaURLNotify = (byte) (6 + 39); // 45
    public static final byte MessageType_mxpUpdateRoomMediaURLNotify = (byte) (6 + 40); // 46
    public static final byte MessageType_mxpSetRoomNoticeNotify = (byte) (6 + 44); // 50
    public static final byte MessageType_mxpUpdateRoomNoticeNotify = (byte) (6 + 45); // 51
    public static final byte MessageType_mxpSetRoomRunStateNotify = (byte) (6 + 49); // 55
    public static final byte MessageType_mxpTransMediaRequest = (byte) (6 + 50); // 56
    public static final byte MessageType_mxpTransMediaResponse = (byte) (6 + 51); // 57
    public static final byte MessageType_mxpTransMediaError = (byte) (6 + 52); // 58
    public static final byte MessageType_mxpSetMicStateRequest = (byte) (6 + 53); // 59
    public static final byte MessageType_mxpSetMicStateNotify = (byte) (6 + 56); // 62
    public static final byte MessageType_mxpSetDevStateNotify = (byte) (6 + 60); // 66
    public static final byte MessageType_mxpSyncUserAliasNotify = (byte) (6 + 64); // 70
    public static final byte MessageType_mxpSyncDecoColorNotify = (byte) (6 + 68); // 74
    public static final byte MessageType_mxpSetUserPriorityResponse = (byte) (6 + 70); // 76
    public static final byte MessageType_mxpSetUserPriorityNotify = (byte) (6 + 72); // 78
    public static final byte MessageType_mxpLocateUserIPResponse = (byte) (6 + 74); // 80
    public static final byte MessageType_mxpGetSiegeInfoResponse = (byte) (6 + 81); // 87
    public static final byte MessageType_mxpSiegeInfoNotify = (byte) (6 + 82); // 88
    public static final byte MessageType_mxpSendSealNotify = (byte) (6 + 84); // 90
    public static final byte MessageType_mxpForbidUserChatRequest = (byte) (6 + 85); // 91
    public static final byte MessageType_mxpForbidUserChatNotify = (byte) (6 + 86); // 92
    public static final byte MessageType_mxpSetRoomBKgroupNotify = (byte) (6 + 90); // 96
    public static final byte MessageType_mxpGetRoomMicListResponse = (byte) (6 + 101); // 107
    public static final byte MessageType_mxpSetUserPwdRepsonse = (byte) (6 + 105); // 111
    public static final byte MessageType_mxpSetUserPwdError = (byte) (6 + 106); //112
    public static final byte MessageType_mxpLotteryGiftNotify = (byte) (6 + 107); // 113
    public static final byte MessageType_mxpLotteryNotify = (byte) (6 + 108); // 114
    public static final byte MessageType_mxpChestBonusAmountNotify = (byte) (6 + 109); // 115
    public static final byte MessageType_mxpUserChestChangeNotify = (byte) (6 + 110); // 116
    public static final byte MessageType_mxpGetOpenChestInfoResponse = (byte) (6 + 112); // 118
    public static final byte MessageType_mxpOpenChestNotify = (byte) (6 + 114); // 120
    public static final byte MessageType_mxpGiveColorbarNotify = (byte) (6 + 116); // 122
    public static final byte MessageType_mxpAddClosedFriendNotify = (byte) (6 + 117); // 123
    public static final byte MessageType_mxpBankDepositResponse = (byte) (6 + 119); // 125
    public static final byte MessageType_mxpBankDepositError = (byte) (6 + 120); // 126
    public static final byte MessageType_mxpAddMicTimeNotify = (byte) (6 + 122); // 128
    public static final byte MessageType_mxpActWaitMicUserNotify = (byte) (6 + 124); // 130
    public static final byte MessageType_mxpRedPagerResponse = (byte) (6 + 126); // 132
    public static final byte MessageType_mxpRedPagerError = (byte) (6 + 127); // 133
    public static final byte MessageType_mxpGrabRedPagerResponse = (byte) (6 + 129); // 135
    public static final byte MessageType_mxpPreTradeGiftResponse = (byte) (6 + 131); // 137

    public static final byte MessageType_mxpSysCastNotify = (byte) 189; // 189
    public static final byte MessageType_mxpClientHelloCommand = (byte) 0xbe; // 190;
    public static final byte MessageType_mxpRoomUserKeepAliveRequest = (byte) (190 + 4); // 194
    public static final byte MessageType_mxpRoomUserKeepAliveResponse = (byte) (190 + 5); // 195

    public static final byte MessageType_mxpRegisterRequest = (byte) (200 + 6); // 206
    public static final byte MessageType_mxpSyncUserHeadPicNotify = (byte) (210 + 9); // 219
    public static final byte MessageType_mxpCollageRequest = (byte) (210 + 10);//220
    public static final byte MessageType_mxpAuthorityRejected = (byte) (210 + 12); // 222
    public static final byte MessageType_mxpDigTreasureResponse = (byte) (210 + 16);  // 226


    private int length;        //数据包总长
    private byte version = 1;    //版本号 0->旧版本  1->本文件定义版本
    //	private byte	reserve;	//保留
    private byte cmd1;        //消息类型，一级消息
    private byte cmd2;        //消息类型，二级消息
    private byte cmd3;        //保留。可以放随机数用于安全
    private long timer;        //随机种子.用于安全。
    private short st = 0;
    private byte[] arr = new byte[30];

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

//	public byte getReserve() {
//		return reserve;
//	}
//	
//	public void setReserve(byte reserve) {
//		this.reserve = reserve;
//	}

    public byte getCmd1() {
        return cmd1;
    }

    public void setCmd1(byte cmd1) {
        this.cmd1 = cmd1;
    }

    public byte getCmd2() {
        return cmd2;
    }

    public void setCmd2(byte cmd2) {
        this.cmd2 = cmd2;
    }

    public byte getCmd3() {
        return cmd3;
    }

    public void setCmd3(byte cmd3) {
        this.cmd3 = cmd3;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

}
