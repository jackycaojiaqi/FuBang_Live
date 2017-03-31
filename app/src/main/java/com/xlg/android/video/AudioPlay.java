package com.xlg.android.video;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AudioPlay {
	private boolean isPlaying = false;

	private int audioTime = -1;// 时间戳
	private int audioType = -1; // 语音类型， 0表示adpcm
	private int audioCycle = -1; // 赫兹
	private int audioBit = -1; // 采样位数
	private int audioChannel = -1; // 声道
	private int audioBlock = -1; // 原始块大小
	private ConcurrentLinkedQueue<byte[]> audioData = new ConcurrentLinkedQueue<byte[]>();
	private AudioTrack audioTrack; // 音频播放类
	private byte blockBuff[];
	private byte adpcmBuff[];
	private boolean isStop = false;

	public AudioPlay() {

	}
	public void start(){
		isStop = false;
	}
	/**
	 * 开始播放
	 */
	public void play(byte[] head) {
		if(false == isStop) {
			try{
				if (audioTrack != null && head!= null) {
					audioTrack.write(head, 0, head.length);
					audioTrack.flush();
//					Log.d("123","audiotrack ----head"+head.length);
				}
			} catch(Exception e) {
				System.out.println("onAudio play error:" + e.getMessage());
				e.printStackTrace();
			}
		}
	}



	/**
	 * 停止播放
	 */
	public void stop() {
		isStop = true;
		try {
			if (null != audioTrack) {
				Log.d("123", "audioTrack----");
				audioTrack.stop();
				audioTrack.release();
				audioTrack = null;
				audioCycle = 0;
				audioChannel = 0;
			}
		}catch (IllegalStateException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置采样率
	 */
	public void setConfig(int sample, int channel) {
		if(false != isStop) {
			return;
		}
		sample = 44100;
		if(audioCycle == sample && audioChannel == channel) {
			return;
		}
//		audioCycle = 44100;
		audioCycle = sample;
		audioChannel = channel;
		
		if (null != audioTrack) {
			audioTrack.stop();
			audioTrack.release();
			audioTrack = null;
		}
		
		try{
			int format = (2==audioChannel)? AudioFormat.CHANNEL_CONFIGURATION_STEREO: AudioFormat.CHANNEL_CONFIGURATION_DEFAULT;
			int bufsize = AudioTrack.getMinBufferSize(audioCycle, audioChannel, format);// audioCycle * 20;
			audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
					audioCycle,
					format,
					AudioFormat.ENCODING_PCM_16BIT, bufsize,
					AudioTrack.MODE_STREAM);
			audioTrack.play();
		} catch( Exception e) {
			System.out.println("onAudio error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
