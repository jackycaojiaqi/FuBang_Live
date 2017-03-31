package com.xlg.android.protocol;

public class GrabRedPagerRequest {
	@StructOrder(0)
	private int id;					// 红包id
	@StructOrder(1)
	private  int vcbid;	   // 房间ID
	@StructOrder(2)
	private  int userid;   // 请求者ID
	@StructOrder(3)
	private byte[] alias = new byte[32];	//昵称
	@StructOrder(4)
	private int level;				//等级
	@StructOrder(5)
	private  long money;			//中奖金额
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public byte[] getAlias() {
		return alias;
	}
	public void setAlias(byte[] alias) {
		this.alias = alias;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	
	
}
