package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

public class DigTreasureResponse {
	@StructOrder(0)
	private int vcbid;		// 房间ID
	@StructOrder(1)
	private int userid;	// 请求者ID
	@StructOrder(2)
	private byte [] alias = new byte[32];	//昵称
	@StructOrder(3)
	private int winnum;				// 中奖号
	@StructOrder(4)
	private long winmoney;		// 中奖金币
	@StructOrder(5)
	private long money;		// 当前金币
	@StructOrder(6)
	private long giftmoney;
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
	public String getAlias() {
		return Tools.ByteArray2StringGBK(alias);
	}
	public void setAlias(String alias) {
		Tools.String2ByteArrayGBK(this.alias, alias);
	}
	public int getWinnum() {
		return winnum;
	}
	public void setWinnum(int winnum) {
		this.winnum = winnum;
	}
	public long getWinmoney() {
		return winmoney;
	}
	public void setWinmoney(long winmoney) {
		this.winmoney = winmoney;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	public long getGiftmoney() {
		return giftmoney;
	}
	public void setGiftmoney(long giftmoney) {
		this.giftmoney = giftmoney;
	}

}
