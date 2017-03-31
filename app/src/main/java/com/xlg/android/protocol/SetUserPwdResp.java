package com.xlg.android.protocol;

public class SetUserPwdResp {
	@StructOrder(0)
	private int userid;                       //用户id
	@StructOrder(1)
	private byte        pwdtype;                       //密码类型 ，1-用户登录密码,2-用户银行密码  
	@StructOrder(2)
	private byte[]		cpwd = new byte[34];			   //用户密码
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public byte getPwdtype() {
		return pwdtype;
	}
	public void setPwdtype(byte pwdtype) {
		this.pwdtype = pwdtype;
	}
	public byte[] getCpwd() {
		return cpwd;
	}
	public void setCpwd(byte[] cpwd) {
		this.cpwd = cpwd;
	}

	
	
}
