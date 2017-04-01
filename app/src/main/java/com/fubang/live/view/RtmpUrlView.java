package com.fubang.live.view;


import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RtmpUrlEntity;

/**
 * 显示房间列表的网络请求接口
 * Created by dell on 2016/4/7.
 */
public interface RtmpUrlView {
    void success(RtmpUrlEntity entity);
    void faided();
}
