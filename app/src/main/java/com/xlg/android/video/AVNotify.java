package com.xlg.android.video;

public interface AVNotify {
	public void onVideo(int ssrc, int width, int height, byte[] img);
	public void onAudio(int ssrc, int sample, int channel, byte[] pcm);
}
