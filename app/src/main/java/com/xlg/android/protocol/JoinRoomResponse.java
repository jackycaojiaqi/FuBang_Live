package com.xlg.android.protocol;

import com.xlg.android.utils.ByteBuffer;
import com.xlg.android.utils.Tools;

public class JoinRoomResponse {
	@StructOrder(0)
	private int	userid;
	@StructOrder(1)
	private int	vcbid;								//房间id
	@StructOrder(2)
	private byte	        attributeid;                //房间属性（数字号码）
	@StructOrder(3)
	private byte   chestnum;                   //用户宝箱数目
	@StructOrder(4)
	private short	seats;						//座位数，目标1000~2000人。
	@StructOrder(5)
	private int	groupid;					//房间组ID
	@StructOrder(6)
	private int	runstate;					//房间管理状态。使用bit,一共可以放置32个,第1位:是否允许悄悄话? 第2位:是否允许仍塞子，上传和广播时均是混合结果数据
	@StructOrder(7)
	private int	createrid;					//房主id
	@StructOrder(8)
	private int	op1id;						//1第一房主
	@StructOrder(9)
	private int	op2id;						//2第二房主
	@StructOrder(10)
	private int	op3id;						//3第三房主
	@StructOrder(11)
	private int	op4id;						//4第四房主
	@StructOrder(12)
	private int	op5id;						//5第五房主
	@StructOrder(13)
	private int	op6id;						//6第六房主
	@StructOrder(14)
	private int	ntype;						//用来标识房间类型(1-横3; 2-竖3; 3-2视频)
	@StructOrder(15)
	private short	bkgroundid;					//房间背景id.
	@StructOrder(16)
	private short	reserve1;					//预留
//	unsigned int	op1list[ASSOWNERLEN];		//副房主列表 (暂未使用)
	@StructOrder(17)
	private short	playernumer;				//管理员列表人数。
	@StructOrder(18)
	private short	colorbarnum;			    //自己的彩条总数。
	@StructOrder(19)
	private long    	deposit;                    //银行存款
	@StructOrder(20)
	private long		nk;							//剩余K币(请求者的)
	@StructOrder(21)
	private long		nb;							//积分数(请求者的)
	@StructOrder(22)
	private int    nchestbonusamount;          //幸运奖池最新金额总数
	@StructOrder(23)
	private byte[]			vcbname = new byte[32];		//房间名称
	@StructOrder(24)
	private byte[]			vcbtheme = new byte[64];		//房间描述
	@StructOrder(25)
	private byte[]			mediaserver = new byte[128];	//房间媒体服务器。
	@StructOrder(26)
	private long           nmictime;                   //房间麦时
	@StructOrder(27)
	private int           nmiclimit;                  //房间默认上麦几次
	@StructOrder(28)
	private int           nmiclimitall;
	@StructOrder(29)
	private byte[]          carname = new byte[32];                    //用户座驾名字
	@StructOrder(30)
	private int 			ntimeelapse;					//在房间时长
	@StructOrder(31)
	private ByteBuffer playerlist = new ByteBuffer();				//管理员列表。变长
	
	
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
	public byte getAttributeid() {
		return attributeid;
	}
	public void setAttributeid(byte attributeid) {
		this.attributeid = attributeid;
	}
	public byte getChestnum() {
		return chestnum;
	}
	public void setChestnum(byte chestnum) {
		this.chestnum = chestnum;
	}
	public short getSeats() {
		return seats;
	}
	public void setSeats(short seats) {
		this.seats = seats;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public int getRunstate() {
		return runstate;
	}
	public void setRunstate(int runstate) {
		this.runstate = runstate;
	}
	public int getCreaterid() {
		return createrid;
	}
	public void setCreaterid(int createrid) {
		this.createrid = createrid;
	}
	public int getOp1id() {
		return op1id;
	}
	public void setOp1id(int op1id) {
		this.op1id = op1id;
	}
	public int getOp2id() {
		return op2id;
	}
	public void setOp2id(int op2id) {
		this.op2id = op2id;
	}
	public int getOp3id() {
		return op3id;
	}
	public void setOp3id(int op3id) {
		this.op3id = op3id;
	}
	public int getOp4id() {
		return op4id;
	}
	public void setOp4id(int op4id) {
		this.op4id = op4id;
	}
	public int getOp5id() {
		return op5id;
	}
	public void setOp5id(int op5id) {
		this.op5id = op5id;
	}
	public int getOp6id() {
		return op6id;
	}
	public void setOp6id(int op6id) {
		this.op6id = op6id;
	}
	public int getNtype() {
		return ntype;
	}
	public void setNtype(int ntype) {
		this.ntype = ntype;
	}
	public short getBkgroundid() {
		return bkgroundid;
	}
	public void setBkgroundid(short bkgroundid) {
		this.bkgroundid = bkgroundid;
	}
	public short getReserve1() {
		return reserve1;
	}
	public void setReserve1(short reserve1) {
		this.reserve1 = reserve1;
	}
	public short getPlayernumer() {
		return playernumer;
	}
	public void setPlayernumer(short playernumer) {
		this.playernumer = playernumer;
	}
	public short getColorbarnum() {
		return colorbarnum;
	}
	public void setColorbarnum(short colorbarnum) {
		this.colorbarnum = colorbarnum;
	}
	public long getDeposit() {
		return deposit;
	}
	public void setDeposit(long deposit) {
		this.deposit = deposit;
	}
	public long getNk() {
		return nk;
	}
	public void setNk(long nk) {
		this.nk = nk;
	}
	public long getNb() {
		return nb;
	}
	public void setNb(long nb) {
		this.nb = nb;
	}
	public int getNchestbonusamount() {
		return nchestbonusamount;
	}
	public void setNchestbonusamount(int nchestbonusamount) {
		this.nchestbonusamount = nchestbonusamount;
	}
	public String getVcbname() {
		return Tools.ByteArray2StringGBK(vcbname);
	}
	public void setVcbname(String vcbname) {
		// this.vcbname = vcbname;
		Tools.String2ByteArrayGBK(this.vcbname, vcbname);
	}
	public String getVcbtheme() {
		return Tools.ByteArray2StringGBK(vcbtheme);
	}
	public void setVcbtheme(String vcbtheme) {
		Tools.String2ByteArrayGBK(this.vcbtheme, vcbtheme);
	}
	public String getMediaserver() {
		return Tools.ByteArray2StringGBK(mediaserver);
	}
	public void setMediaserver(String mediaserver) {
		Tools.String2ByteArrayGBK(this.mediaserver, mediaserver);
	}
	public long getNmictime() {
		return nmictime;
	}
	public void setNmictime(long nmictime) {
		this.nmictime = nmictime;
	}
	public int getNmiclimit() {
		return nmiclimit;
	}
	public void setNmiclimit(int nmiclimit) {
		this.nmiclimit = nmiclimit;
	}
	public int getNmiclimitall() {
		return nmiclimitall;
	}
	public void setNmiclimitall(int nmiclimitall) {
		this.nmiclimitall = nmiclimitall;
	}
	public String getCarname() {
		return Tools.ByteArray2StringGBK(carname);
	}
	public void setCarname(String carname) {
		Tools.String2ByteArrayGBK(this.carname, carname);
	}

	public int getNtimeelapse() {
		return ntimeelapse;
	}

	public void setNtimeelapse(int ntimeelapse) {
		this.ntimeelapse = ntimeelapse;
	}

	public int[] getPlayerlist() {
		int len = playerlist.size() / 4;
		int[] ret = new int[len];
		
		for(int i = 0; i < len; i++) {
			ret[i] = playerlist.getInt(i * 4);
		}
		
		return ret;
	}
	public void setPlayerlist(int[] playerlist) {
		// this.playerlist = playerlist;
		this.playerlist.clear();
		for(int i = 0; i < playerlist.length; i++) {
			this.playerlist.addInt(playerlist[i]);
		}
	}
	
	
}
