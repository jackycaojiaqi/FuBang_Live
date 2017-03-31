package com.xlg.android.protocol;

public class UserPriority {
	@StructOrder(0)
	private int vcbid;       //房间id
	@StructOrder(1)
	private int runnerid;    //操作人员id
	@StructOrder(2)
	private int userid;      //被操作人员id
	@StructOrder(3)
	private byte	action;				//动作 （1->加 2->卸）
	@StructOrder(4)
	private byte	priority;			//用户权限（1->管理员，2->临时管理员，3->黑名单，4->封号） // 增加了封号处理
	@StructOrder(5)
	private short	reserve;			//保留
	
	
	public int getVcbid() {
		return vcbid;
	}
	public void setVcbid(int vcbid) {
		this.vcbid = vcbid;
	}
	public int getRunnerid() {
		return runnerid;
	}
	public void setRunnerid(int runnerid) {
		this.runnerid = runnerid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public byte getAction() {
		return action;
	}
	public void setAction(byte action) {
		this.action = action;
	}
	public byte getPriority() {
		return priority;
	}
	public void setPriority(byte priority) {
		this.priority = priority;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}

	
}
