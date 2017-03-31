package com.xlg.android.protocol;

import com.xlg.android.utils.ByteBuffer;
import com.xlg.android.utils.Tools;

public class RoomChatMsg {
	@StructOrder(0)
	private int	vcbid;
	@StructOrder(1)
	private int	dstvcbid;			//目标房间id
	@StructOrder(2)
	private short  srcplatformid;		//原平台ID
	@StructOrder(3)
	private short	dstplatformid;		//用于跨平台
	@StructOrder(4)
	private int	srcid;				//发送者ID
	@StructOrder(5)
	private int	toid;				//接收者ID
	@StructOrder(6)
	private int             srclevel;           //发送者等级id(用于非本房间src-user)
	@StructOrder(7)
	private short  srcsealid;          //发送者的盖章id(用于非本房间src-user)
	@StructOrder(8)
	private short  familyid;           //发送者的家族id(用于非本房间src-user)
	@StructOrder(9)
	private byte			msgtype;			//消息类型
	@StructOrder(10)
	private byte			isprivate;			//是否私聊
	@StructOrder(11)
	private short	textlen;			//聊天内容长度
	@StructOrder(12)
	private byte []			srcalias = new byte[32];	//发送者昵称
	@StructOrder(13)
	private byte []			toalias = new byte[32];	//接收者昵称
	@StructOrder(14)
	private ByteBuffer content = new ByteBuffer();			//聊天内容
	
	public int getVcbid() {
		return vcbid;
	}
	public void setVcbid(int vcbid) {
		this.vcbid = vcbid;
	}
	public int getDstvcbid() {
		return dstvcbid;
	}
	public void setDstvcbid(int dstvcbid) {
		this.dstvcbid = dstvcbid;
	}
	public short getSrcplatformid() {
		return srcplatformid;
	}
	public void setSrcplatformid(short srcplatformid) {
		this.srcplatformid = srcplatformid;
	}
	public short getDstplatformid() {
		return dstplatformid;
	}
	public void setDstplatformid(short dstplatformid) {
		this.dstplatformid = dstplatformid;
	}
	public int getSrcid() {
		return srcid;
	}
	public void setSrcid(int srcid) {
		this.srcid = srcid;
	}
	public int getToid() {
		return toid;
	}
	public void setToid(int toid) {
		this.toid = toid;
	}
	public int getSrclevel() {
		return srclevel;
	}
	public void setSrclevel(int srclevel) {
		this.srclevel = srclevel;
	}
	public short getSrcsealid() {
		return srcsealid;
	}
	public void setSrcsealid(short srcsealid) {
		this.srcsealid = srcsealid;
	}
	public short getFamilyid() {
		return familyid;
	}
	public void setFamilyid(short familyid) {
		this.familyid = familyid;
	}
	public byte getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(byte msgtype) {
		this.msgtype = msgtype;
	}
	public byte getIsprivate() {
		return isprivate;
	}
	public void setIsprivate(byte isprivate) {
		this.isprivate = isprivate;
	}
	public short getTextlen() {
		return textlen;
	}
	public void setTextlen(short textlen) {
		this.textlen = textlen;
	}
	public String getSrcalias() {
		return Tools.ByteArray2StringGBK(srcalias);
	}
	public void setSrcalias(String srcalias) {
		Tools.String2ByteArrayGBK(this.srcalias, srcalias);
	}
	public String getToalias() {
		return Tools.ByteArray2StringGBK(toalias);
	}
	public void setToalias(String toalias) {
		Tools.String2ByteArrayGBK(this.toalias, toalias);
	}
	public String getContent() {
		return content.toStringGBK();
	}
	public void setContent(String content) {
		this.content.clear();
		this.textlen = 0;
		
		// 添加新值
		this.content.addStringGBK(content);
		this.textlen = (short)this.content.size();
	}

	
}
