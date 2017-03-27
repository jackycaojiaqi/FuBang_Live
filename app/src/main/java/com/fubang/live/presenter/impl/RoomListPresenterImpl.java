package com.fubang.live.presenter.impl;


import com.fubang.live.entities.RoomEntity;
import com.fubang.live.modle.ModelFactory;
import com.fubang.live.presenter.RoomListPresenter;
import com.fubang.live.view.RoomListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首页房间列表逻辑处理
 * Created by dell on 2016/4/7.
 */
public class RoomListPresenterImpl implements RoomListPresenter {
    private RoomListView roomListView;
    private int count;
    private int page;
    private int group;

    public RoomListPresenterImpl(RoomListView roomListView, int count, int page, int group) {
        this.roomListView = roomListView;
        this.count = count;
        this.page = page;
        this.group = group;
    }

    @Override
    public void getRoomList() {
        ModelFactory.getInstance().getRoomListModelImpl().getRoomListData(new Callback<RoomEntity>() {
            @Override
            public void onResponse(Call<RoomEntity> call, Response<RoomEntity> response) {
                roomListView.successRoomList(response.body());
            }

            @Override
            public void onFailure(Call<RoomEntity> call, Throwable t) {
                roomListView.faidedRoomList();
            }
        }, count, page, group);
    }
}
