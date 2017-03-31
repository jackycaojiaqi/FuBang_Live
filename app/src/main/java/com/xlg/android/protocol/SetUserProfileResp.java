package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

public class SetUserProfileResp {
	@StructOrder(0)
	private int	userid;						//用户id
	@StructOrder(1)
	private int	decocolor;					//马甲颜色
	@StructOrder(2)
	private byte			gender;						//性别（0－女，1－男，2－未知）
	@StructOrder(3)
	private byte[]			calias = new byte[32];			//昵称
	@StructOrder(4)
	private byte[]			cidiograph = new byte[128];	//签名
	@StructOrder(5)
	private int	headid;						//头像id
	
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getDecocolor() {
		return decocolor;
	}
	public void setDecocolor(int decocolor) {
		this.decocolor = decocolor;
	}
	public byte getGender() {
		return gender;
	}
	public void setGender(byte gender) {
		this.gender = gender;
	}
	public String getCalias() {
		return Tools.ByteArray2StringGBK(calias);
	}
	public void setCalias(String calias) {
		Tools.String2ByteArrayGBK(this.calias, calias);
	}
	public String getCidiograph() {
		return Tools.ByteArray2StringGBK(cidiograph);
	}
	public void setCidiograph(String cidiograph) {
		Tools.String2ByteArrayGBK(this.cidiograph, cidiograph);
	}
	public int getHeadid() {
		return headid;
	}
	public void setHeadid(int headid) {
		this.headid = headid;
	}


}
