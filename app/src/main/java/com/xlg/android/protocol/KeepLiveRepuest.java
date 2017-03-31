package com.xlg.android.protocol;

public class KeepLiveRepuest {
	@StructOrder(0)
	private int	vcbid;
	@StructOrder(1)
	private int	userid;				//用户ID
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

}
