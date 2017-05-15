package com.fubang.live.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.live.R;
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

    private Uri uri;

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_room_gift_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        int gift_id = list.get(position).getGiftid();
        getUrl(gift_id);
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

    private void getUrl(int gift_id) {
        switch (gift_id) {
            case 21:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_21))
                        .build();
                break;
            case 22:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_22))
                        .build();
                break;
            case 23:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_23))
                        .build();
                break;
            case 24:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_24))
                        .build();
                break;
            case 25:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_25))
                        .build();
                break;
            case 26:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_26))
                        .build();
                break;
            case 27:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_27))
                        .build();
                break;
            case 28:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_28))
                        .build();
                break;
            case 29:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_29))
                        .build();
                break;

            case 30:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_30))
                        .build();
                break;
            case 31:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_31))
                        .build();
                break;
            case 32:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_32))
                        .build();
                break;
            case 33:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_33))
                        .build();
                break;
            case 34:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_34))
                        .build();
                break;
            case 35:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_35))
                        .build();
                break;
            case 36:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_36))
                        .build();
                break;
            case 37:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_37))
                        .build();
                break;
            case 38:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_38))
                        .build();
                break;
            case 39:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_39))
                        .build();
                break;
            case 40:
                uri = new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(R.drawable.ic_gift_40))
                        .build();
                break;
        }
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
