package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

public class OpenChestInfo {
	@StructOrder(0)
	private int vcbid;  //房间id
	@StructOrder(1)
	private int userid;  //用户id
	@StructOrder(2)
	private int amount;  //金额
	@StructOrder(3)
	private long openedtime;  //开宝箱的时间
	@StructOrder(4)
	private byte chestnum;  //剩余宝箱数,在通知消息中有用。
	@StructOrder(5)
	private byte casttype;  //广播类型： 0-无广播,自己接受(如不中), 1-房间广播(小奖) 2-全站广播(大奖,替换跑道)，3-下拉列表记录
	@StructOrder(6)
	private short	reserve; //保留
	@StructOrder(7)
	private byte[] srcalias = new byte[32];	//昵称
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public long getOpenedtime() {
		return openedtime;
	}
	public void setOpenedtime(long openedtime) {
		this.openedtime = openedtime;
	}
	public byte getChestnum() {
		return chestnum;
	}
	public void setChestnum(byte chestnum) {
		this.chestnum = chestnum;
	}
	public byte getCasttype() {
		return casttype;
	}
	public void setCasttype(byte casttype) {
		this.casttype = casttype;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}
	public String getSrcalias() {
		return Tools.ByteArray2StringGBK(srcalias);
	}
	public void setSrcalias(String srcalias) {
		Tools.String2ByteArrayGBK(this.srcalias, srcalias);
	}
	
	
}
