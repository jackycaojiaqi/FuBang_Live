package com.fubang.live.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
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

    public void addData(RoomChatMsg msg){
        if (list.size()>=100){
            list.remove(0);
        }
        list.add(msg);
        notifyDataSetChanged();

    }
    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_room_message,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        if (list.get(position).getIsprivate()==1){
            holder.userTv.setText(list.get(position).getSrcalias()+":悄悄的说");
        }else
            holder.userTv.setText(list.get(position).getSrcalias()+":");
//        holder.messageTv.setText(Html.fromHtml(list.get(position).getContent()));
        if (list.get(position).getToid() == -1){
            holder.simpleDraweeView.setVisibility(View.VISIBLE);
            Uri uri =Uri.parse("res://"+context.getPackageName()+"/" + getResourceId(list.get(position).getContent()));
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setAutoPlayAnimations(true)
                    .build();
//            Log.d("123", list.get(position).getContent());
            holder.simpleDraweeView.setController(controller);
            holder.messageTv.setText("   X"+list.get(position).getDstvcbid());
        }else {
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
        return convertView;
    }

    static class ViewHolder{
        TextView userTv;
        TextView messageTv;
        SimpleDraweeView simpleDraweeView;
        public ViewHolder(View itemView){
            //显示聊天室消息发送人和消息
            simpleDraweeView = (SimpleDraweeView)itemView.findViewById(R.id.item_chat_gift);
            userTv = (TextView) itemView.findViewById(R.id.item_chat_user);
            messageTv = (TextView) itemView.findViewById(R.id.item_chat_message);
        }
    }
    public int getResourceId(String name){
        try {
            Field field = R.drawable.class.getField(name);
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
