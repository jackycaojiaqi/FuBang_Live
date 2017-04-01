package com.fubang.live.modle.impl;


import com.fubang.live.AppConstant;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RtmpUrlEntity;
import com.fubang.live.modle.BaseModel;
import com.fubang.live.modle.BaseRtmpModel;
import com.fubang.live.modle.RoomListModel;
import com.fubang.live.modle.RtmpUrlModel;
import com.fubang.live.util.ParamsMap;

import retrofit2.Callback;

/**
 * Created by dell on 2016/4/7.
 */
public class RtmpUrlModelImpl extends BaseRtmpModel implements RtmpUrlModel {
    private static volatile RtmpUrlModelImpl instance = null;

    private RtmpUrlModelImpl(){

    }

    public static RtmpUrlModelImpl getInstance() {
        if (instance == null) {
            synchronized (RtmpUrlModelImpl.class) {
                if (instance == null) {
                    instance = new RtmpUrlModelImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void getRtmpUrl(Callback<RtmpUrlEntity> callback, String roomiId, String userId) {
        ParamsMap map = ParamsMap.getInstance();
        String url ="wh_"+roomiId+"_"+userId;
        map.put("streamKey",url);
        service.getRtmpUrlEntity(map).enqueue(callback);
    }
}
