package com.xlg.android.protocol;

public class PreTradeGift {
	@StructOrder(0)
	private int	vcbid;
	@StructOrder(1)
	private int	srcid;				//发送者id
	@StructOrder(2)
	private int	toid;				//接收者id
	@StructOrder(3)
	private int	giftid;				//礼物id
	@StructOrder(4)
	private int				errorid;			//错误id
	
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
	public int getGiftid() {
		return giftid;
	}
	public void setGiftid(int giftid) {
		this.giftid = giftid;
	}
	public int getErrorid() {
		return errorid;
	}
	public void setErrorid(int errorid) {
		this.errorid = errorid;
	}

}
