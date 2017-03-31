package com.xlg.android.protocol;

import com.xlg.android.utils.ByteBuffer;

public class UseridList {
	@StructOrder(0)
	private short count;
	@StructOrder(1)
	private ByteBuffer list = new ByteBuffer();
	
	public int[] getList() {
		int cnt = count > (list.size() / 4) ? list.size() / 4 : count;
		int[] data = new int[cnt];
		for(int i = 0; i < cnt; i++) {
			data[i] = list.getInt(i * 4);
		}
		
		return data;
	}
	public void setList(int[] data) {
		this.list.clear();
		
		for(int i = 0; i < data.length; i++) {
			this.list.addInt(data[i]);
		}
		
		count = (short)data.length;
	}
	
	
}
