package com.xlg.android.video;

public class AVModuleMgr {
	private int _session = 0;
	
	private native int _Init();
	private native void _Uninit(int id);
	private native void _CreateRTPSession(int id, int port);
	private native void _DestroyRTPSession(int id);
	private native void _SetServerAddr2(int id, String ip, int port, int bisreset);
	private native void _StartRTPSession(int id);
	private native void _StopRTPSession(int id);

	private native void _AddAudioStream(int id, int ssrc, int nDataType, Object context);
	private native void _DelAudioStream(int id, int ssrc);

    private native void _AddVideoStream(int id, int ssrc, int losswaitIDRmode, int video_decoder_type, Object context); //1:Qeshow_H264, 2:huishi_H264
    private native void _DelVideoStream(int id, int ssrc);

    private native void _AddRTPRecver(int id, int recvertype, int ssrc, int pt, int jittertime);
    private native void _SetRTPRecverARQMode(int id, int ssrc, int pt, int buseARQ);

	static {
		System.loadLibrary("avmodule");
	}

	// 初始化
	public void Init() {
		_session = _Init();
	}
	
	// 注销
	public void Uninit() {
		_Uninit(_session);
	}
	
    // 创建会话
    public void CreateRTPSession(int port) {
    	_CreateRTPSession(_session, port);
    }
    
    public void DestroyRTPSession() {
    	_DestroyRTPSession(_session);
    }
    
    public void SetServerAddr2(String szIp, int port, int bisreset) {
    	_SetServerAddr2(_session, szIp, port, bisreset);
    }
    
    public void StartRTPSession() {
    	_StartRTPSession(_session);
    }
    
    public void StopRTPSession() {
    	_StopRTPSession(_session);
    }

    //增加声音流用户
    public void AddAudioStream(int ssrc, int nDataType, AVNotify notify) {
    	_AddAudioStream(_session, ssrc, nDataType, notify);
    }
    
    public void DelAudioStream(int ssrc) {
    	_DelAudioStream(_session, ssrc);
    }

    //增加视频流用户
    public void AddVideoStream(int ssrc, int losswaitIDRmode, int video_decoder_type, AVNotify notify) {
    	_AddVideoStream(_session, ssrc, losswaitIDRmode, video_decoder_type, notify);
    }
    
    public void DelVideoStream(int ssrc) {
    	_DelVideoStream(_session, ssrc);
    }

    public void AddRTPRecver(int recvertype, int ssrc, int pt, int jittertime) {
    	_AddRTPRecver(_session, recvertype, ssrc, pt, jittertime);
    }
    
    public void SetRTPRecverARQMode(int ssrc, int pt, int buseARQ) {
    	_SetRTPRecverARQMode(_session, ssrc, pt, buseARQ);
    }

}
