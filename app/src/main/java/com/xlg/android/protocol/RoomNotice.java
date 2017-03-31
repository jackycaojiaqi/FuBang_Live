package com.xlg.android.protocol;

import com.xlg.android.utils.ByteBuffer;

public class RoomNotice {
	@StructOrder(0)
	private int	vcbid;
	@StructOrder(1)
	private int	userid;				//用户id
	@StructOrder(2)
	private short	idx;				//消息索引idx:0~2(0,1,2)
	@StructOrder(3)
	private short	textlen;			//公告消息长度
	@StructOrder(4)
	private ByteBuffer content = new ByteBuffer();			//消息内容。
	
	
	public int getVcbid() {
		return vcbid;
	}
	public void setVcbid(int vcbid) {
		this.vcbid = vcbid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public short getIdx() {
		return idx;
	}
	public void setIdx(short idx) {
		this.idx = idx;
	}
	public String getContent() {
		return content.toStringGBK();
	}
	public void setContent(String content) {
		this.content.addStringGBK(content);
		this.textlen = (short)this.content.size();
	}

	
}
