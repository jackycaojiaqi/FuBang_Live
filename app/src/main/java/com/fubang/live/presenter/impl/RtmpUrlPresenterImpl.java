package com.fubang.live.presenter.impl;


import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RtmpUrlEntity;
import com.fubang.live.modle.ModelFactory;
import com.fubang.live.presenter.RoomListPresenter;
import com.fubang.live.presenter.RtmpUrlPresenter;
import com.fubang.live.view.RoomListView;
import com.fubang.live.view.RtmpUrlView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首页房间列表逻辑处理
 * Created by dell on 2016/4/7.
 */
public class RtmpUrlPresenterImpl implements RtmpUrlPresenter {
    private RtmpUrlView roomListView;
    private String RoomId;
    private String userId;

    public RtmpUrlPresenterImpl(RtmpUrlView roomListView, String RoomId, String userId) {
        this.roomListView = roomListView;
        this.RoomId = RoomId;
        this.userId = userId;
    }


    @Override
    public void getRtmpUrl() {
        ModelFactory.getInstance().getRtmpUrlImpl().getRtmpUrl(new Callback<RtmpUrlEntity>() {
            @Override
            public void onResponse(Call<RtmpUrlEntity> call, Response<RtmpUrlEntity> response) {
                roomListView.success(response.body());
            }

            @Override
            public void onFailure(Call<RtmpUrlEntity> call, Throwable t) {
                roomListView.faided();
            }
        }, RoomId, userId);
    }
}
