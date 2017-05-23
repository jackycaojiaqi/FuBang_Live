package com.fubang.live.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.fubang.live.entities.AdEntity;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RoomListEntity;
import com.fubang.live.listener.HidingScrollListener;
import com.fubang.live.listener.UpDownScrollListener;
import com.fubang.live.presenter.impl.RoomListPresenterImpl;
import com.fubang.live.ui.RoomActivity;
import com.fubang.live.util.LiteOrmDBUtil;
import com.fubang.live.view.RoomListView;
import com.fubang.live.widget.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.socks.library.KLog;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
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
public class HotFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_hot)
    RecyclerView rvhot;
    @BindView(R.id.srl_room)
    SwipeRefreshLayout srlRoom;
    private Context context;
    private int count = 10;
    private int page = 1;
    private int date_type = 0;//0 第一次进入这个刷新   1、下拉刷新  2上拉加载
    private int group = 0;
    private List<RoomListEntity> list = new ArrayList<>();
    private BaseQuickAdapter roomFavAdapter;
    private List<String> list_url = new ArrayList<>();
    private Banner banner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
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
        page = 1;
        date_type = 0;
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
        rvhot.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        roomFavAdapter.openLoadAnimation();
        roomFavAdapter.setEnableLoadMore(true);
        roomFavAdapter.bindToRecyclerView(rvhot);
        roomFavAdapter.setEmptyView(R.layout.empty_view);
        roomFavAdapter.disableLoadMoreIfNotFullPage();//检查是否满一屏，如果不满足关闭loadMore   必须在bindToRecyclerView之后
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
        roomFavAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                rvhot.postDelayed(new Runnable() {//延迟800毫秒再操作，防止上拉加载线程 和正常刷新线程回调时间引起的数据异常
                    @Override
                    public void run() {
                        page++;
                        date_type = 2;
                        initdate();
                    }
                }, 800);
            }
        });
        rvhot.setAdapter(roomFavAdapter);
        //水平分割线
        rvhot.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL_LIST, 5, getActivity().getResources().getColor(R.color.gray_c)));
        //======================banner
        final List<AdEntity.PicListBean> listBeen = LiteOrmDBUtil.getQueryAll(AdEntity.PicListBean.class);
        if (listBeen.size() > 0) {
            list_url.clear();
            for (int i = 0; i < listBeen.size(); i++) {
                list_url.add(listBeen.get(i).getXuhao());
            }
            View header = LayoutInflater.from(context).inflate(R.layout.header, rvhot, false);
            banner = (Banner) header.findViewById(R.id.banner);
            //banner数据
            //设置图片加载器
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Uri uri = Uri.parse(listBeen.get(position).getHref());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
            banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            banner.setImages(list_url);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
            banner.setDelayTime(3000);
            roomFavAdapter.setHeaderView(header);
        }
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
                            if (roomEntity != null) {
                                if (roomEntity.getStatus().equals("success")) {
                                    if (date_type == 0 || date_type == 1) {//下拉刷新和第一次处理数据方式一致
                                        list.clear();
                                        List<RoomListEntity> roomListEntities = roomEntity.getRoomlist();
                                        list.addAll(roomListEntities);
                                        roomFavAdapter.notifyDataSetChanged();
                                    } else if (date_type == 2) {//上拉加载
                                        if (roomEntity.getRoomlist().size() > 0) {
                                            list.addAll(roomEntity.getRoomlist());
                                            roomFavAdapter.notifyDataSetChanged();
                                            roomFavAdapter.loadMoreComplete();
                                        } else {
                                            roomFavAdapter.notifyDataSetChanged();
                                            roomFavAdapter.loadMoreEnd();
                                        }
                                    }

                                }
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            if (date_type == 1) {
                                list.clear();
                            }
                            roomFavAdapter.loadMoreEnd();
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
        date_type = 1;
        initdate();

    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).fit().into(imageView);
        }
    }
}
