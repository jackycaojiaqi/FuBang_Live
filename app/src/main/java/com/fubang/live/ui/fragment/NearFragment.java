package com.fubang.live.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.RoomFavAdapter;
import com.fubang.live.adapter.RoomNearAdapter;
import com.fubang.live.base.BaseFragment;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RoomListEntity;
import com.fubang.live.listener.HidingScrollListener;
import com.fubang.live.presenter.impl.RoomListPresenterImpl;
import com.fubang.live.ui.RoomActivity;
import com.fubang.live.view.RoomListView;
import com.fubang.live.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearFragment extends BaseFragment implements RoomListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.tv_near_filter_name)
    TextView tvNearFilterName;
    @BindView(R.id.rl_near_filter)
    RelativeLayout rlNearFilter;
    @BindView(R.id.rv_near)
    RecyclerView rvNear;
    Unbinder unbinder;
    @BindView(R.id.srl_near)
    SwipeRefreshLayout srlNear;
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
        View view = inflater.inflate(R.layout.fragment_near, container, false);
        unbinder = ButterKnife.bind(this, view);
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

        //=========================recycleview
        roomFavAdapter = new RoomNearAdapter(R.layout.item_room_near, list);
        rvNear.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        roomFavAdapter.openLoadAnimation();
        roomFavAdapter.setAutoLoadMoreSize(5);
        roomFavAdapter.setEnableLoadMore(true);
        roomFavAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, RoomActivity.class);
                intent.putExtra(AppConstant.ROOMID, list.get(position).getRoomid());
                intent.putExtra(AppConstant.ROOMIP, list.get(position).getGateway());
                intent.putExtra(AppConstant.ROOMPWD, list.get(position).getRoompwd());
                startActivity(intent);
            }
        });
        rvNear.setAdapter(roomFavAdapter);
        //=====================下拉刷新
        srlNear.setOnRefreshListener(this);
        //设置样式刷新显示的位置
        srlNear.setProgressViewOffset(true, -10, 50);

    }

    private void initdate() {
        presenter.getRoomList();

    }

    @OnClick(R.id.rl_near_filter)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_near_filter:
                break;
        }
    }


    @Override
    public void successRoomList(RoomEntity entity) {
        srlNear.setRefreshing(false);
        if (page == 1) {
            list.clear();
        }
        List<RoomListEntity> roomListEntities = entity.getRoomlist();
        list.addAll(roomListEntities);
        roomFavAdapter.notifyDataSetChanged();
        rvNear.clearOnScrollListeners();
        rvNear.setOnScrollListener(new HidingScrollListener(roomListEntities.size()) {
            @Override
            public void onHide() {

            }

            @Override
            public void onShow() {

            }
        });
    }

    @Override
    public void faidedRoomList() {
        srlNear.setRefreshing(false);
        Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        presenter.getRoomList();
    }

}
