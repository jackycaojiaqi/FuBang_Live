package com.fubang.live.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.fubang.live.R;
import com.fubang.live.entities.GiftEntity;

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
public class RoomGiftAdapter extends ListBaseAdapter<GiftEntity> {
    public RoomGiftAdapter(List<GiftEntity> list, Context context) {
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
        holder.giftImage.setImageResource(list.get(position).getGiftImage());
        holder.giftName.setText(list.get(position).getGiftName());
        holder.giftGiven.setText("小新送出：" + list.get(position).getGiftName());
        return convertView;
    }

    static class ViewHolder {
        //显示礼物图片
        ImageView giftImage;
        //显示礼物名字
        TextView giftName;
        //显示礼物赠送人
        TextView giftGiven;

        public ViewHolder(View itemView) {
            giftImage = (ImageView) itemView.findViewById(R.id.room_gift_image);
            giftName = (TextView) itemView.findViewById(R.id.room_gift_name);
            giftGiven = (TextView) itemView.findViewById(R.id.room_gift_given);
        }
    }
}
