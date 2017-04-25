package com.fubang.live.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.entities.RoomListEntity;
import com.fubang.live.util.FBImage;
import com.fubang.live.util.StringUtil;

import java.util.List;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * Created by jacky on 17/3/10.
 */
public class RoomFavAdapter extends BaseQuickAdapter<RoomListEntity, BaseViewHolder> {
    private List<RoomListEntity> list;

    public RoomFavAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomListEntity item) {
        helper.setText(R.id.tv_anchor_name, item.getCalias())
                .setText(R.id.tv_anchor_audience_num, helper.getLayoutPosition() + 1 + " ");
        if (!StringUtil.isEmptyandnull(item.getCphoto()))
            FBImage.Create(mContext, AppConstant.BASE_IMG_URL + item.getCphoto()).into((ImageView) helper.getView(R.id.civ_anchor_pic));

        if (!StringUtil.isEmptyandnull(item.getBphoto()))
            FBImage.Create(mContext, AppConstant.BASE_IMG_URL + item.getBphoto()).into((ImageView) helper.getView(R.id.tv_anchor_bg));
    }
}
