package com.xlg.android;

import android.util.Log;

import com.socks.library.KLog;
import com.xlg.android.protocol.ActWaitMicUserInfo;
import com.xlg.android.protocol.AddClosedFriendInfo;
import com.xlg.android.protocol.AuthorityRejected;
import com.xlg.android.protocol.BigGiftRecord;
import com.xlg.android.protocol.ChestBonusAmount;
import com.xlg.android.protocol.CollageRequest;
import com.xlg.android.protocol.DecoColor;
import com.xlg.android.protocol.DevState;
import com.xlg.android.protocol.DigTreasureResponse;
import com.xlg.android.protocol.ForbidUserChat;
import com.xlg.android.protocol.GiveColorbarInfo;
import com.xlg.android.protocol.Header;
import com.xlg.android.protocol.Hello;
import com.xlg.android.protocol.JoinRoomRequest;
import com.xlg.android.protocol.JoinRoomResponse;
import com.xlg.android.protocol.KeepLiveRepuest;
import com.xlg.android.protocol.LocateIPResp;
import com.xlg.android.protocol.LotteryGiftNotice;
import com.xlg.android.protocol.LotteryNotice;
import com.xlg.android.protocol.Message;
import com.xlg.android.protocol.MicState;
import com.xlg.android.protocol.OpenChestInfo;
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
import com.xlg.android.utils.ByteBuffer;
import com.xlg.android.utils.Tools;

public class RoomChannel implements ClientSocketHandler {
    private int mRoomID; // 房间id
    private int mUserID; // 用户ID
    private String mUserPwd; // 用户密码
    private String mRoomPwd; // 房间密码
    private int mUserState; // 用户状态

    // 客户端回调
    private RoomHandler mHandler;
    // 新的socket对像
    private ClientSocket mSocket = new ClientSocket(this);
    // 接收缓冲区
    private ByteBuffer mBuffer = new ByteBuffer();


    public RoomChannel(RoomHandler handler) {
        mHandler = handler;
    }

    // 设置信息
    public void setRoomID(int roomID) {
        mRoomID = roomID;
    }

    public void setUserID(int userID) {
        mUserID = userID;
    }

    public void setUserPwd(String pwd) {
        mUserPwd = pwd;
    }

    public void setRoomPwd(String pwd) {
        mRoomPwd = pwd;
    }

    public void setUserState(int n) {
        mUserState = n;
    }

    // 连接服务器
    public int Connect(String ip, int port) {
        return mSocket.Connect(ip, port);
    }

    // 关闭
    public void Close() {
        mSocket.Close();
    }

    @Override
    public void onConnected(int ret) {
        if (0 == ret) {
            mHandler.onConnectSuccessed();
        } else {
            mHandler.onConnectFailed();
        }
    }

