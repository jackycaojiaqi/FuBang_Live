package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

public class SiegeInfo {
	@StructOrder(0)
	private int	vcbid;
	@StructOrder(1)
	private int	srcid;				//发送者id
	@StructOrder(2)
	private int	toid;				//接收者id
	@StructOrder(3)
	private int	giftid;				//礼物id
	@StructOrder(4)
	private int	count;				//礼物数目
	@StructOrder(5)
	private long	time;				//发起时间
	@StructOrder(6)
	private byte[]			srcalias = new byte[32];	//发送者昵称
	@StructOrder(7)
	private byte[]			toalias = new byte[32];	//接收者昵称
	
	
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
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
	
	
}
