package com.fubang.live.util;

import com.fubang.live.R;
import com.fubang.live.entities.GiftEntity;

import java.util.ArrayList;
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
 * <p/>
 * 项目名称：fubangzhibo
 * 类描述：
 * 创建人：dell
 * 创建时间：2016-05-25 14:22
 * 修改人：dell
 * 修改时间：2016-05-25 14:22
 * 修改备注：添加礼物的工具类
 */
public class GiftUtil {

    public static List<GiftEntity> getGifts() {
        List<GiftEntity> list = new ArrayList<>();
        list.clear();
        list.add(new GiftEntity(32, R.drawable.ic_gift_32, "鼓掌(10)"));
        list.add(new GiftEntity(33, R.drawable.ic_gift_33, "啤酒(50)"));
        list.add(new GiftEntity(34, R.drawable.ic_gift_34, "雷到了(150)"));
        list.add(new GiftEntity(35, R.drawable.ic_gift_35, "歌神(200)"));
        list.add(new GiftEntity(36, R.drawable.ic_gift_36, "爱心(300)"));
        list.add(new GiftEntity(37, R.drawable.ic_gift_37, "呀美女(500)"));
        list.add(new GiftEntity(38, R.drawable.ic_gift_38, "嗨帅哥(500)"));
        list.add(new GiftEntity(39, R.drawable.ic_gift_39, "亲一口(1000)"));
        list.add(new GiftEntity(40, R.drawable.ic_gift_40, "钻戒(2000)"));
        list.add(new GiftEntity(41, R.drawable.ic_gift_41, "药不停(3000)"));
        list.add(new GiftEntity(42, R.drawable.ic_gift_42, "小菊花(4000)"));
        list.add(new GiftEntity(43, R.drawable.ic_gift_43, "大香蕉(5000)"));
        list.add(new GiftEntity(44, R.drawable.ic_gift_44, "大冰棍(10W)"));
        list.add(new GiftEntity(45, R.drawable.ic_gift_45, "梦幻城堡(100W)"));
        list.add(new GiftEntity(46, R.drawable.ic_gift_46, "私人飞机(200W)"));
        list.add(new GiftEntity(47, R.drawable.ic_gift_47, "豪华游轮(300W)"));
        list.add(new GiftEntity(48, R.drawable.ic_gift_48, "浪漫烟花(400W)"));
        list.add(new GiftEntity(62, R.drawable.ic_gift_62, "超级跑车(500W)"));
        list.add(new GiftEntity(63, R.drawable.ic_gift_63, "热气球(1000W)"));
        return list;
    }
}