    @Override
    public void onRecv(byte[] data, int size) {
        // 加入缓冲区
        mBuffer.addBytes(data, 0, size);
        // 解析数据
        Header head = new Header();
        int len;

        while (true) {
            len = Message.DecodeHeader(mBuffer, head);
            if (len <= 0) {
                break;
            }

            switch (head.getCmd1()) {
                case Header.MessageType_mxpJoinRoomResponse: {
                    JoinRoomResponse obj = new JoinRoomResponse();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onJoinRoomResponse(obj);
                }
                break;
                case Header.MessageType_mxpRoomUserNotify: {
                    RoomUserInfo obj = new RoomUserInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onRoomUserNotify(obj);
                }
                break;
                case Header.MessageType_mxpGetOpenChestInfoResponse: {
                    OpenChestInfo obj = new OpenChestInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onGetOpenChestInfoResponse(obj);
                }
                break;
                case Header.MessageType_mxpOpenChestNotify: {
                    OpenChestInfo obj = new OpenChestInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onOpenChestNotify(obj);
                }
                break;
                case Header.MessageType_mxpChestBonusAmountNotify: {
                    ChestBonusAmount obj = new ChestBonusAmount();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onChestBonusAmountNotify(obj);
                }
                break;

                case Header.MessageType_mxpUserChestChangeNotify: {
                    UserChestNumInfo obj = new UserChestNumInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onUserChestChangeNotify(obj);
                }
                break;
                case Header.MessageType_mxpJoinRoomError: {
                    ByteBuffer buf = Message.DecodeBody(mBuffer);
                    mHandler.onJoinRoomError(buf.getInt(0));
                }
                break;
                case Header.MessageType_mxpUpdateRoomNoticeNotify:
                case Header.MessageType_mxpSetRoomNoticeNotify: {
                    RoomNotice[] obj = new RoomNotice[head.getCmd3()];
                    int index = Header.SIZE_HEADER;
                    ByteBuffer item = new ByteBuffer();

                    // 解析出其中的几项
                    for (int i = 0; i < head.getCmd3(); i++) {
                        obj[i] = new RoomNotice();
                        int txtLen = mBuffer.getShort(index + 10);
                        if (txtLen < 0) {
                            txtLen = 0;
                        }
                        byte[] by = new byte[txtLen + 12];

                        mBuffer.getBytes(by, index, by.length);
                        item.clear();
                        item.addBytes(by, 0, by.length);
                        Message.UnserializeObject(item, 0, obj[i]);

                        index += by.length; // 一个包长度
                    }

                    mHandler.onRoomNoticeNotify(obj);
                }
                break;
                case Header.MessageType_mxpGetRoomUserListResponse: {
                    RoomUserInfo[] obj = new RoomUserInfo[head.getCmd3()];
                    int index = Header.SIZE_HEADER;

                    // 解析出其中的几项
                    for (int i = 0; i < head.getCmd3(); i++) {
                        obj[i] = new RoomUserInfo();

                        if (Message.UnserializeObject(mBuffer, index, obj[i])) {
                            index += Message.SizeOfObject(obj[i]); // 一个包长度
                        }
                    }

                    mHandler.onGetRoomUserListResponse(head.getCmd2(), obj);
                }
                break;
                case Header.MessageType_mxpGetRoomMicListResponse: {
                    UseridList obj = new UseridList();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onGetRoomMicListResponse(obj);
                }
                break;
                case Header.MessageType_mxpGetFlygiftListResponse: {
                    BigGiftRecord[] obj = new BigGiftRecord[head.getCmd3()];
                    for (int i = 0; i < head.getCmd3(); i++) {
                        obj[i] = new BigGiftRecord();
                        Message.UnserializeObject(mBuffer,
                                Header.SIZE_HEADER + i * Message.SizeOfObject(obj[i]),
                                obj[i]);
                    }

                    mHandler.onGetFlygiftListResponse(obj);
                }
                break;
                case Header.MessageType_mxpAuthorityRejected: {
                    AuthorityRejected obj = new AuthorityRejected();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onAuthorityRejected(obj);
                }
                break;
                //TODO:
                case Header.MessageType_mxpGetSiegeInfoResponse:
                case Header.MessageType_mxpSiegeInfoNotify: {
                    SiegeInfo obj = new SiegeInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onSiegeInfoNotify(obj);
                }
                break;
                case Header.MessageType_mxpKickoutRoomUserNotify: {
                    RoomKickoutUserInfo obj = new RoomKickoutUserInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onKickoutRoomUserNotify(obj);
                }
                break;
                case Header.MessageType_mxpChatMsgNotify: {
                    RoomChatMsg obj = new RoomChatMsg();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onChatMsgNotify(obj);
                }
                break;
                case Header.MessageType_mxpChatMsgError: {
                    mHandler.onChatMsgError(mBuffer.getInt(Header.SIZE_HEADER));
                }
                break;
                case Header.MessageType_mxpUserPayResponse: {
                    UserPayResponse obj = new UserPayResponse();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onUserPayResponse(obj);
                }
                break;
                //TODO:
                case Header.MessageType_mxpSendSealNotify: {
                    SendSeal obj = new SendSeal();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onSendSealNotify(obj);
                }
                break;
                //TODO:
                case Header.MessageType_mxpSetMicStateNotify: {
                    MicState obj = new MicState();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onSetMicStateNotify(obj);
                }
                break;
                //TODO:
                case Header.MessageType_mxpSetDevStateNotify: {
                    DevState obj = new DevState();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onSetDevStateNotify(obj);
                }
                break;
                //TODO:
                case Header.MessageType_mxpSyncUserAliasNotify: {
                    UserAlias obj = new UserAlias();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onSyncUserAliasNotify(obj);
                }
                break;
                //TODO:
                case Header.MessageType_mxpSyncUserHeadPicNotify: {
                    UserHeadPic obj = new UserHeadPic();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onSyncUserHeadPicNotify(obj);
                }
                break;
                //TODO:
                case Header.MessageType_mxpSyncDecoColorNotify: {
                    DecoColor obj = new DecoColor();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onSyncDecoColorNotify(obj);
                }
                break;
//			case Header.MessageType_mxpSetRoomBKgroupNotify:
//				{
//					RoomBKGround obj = new RoomBKGround();
//					Message.DecodeObject(mBuffer, obj);
//					mHandler.onSetRoomBKgroupNotify(obj);
//				}
//				break;
                case Header.MessageType_mxpSetRoomBaseInfoResponse: {
                    mHandler.onSetRoomBaseInfoResponse();
                }
                break;
                //TODO:
                case Header.MessageType_mxpSetRoomBaseInfoNotify:
                case Header.MessageType_mxpUpdateRoomBaseInfoNotify: {
                    RoomBaseInfo obj = new RoomBaseInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onRoomBaseInfoNotify(obj);
                }
                break;
                case Header.MessageType_mxpSetRoomManagersResponse: {
                    mHandler.onSetRoomManagersResponse();
                }
                break;

                case Header.MessageType_mxpSetRoomManagersNotify: {
                    RoomManagerInfo obj = new RoomManagerInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onSetRoomManagersNotify(obj);
                }
                break;
                case Header.MessageType_mxpSetRoomMediaURLNotify:
                case Header.MessageType_mxpUpdateRoomMediaURLNotify: {
                    RoomMediaInfo obj = new RoomMediaInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onRoomMediaURLNotify(obj);
                }
                break;
                case Header.MessageType_mxpSetRoomRunStateNotify: {
                    RoomState obj = new RoomState();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onSetRoomRunStateNotify(obj);
                }
                break;
                case Header.MessageType_mxpSetUserPriorityResponse: {
                    //操作成功
                    mHandler.onSetUserPriorityResponse();
                }
                break;
                case Header.MessageType_mxpSetUserProfileResponse: {
                    SetUserProfileResp obj = new SetUserProfileResp();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onSetUserProfileResponse(obj);
                }
                break;
                case Header.MessageType_mxpSetUserPwdRepsonse: {
                    SetUserPwdResp obj = new SetUserPwdResp();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onSetUserPwdRepsonse(obj);
                }
                break;
                case Header.MessageType_mxpSetUserPwdError: {
                    mHandler.onSetUserPwdError();
                }
                break;
                case Header.MessageType_mxpSetUserPriorityNotify: {
                    UserPriority obj = new UserPriority();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onSetUserPriorityNotify(obj);
                }
                break;
                case Header.MessageType_mxpTransMediaRequest: {
                    TransMediaInfo obj = new TransMediaInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onTransMediaRequest(obj);
                }
                break;
                case Header.MessageType_mxpTransMediaResponse: {
                    TransMediaInfo obj = new TransMediaInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onTransMediaResponse(obj);
                }
                break;
                case Header.MessageType_mxpTransMediaError: {
                    TransMediaInfo obj = new TransMediaInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onTransMediaError(obj);
                }
                break;
                case Header.MessageType_mxpRoomUserKeepAliveResponse: {
                    mHandler.onRoomUserKeepAliveResponse();
                }
                break;
                //TODO:
                case Header.MessageType_mxpForbidUserChatNotify: {
                    ForbidUserChat obj = new ForbidUserChat();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onForbidUserChatNotify(obj);
                }
                break;
                //TODO:
                case Header.MessageType_mxpTradeGiftResponse: {
                    mHandler.onTradeGiftResponse();
                }
                break;
                case Header.MessageType_mxpTradeGiftError: {
                    ByteBuffer obj = Message.DecodeBody(mBuffer);
                    mHandler.onTradeGiftError(obj.getInt(0));
                }
                break;
                case Header.MessageType_mxpTradeGiftNotify: {
                    BigGiftRecord obj = new BigGiftRecord();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onTradeGiftNotify(obj);
                }
                break;
                case Header.MessageType_mxpLotteryGiftNotify: {
                    LotteryGiftNotice obj = new LotteryGiftNotice();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onLotteryGiftNotify(obj);
                }
                break;
                case Header.MessageType_mxpLotteryNotify: {
                    LotteryNotice obj = new LotteryNotice();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onLotteryNotify(obj);
                }
                break;

                case Header.MessageType_mxpSysCastNotify: {
                    SysCastNotice obj = new SysCastNotice();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onSysCastNotify(obj);
                }
                break;

                case Header.MessageType_mxpLocateUserIPResponse: {
                    LocateIPResp obj = new LocateIPResp();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onLocateUserIPResponse(obj);
                }
                break;

                case Header.MessageType_mxpGiveColorbarNotify: {
                    GiveColorbarInfo obj = new GiveColorbarInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onGiveColorbarNotify(obj);
                }
                break;
                // 增加麦时
                case Header.MessageType_mxpAddMicTimeNotify: {
                    UserAddMicTimeInfo obj = new UserAddMicTimeInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onAddMicTimeNotify(obj);
                }
                break;
                // 顶麦 消麦
                case Header.MessageType_mxpActWaitMicUserNotify: {
                    ActWaitMicUserInfo obj = new ActWaitMicUserInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onActWaitMicUserNotify(obj);
                }
                break;

                case Header.MessageType_mxpAddClosedFriendNotify: {
                    AddClosedFriendInfo obj = new AddClosedFriendInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onAddClosedFriendNotify(obj);
                }
                break;

                case Header.MessageType_mxpBankDepositResponse: {
                    //TODO:
                    UserBankDepositRespInfo obj = new UserBankDepositRespInfo();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onBankDepositResponse(obj);
                }
                break;

                case Header.MessageType_mxpBankDepositError: {
                    //TODO: 暂时未实现
                    mHandler.onBankDepositError();
                }
                break;


                case Header.MessageType_mxpDoNotReachRoomServer: {
                    mHandler.onDoNotReachRoomServer();
                }
                break;


                //case MessageType_mxpSiegeInfoNotify:
                //	{
                //		if (_GlobalSetting.m_wndMain.m_hWnd)
                //		{
                //			SiegeInfo_t *pNotify = new SiegeInfo_t;
                //			memcpy(pNotify, pMsg->content, sizeof(SiegeInfo_t));

                //			::PostMessage(_GlobalSetting.m_wndMain.m_hWnd, UM_MAINWND_SVRMSG_SIEGEINFO_NOTIFY, NULL, (LPARAM)pNotify);
                //		}
                //	}
                //	break;
                case Header.MessageType_mxpDigTreasureResponse: {
                    DigTreasureResponse obj = new DigTreasureResponse();
                    Message.DecodeObject(mBuffer, obj);
                    mHandler.onDigTreasureResponse(obj);
                }
                break;
//			case Header.MessageType_mxpRedPagerResponse:
//				{
//					RedPagerRequest obj = new RedPagerRequest();
//					Message.DecodeObject(mBuffer, obj);
//					mHandler.onRedPagerResponse(obj);
//				}
//				break;
//			case Header.MessageType_mxpRedPagerError:
//				{
//					ByteBuffer obj = Message.DecodeBody(mBuffer);
//					mHandler.onRedPagerError(obj.getInt(0));
//				}
//				break;
//			case Header.MessageType_mxpGrabRedPagerResponse:
//				{
//					GrabRedPagerRequest obj = new GrabRedPagerRequest();
//					Message.DecodeObject(mBuffer, obj);
//					mHandler.onGrabRedPagerResponse(obj);
//				}
//				break;
//			case Header.MessageType_mxpPreTradeGiftResponse:
//				{
//					PreTradeGift obj = new PreTradeGift();
//					Message.DecodeObject(mBuffer, obj);
//					mHandler.onPreTradeGiftResponse(obj);
//				}
//				break;
                default:
                    break;
            }

            // 移除一个包
            mBuffer.rdarin(head.getLength());
        }
    }

