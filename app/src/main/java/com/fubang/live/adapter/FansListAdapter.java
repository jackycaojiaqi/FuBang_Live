package com.fubang.live.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.entities.RoomFansEntity;
import com.fubang.live.entities.SearchListEntity;
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
public class FansListAdapter extends BaseQuickAdapter<RoomFansEntity.DatalistBean, BaseViewHolder> {
    private List<RoomFansEntity.DatalistBean> list;

    public FansListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper,RoomFansEntity.DatalistBean item) {
        helper.setText(R.id.tv_room_fans_name, item.getCalias() + " ")
                .setText(R.id.tv_room_fans_signe, item.getCidiograph() + " ");
        if (!StringUtil.isEmptyandnull(item.getCphoto()))
            FBImage.Create(mContext, AppConstant.BASE_IMG_URL + item.getCphoto()).into((ImageView) helper.getView(R.id.riv_room_fans_pic));

        if (!StringUtil.isEmptyandnull(item.getNgender())) {
            if (item.getNgender().equals("0")) {
                helper.setImageResource(R.id.iv_room_fans_gender, R.drawable.ic_female_select);
            } else {
                helper.setImageResource(R.id.iv_room_fans_gender, R.drawable.ic_male_select);
            }
        }
    }
}
