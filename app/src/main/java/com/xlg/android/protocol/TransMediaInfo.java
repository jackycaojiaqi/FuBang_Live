package com.xlg.android.protocol;

public class TransMediaInfo {
	@StructOrder(0)
	private int	vcbid;
	@StructOrder(1)
	private int	srcid;				//发送者id
	@StructOrder(2)
	private int	toid;				//接收者id
	@StructOrder(3)
	private byte			action;				//动作：1表示请求打开对方音视频，3表示请求关闭对方音视频。
	public int getVcbid() {
		return vcbid;
	}
	public void setVcbid(int vcbid) {
		this.vcbid = vcbid;
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
	public byte getAction() {
		return action;
	}
	public void setAction(byte action) {
		this.action = action;
	}

	
	
}