    @Override
    public void onDisconnected() {
        mHandler.onDisconnected();
        Log.d("123", "断开连接");
    }

    private void sendPack(Header head, Object obj) {
        ByteBuffer buf = new ByteBuffer();

        if (Message.EncodePack(buf, head, obj, false)) {
            byte[] data = new byte[buf.size()];

            buf.getBytes(data, 0, buf.size());
            mSocket.Send(data);
        }
    }

    // 发送Hello
    public void SendHello() {
        Header head = new Header();
        Hello obj = new Hello();

        // 构建消息头
        head.setCmd1(Header.MessageType_mxpClientHelloCommand);
        // 构建消息体
        obj.setParam0((int) (Math.random() * 0xffff));
        obj.setParam1(obj.getParam0() + (obj.getParam0() & 0xffff));
        obj.setParam2(obj.getParam0() - 2 * (obj.getParam0() & 0xffff));

        sendPack(head, obj);
    }

    // 发起心跳
    public void SendKeepAliveReq() {
        Header head = new Header();
        KeepLiveRepuest obj = new KeepLiveRepuest();

        // 构建消息头
        head.setCmd1(Header.MessageType_mxpRoomUserKeepAliveRequest);
        // 构建头
        obj.setUserid(mUserID);
        obj.setVcbid(mRoomID);

        sendPack(head, obj);
//		Log.d("123","发送心跳");
    }

