package com.xlg.android.protocol;

public class LotteryGiftNotice {
	@StructOrder(0)
	private int vcbid;        //房间id
	@StructOrder(1)
	private int srcid;        //赠送userid
	@StructOrder(2)
	private short giftid;     //礼物id
	@StructOrder(3)
	private short nodds;      //中奖倍数
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
	public short getGiftid() {
		return giftid;
	}
	public void setGiftid(short giftid) {
		this.giftid = giftid;
	}
	public short getNodds() {
		return nodds;
	}
	public void setNodds(short nodds) {
		this.nodds = nodds;
	}

}
