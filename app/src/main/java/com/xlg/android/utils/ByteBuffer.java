package com.xlg.android.utils;

import java.io.UnsupportedEncodingException;

public class ByteBuffer {
	private int mSize = 0;
	private byte [] mI2A = new byte[8];
	private byte [] mData = null;
	
	public ByteBuffer() {
		
	}
	
	public void addBytes(byte [] data, int start, int len) {
		if(len <= 0) {
			return;
		}
		
		if(null == mData) {
			int size = (len + 1023) / 8 * 8;
			mData = new byte[size];
		} else if((mSize + len) >= mData.length) {
			int size = (mSize + len + 1023) / 8 * 8;
			byte [] tmp = new byte[size];
			
			System.arraycopy(mData, 0, tmp, 0, mSize);
			mData = tmp;
		}
		
		System.arraycopy(data, start, mData, mSize, len);
		mSize += len;
	}
	
	public void addByte(byte i) {
		mI2A[0] = i;
		
		addBytes(mI2A, 0, 1);
	}
	
	public void addShort(short i) {
		mI2A[0] = (byte) (i & 0xff);
		mI2A[1] = (byte) ((i >> 8) & 0xff);
		
		addBytes(mI2A, 0, 2);
	}
	
	public void addInt(int i) {
		mI2A[0] = (byte)(i & 0xff);
		mI2A[1] = (byte)((i >> 8) & 0xff);
		mI2A[2] = (byte)((i >> 16) & 0xff);
		mI2A[3] = (byte)((i >> 24) & 0xff);
		
		addBytes(mI2A, 0, 4);
	}
	
	public void addLong(long i) {
		mI2A[0] = (byte)(i & 0xff);
		mI2A[1] = (byte)((i >> 8) & 0xff);
		mI2A[2] = (byte)((i >> 16) & 0xff);
		mI2A[3] = (byte)((i >> 24) & 0xff);
		mI2A[4] = (byte)((i >> 32) & 0xff);
		mI2A[5] = (byte)((i >> 40) & 0xff);
		mI2A[6] = (byte)((i >> 48) & 0xff);
		mI2A[7] = (byte)((i >> 56) & 0xff);
		
		addBytes(mI2A, 0, 8);
		
	}
	
	public void addString(String txt, String charset) {
		byte [] by = null;
		
		try {
			by = txt.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(null != by) {
			addBytes(by, 0, by.length);
		}
	}
	
	public void addStringUTF(String txt) {
		addString(txt, "utf-8");
	}
	
	public void addStringGBK(String txt) {
		addString(txt, "gbk");
	}
	
	public void resize(int size) {
		mSize = size;
	}
	
	public int size() {
		return mSize;
	}
	
	public void clear() {
		mSize = 0;
		
		if(null == mData) {
			return;
		}
		
		// 清空内容
		for(int i = 0; i < mData.length; i++) {
			mData[i] = 0;
		}
	}
	
	public void rdarin(int size) {
		if(size <= 0) {
			return;
		}
		
		if(size >= mSize) {
			clear();
			return;
		}
		
		int len = mSize - size;
		byte [] tmp = new byte[mSize - size];
		System.arraycopy(mData, size, tmp, 0, len);
		// 复制数据区
		System.arraycopy(tmp, 0, mData, 0, len);
		mSize = len;
	}
	
	public byte getByte(int start) {
		if(mSize < (start + 1)) {
			return 0;
		}
		
		return mData[start];
	}
	
	public short getShort(int start) {
		if(mSize < (start + 2)) {
			return 0;
		}
		
		short ret = 0;
		
		ret = (short) ( (short)mData[start] & 0xff );
		ret = (short) (ret | (short)((mData[start + 1] << 8) & 0xff00) );
		
		return ret;
	}
	
	public int getInt(int start) {
		if(mSize < (start + 4)) {
			return 0;
		}
		
		int ret = 0;
		
		ret = (int)mData[start] & 0xff;
		ret = ret | (int)(((int)mData[start + 1] << 8) & 0xff00);
		ret = ret | (int)(((int)mData[start + 2] << 16) & 0xff0000);
		ret = ret | (int)(((int)mData[start + 3] << 24) & 0xff000000);
		
		return ret;
	}
	
	public long getLong(int start) {
		if(mSize < (start + 8)) {
			return 0;
		}
		
		long ret = 0;
				
		ret = (long)mData[start] & 0xff;
		ret = ret | (long)(((long)mData[start + 1] << 8) & 0xff00l);
		ret = ret | (long)(((long)mData[start + 2] << 16) & 0xff0000l);
		ret = ret | (long)(((long)mData[start + 3] << 24) & 0xff000000l);
		ret = ret | (long)(((long)mData[start + 4] << 32) & 0xff00000000l);
		ret = ret | (long)(((long)mData[start + 5] << 40) & 0xff0000000000l);
		ret = ret | (long)(((long)mData[start + 6] << 48) & 0xff000000000000l);
		ret = ret | (long)(((long)mData[start + 7] << 56) & 0xff00000000000000l);
		
		return ret;
	}
	
	public int getBytes(byte []data, int start, int len) {
		if(mSize < (len + start)) {
			return 0;
		}
		
		System.arraycopy(mData, start, data, 0, len);
		return data.length;
	}
	
	public byte[] getData() {
		return mData;
	}

	public byte[] toBytes() {
		if(mSize <= 0) {
			return null;
		}
		
		byte [] data = new byte[mSize];
		System.arraycopy(mData, 0, data, 0, mSize);
		return data;
	}
	
	public String toStringUTF() {
		return Tools.ByteArray2StringUTF(mData);
	}
	
	public String toStringGBK() {
		return Tools.ByteArray2StringGBK(mData);
	}
}