    // 申请加入房间
    public void SendJoinRoomReq() {
        Header head = new Header();
        JoinRoomRequest obj = new JoinRoomRequest();

        // 构建消息头
        head.setCmd1(Header.MessageType_mxpJoinRoomRequest);
        // 构建头
        obj.setUserid(mUserID);
        obj.setVcbid(mRoomID);
        obj.setCuserpwd(mUserPwd);
        obj.setVcbpwd(mRoomPwd);
        obj.setUserstate(mUserState);

        sendPack(head, obj);
    }

    //	=>Dstplatformid: 0
//	 =>Dstvcbid: 0
//	 =>Familyid: 0
//	 =>Isprivate: 0
//	=>Msgtype: 0
//	=>Srcalias: 好声音小汪汪
//	 =>Srcid: 888881
//	=>Srclevel: 288
//	 =>Srcplatformid: 0
//	 =>Srcsealid: 0
//	 =>Textlen: 70
//	 =>Toalias:
//	 =>Toid: 0
//	 =>Vcbid: 100003
    //发送聊天消息
    public void sendChatMsg(int toid, byte msgType, byte isprivate, String chatmsg, String userName, int level) {
        Header header = new Header();
        RoomChatMsg obj = new RoomChatMsg();
        header.setCmd1(Header.MessageType_mxpChatMsgRequest);
        obj.setDstplatformid((short) 0);
        obj.setDstvcbid(0);
        obj.setSrcplatformid((short) 0);
        obj.setVcbid(mRoomID);//房间ID
        obj.setSrcid(mUserID);//发送者id
        obj.setToid(toid);//0表示房间大厅发送
        obj.setSrclevel(level);
        obj.setSrcsealid((short) 0);
        obj.setMsgtype(msgType);//0普通聊天  1 房间广播
        obj.setIsprivate(isprivate);
        obj.setSrcalias(userName);
        obj.setToalias("");
        obj.setContent(chatmsg);
        KLog.e(chatmsg);
        sendPack(header, obj);

    }

