package com.xlg.android.protocol;

public class RoomBKGround {
	@StructOrder(0)
	private int	vcbid;				//房间id
	@StructOrder(1)
	private int	userid;				//发送者ID
	@StructOrder(2)
	private short	bkgroundid;			//房间背景ID
	@StructOrder(3)
	private short	reserve;			//保留
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
	public short getBkgroundid() {
		return bkgroundid;
	}
	public void setBkgroundid(short bkgroundid) {
		this.bkgroundid = bkgroundid;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}
	
	
}
