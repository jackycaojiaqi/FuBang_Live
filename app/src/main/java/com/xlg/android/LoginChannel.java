package com.xlg.android;

import com.xlg.android.protocol.Header;
import com.xlg.android.protocol.Hello;
import com.xlg.android.protocol.LogonError;
import com.xlg.android.protocol.LogonRequest;
import com.xlg.android.protocol.LogonResponse;
import com.xlg.android.protocol.Message;
import com.xlg.android.protocol.RegisterRequest;
import com.xlg.android.utils.ByteBuffer;

public class LoginChannel  implements ClientSocketHandler {
	// 客户端回调
	private LoginHandler mHandler;
	// 新的socket对像
	private ClientSocket mSocket = new ClientSocket(this);
	// 接收缓冲区
	private ByteBuffer mBuffer = new ByteBuffer();
	
	
	public LoginChannel(LoginHandler handler) {
		mHandler = handler;
	}
	
	// 连接服务器
	public int Connect(String ip, int port) {
		return mSocket.Connect(ip, port);
	}
	
	// 关闭
	public void Close() {
		mSocket.Close();
	}

	@Override
	public void onConnected(int ret) {
		if(0 == ret) {
			mHandler.onConnectSuccessed();
		} else {
			mHandler.onConnectFailed();
		}
	}

	@Override
	public void onRecv(byte[] data, int size) {
//		System.out.println("接收数据"+data.length+"----size"+size);
		// 加入缓冲区
		mBuffer.addBytes(data, 0, size);
//		System.out.println("mBuffer"+mBuffer.size());
		// 解析数据
		Header head = new Header();
		int len;
		
		while(true)
		{
			len = Message.DecodeHeader(mBuffer, head);
//			System.out.println("len"+len);
			if(len <= 0) {
				break;
			}
			System.out.println("登录响应"+head.getCmd1());
			switch (head.getCmd1()) {

			case Header.MessageType_mxpLogonResponse:
			{
				LogonResponse obj = new LogonResponse();
				Message.DecodeObject(mBuffer, obj);
				mHandler.onLogonResponse(obj);
				break;
			}
			case Header.MessageType_mxpLogonError:
			{
				LogonError obj = new LogonError();
				Message.DecodeObject(mBuffer, obj);
				mHandler.onLogonError(obj);
				break;
			}
			default:
				break;
			}
			
			// 移除一个包
			mBuffer.rdarin(head.getLength());
		}		
	}

	@Override
	public void onDisconnected() {
		mHandler.onDisconnected();
	}

	private void sendPack(Header head, Object obj) {
		ByteBuffer buf = new ByteBuffer();
		
		if( Message.EncodePack(buf, head, obj, true) ) {
			byte [] data = new byte[buf.size()];
			
			buf.getBytes(data, 0, buf.size());
			mSocket.Send(data);
		}
	}
	
	// 发送Hello
	public void SendHello() {
		Header head = new Header();
		Hello obj = new Hello();
		
		// 构建消息头
		head.setCmd1(Header.MessageType_mxpClientHelloCommand);
		// 构建消息体
		obj.setParam0((int)(Math.random() * 0xffff));
		obj.setParam1(obj.getParam0() + (obj.getParam0() & 0xffff));
		obj.setParam2(obj.getParam0() - 2 * (obj.getParam0() & 0xffff));
		
		sendPack(head, obj);
	}
	
	// 发送登录请求
	public void SendLogonRequest(int nUserId, int visitor, int nOnlineSate, String pszPwd,
								 String pszMacAddr, String pszDiskId, String cldcode, int flag) {
		Header head = new Header();
		LogonRequest obj = new LogonRequest();
		
		// 构建消息头
		head.setCmd1(Header.MessageType_mxpLogonRequest);
		// 构建消息体
		obj.setUserid(nUserId);
//		obj.setPort((short) 2);
		obj.setPort((short)2);
		obj.setVisitor((byte)visitor);
		obj.setOnline_stat((byte)nOnlineSate);
		if (flag == 0) {
			obj.setCuserpwd(pszPwd);
		}
		obj.setCmac(pszMacAddr);

		if (flag == 2 ){
			obj.setCldcode(cldcode);
		}
		sendPack(head, obj);
	}
	
	// 临时用户登录请求
	public void SendTempUserLogonRequest(String pszMacAddr, String pszDiskId) {
		Header head = new Header();
		LogonRequest obj = new LogonRequest();
		
		// 构建消息头
		head.setCmd1(Header.MessageType_mxpLogonRequest);
		// 构建消息体
		obj.setVisitor((byte)1);
		obj.setOnline_stat((byte)1);
		obj.setCmac(pszMacAddr);
		
		sendPack(head, obj);
	}
	//注册
	public void SendRegisterRequest(String psd,String cldcode){
		Header head = new Header();
		RegisterRequest obj = new RegisterRequest();
		head.setCmd1(Header.MessageType_mxpRegisterRequest);
		obj.setDeviceid((short) 2);
		obj.setVisitor((byte) 5);
		obj.setCldcode(cldcode);
		obj.setCuserpwd(psd);
		sendPack(head,obj);
	}
}
