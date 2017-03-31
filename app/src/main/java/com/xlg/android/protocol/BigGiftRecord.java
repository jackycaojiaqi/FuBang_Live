package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

public class BigGiftRecord {
	@StructOrder(0)
	private int	vcbid;
	@StructOrder(1)
	private  int	dstvcbid;			//目标房间ID
	@StructOrder(2)
	private  short  srcplatformid;		//原平台ID
	@StructOrder(3)
	private  short	dstplatformid;		//用于跨平台
	@StructOrder(4)
	private  int	srcid;				//发送者id
	@StructOrder(5)
	private  int	toid;				//接收者id
	@StructOrder(6)
	private  int	giftid;				//礼物id
	@StructOrder(7)
	private  int	count;				//礼物数目
	@StructOrder(8)
	private byte			action;				//交易动作:action=2普通礼物赠送,3-世界道, 5-收费麦礼物
	@StructOrder(9)
	private byte			servertype;			//服务器转发类型。	0表示普通转发(房间内）。1表示通过centerSvr转发
	@StructOrder(10)
	private byte			banonymous;			//是否匿名。0表示不匿名，1表示匿名
	@StructOrder(11)
	private byte			casttype;			//是否广播，0表示不广播，1表示广播,客户端设是否需要上小喇叭通知
	@StructOrder(12)
	private long	time;				//发起时间
	@StructOrder(13)
	private  int	oldcount;			//上次数目
	@StructOrder(14)
	private short			flyid;				//跑道ID
	@StructOrder(15)
	private  short	reserve;			//保留
	@StructOrder(16)
	private byte[]			srcalias = new byte[32];	//发送者昵称
	@StructOrder(17)
	private byte[]			toalias = new byte[32];	//接收者昵称
	@StructOrder(18)
	private byte[]			sztext = new byte[40];//定义40,实际使用最多18个汉字或英文（最多占36个长度)
	
	
	public int getVcbid() {
		return vcbid;
	}
	public void setVcbid(int vcbid) {
		this.vcbid = vcbid;
	}
	public int getDstvcbid() {
		return dstvcbid;
	}
	public void setDstvcbid(int dstvcbid) {
		this.dstvcbid = dstvcbid;
	}
	public short getSrcplatformid() {
		return srcplatformid;
	}
	public void setSrcplatformid(short srcplatformid) {
		this.srcplatformid = srcplatformid;
	}
	public short getDstplatformid() {
		return dstplatformid;
	}
	public void setDstplatformid(short dstplatformid) {
		this.dstplatformid = dstplatformid;
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
	public int getGiftid() {
		return giftid;
	}
	public void setGiftid(int giftid) {
		this.giftid = giftid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public byte getAction() {
		return action;
	}
	public void setAction(byte action) {
		this.action = action;
	}
	public byte getServertype() {
		return servertype;
	}
	public void setServertype(byte servertype) {
		this.servertype = servertype;
	}
	public byte getBanonymous() {
		return banonymous;
	}
	public void setBanonymous(byte banonymous) {
		this.banonymous = banonymous;
	}
	public byte getCasttype() {
		return casttype;
	}
	public void setCasttype(byte casttype) {
		this.casttype = casttype;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getOldcount() {
		return oldcount;
	}
	public void setOldcount(int oldcount) {
		this.oldcount = oldcount;
	}
	public short getFlyid() {
		return flyid;
	}
	public void setFlyid(short flyid) {
		this.flyid = flyid;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}
	public String getSrcalias() {
		return Tools.ByteArray2StringGBK(srcalias);
	}
	public void setSrcalias(String srcalias) {
		Tools.String2ByteArrayGBK(this.srcalias, srcalias);
	}
	public String getToalias() {
		return Tools.ByteArray2StringGBK(toalias);
	}
	public void setToalias(String toalias) {
		Tools.String2ByteArrayGBK(this.toalias, toalias);
	}
	public String getSztext() {
		return Tools.ByteArray2StringGBK(sztext);
	}
	public void setSztext(String sztext) {
		Tools.String2ByteArrayGBK(this.sztext, sztext);
	}

	
	
}
