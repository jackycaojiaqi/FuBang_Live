package com.xlg.android.protocol;

public class RedPagerRequest {
	@StructOrder(0)
	private int vcbid;	   // 房间ID
	@StructOrder(1)
	private int userid;   // 请求者ID
	@StructOrder(2)
	private byte[] alias = new byte[32];	//昵称
	@StructOrder(3)
	private int level;				//等级
	@StructOrder(4)
	private long money;		// 金额
	@StructOrder(5)
	private long total;		// 红包个数
	@StructOrder(6)
	private int type;		// 红包类型
	@StructOrder(7)
	private int id;					// 红包id
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
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
