package com.xlg.android.protocol;

public class UserHeadPic {
	@StructOrder(0)
	private int  vcbid;    //房间id
	@StructOrder(1)
	private int  userid;   //发送请求者ID
	@StructOrder(2)
	private int  headid; //头像id
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
	public int getHeadid() {
		return headid;
	}
	public void setHeadid(int headid) {
		this.headid = headid;
	}
	
	
}
