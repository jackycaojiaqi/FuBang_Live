package com.fubang.live.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.LiveMusicAdapter;
import com.fubang.live.base.BaseFragment;
import com.fubang.live.entities.MusicListEntity;
import com.fubang.live.util.FileUtils;
import com.fubang.live.util.LiteOrmDBUtil;
import com.fubang.live.util.ToastUtil;
import com.fubang.live.widget.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.socks.library.KLog;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static com.fubang.live.AppConstant.BASE_DOWNLOAD_LRC;
import static com.fubang.live.AppConstant.BASE_DOWNLOAD_MUSIC;

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
    private int count = 5;
    private int page = 1;
    private int group = 0;
    private List<MusicListEntity.MusicBean> list = new ArrayList<>();
    private BaseQuickAdapter roomFavAdapter;
    private CustomPopWindow pop_delete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        EventBus.getDefault().register(this);
        initview();
        initdate("");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //在用户信息界面发送的关注指令，在房间中操作
    @Subscriber(tag = "music_key")
    private void addFav(final String key) {
        initdate(key);
    }

    private void initview() {
        //=========================recycleview
        roomFavAdapter = new LiveMusicAdapter(R.layout.item_music_mine, list);
        rvNear.setLayoutManager(new LinearLayoutManager(getActivity()));
        roomFavAdapter.openLoadAnimation();
        roomFavAdapter.setAutoLoadMoreSize(5);
        roomFavAdapter.setEnableLoadMore(true);
        roomFavAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_delete_music, null);
                //处理popWindow 显示内容
                handleView(contentView, position);
                //创建并显示popWindow
                pop_delete = new CustomPopWindow.PopupWindowBuilder(getActivity())
                        .setView(contentView)
                        .setOutsideTouchable(false)//是否PopupWindow 以外触摸dissmiss
                        .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                        .setBgDarkAlpha(0.5f) // 控制亮度
                        .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                        .create()
                        .showAtLocation(srlNear, Gravity.CENTER, 0, 0);
                return false;
            }
        });
        roomFavAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.tv_music_download:
                        list.get(position).setState(1);
                        adapter.notifyDataSetChanged();
                        OkGo.get(BASE_DOWNLOAD_MUSIC + list.get(position).getMp3_path())//先下载MP3
                                .tag(this)//
                                .execute(new FileCallback(FileUtils.getMusicFiles(), list.get(position).getNid() + ".mp3") {  //文件下载时，可以指定下载的文件目录和文件名
                                    @Override
                                    public void onSuccess(File file, Call call, Response response) {
                                        OkGo.get(BASE_DOWNLOAD_LRC + list.get(position).getLrc())//再下载lrc
                                                .tag(this)//
                                                .execute(new FileCallback(FileUtils.getLrcFiles(), list.get(position).getNid() + ".lrc") {
                                                    @Override
                                                    public void onSuccess(File file, Call call, Response response) {
                                                        List<MusicListEntity.MusicBean> list_local = LiteOrmDBUtil.getQueryAll(MusicListEntity.MusicBean.class);
                                                        for (MusicListEntity.MusicBean bean : list_local) {
                                                            if (bean.getNid().equals(list.get(position).getNid())) {//如果本地数据库已经有数据，先删除，再插入新的数据
                                                                LiteOrmDBUtil.deleteWhere(MusicListEntity.MusicBean.class, "nid", new String[]{bean.getNid()});
                                                                LiteOrmDBUtil.insert(list_local.get(position));
                                                                list.get(position).setState(2);
                                                                adapter.notifyDataSetChanged();
                                                                KLog.e(bean.getNid());
                                                                return;
                                                            }
                                                        }
                                                        LiteOrmDBUtil.insert(list.get(position));//如果本地数据库没有数据，直接插入
                                                        KLog.e(list.get(position).getNid());
                                                        list.get(position).setState(2);
                                                        adapter.notifyDataSetChanged();
                                                    }

                                                    @Override
                                                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
                                                    }
                                                });
                                    }

                                    @Override
                                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
                                    }
                                });
                        break;
                    case R.id.tv_music_pick:
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("music_id", list.get(position).getNid());
                        getActivity().setResult(RESULT_OK, resultIntent);
                        getActivity().finish();
                        break;
                }

            }
        });
        roomFavAdapter.bindToRecyclerView(rvNear);
        roomFavAdapter.setEmptyView(R.layout.empty_view_music);
        rvNear.setAdapter(roomFavAdapter);
        //水平分割线
        rvNear.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL_LIST, 5, getActivity().getResources().getColor(R.color.transparent)));
        //=====================下拉刷新
        srlNear.setOnRefreshListener(this);
        //设置样式刷新显示的位置
        srlNear.setProgressViewOffset(true, -10, 50);

    }

    private void handleView(View contentView, final int position) {
        TextView tv_delete = (TextView) contentView.findViewById(R.id.pop_music_delete);
        TextView tv_cancle = (TextView) contentView.findViewById(R.id.pop_music_cancle);

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean is_music_delete = FileUtils.deleteFolderFile(FileUtils.getMusicFiles() + list.get(position).getNid() + ".mp3");
                boolean is_lrc_delete = FileUtils.deleteFolderFile(FileUtils.getLrcFiles() + list.get(position).getNid() + ".lrc");
                if (is_music_delete && is_lrc_delete) {
                    LiteOrmDBUtil.deleteWhere(MusicListEntity.MusicBean.class, "nid", new String[]{list.get(position).getNid()});//删除制定条目
                    list.get(position).setState(0);//状态重置0
                    roomFavAdapter.notifyDataSetChanged();
                }
                pop_delete.dissmiss();
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_delete.dissmiss();
            }
        });
    }

    private MusicListEntity musicListEntity;

    private ArrayList<String> localMusicList = new ArrayList<>();
    private ArrayList<String> localLrcList = new ArrayList<>();

    private void initdate(String key) {
        String url = AppConstant.BASE_URL + AppConstant.MSG_GET_MP3;
        OkGo.get(url)//
                .tag(this)//
                .params(AppConstant.COUNT, count)
                .params(AppConstant.PAGE, page)
                .params("title", key)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        srlNear.setRefreshing(false);
                        try {
                            musicListEntity = new Gson().fromJson(s, MusicListEntity.class);

                            if (musicListEntity.getStatus().equals("success")) {
                                list.clear();
                                list.addAll(musicListEntity.getList());
                                ScaneLocalMusic();
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

    /**
     * 扫描本地音乐，并且设置list中匹配了本地的音乐state状态
     */
    private void ScaneLocalMusic() {
        List<MusicListEntity.MusicBean> list_local = LiteOrmDBUtil.getQueryAll(MusicListEntity.MusicBean.class);
        for (MusicListEntity.MusicBean bean : list_local) {
            for (int i = 0; i < list.size(); i++) {
                if (bean.getNid().equals(list.get(i).getNid())) {
                    list.get(i).setState(2);
                }
            }

        }
    }


    @Override
    public void onRefresh() {
        page = 1;
        initdate("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelAll();
        EventBus.getDefault().unregister(this);
    }
}
