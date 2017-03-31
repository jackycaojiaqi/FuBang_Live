package com.xlg.android.protocol;

import com.xlg.android.utils.ByteBuffer;

public class LocateIPResp {
	@StructOrder(0)
	private int vcbid;              //房间id
	@StructOrder(1)
	private int userid;             //被查看者id 
	@StructOrder(2)
	private short textlen;          //描述信息长度
	@StructOrder(3)
	private short reserve;          //保留
	@StructOrder(4)
	private ByteBuffer content;       //地址内容
	
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
	public short getTextlen() {
		return textlen;
	}
	public void setTextlen(short textlen) {
		this.textlen = textlen;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}
	public String getContent() {
		return content.toStringGBK();
	}
	public void setContent(String content) {
		this.content.clear();
		this.content.addStringGBK(content);
	}
	
	
}
