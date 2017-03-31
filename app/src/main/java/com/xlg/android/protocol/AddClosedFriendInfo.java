package com.xlg.android.protocol;

public class AddClosedFriendInfo {
	@StructOrder(0)
	private int vcbid;     //房间id
	@StructOrder(1)
	private int userid;    //用户id
	@StructOrder(2)
	private int friendid;   //密友is
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
	public int getFriendid() {
		return friendid;
	}
	public void setFriendid(int friendid) {
		this.friendid = friendid;
	}
	
	
}
