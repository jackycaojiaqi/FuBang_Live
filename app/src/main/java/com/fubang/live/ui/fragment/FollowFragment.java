package com.fubang.live.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.live.R;
import com.fubang.live.adapter.RoomFavAdapter;
import com.fubang.live.base.BaseFragment;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RoomListEntity;
import com.fubang.live.listener.HidingScrollListener;
import com.fubang.live.presenter.impl.RoomListPresenterImpl;
import com.fubang.live.ui.RoomActivity;
import com.fubang.live.view.RoomListView;
import com.socks.library.KLog;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowFragment extends BaseFragment implements RoomListView {
    @BindView(R.id.rv_follow)
    RecyclerView rvFollow;
    private Context context;
    private RoomListPresenterImpl presenter;
    private int count = 40;
    private int page = 1;
    private int group = 0;
    private List<RoomListEntity> list = new ArrayList<>();
    private BaseQuickAdapter roomFavAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        presenter = new RoomListPresenterImpl(this, count, page, group);
        initview();
        initdate();
    }

    private void initview() {
        roomFavAdapter = new RoomFavAdapter(R.layout.item_room, list);
        rvFollow.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        roomFavAdapter.openLoadAnimation();
//        roomFavAdapter.setAutoLoadMoreSize(5);
//        roomFavAdapter.setEnableLoadMore(true);
        roomFavAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                KLog.e(position);
                Intent intent = new Intent(context,RoomActivity.class);
                startActivity(intent);
            }
        });
        rvFollow.setAdapter(roomFavAdapter);

    }

    private void initdate() {
        presenter.getRoomList();

    }


    @Override
    public void successRoomList(RoomEntity entity) {
        if (page == 1) {
            list.clear();
        }
        List<RoomListEntity> roomListEntities = entity.getRoomlist();
        list.addAll(roomListEntities);
        roomFavAdapter.notifyDataSetChanged();
        rvFollow.setOnScrollListener(new HidingScrollListener(list.size()) {
            @Override
            public void onHide() {
                KLog.e("hide");
            }

            @Override
            public void onShow() {
                KLog.e("show");
            }
        });
    }

    @Override
    public void faidedRoomList() {
        Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
    }
}
