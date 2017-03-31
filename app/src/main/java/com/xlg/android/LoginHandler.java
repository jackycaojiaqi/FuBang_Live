package com.xlg.android;

import com.xlg.android.protocol.LogonError;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.protocol.RegisterResponse;

// 各个事件回调函数

public interface LoginHandler {
	// 连接成功
	public void onConnectSuccessed();
	// 连接失败
	public void onConnectFailed();
	// 连接消息
	public void onLogonResponse(LogonResponse res);
	// 登录失败
	public void onLogonError(LogonError err);
	// 连接断开
	public void onDisconnected();

	public void onRegisterResponse(RegisterResponse res);
}
