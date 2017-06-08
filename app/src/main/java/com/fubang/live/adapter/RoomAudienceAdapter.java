package com.fubang.live.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.entities.RoomListEntity;
import com.fubang.live.util.FBImage;
import com.fubang.live.util.StringUtil;
import com.socks.library.KLog;
import com.xlg.android.protocol.RoomUserInfoNew;

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
public class RoomAudienceAdapter extends BaseQuickAdapter<RoomUserInfoNew, BaseViewHolder> {
    private List<RoomUserInfoNew> list;

    public RoomAudienceAdapter(int layoutResId, List data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomUserInfoNew item) {
//        helper.setText(R.id.tv_anchor_name, item.getRoomname())
//                .setText(R.id.tv_anchor_audience_num, helper.getLayoutPosition() + 1 + " ");
        if (!StringUtil.isEmptyandnull(item.getCphoto())) {//有头像
            KLog.e(AppConstant.BASE_IMG_URL + item.getCphoto());
            FBImage.Create(mContext, AppConstant.BASE_IMG_URL + item.getCphoto()).into((ImageView) helper.getView(R.id.civ_audience_pic));
        } else {                            //没有头像
            helper.setImageResource(R.id.civ_audience_pic, R.drawable.ic_user_pic);
        }
        if (helper.getPosition() == 0) {
            helper.setBackgroundRes(R.id.rll_audience_ranking_bg, R.drawable.ic_room_user_no1);
        } else if (helper.getPosition() == 1) {
            helper.setBackgroundRes(R.id.rll_audience_ranking_bg, R.drawable.ic_room_user_no2);
        } else if (helper.getPosition() == 2) {
            helper.setBackgroundRes(R.id.rll_audience_ranking_bg, R.drawable.ic_room_user_no3);
        }else {
            helper.setBackgroundRes(R.id.rll_audience_ranking_bg, R.color.transparent_50);
        }
    }
}
