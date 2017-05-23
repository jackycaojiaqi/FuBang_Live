package com.fubang.live.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fubang.live.AppConstant;
import com.fubang.live.R;
import com.fubang.live.entities.RoomFollowEntity;
import com.fubang.live.util.FBImage;
import com.fubang.live.util.StringUtil;
import com.xlg.android.protocol.RoomChatMsg;

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
public class RoomProviteChatAdapter extends BaseMultiItemQuickAdapter<RoomChatMsg, BaseViewHolder> {
    private List<RoomChatMsg> list;

    public RoomProviteChatAdapter(List data) {
        super(data);
        addItemType(RoomChatMsg.CHAT_TYPE_OTHER, R.layout.item_room_privite_chat_other);
        addItemType(RoomChatMsg.CHAT_TYPE_SELF, R.layout.item_room_privite_chat_self);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoomChatMsg item) {
        switch (helper.getItemViewType()) {
            case RoomChatMsg.CHAT_TYPE_OTHER:
                break;
            case RoomChatMsg.CHAT_TYPE_SELF:
                break;
        }
    }
}
