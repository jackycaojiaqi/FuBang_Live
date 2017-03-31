package com.xlg.android.protocol;

public class ForbidUserChat {
	@StructOrder(0)
	private int	vcbid;				//房间id
	@StructOrder(1)
	private int	srcid;				//发送者ID
	@StructOrder(2)
	private int	toid;				//接收者ID
	@StructOrder(3)
	private short	action;				//动作：1禁言 0解禁
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
	public short getAction() {
		return action;
	}
	public void setAction(short action) {
		this.action = action;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}


}
