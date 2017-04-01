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
        list.add(new GiftEntity(32, R.drawable.ic_gift_beautiful, "你真美"));
        list.add(new GiftEntity(33, R.drawable.ic_gift_car, "跑车"));
        list.add(new GiftEntity(34, R.drawable.ic_gift_cool, "酷"));
        list.add(new GiftEntity(35, R.drawable.ic_gift_flower_fall_in_love, "坠入爱河"));
        list.add(new GiftEntity(36, R.drawable.ic_gift_flower_only_you, "只爱你"));
        list.add(new GiftEntity(37, R.drawable.ic_gift_flower_wait_for_you, "等你"));
        list.add(new GiftEntity(38, R.drawable.ic_gift_love, "爱"));
        list.add(new GiftEntity(39, R.drawable.ic_gift_shuai, "帅"));
        list.add(new GiftEntity(40, R.drawable.ic_gift_stars, "星星"));

        return list;
    }
}
