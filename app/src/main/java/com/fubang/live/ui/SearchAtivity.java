package com.fubang.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.adapter.RoomFavAdapter;
import com.fubang.live.adapter.SearchListAdapter;
import com.fubang.live.base.BaseActivity;
import com.fubang.live.entities.RoomHistoryEntity;
import com.fubang.live.entities.SearchListEntity;
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
 * Created by jacky on 17/3/28.
 */
public class SearchAtivity extends BaseActivity {
    @BindView(R.id.et_search_content)
    EditText etSearchContent;
    @BindView(R.id.tv_search_cancle)
    TextView tvSearchCancle;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;
    private BaseQuickAdapter SearchListAdapter;
    private Context context;
    private List<SearchListEntity.RoomlistBean> list_key = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_search);
        context = this;
        ButterKnife.bind(this);
        initview();

    }


    private void initview() {
        //=========================recycleview
        SearchListAdapter = new SearchListAdapter(R.layout.item_room_history, list_key);
        rvSearch.setLayoutManager(new GridLayoutManager(context, 1));
        SearchListAdapter.openLoadAnimation();
        SearchListAdapter.setAutoLoadMoreSize(5);
        SearchListAdapter.setEnableLoadMore(true);
        SearchListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, UserInfoPageActivity.class);
                intent.putExtra(AppConstant.ROOMID, list_key.get(position).getRoomid());
                intent.putExtra(AppConstant.ROOMIP, list_key.get(position).getGateway());
                intent.putExtra(AppConstant.ROOMPWD, list_key.get(position).getRoompwd());
                startActivity(intent);
            }
        });
        SearchListAdapter.bindToRecyclerView(rvSearch);
        SearchListAdapter.setEmptyView(R.layout.empty_view_no_main);
        rvSearch.setAdapter(SearchListAdapter);
        //水平分割线
        rvSearch.addItemDecoration(new DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL_LIST, 5, getResources().getColor(R.color.gray_c)));
        //======================输入框文字监听
        etSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initdate(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int count = 50;
    private int page = 1;
    private SearchListEntity searchListEntity;

    private void initdate(String key_word) {
        if (!StringUtil.isEmptyandnull(key_word)) {
            String url = AppConstant.BASE_URL + AppConstant.MSG_GET_ROOM_BY_KEY;
            OkGo.get(url)//
                    .tag(this)//
                    .params("nuserid", StartUtil.getUserId(context))
                    .params("count", count)
                    .params("page", page)
                    .params("ser_txt", key_word)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                searchListEntity = new Gson().fromJson(s, SearchListEntity.class);
                                if (searchListEntity != null) {
                                    if (searchListEntity.getRoomlist() != null) {
                                        if (searchListEntity.getRoomlist().size() > 0) {
                                            list_key.clear();
                                            list_key = searchListEntity.getRoomlist();
                                            SearchListAdapter.setNewData(list_key);
                                        }
                                    } else {
                                        list_key.clear();
                                        SearchListAdapter.setNewData(list_key);
                                    }
                                }
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                                list_key.clear();
                                SearchListAdapter.setNewData(list_key);
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            e.printStackTrace();
                        }
                    });
        } else {
            list_key.clear();
            SearchListAdapter.setNewData(list_key);
        }

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
