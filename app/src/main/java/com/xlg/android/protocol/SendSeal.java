package com.xlg.android.protocol;

public class SendSeal {
	@StructOrder(0)
	private int	vcbid;				//房间id
	@StructOrder(1)
	private int	srcid;				//发送者ID
	@StructOrder(2)
	private int	toid;				//接收者ID
	@StructOrder(3)
	private short	sealid;				//盖章ID  0表示清除
	@StructOrder(4)
	private short	reserve;			//保留
	@StructOrder(5)
	private long sealbringtime;				//时间

	public long getSealbringtime() {
		return sealbringtime;
	}

	public void setSealbringtime(long sealbringtime) {
		this.sealbringtime = sealbringtime;
	}

	public int getVcbid() {
		return vcbid;
	}
	public void setVcbid(int vcbid) {
		this.vcbid = vcbid;
	}
	public int getSrcid() {
		return srcid;
	}
	public void setSrcid(int srcid) {
		this.srcid = srcid;
	}
	public int getToid() {
		return toid;
	}
	public void setToid(int toid) {
		this.toid = toid;
	}
	public short getSealid() {
		return sealid;
	}
	public void setSealid(short sealid) {
		this.sealid = sealid;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}

	
	
}
