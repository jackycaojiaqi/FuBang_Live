package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.UserFavAdapter;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.RoomFavEntity;
import com.fubang.live.util.StringUtil;
import com.fubang.live.util.ToastUtil;
import com.fubang.live.widget.DividerItemDecoration;
import com.google.gson.Gson;
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
public class FavListActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_fav)
    RecyclerView rvFav;
    private String user_id;
    private Context context;
    private BaseItemDraggableAdapter roomHistoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_fav_list);
        ButterKnife.bind(this);
        context = this;
        user_id = getIntent().getStringExtra(AppConstant.USER_ID);
        initview();
        initdate();
    }

    private void initview() {
        tvTitle.setText("关注列表");
        //=========================recycleview
        roomHistoryAdapter = new UserFavAdapter(R.layout.item_room_history, list_fav);
        rvFav.setLayoutManager(new GridLayoutManager(context, 1));
        roomHistoryAdapter.openLoadAnimation();
        roomHistoryAdapter.setAutoLoadMoreSize(5);
        roomHistoryAdapter.setEnableLoadMore(true);
        roomHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, UserInfoPageActivity.class);
                intent.putExtra(AppConstant.ROOMID, list_fav.get(position).getRoomid());
                intent.putExtra(AppConstant.ROOMIP, list_fav.get(position).getGateway());
                intent.putExtra(AppConstant.ROOMPWD, list_fav.get(position).getRoompwd());
                startActivity(intent);
            }
        });
        roomHistoryAdapter.bindToRecyclerView(rvFav);
        roomHistoryAdapter.setEmptyView(R.layout.empty_view);
        //水平分割线
        rvFav.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL_LIST, 5, getResources().getColor(R.color.gray_dan)));
        //滑动删除
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                //删除单个历史记录
                deleteOne(pos);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        };
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(roomHistoryAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rvFav);
        // 开启滑动删除
        roomHistoryAdapter.enableSwipeItem();
        roomHistoryAdapter.setOnItemSwipeListener(onItemSwipeListener);
        //绑定adapter
        rvFav.setAdapter(roomHistoryAdapter);
    }

    private void deleteOne(int pos) {
        String url = AppConstant.BASE_URL + AppConstant.MSG_DELETE_ONE_FAV;
        OkGo.get(url)//
                .tag(this)//
                .params(AppConstant.USERID, user_id)
                .params("del_nuserid", list_fav.get(pos).getRoomid())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ToastUtil.show(context, R.string.delete_sucess);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();
                        ToastUtil.show(context, R.string.delete_fail);
                    }
                });
    }


    private RoomFavEntity roomFavEntity;
    private List<RoomFavEntity.DatalistBean> list_fav = new ArrayList<>();

    private void initdate() {

        String url = AppConstant.BASE_URL + AppConstant.MSG_GET_FAV_LIST;
        OkGo.get(url)
                .tag(this)
                .params("nuserid", user_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (!StringUtil.isEmptyandnull(s)) {
                            roomFavEntity = new Gson().fromJson(s, RoomFavEntity.class);
                            if (roomFavEntity.getDatalist() != null) {
                                if (roomFavEntity.getDatalist().size() > 0) {
                                    list_fav.clear();
                                    list_fav = roomFavEntity.getDatalist();
                                    roomHistoryAdapter.setNewData(list_fav);
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
