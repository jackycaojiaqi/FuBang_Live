package com.xlg.android.protocol;

public class RoomState {
	@StructOrder(0)
	private int	vcbid;
	@StructOrder(1)
	private int	userid;				//added by xcc on 2013.10.19 因为权限判断要移到服务端，所以加了用户id
	@StructOrder(2)
	private int	runstate;			//使用bit,一共可以放置32个,第1位:是否允许悄悄话? 第2位:是否允许仍塞子，上传和广播时均是混合结果数据.
	
	
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
	public int getRunstate() {
		return runstate;
	}
	public void setRunstate(int runstate) {
		this.runstate = runstate;
	}

	
}
