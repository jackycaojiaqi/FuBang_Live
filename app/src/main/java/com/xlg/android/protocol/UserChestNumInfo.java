package com.xlg.android.protocol;

public class UserChestNumInfo {
	@StructOrder(0)
	private int    vcbid;                 //当前用户所在的房间id
	@StructOrder(1)
	private int    userid;                //用户id
	@StructOrder(2)
	private byte   newchestcount;         //获取的新宝箱数目
	@StructOrder(3)
	private byte   totalchestcount;       //用户剩余的宝箱数目
	@StructOrder(4)
	private short	reserve;			   //保留
	
	
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
	public byte getNewchestcount() {
		return newchestcount;
	}
	public void setNewchestcount(byte newchestcount) {
		this.newchestcount = newchestcount;
	}
	public byte getTotalchestcount() {
		return totalchestcount;
	}
	public void setTotalchestcount(byte totalchestcount) {
		this.totalchestcount = totalchestcount;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}
	
	
}