    /*
    *  =>Action: 2
07-14 14:22:56.251 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Banonymous: 0
07-14 14:22:56.251 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Casttype: 1
07-14 14:22:56.251 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Class: class com.xlg.android.protocol.BigGiftRecord
07-14 14:22:56.251 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Count: 1
07-14 14:22:56.251 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Flyid: -1
07-14 14:22:56.251 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Giftid: 374
07-14 14:22:56.251 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Oldcount: 0
07-14 14:22:56.251 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Reserve: 0
07-14 14:22:56.251 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Servertype: 0
07-14 14:22:56.251 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Srcalias: 217
07-14 14:22:56.255 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Srcid: 217
07-14 14:22:56.255 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Sztext:
07-14 14:22:56.255 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Time: 1468477376
07-14 14:22:56.255 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Toalias: 小小小小汪
07-14 14:22:56.255 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Toid: 1
07-14 14:22:56.255 9493-17445/com.fubang.fubangzhibo I/System.out:     =>Vcbid: 123456*/
    //发送礼物
    public void sendGiftRecord(int toid, int giftId, int count, String toName, String userName) {
        Header header = new Header();
        BigGiftRecord obj = new BigGiftRecord();
        header.setCmd1(Header.MessageType_mxpTradeGiftRequest);
        obj.setVcbid(mRoomID);
        obj.setSrcid(99888);
        obj.setDstplatformid((short) 0);
        obj.setToid(toid);
        obj.setGiftid(giftId);
        obj.setCount(count);
        obj.setAction((byte) 2);
        obj.setServertype((byte) 0);
        obj.setBanonymous((byte) 0);
        obj.setCasttype((byte) 1);
        obj.setTime(System.currentTimeMillis());
        obj.setOldcount(0);
        obj.setFlyid((short) -1);
        obj.setSrcalias("99888");
//		obj.setToalias(toid+"");
        obj.setToalias(toName);
        obj.setSztext("");
        Log.d("123", obj.toString());
        sendPack(header, obj);
    }

