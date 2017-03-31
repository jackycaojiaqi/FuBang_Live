package com.xlg.android.protocol;

public class ActWaitMicUserInfo {
	@StructOrder(0)
	private int vcbid;	   // 房间ID
	@StructOrder(1)
	private int userid;   // 用户ID
	@StructOrder(2)
	private int toid;     // 对方ID
	@StructOrder(3)
	private int          act;      // 1为置顶 0为取消
	@StructOrder(4)
	private int          sortid;   // 当前麦序里的排序位 供一人排多麦时候使用
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
	public int getToid() {
		return toid;
	}
	public void setToid(int toid) {
		this.toid = toid;
	}
	public int getAct() {
		return act;
	}
	public void setAct(int act) {
		this.act = act;
	}
	public int getSortid() {
		return sortid;
	}
	public void setSortid(int sortid) {
		this.sortid = sortid;
	}

}
