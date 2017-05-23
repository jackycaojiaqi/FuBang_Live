package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.FansListAdapter;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.RoomFansEntity;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.StringUtil;
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
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by jacky on 2017/4/28.
 */
public class FansListActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_fav)
    RecyclerView rvFav;
    private String user_id;
    private Context context;
    private BaseQuickAdapter roomFansAdapter;
    private int count = 20;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_fav_list);
        ButterKnife.bind(this);
        context = this;
        initview();
        initdate();
    }

    private void initview() {
        tvTitle.setText("粉丝列表");
        //=========================recycleview
        roomFansAdapter = new FansListAdapter(R.layout.item_room_fans, list_fans);
        rvFav.setLayoutManager(new GridLayoutManager(context, 1));
        roomFansAdapter.openLoadAnimation();
        roomFansAdapter.setAutoLoadMoreSize(5);
        roomFansAdapter.setEnableLoadMore(true);
        roomFansAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, UserInfoPageActivity.class);
                intent.putExtra(AppConstant.ROOMID, list_fans.get(position).getRoomid());
                intent.putExtra(AppConstant.ROOMIP, list_fans.get(position).getGateway());
                intent.putExtra(AppConstant.ROOMPWD, list_fans.get(position).getRoompwd());
                startActivity(intent);
            }
        });
        roomFansAdapter.bindToRecyclerView(rvFav);
        roomFansAdapter.setEmptyView(R.layout.empty_view_no_main);
        //水平分割线
        rvFav.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL_LIST, 5, getResources().getColor(R.color.gray_dan)));
        //绑定adapter
        rvFav.setAdapter(roomFansAdapter);
    }


    private RoomFansEntity roomFansEntity;
    private List<RoomFansEntity.DatalistBean> list_fans = new ArrayList<>();

    private void initdate() {

        String url = AppConstant.BASE_URL + AppConstant.MSG_GET_FANS_LIST;
        OkGo.get(url)
                .tag(this)
                .params("nuserid", StartUtil.getUserId(context))
                .params(AppConstant.PAGE, page)
                .params(AppConstant.COUNT, count)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (!StringUtil.isEmptyandnull(s)) {
                            try {
                                roomFansEntity = new Gson().fromJson(s, RoomFansEntity.class);
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                            if (roomFansEntity != null) {
                                if (roomFansEntity.getDatalist() != null) {
                                    if (roomFansEntity.getDatalist().size() > 0) {
                                        list_fans.clear();
                                        list_fans = roomFansEntity.getDatalist();
                                        roomFansAdapter.setNewData(list_fans);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();
                    }
                });
    }

    @OnClick({R.id.tv_submit, R.id.iv_back})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
