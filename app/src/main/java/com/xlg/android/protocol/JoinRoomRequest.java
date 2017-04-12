package com.xlg.android.protocol;

import com.xlg.android.utils.SimpSecret;
import com.xlg.android.utils.Tools;

public class JoinRoomRequest {
	@StructOrder(0)
	private int	userid;					//用户id
	@StructOrder(1)
	private int	vcbid;					//房间id
	@StructOrder(2)
	private int	userstate;				//用户在房间内状态，用了低16位(在麦状态等）
	@StructOrder(3)
	private byte []			username = new byte[32];	//暂未用
	@StructOrder(4)
	private byte []			cuserpwd = new byte[32];		//用户密码	@StructOrder(76)
	@StructOrder(5)
	private byte []			vcbpwd = new byte[32];			//房间密码。


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
	public int getUserstate() {
		return userstate;
	}
	public void setUserstate(int userstate) {
		this.userstate = userstate;
	}
	public String getUsername() {
		return Tools.ByteArray2StringGBK(username);
	}
	public void setUsername(String username) {
		Tools.String2ByteArrayGBK(this.username, username);
	}
	public String getCuserpwd() {
		byte [] buf = new byte[cuserpwd.length];
		System.arraycopy(cuserpwd, 0, buf, 0, buf.length);
		SimpSecret.SimpDecrypt(buf);
		return Tools.ByteArray2StringGBK(buf);
	}
	public void setCuserpwd(String cuserpwd) {
		Tools.String2ByteArrayGBK(this.cuserpwd, cuserpwd);
		SimpSecret.SimpEncrypt(this.cuserpwd);
	}
	public String getVcbpwd() {
		byte [] buf = new byte[vcbpwd.length];
		System.arraycopy(vcbpwd, 0, buf, 0, buf.length);
		SimpSecret.SimpDecrypt(buf);
		return Tools.ByteArray2StringGBK(buf);
	}
	public void setVcbpwd(String vcbpwd) {
		Tools.String2ByteArrayGBK(this.vcbpwd, vcbpwd);
		SimpSecret.SimpEncrypt(this.vcbpwd);
	}


}
