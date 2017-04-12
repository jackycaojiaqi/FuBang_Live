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
        list.add(new GiftEntity(617, R.drawable.ic_gift_617, "鼓掌"));
        list.add(new GiftEntity(618, R.drawable.ic_gift_618, "啤酒"));
        list.add(new GiftEntity(619, R.drawable.ic_gift_619, "炸弹"));
        list.add(new GiftEntity(620, R.drawable.ic_gift_620, "话筒"));
        list.add(new GiftEntity(621, R.drawable.ic_gift_621, "爱心"));
        list.add(new GiftEntity(622, R.drawable.ic_gift_622, "嗨美女"));
        list.add(new GiftEntity(623, R.drawable.ic_gift_623, "嗨帅哥"));
        list.add(new GiftEntity(624, R.drawable.ic_gift_624, "亲"));
        list.add(new GiftEntity(625, R.drawable.ic_gift_625, "钻戒"));
        list.add(new GiftEntity(626, R.drawable.ic_gift_626, "药"));
        list.add(new GiftEntity(627, R.drawable.ic_gift_627, "菊花"));
        list.add(new GiftEntity(628, R.drawable.ic_gift_628, "玉米"));
        list.add(new GiftEntity(629, R.drawable.ic_gift_629, "棒冰"));
        list.add(new GiftEntity(630, R.drawable.ic_gift_630, "城堡"));
        list.add(new GiftEntity(631, R.drawable.ic_gift_631, "飞机"));
        list.add(new GiftEntity(632, R.drawable.ic_gift_632, "游艇"));
        list.add(new GiftEntity(633, R.drawable.ic_gift_633, "蒲公英"));
        list.add(new GiftEntity(634, R.drawable.ic_gift_634, "跑车"));
        list.add(new GiftEntity(635, R.drawable.ic_gift_635, "热气球"));
        return list;
    }
}
