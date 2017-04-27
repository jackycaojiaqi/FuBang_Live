package com.xlg.android.protocol;

public class RoomKickoutUserInfo {
	@StructOrder(0)
	private int	vcbid;        //主播id
	@StructOrder(1)
	private int	srcid;				//发送者id
	@StructOrder(2)
	private int	toid;				//接收者id
	@StructOrder(3)
	private short	reasonid;			//原因id.
	@StructOrder(4)
	private short	reserve;			//保留
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
	public short getReasonid() {
		return reasonid;
	}
	public void setReasonid(short reasonid) {
		this.reasonid = reasonid;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}

	
	
}
