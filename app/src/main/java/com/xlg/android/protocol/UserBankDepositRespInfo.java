package com.xlg.android.protocol;

public class UserBankDepositRespInfo {
	@StructOrder(0)
	private int userid;
	@StructOrder(1)
	private int vcbid;
	@StructOrder(2)
	private long    ndeposit;     //用户银行剩余金额
	@StructOrder(3)
	private long    nk;               //用户现有金额
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
	public long getNdeposit() {
		return ndeposit;
	}
	public void setNdeposit(long ndeposit) {
		this.ndeposit = ndeposit;
	}
	public long getNk() {
		return nk;
	}
	public void setNk(long nk) {
		this.nk = nk;
	}

	
}
