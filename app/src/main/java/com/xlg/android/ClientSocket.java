package com.xlg.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientSocket {
	// 事件回调
	protected ClientSocketHandler mHandler;
	// 线程事件
	private Thread mThread;
	private String mSvrIP;
	private int 	mSvrPort;
	private Socket mSocket;
	private OutputStream mOutput;
	
	public ClientSocket(ClientSocketHandler evt) {
		this.mHandler = evt;
	}
	
	// 连接服务器
	public int Connect(String ip, int port) {
		if(null != mThread && mThread.isAlive()) {
			return -1;
		}
		
		mSvrPort = port;
		mSvrIP = ip;
		// 启动线程
		mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				doSocketRun();
			}});
		
		mThread.start();
		return -1;
	}
	
	// 网络连接线程
	protected void doSocketRun() {
		// 关闭原socket
		Close();
		// 申明新的socket
		try {
			mSocket = new Socket(mSvrIP, mSvrPort);
		} catch (IOException e) {
			e.printStackTrace();
			mHandler.onConnected(-1);
			return;
		}
		
		// 获取输出缓冲区
		try {
			mOutput = mSocket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mHandler.onConnected(-1);
			return;
		}
		
		mHandler.onConnected(0);
		// 获取输出缓冲区
		try {
			InputStream input = mSocket.getInputStream();
			// 分配空间
			byte [] data = new byte[1024];
			
			try {
				while(true) {
					int ret = input.read(data);
					
					mHandler.onRecv(data, ret);
				}
			}catch(SocketException e) {
				e.printStackTrace();
			}
			finally {
				input.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mHandler.onDisconnected();
		}
	}

	// 关闭服务器连接
	public void Close() {
		synchronized (this) {
			// 关闭发送流
			if(null != mOutput) {
				try {
					mOutput.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				mOutput = null;
			}
			
			// 关闭socket
			if(null != mSocket) {
				try {
					mSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				mSocket = null;
			}			
		}
	}
	
	// 发送信息到服务器
	public int Send(byte [] data) {
		synchronized (this) {
			if(null == mOutput) {
				return -1;
			}
			
			try {
				mOutput.write(data);
				return 0;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return -1;
	}
}
