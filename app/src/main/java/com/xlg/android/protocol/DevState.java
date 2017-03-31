package com.xlg.android.protocol;

public class DevState {
	   private int    vcbid;      //房间id
	   private int    userid;     //发送请求者ID
	   private byte   audiostate; //设备状态
	   private byte   videostate; //设备状态 ,0-无视频设备 ,2-有视频设备， 1-视频关了(有视频设备,但发送者禁止发送数据)
	   private short  reserve;    //保留
	   
	   
	public int getVcbid() {
		return vcbid;
	}
	public void setVcbid(int vcbid) {
		this.vcbid = vcbid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public byte getAudiostate() {
		return audiostate;
	}
	public void setAudiostate(byte audiostate) {
		this.audiostate = audiostate;
	}
	public byte getVideostate() {
		return videostate;
	}
	public void setVideostate(byte videostate) {
		this.videostate = videostate;
	}
	public short getReserve() {
		return reserve;
	}
	public void setReserve(short reserve) {
		this.reserve = reserve;
	}

	   
}
