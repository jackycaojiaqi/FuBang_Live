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
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.RoomFavAdapter;
import com.fubang.live.base.BaseFragment;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RoomListEntity;
import com.fubang.live.listener.HidingScrollListener;
import com.fubang.live.listener.UpDownScrollListener;
import com.fubang.live.presenter.impl.RoomListPresenterImpl;
import com.fubang.live.ui.RoomActivity;
import com.fubang.live.view.RoomListView;
import com.fubang.live.widget.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoader;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_follow)
    RecyclerView rvFollow;
    @BindView(R.id.srl_room)
    SwipeRefreshLayout srlRoom;
    private Context context;
    private int count = 10;
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
        initview();
    }

    @Override
    public void onResume() {
        super.onResume();
        initdate();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void initview() {
        //=========================recycleview
        roomFavAdapter = new RoomFavAdapter(R.layout.item_room, list);
        rvFollow.setLayoutManager(new GridLayoutManager(getActivity(), 1));
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
        roomFavAdapter.bindToRecyclerView(rvFollow);
        roomFavAdapter.setEmptyView(R.layout.empty_view);
        rvFollow.setAdapter(roomFavAdapter);
        //水平分割线
        rvFollow.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL_LIST, 5, getActivity().getResources().getColor(R.color.gray_c)));
        //=====================下拉刷新
        srlRoom.setOnRefreshListener(this);
        //设置样式刷新显示的位置
        srlRoom.setProgressViewOffset(true, -10, 50);

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
                        srlRoom.setRefreshing(false);
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
                        srlRoom.setRefreshing(false);
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        page = 1;
        initdate();
    }
}
