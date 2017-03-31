package com.xlg.android.protocol;

public class UserAddMicTimeInfo {
	@StructOrder(0)
	private int userid;   // 用户ID
	@StructOrder(1)
	private int vcbid;	   // 房间ID
	@StructOrder(2)
	private int toid;     // 对方ID
	@StructOrder(3)
	private long          addmictime;
	@StructOrder(4)
	private long          nowtime;	//服务器当前时间
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getVcbid() {
		return vcbid;
	}
	public void setVcbid(int vcbid) {
		this.vcbid = vcbid;
	}
	public int getToid() {
		return toid;
	}
	public void setToid(int toid) {
		this.toid = toid;
	}
	public long getAddmictime() {
		return addmictime;
	}
	public void setAddmictime(long addmictime) {
		this.addmictime = addmictime;
	}
	public long getNowtime() {
		return nowtime;
	}
	public void setNowtime(long nowtime) {
		this.nowtime = nowtime;
	}
	
	
}
