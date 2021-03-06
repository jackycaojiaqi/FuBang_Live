package com.fubang.live.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fubang.live.R;
import com.xlg.android.protocol.RoomChatMsg;

import java.lang.reflect.Field;
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
 * 类描述：
 * 创建人：dell
 * 创建时间：2016-05-18 09:04
 * 修改人：dell
 * 修改时间：2016-05-18 09:04
 * 修改备注：聊天消息的适配器
 */
public class RoomChatAdapter extends ListBaseAdapter<RoomChatMsg> {
    private List<RoomChatMsg> list;

    public RoomChatAdapter(List<RoomChatMsg> list, Context context) {
        super(list, context);
        this.list = list;
    }

    public void addData(RoomChatMsg msg) {
        if (list.size() >= 100) {
            list.remove(0);
        }
        list.add(msg);
        notifyDataSetChanged();

    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_room_message, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        if (list.get(position).getType() == 0) {//正常的聊天消息
            holder.ll_message.setVisibility(View.VISIBLE);
            holder.ll_notify.setVisibility(View.GONE);
            //等级
            holder.userLevel.setText(String.valueOf(list.get(position).getUser_level()));
            //等级背景
            if (list.get(position).getUser_level() >= 1 && list.get(position).getUser_level() <= 14) {
                holder.userLevel.setBackgroundResource(R.drawable.ic_user_level_star);
            } else if (list.get(position).getUser_level() >= 15 && list.get(position).getUser_level() <= 29) {
                holder.userLevel.setBackgroundResource(R.drawable.ic_user_level_moon);
            } else if (list.get(position).getUser_level() >= 31 && list.get(position).getUser_level() <= 49) {
                holder.userLevel.setBackgroundResource(R.drawable.ic_user_level_sun);
            } else if (list.get(position).getUser_level() >= 50 && list.get(position).getUser_level() <= 79) {
                holder.userLevel.setBackgroundResource(R.drawable.ic_user_level_crown_small);
            } else if (list.get(position).getUser_level() >= 80 && list.get(position).getUser_level() <= 100) {
                holder.userLevel.setBackgroundResource(R.drawable.ic_user_level_crown_big);
            }
            if (list.get(position).getIsprivate() == 1) {
                holder.userTv.setText(list.get(position).getSrcalias() + ":悄悄的说");
            } else {
                holder.userTv.setText(list.get(position).getSrcalias() + ":");
            }

            if (list.get(position).getToid() == -1) {
                holder.simpleDraweeView.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse("res://" + context.getPackageName() + "/" + getResourceId(list.get(position).getContent()));
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true)
                        .build();
//            Log.d("123", list.get(position).getContent());
                holder.simpleDraweeView.setController(controller);
                holder.messageTv.setText("   X" + list.get(position).getDstvcbid());
            } else {
                holder.simpleDraweeView.setVisibility(View.GONE);
                String spanned = String.valueOf(Html.fromHtml(list.get(position).getContent()));
                StringBuilder stringBuilder = new StringBuilder();
                if (spanned.indexOf('/') != -1) {
                    for (int i = 0; i < spanned.length(); i++) {
                        if (spanned.charAt(i) == '/') {
                            String s = spanned.substring(i + 3, i + 6);
                            int number = Integer.parseInt(s);
                            if (number > 700 && number < 791) {
                                stringBuilder.append("<img src='");
                                stringBuilder.append(spanned.substring(i + 1, i + 6));
                                stringBuilder.append("'/>");
                            } else {
                                stringBuilder.append("");
                            }
                            i = i + 5;
                        } else {
                            stringBuilder.append(spanned.charAt(i));
                        }
                    }
//                Log.d("123", "stringBuilder" + stringBuilder);
                    holder.messageTv.setText(Html.fromHtml(stringBuilder.toString(), new Html.ImageGetter() {
                        @Override
                        public Drawable getDrawable(String source) {
                            // 获得系统资源的信息，比如图片信息
                            Drawable drawable = context.getResources().getDrawable(getResourceId(source));
                            // 第三个图片文件按照50%的比例进行压缩
//                    if (source.equals("image3")) {
//                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 2,
//                                drawable.getIntrinsicHeight() / 2);
//                    } else {
                            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                                    drawable.getIntrinsicHeight());
//                    }
                            return drawable;
                        }
                    }, null));
                } else {
                    holder.messageTv.setText(spanned);
                }
            }
        } else if (list.get(position).getType() == 1) {
            holder.ll_message.setVisibility(View.GONE);
            holder.ll_notify.setVisibility(View.VISIBLE);
            holder.tv_notify_content.setText("                    " + context.getResources().getString(R.string.come_room_first));
        } else if (list.get(position).getType() == 2) {
            holder.ll_message.setVisibility(View.GONE);
            holder.ll_notify.setVisibility(View.VISIBLE);
            holder.tv_notify_content.setText("                    " + context.getResources().getString(R.string.come_before) + " " +
                    list.get(position).getSrcalias() + " " + context.getResources().getString(R.string.come_after));
        }
        return convertView;
    }

    static class ViewHolder {
        TextView userTv, tv_notify_content;
        TextView messageTv;
        TextView userLevel;
        SimpleDraweeView simpleDraweeView;
        ImageView userFans;
        LinearLayout ll_message;
        RelativeLayout ll_notify;

        public ViewHolder(View itemView) {
            //显示聊天室消息发送人和消息
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.item_chat_gift);
            userTv = (TextView) itemView.findViewById(R.id.item_chat_user);
            messageTv = (TextView) itemView.findViewById(R.id.item_chat_message);

            userLevel = (TextView) itemView.findViewById(R.id.tv_chat_level);
            userFans = (ImageView) itemView.findViewById(R.id.iv_chat_is_fans);
            ll_message = (LinearLayout) itemView.findViewById(R.id.ll_room_message);
            ll_notify = (RelativeLayout) itemView.findViewById(R.id.ll_room_first_notify);
            tv_notify_content = (TextView) itemView.findViewById(R.id.tv_room_notify_content);
        }
    }

    public int getResourceId(String name) {
        try {
            Field field = R.drawable.class.getField(name);
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
