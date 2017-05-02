package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.RoomFavAdapter;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.RoomFavEntity;
import com.fubang.live.entities.RoomHistoryEntity;
import com.fubang.live.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 17/3/28.
 */
public class SearchAtivity extends BaseActivity {
    @BindView(R.id.et_search_content)
    EditText etSearchContent;
    @BindView(R.id.tv_search_cancle)
    TextView tvSearchCancle;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    private BaseQuickAdapter roomFavAdapter;
    private Context context;
    private List<RoomHistoryEntity> list_key = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_search);
        context = this;
        ButterKnife.bind(this);
        initview();
        initdate();
    }


    private void initview() {
        //=========================recycleview
        roomFavAdapter = new RoomFavAdapter(R.layout.item_room, list_key);
        rvSearch.setLayoutManager(new GridLayoutManager(context, 1));
        roomFavAdapter.openLoadAnimation();
        roomFavAdapter.setAutoLoadMoreSize(5);
        roomFavAdapter.setEnableLoadMore(true);
        roomFavAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, UserInfoPageActivity.class);
                intent.putExtra(AppConstant.ROOMID, list_key.get(position).getRoomid());
                intent.putExtra(AppConstant.ROOMIP, list_key.get(position).getGateway());
                intent.putExtra(AppConstant.ROOMPWD, list_key.get(position).getRoompwd());
                startActivity(intent);
            }
        });
        roomFavAdapter.bindToRecyclerView(rvSearch);
        roomFavAdapter.setEmptyView(R.layout.empty_view);
        rvSearch.setAdapter(roomFavAdapter);
        //水平分割线
        rvSearch.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL_LIST, 5, getResources().getColor(R.color.gray_c)));
        //======================banner
    }

    private void initdate() {

    }

    @OnClick(R.id.tv_search_cancle)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_search_cancle:
                finish();
                break;
        }
    }
}
