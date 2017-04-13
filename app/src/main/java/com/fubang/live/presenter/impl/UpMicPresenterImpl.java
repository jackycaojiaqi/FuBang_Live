package com.fubang.live.presenter.impl;


import com.fubang.live.entities.RoomEntity;
import com.fubang.live.modle.ModelFactory;
import com.fubang.live.presenter.RoomListPresenter;
import com.fubang.live.presenter.UpMicPresenter;
import com.fubang.live.view.RoomListView;
import com.fubang.live.view.UpMicView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首页房间列表逻辑处理
 * Created by dell on 2016/4/7.
 */
public class UpMicPresenterImpl implements UpMicPresenter {
    private UpMicView roomListView;
    private int uid;

    public UpMicPresenterImpl(UpMicView roomListView, int uid) {
        this.roomListView = roomListView;
        this.uid = uid;
    }

    @Override
    public void getUpMicInfo() {
        ModelFactory.getInstance().getUpMicModelImpl().getUpMicData(new Callback<RoomEntity>() {
            @Override
            public void onResponse(Call<RoomEntity> call, Response<RoomEntity> response) {
                roomListView.successUpMic(response.body());
            }

            @Override
            public void onFailure(Call<RoomEntity> call, Throwable t) {
                roomListView.faidedUpMic(t);
            }
        }, uid);
    }

}
