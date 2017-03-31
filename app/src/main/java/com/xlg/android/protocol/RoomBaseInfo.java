package com.xlg.android.protocol;

import com.xlg.android.utils.Tools;

public class RoomBaseInfo {
	@StructOrder(0)
	private int	vcbid;
	@StructOrder(1)
	private int	userid;				//用户ID
	@StructOrder(2)
	private int	groupid;			//房间组ID
	@StructOrder(3)
	private int	level;				//房间级别
	@StructOrder(4)
	private short	seats;				//座位数
	@StructOrder(5)
	private short	visible;			//是否可见 0－不可见，1－可见
	@StructOrder(6)
	private short	mask;				//操作（用于请求时）
	@StructOrder(7)
	private short	reserve;			//保留
	@StructOrder(8)
	private int	createrid;			//房主id
	@StructOrder(9)
	private int	op1id;				//1第二房主
	@StructOrder(10)
	private int	op2id;				//2第二房主
	@StructOrder(11)
	private int	op3id;				//3第三房主
	@StructOrder(12)
	private int	op4id;				//4第四房主
	@StructOrder(13)
	private int	op5id;				//5第五房主
	@StructOrder(14)
	private int	op6id;				//6第六房主
	@StructOrder(15)
	private int	runstate;			//使用bit,一共可以放置32个,第1位:是否允许悄悄话? 第2位:是否允许仍塞子，上传和广播时均是混合结果数据.
	@StructOrder(16)
	private byte[]			name = new byte[32];	//房间名
	@StructOrder(17)
	private byte[]			pwd = new byte[32];		//密码
	@StructOrder(18)
	private byte[]			theme = new byte[64];	//房间描述。
	@StructOrder(19)
	private long           nmictime;           //房间麦时
	@StructOrder(20)
	private int           nmiclimit;          //每个人最多排多少个麦
	@StructOrder(21)
	private int           nmiclimitall;       //房间总麦序人数最多排几个
	
	
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
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public short getSeats() {
		return seats;
	}
	public void setSeats(short seats) {
		this.seats = seats;
	}
	public short getVisible() {
		return visible;
	}
	public void setVisible(short visible) {
		this.visible = visible;
	}
	public short getMask() {
		return mask;
	}
	public void setMask(short mask) {
		this.mask = mask;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
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
	public int getRunstate() {
		return runstate;
	}
	public void setRunstate(int runstate) {
		this.runstate = runstate;
	}
	public String getName() {
		return Tools.ByteArray2StringGBK(name);
	}
	public void setName(String name) {
		Tools.String2ByteArrayGBK(this.name, name);
	}
	public String getPwd() {
		return Tools.ByteArray2StringGBK(pwd);
	}
	public void setPwd(String pwd) {
		Tools.String2ByteArrayGBK(this.pwd, pwd);
	}
	public String getTheme() {
		return Tools.ByteArray2StringGBK(theme);
	}
	public void setTheme(String theme) {
		Tools.String2ByteArrayGBK(this.theme, theme);
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
	
	
}
