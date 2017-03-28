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
import com.fubang.live.R;
import com.fubang.live.adapter.RoomFavAdapter;
import com.fubang.live.base.BaseFragment;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RoomListEntity;
import com.fubang.live.listener.HidingScrollListener;
import com.fubang.live.listener.OnVerticalScrollListener;
import com.fubang.live.presenter.impl.RoomListPresenterImpl;
import com.fubang.live.ui.RoomActivity;
import com.fubang.live.util.ScreenUtils;
import com.fubang.live.view.RoomListView;
import com.fubang.live.widget.DividerItemDecoration;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowFragment extends BaseFragment implements RoomListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_follow)
    RecyclerView rvFollow;
    @BindView(R.id.srl_room)
    SwipeRefreshLayout srlRoom;
    private Context context;
    private RoomListPresenterImpl presenter;
    private int count = 40;
    private int page = 1;
    private int group = 0;
    private List<RoomListEntity> list = new ArrayList<>();
    private BaseQuickAdapter roomFavAdapter;
    private List<String> list_url = new ArrayList<>();
    private Banner banner;

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

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if (banner != null) {
            banner.stopAutoPlay();
        }

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
                startActivity(intent);
            }
        });
        rvFollow.setAdapter(roomFavAdapter);
        //水平分割线
        rvFollow.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL_LIST, 10, getActivity().getResources().getColor(R.color.color_main)));
        //======================banner
        list_url.add("http://img05.tooopen.com/images/20150830/tooopen_sy_140703593676.jpg");
        list_url.add("http://img.sc115.com/uploads1/sc/jpgs/1503/apic19333_sc115.com.jpg");
        list_url.add("http://img.7139.com/file/201207/04/299ac0ab2be96c216c6bd5255945cb6c.jpg");
        View header = LayoutInflater.from(context).inflate(R.layout.header, rvFollow, false);
        banner = (Banner) header.findViewById(R.id.banner);
        //banner数据
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list_url);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setDelayTime(3000);
        roomFavAdapter.setHeaderView(header);
        //=====================下拉刷新
        srlRoom.setOnRefreshListener(this);
        //设置样式刷新显示的位置
        srlRoom.setProgressViewOffset(true, -20, 100);

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
        rvFollow.clearOnScrollListeners();
        rvFollow.setOnScrollListener(new HidingScrollListener(roomListEntities.size()) {
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
        Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                srlRoom.setRefreshing(false);
            }
        }, 2000);
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            //Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);
        }
    }
}
