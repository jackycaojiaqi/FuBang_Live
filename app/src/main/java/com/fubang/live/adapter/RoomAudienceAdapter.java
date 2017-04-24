package com.fubang.live.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.live.R;
import com.fubang.live.entities.RoomListEntity;
import com.fubang.live.util.StringUtil;
import com.socks.library.KLog;
import com.xlg.android.protocol.RoomUserInfo;

import java.io.UnsupportedEncodingException;
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
public class RoomAudienceAdapter extends BaseQuickAdapter<RoomUserInfo, BaseViewHolder> {
    private List<RoomUserInfo> list;

    public RoomAudienceAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomUserInfo item) {
//        helper.setText(R.id.tv_anchor_name, item.getRoomname())
//                .setText(R.id.tv_anchor_audience_num, helper.getLayoutPosition() + 1 + " ");
        if (!StringUtil.isEmptyandnull(item.getCphoto())) {//有头像
            KLog.e(item.getCphoto());
            helper.setImageResource(R.id.civ_audience_pic, R.drawable.ic_user_pic);
        } else {                            //没有头像
            helper.setImageResource(R.id.civ_audience_pic, R.drawable.ic_user_pic);
        }
    }
}
