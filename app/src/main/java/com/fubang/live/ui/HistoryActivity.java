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
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.RoomFavAdapter;
import com.fubang.live.adapter.RoomHistoryAdapter;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RoomHistoryEntity;
import com.fubang.live.entities.RoomListEntity;
import com.fubang.live.util.StartUtil;
import com.fubang.live.util.StringUtil;
import com.fubang.live.util.ToastUtil;
import com.fubang.live.widget.DividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.socks.library.KLog;

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
public class HistoryActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    private Context context;
    private BaseItemDraggableAdapter roomHistoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        context = this;
        initview();
        initdate();
    }

    private void initview() {
        tvTitle.setText("观看历史");
        tvSubmit.setText("清除历史");
        tvSubmit.setVisibility(View.VISIBLE);
        //=========================recycleview
        roomHistoryAdapter = new RoomHistoryAdapter(R.layout.item_room_history, list_history);
        rvHistory.setLayoutManager(new GridLayoutManager(context, 1));
        roomHistoryAdapter.openLoadAnimation();
        roomHistoryAdapter.setAutoLoadMoreSize(5);
        roomHistoryAdapter.setEnableLoadMore(true);
        roomHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, UserInfoPageActivity.class);
                intent.putExtra(AppConstant.ROOMID, list_history.get(position).getRoomid());
                intent.putExtra(AppConstant.ROOMIP, list_history.get(position).getGateway());
                intent.putExtra(AppConstant.ROOMPWD, list_history.get(position).getRoompwd());
                startActivity(intent);
            }
        });
        roomHistoryAdapter.bindToRecyclerView(rvHistory);
        roomHistoryAdapter.setEmptyView(R.layout.empty_view);
        //水平分割线
        rvHistory.addItemDecoration(new DividerItemDecoration(
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
        itemTouchHelper.attachToRecyclerView(rvHistory);
        // 开启滑动删除
        roomHistoryAdapter.enableSwipeItem();
        roomHistoryAdapter.setOnItemSwipeListener(onItemSwipeListener);
        //绑定adapter
        rvHistory.setAdapter(roomHistoryAdapter);
    }

    private void deleteOne(int pos) {
        String url = AppConstant.BASE_URL + AppConstant.MSG_DELETE_ONE_HISTORY;
        OkGo.get(url)//
                .tag(this)//
                .params(AppConstant.USERID, StartUtil.getUserId(context))
                .params("del_nuserid", list_history.get(pos).getRoomid())
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

    private void deleteAll() {
        String url = AppConstant.BASE_URL + AppConstant.MSG_DELETE_ALL_HISTORY;
        OkGo.get(url)//
                .tag(this)//
                .params(AppConstant.USERID, StartUtil.getUserId(context))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        tvSubmit.setVisibility(View.GONE);
                        list_history.clear();
                        roomHistoryAdapter.setNewData(list_history);
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

    private List<RoomHistoryEntity> list_history = new ArrayList<>();

    private void initdate() {
        String url = AppConstant.BASE_URL + AppConstant.MSG_GET_SEE_HISTORY;
        OkGo.get(url)//
                .tag(this)//
                .params("nuserid", StartUtil.getUserId(context))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        if (!StringUtil.isEmptyandnull(s)) {
                            tvSubmit.setVisibility(View.VISIBLE);
                            try {
                                list_history = new Gson().fromJson(s, new TypeToken<List<RoomHistoryEntity>>() {
                                }.getType());
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                            roomHistoryAdapter.setNewData(list_history);
                        } else {
                            tvSubmit.setVisibility(View.GONE);
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
            case R.id.tv_submit:
                if (list_history != null) {
                    if (list_history.size() > 0) {
                        deleteAll();
                    }
                } else {
                    ToastUtil.show(context, "没有历史记录");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
