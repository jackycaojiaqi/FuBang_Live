package com.fubang.live.modle;


import com.fubang.live.entities.RoomEntity;

import retrofit2.Callback;

/**
 * Created by dell on 2016/4/7.
 */
public interface RoomListModel {
    void getRoomListData(Callback<RoomEntity> callback, int count, int page, int groupId);
}