    //退出房间请求
    public void sendLeaveRoom(int srcId) {
        Header header = new Header();
        RoomKickoutUserInfo obj = new RoomKickoutUserInfo();
        header.setCmd1(Header.MessageType_mxpKickoutRoomUserRequest);
        obj.setSrcid(srcId);
        obj.setToid(srcId);
        obj.setReserve((byte) 0);
        obj.setReasonid((byte) 0);
        obj.setVcbid(mRoomID);
        sendPack(header, obj);
    }

    //	//关注艺人
//	public void actorUser(int buddyid ,int actorid, int type){
//		Header header = new Header();
//		UserActor obj = new UserActor();
//		header.setCmd1(Header.MessageType_mxpUserActorRequest);
//		obj.setActorid(actorid);
//		obj.setBuddyid(buddyid);
//		obj.setUserid(mUserID);
//		obj.setType(type);
//		obj.setVcbid(mRoomID);
//		obj.setCount(0);
//		sendPack(header,obj);
//	}
    //收藏房间
    public void followRoom(int vcbid, int userid) {
        Header header = new Header();
        CollageRequest obj = new CollageRequest();
        header.setCmd1(Header.MessageType_mxpCollageRequest);
        obj.setVcbid(mRoomID);
        obj.setUserid(mUserID);
        sendPack(header, obj);
    }

    //踢出房间
    public void kickOutRoom(int toid) {
        Header header = new Header();
        RoomKickoutUserInfo obj = new RoomKickoutUserInfo();
        header.setCmd1(Header.MessageType_mxpKickoutRoomUserRequest);
        obj.setReasonid((short) 0);
        obj.setReserve((short) 0);
        obj.setSrcid(mUserID);
        obj.setToid(toid);
        obj.setVcbid(mRoomID);
        sendPack(header, obj);
    }

    //禁言,解除禁言
    public void forbidChat(int toid, short action) {
        Header header = new Header();
        ForbidUserChat obj = new ForbidUserChat();
        header.setCmd1(Header.MessageType_mxpForbidUserChatRequest);
        obj.setVcbid(mRoomID);
        obj.setToid(toid);
        obj.setSrcid(mUserID);
        obj.setAction(action);
        obj.setReserve((short) 0);
        sendPack(header, obj);
    }

    //主播上麦
    public void sendUpMic() {
        Header header = new Header();
        MicState obj = new MicState();
        header.setCmd1(Header.MessageType_mxpSetMicStateRequest);
        obj.setVcbid(mRoomID);
        obj.setRunnerid(mUserID);
        obj.setUserid(mUserID);
        obj.setChargemicgiftid((short) 0);
        obj.setChargemicgiftcount((short)0);
        obj.setReserve((short)0);
        obj.setMicendtime(0);
        obj.setMicnowtime(0);
        obj.setMicindex((byte)0);
        obj.setMicstate((short) 0x00000008);
        obj.setIsallowupmic(0);
        obj.setnIndex((byte)0);
        sendPack(header,obj);
    }
}
