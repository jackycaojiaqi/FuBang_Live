package com.xlg.android.protocol;

import com.xlg.android.utils.ByteBuffer;

public class RoomManagerInfo {
	@StructOrder(0)
	private int	vcbid;						//房间ID
	@StructOrder(1)
	private int	userid;						//用户ID
	@StructOrder(2)
	private char	membertype;					//0-副房主，1-房主
	@StructOrder(3)
	private char   reserve1;                   //保留
	@StructOrder(4)
	private short	number;						//用户数
	@StructOrder(5)
	private ByteBuffer members;					//id列表，变长
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
	public char getMembertype() {
		return membertype;
	}
	public void setMembertype(char membertype) {
		this.membertype = membertype;
	}
	public char getReserve1() {
		return reserve1;
	}
	public void setReserve1(char reserve1) {
		this.reserve1 = reserve1;
	}
	public short getNumber() {
		return number;
	}
	public void setNumber(short number) {
		this.number = number;
	}
	public int[] getMembers() {
		int len = members.size() / 4;
		int [] data = new int[len];
		for(int i = 0; i < len; i++) {
			data[i] = members.getInt(i * 4);
		}
		
		return data;
	}
	public void setMembers(int[] members) {
		this.members.clear();
		for(int i = 0; i < members.length; i++) {
			this.members.addInt(members[i]);
		}
	}

	
	
}
