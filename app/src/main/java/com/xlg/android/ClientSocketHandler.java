package com.xlg.android;

public interface ClientSocketHandler {
	// 连接返回函数, 返回非0表示错误
	public void onConnected(int ret);
	// 接收回调函数
	public void onRecv(byte[] data, int size);
	// 断开回调函数
	public void onDisconnected();
}
