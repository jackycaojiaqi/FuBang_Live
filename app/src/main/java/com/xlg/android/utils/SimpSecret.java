package com.xlg.android.utils;

public class SimpSecret {
	public static final byte SECRET_MAGIC = (byte) 0x15;
	
	public static void SimpEncrypt(byte[] data) {
		SimpEncrypt(data, SECRET_MAGIC);
	}
	
	public static void SimpDecrypt(byte [] data) {
		SimpDecrypt(data, SECRET_MAGIC);
	}
	
	// 加密
	public static void SimpEncrypt(byte [] data, byte magic) {
		if(null == data || data.length < 1) {
			return;
		}
		
		int i;
		data[0] ^= magic;

		for(i = 1; i < data.length; i++)
		{
			data[i] ^= data[i-1];
		}
		
		return;
	}
	
	// 解密
	public static void SimpDecrypt(byte [] data, byte magic) {
		if(null == data || data.length < 1) {
			return;
		}

		int i;
		for(i = data.length - 1; i > 0; i--)
		{
			data[i] ^= data[i-1];
		}
		
		data[0] ^= magic;

	}
}
