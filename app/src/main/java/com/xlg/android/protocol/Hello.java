package com.xlg.android.protocol;

public class Hello {	
	@StructOrder(0)
	private int param0;
	@StructOrder(1)
	private int param1;
	@StructOrder(2)
	private int param2;	
	
	public int getParam0() {
		return param0;
	}
	public void setParam0(int param0) {
		this.param0 = param0;
	}
	public int getParam1() {
		return param1;
	}
	public void setParam1(int param1) {
		this.param1 = param1;
	}
	public int getParam2() {
		return param2;
	}
	public void setParam2(int param2) {
		this.param2 = param2;
	}
	
	
}
