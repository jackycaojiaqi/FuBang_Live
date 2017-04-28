package com.fubang.live.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.entities.RoomHistoryEntity;
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
public class RoomHistoryAdapter extends BaseItemDraggableAdapter<RoomHistoryEntity, BaseViewHolder> {
    private List<RoomHistoryEntity> list;
    private int layoutResId;
    public RoomHistoryAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        this.layoutResId = layoutResId;
        list = data;
    }
    public void notifyDateSetChange(List list){
    }
    @Override
    protected void convert(BaseViewHolder helper, RoomHistoryEntity item) {
        helper.setText(R.id.tv_room_history_name, item.getCalias() + " ")
                .setText(R.id.tv_room_history_signe, item.getCidiograph() + " ");
        if (!StringUtil.isEmptyandnull(item.getCphoto()))
            FBImage.Create(mContext, AppConstant.BASE_IMG_URL + item.getCphoto()).into((ImageView) helper.getView(R.id.riv_room_history_pic));

        if (!StringUtil.isEmptyandnull(item.getNgender())) {
            if (item.getNgender().equals("0")) {
                helper.setImageResource(R.id.iv_room_history_gender, R.drawable.ic_female_select);
            } else {
                helper.setImageResource(R.id.iv_room_history_gender, R.drawable.ic_male_select);
            }
        }

    }
}
