package com.xlg.android.protocol;

public class LotteryNotice {
	@StructOrder(0)
	private int vcbid;        //房间id
	@StructOrder(1)
	private int srcid;        //赠送userid
	@StructOrder(2)
	private short giftid;     //礼物id
	@StructOrder(3)
	private int number1;     //10倍数
	@StructOrder(4)
	private int number2;      //50倍数
	@StructOrder(5)
	private int number3;		//100倍数
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
	public short getGiftid() {
		return giftid;
	}
	public void setGiftid(short giftid) {
		this.giftid = giftid;
	}
	public int getNumber1() {
		return number1;
	}
	public void setNumber1(int number1) {
		this.number1 = number1;
	}
	public int getNumber2() {
		return number2;
	}
	public void setNumber2(int number2) {
		this.number2 = number2;
	}
	public int getNumber3() {
		return number3;
	}
	public void setNumber3(int number3) {
		this.number3 = number3;
	}

}
