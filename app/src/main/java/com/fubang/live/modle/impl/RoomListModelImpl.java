package com.fubang.live.modle.impl;


import com.fubang.live.AppConstant;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.modle.BaseModel;
import com.fubang.live.modle.RoomListModel;
import com.fubang.live.util.ParamsMap;

import retrofit2.Callback;

/**
 * Created by dell on 2016/4/7.
 */
public class RoomListModelImpl extends BaseModel implements RoomListModel {
    private static volatile RoomListModelImpl instance = null;

    private RoomListModelImpl(){

    }

    public static RoomListModelImpl getInstance() {
        if (instance == null) {
            synchronized (RoomListModelImpl.class) {
                if (instance == null) {
                    instance = new RoomListModelImpl();
                }
            }
        }
        return instance;
    }
    @Override
    public void getRoomListData(Callback<RoomEntity> callback, int count, int page, int groupId) {
        ParamsMap map = ParamsMap.getInstance();
        map.put(AppConstant.COUNT,count);
        map.put(AppConstant.PAGE,page);
        map.put(AppConstant.GROUP,groupId);
        service.getRoomEntity(map).enqueue(callback);
    }
}
