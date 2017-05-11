package com.fubang.live.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
import com.fubang.live.entities.MusicListEntity;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RoomListEntity;
import com.fubang.live.ui.RoomActivity;
import com.fubang.live.util.FileUtils;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.ToastUtil;
import com.fubang.live.widget.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.socks.library.KLog;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicMineFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_near)
    RecyclerView rvNear;
    Unbinder unbinder;
    @BindView(R.id.srl_near)
    SwipeRefreshLayout srlNear;
    private Context context;
    private int count = 20;
    private int page = 1;
    private int group = 0;
    private List<MusicListEntity.ListBean> list = new ArrayList<>();
    private BaseQuickAdapter roomFavAdapter;

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
        roomFavAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent resultIntent = new Intent();
                if (localMusicList.size() > 0)
                    resultIntent.putStringArrayListExtra("music", localMusicList);
                if (localLrcList.size() > 0)
                    resultIntent.putStringArrayListExtra("lrc", localLrcList);
                getActivity().setResult(RESULT_OK, resultIntent);
                getActivity().finish();
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
                            //扫描本地音乐
                            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                                new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            localMusicList.clear();
                                            localLrcList.clear();
                                            File file_music = new File(FileUtils.getMusicFiles());
                                            localMusicList = FileUtils.searchMp3Infos(file_music);
                                            File file_lrc = new File(FileUtils.getLrcFiles());
                                            localLrcList = FileUtils.searchLrcInfos(file_lrc);
                                        } catch (Exception e) {
                                        }
                                    }
                                }.start();
                            }
                            if (musicListEntity.getStatus().equals("success")) {
                                list.clear();
                                list.addAll(musicListEntity.getList());
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
        initdate("");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
