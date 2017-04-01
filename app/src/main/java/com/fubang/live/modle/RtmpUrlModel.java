package com.fubang.live.modle;


import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RtmpUrlEntity;

import retrofit2.Callback;

/**
 * Created by dell on 2016/4/7.
 */
public interface RtmpUrlModel {
    void getRtmpUrl(Callback<RtmpUrlEntity> callback, String roomiId, String  userId);
}
