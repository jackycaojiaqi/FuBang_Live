package com.xlg.android.protocol;

public class ChestBonusAmount {
	@StructOrder(0)
	private int newAmount;    //当前奖池总数

	public int getNewAmount() {
		return newAmount;
	}

	public void setNewAmount(int newAmount) {
		this.newAmount = newAmount;
	}
	
	
}
