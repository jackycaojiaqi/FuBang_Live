package com.fubang.live.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.live.R;
import com.fubang.live.entities.GiftEntity;
import com.xlg.android.protocol.BigGiftRecord;

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
 * <p>
 * 项目名称：fubangzhibo
 * 类描述：礼物的适配器
 * 创建人：dell
 * 创建时间：2016-05-24 15:27
 * 修改人：dell
 * 修改时间：2016-05-24 15:27
 * 修改备注：
 */
public class RoomGiftAdapter extends ListBaseAdapter<BigGiftRecord> {
    public RoomGiftAdapter(List<BigGiftRecord> list, Context context) {
        super(list, context);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_room_gift_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(R.drawable.ic_gift_624_1))
                .build();

        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();

        holder.giftImage.setController(draweeController);
        holder.giftCount.setText("x" + list.get(position).getCount());
        holder.giftGiven.setText(list.get(position).getSrcalias() + "送出礼物： ");
        return convertView;
    }

    static class ViewHolder {
        //显示礼物图片
        SimpleDraweeView giftImage;
        //显示礼物名字
        TextView giftCount;
        //显示礼物赠送人
        TextView giftGiven;

        public ViewHolder(View itemView) {
            giftImage = (SimpleDraweeView) itemView.findViewById(R.id.room_gift_image);
            giftGiven = (TextView) itemView.findViewById(R.id.room_gift_given);
            giftCount = (TextView) itemView.findViewById(R.id.room_gift_count);
        }
    }
}
