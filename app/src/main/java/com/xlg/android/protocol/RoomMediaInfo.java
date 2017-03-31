package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

public class RoomMediaInfo {
	@StructOrder(0)
	private int	vcbid;						//房间id
	@StructOrder(1)
	private int	userid;						//用户id
	@StructOrder(2)
	private byte[]			mediaurl = new byte[128];		//媒体服务器URL
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
	public String getMediaurl() {
		return Tools.ByteArray2StringGBK(mediaurl);
	}
	public void setMediaurl(String mediaurl) {
		Tools.String2ByteArrayGBK(this.mediaurl, mediaurl);
	}
	
	
}
