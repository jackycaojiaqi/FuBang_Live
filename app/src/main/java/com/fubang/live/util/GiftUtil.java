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
        list.add(new GiftEntity(21, R.drawable.ic_gift_21, "爱心(100)"));
        list.add(new GiftEntity(22, R.drawable.ic_gift_22, "荧光棒(100)"));
        list.add(new GiftEntity(23, R.drawable.ic_gift_23, "啤酒(100)"));
        list.add(new GiftEntity(24, R.drawable.ic_gift_24, "鲜花(100)"));
        list.add(new GiftEntity(25, R.drawable.ic_gift_25, "黄瓜(100)"));
        list.add(new GiftEntity(26, R.drawable.ic_gift_26, "花(100)"));
        list.add(new GiftEntity(27, R.drawable.ic_gift_27, "皮鞭(100)"));
        list.add(new GiftEntity(28, R.drawable.ic_gift_28, "香蕉(100)"));
        list.add(new GiftEntity(29, R.drawable.ic_gift_29, "亲吻(100)"));
        list.add(new GiftEntity(30, R.drawable.ic_gift_30, "抱抱(100)"));
        list.add(new GiftEntity(31, R.drawable.ic_gift_31, "冰棍(100)"));
        list.add(new GiftEntity(32, R.drawable.ic_gift_32, "红包(100)"));
        list.add(new GiftEntity(33, R.drawable.ic_gift_33, "烟花(100)"));
        list.add(new GiftEntity(34, R.drawable.ic_gift_34, "钻戒(100)"));
        list.add(new GiftEntity(35, R.drawable.ic_gift_35, "天使(100)"));
        list.add(new GiftEntity(36, R.drawable.ic_gift_36, "豪华游轮(100)"));
        list.add(new GiftEntity(37, R.drawable.ic_gift_37, "超级跑车(100)"));
        list.add(new GiftEntity(38, R.drawable.ic_gift_38, "热气球(100)"));
        list.add(new GiftEntity(39, R.drawable.ic_gift_39, "飞机(100)"));
        list.add(new GiftEntity(40, R.drawable.ic_gift_40, "城堡(100)"));
        return list;
    }
}
