package com.xlg.android.protocol;

import com.xlg.android.utils.SimpSecret;
import com.xlg.android.utils.Tools;

public class LogonRequest {
	@StructOrder(0)
	private int	userid;
	@StructOrder(1)
	private int	ip;
	@StructOrder(2)
	private short  port;
	@StructOrder(3)
	private byte	visitor;				//1表示游客，0表示注册用户
	@StructOrder(4)
	private byte	online_stat;			//状态（0离线 1在线2隐身）
	@StructOrder(5)
	private byte []	cuserpwd = new byte[32];		//密码
	@StructOrder(6)
	private byte []	cmac = new byte[40];			//MAC地址
	@StructOrder(7)
	private byte[] cldcode = new byte[40];			//qqId
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getIp() {
		return ip;
	}
	public void setIp(int ip) {
		this.ip = ip;
	}
	public short getPort() {
		return port;
	}
	public void setPort(short port) {
		this.port = port;
	}
	public byte getVisitor() {
		return visitor;
	}
	public void setVisitor(byte visitor) {
		this.visitor = visitor;
	}
	public byte getOnline_stat() {
		return online_stat;
	}
	public void setOnline_stat(byte online_stat) {
		this.online_stat = online_stat;
	}
	public String getCuserpwd() {
		byte [] data = new byte[cuserpwd.length];
		System.arraycopy(cuserpwd, 0, data, 0, cuserpwd.length);
		SimpSecret.SimpDecrypt(data);
		return Tools.ByteArray2StringGBK(data);
	}
	
	public void setCuserpwd(String pwd) {
		Tools.String2ByteArrayGBK(cuserpwd, pwd);
		SimpSecret.SimpEncrypt(cuserpwd);
	}
	
	public String getCmac() {
		return Tools.ByteArray2StringGBK(cmac);
	}
	
	public void setCmac(String mac) {
		Tools.String2ByteArrayGBK(cmac, mac);
	}

	public String getCldcode() {
		return Tools.ByteArray2StringGBK(cldcode);
	}

	public void setCldcode(String cldcode) {
		Tools.String2ByteArrayGBK(this.cldcode,cldcode);
	}
}
