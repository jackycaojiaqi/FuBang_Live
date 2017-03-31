package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

public class AuthorityRejected {
	@StructOrder(0)
	private short	errorid;
	@StructOrder(1)
	private byte[]			errmsg = new byte[128];
	
	
	public short getErrorid() {
		return errorid;
	}
	public void setErrorid(short errorid) {
		this.errorid = errorid;
	}
	public String getErrmsg() {
		return Tools.ByteArray2StringGBK(errmsg);
	}
	public void setErrmsg(String errmsg) {
		Tools.String2ByteArrayGBK(this.errmsg, errmsg);
	}
	
	
}
