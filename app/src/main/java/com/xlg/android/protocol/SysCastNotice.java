package com.xlg.android.protocol;

import com.xlg.android.utils.ByteBuffer;

public class SysCastNotice {
	@StructOrder(0)
	private char  msgtype;             //消息类型
	@StructOrder(1)
	private char  reserve1;             //保留
	@StructOrder(2)
	private short textlen;             //公告消息长度
	@StructOrder(3)
	private ByteBuffer content;			//消息内容。
	
	public char getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(char msgtype) {
		this.msgtype = msgtype;
	}
	public char getReserve1() {
		return reserve1;
	}
	public void setReserve1(char reserve1) {
		this.reserve1 = reserve1;
	}
	public short getTextlen() {
		return textlen;
	}
	public void setTextlen(short textlen) {
		this.textlen = textlen;
	}
	public String getContent() {
		return content.toStringGBK();
	}
	public void setContent(String content) {
		this.content.clear();
		this.content.addStringGBK(content);
	}

}
