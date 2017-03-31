package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

public class UserAlias {
	@StructOrder(0)
	private int   vcbid;            //房间id
	@StructOrder(1)
	private int   userid;           //发送请求者ID
	@StructOrder(2)
	private byte[]  alias = new byte[32];  //用户昵称
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
	public String getAlias() {
		return Tools.ByteArray2StringGBK(alias);
	}
	public void setAlias(String alias) {
		Tools.String2ByteArrayGBK(this.alias, alias);
	}
	
	
}
