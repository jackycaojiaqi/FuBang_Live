package com.fubang.live.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.LiveMusicAdapter;
import com.fubang.live.adapter.RoomVideoAdapter;
import com.fubang.live.base.BaseFragment;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RoomListEntity;
import com.fubang.live.ui.RoomActivity;
import com.fubang.live.util.ToastUtil;
import com.fubang.live.widget.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicHotFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_near)
    RecyclerView rvNear;
    Unbinder unbinder;
    @BindView(R.id.srl_near)
    SwipeRefreshLayout srlNear;
    private Context context;
    private int count = 10;
    private int page = 1;
    private int group = 0;
    private List<RoomListEntity> list = new ArrayList<>();
    private BaseQuickAdapter roomFavAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_hot, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        initview();
    }

    @Override
    public void onResume() {
        super.onResume();
        initdate();
    }

    private void initview() {

        //=========================recycleview
        roomFavAdapter = new LiveMusicAdapter(R.layout.item_music_mine, list);
        rvNear.setLayoutManager(new LinearLayoutManager(getActivity()));
        roomFavAdapter.openLoadAnimation();
        roomFavAdapter.setAutoLoadMoreSize(5);
        roomFavAdapter.setEnableLoadMore(true);
        roomFavAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.show(context, position + " ");
            }
        });
        roomFavAdapter.bindToRecyclerView(rvNear);
        roomFavAdapter.setEmptyView(R.layout.empty_view);
        rvNear.setAdapter(roomFavAdapter);
        //水平分割线
        rvNear.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL_LIST, 5, getActivity().getResources().getColor(R.color.transparent)));
        //=====================下拉刷新
        srlNear.setOnRefreshListener(this);
        //设置样式刷新显示的位置
        srlNear.setProgressViewOffset(true, -10, 50);
    }

    private RoomEntity roomEntity;

    private void initdate() {
        String url = AppConstant.BASE_URL + AppConstant.MSG_GET_ROOM_INFO;
        OkGo.get(url)//
                .tag(this)//
                .params(AppConstant.COUNT, count)
                .params(AppConstant.PAGE, page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        srlNear.setRefreshing(false);
                        try {
                            roomEntity = new Gson().fromJson(s, RoomEntity.class);
                            if (roomEntity.getStatus().equals("success")) {
                                list.clear();
                                List<RoomListEntity> roomListEntities = roomEntity.getRoomlist();
                                list.addAll(roomListEntities);
                                roomFavAdapter.notifyDataSetChanged();
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            list.clear();
                            roomFavAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();
                        srlNear.setRefreshing(false);
                    }
                });
    }


    @Override
    public void onRefresh() {
        page = 1;
        initdate();
    }

}
