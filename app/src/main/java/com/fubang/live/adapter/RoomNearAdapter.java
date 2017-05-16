package com.fubang.live.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.live.R;
import com.fubang.live.entities.RoomDistanceEntity;
import com.fubang.live.entities.RoomListEntity;

import java.math.BigDecimal;
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
public class RoomNearAdapter extends BaseQuickAdapter<RoomDistanceEntity.RoomlistBean, BaseViewHolder> {
    private List<RoomDistanceEntity.RoomlistBean> list;

    public RoomNearAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomDistanceEntity.RoomlistBean item) {
        Float dis = Float.valueOf(item.getDis());

        if (dis > 1000) {
            dis = dis / 1000;
            BigDecimal b = new BigDecimal(dis);
            float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            helper.setText(R.id.tv_room_near_name, "距离：" + f1 + "km");
        } else {
            BigDecimal b = new BigDecimal(dis);
            float f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            helper.setText(R.id.tv_room_near_name, "距离：" + f1 + "m");
        }

//        if (item.get() >= 0) {
//            helper.setImageResource(R.id.iv_mic_people_pic, R.drawable.head0);
//        }
    }
}
