package com.fubang.live.modle.impl;


import com.fubang.live.AppConstant;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.modle.BaseModel;
import com.fubang.live.modle.RoomListModel;
import com.fubang.live.modle.UpMicModel;
import com.fubang.live.util.ParamsMap;

import retrofit2.Callback;

/**
 * Created by dell on 2016/4/7.
 */
public class UpMicModelImpl extends BaseModel implements UpMicModel {
    private static volatile UpMicModelImpl instance = null;

    private UpMicModelImpl() {

    }

    public static UpMicModelImpl getInstance() {
        if (instance == null) {
            synchronized (UpMicModelImpl.class) {
                if (instance == null) {
                    instance = new UpMicModelImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void getUpMicData(Callback<RoomEntity> callback, int nvcbid) {
        ParamsMap map = ParamsMap.getInstance();
        map.put(AppConstant.NVCBID, nvcbid);
        service.getUpMic(map).enqueue(callback);
    }
}
