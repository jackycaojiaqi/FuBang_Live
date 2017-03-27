package com.fubang.live.util;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * 添加请求参数的工具类
 * Created by dell on 2016/4/5.
 */
public class ParamsMap extends HashMap<String,String> {
    private static volatile ParamsMap instance = null;

    public ParamsMap() {
//        put(AppConstant.VER,"2.6.6");
//        put(AppConstant.OS,"3");
//        put(AppConstant.TIME,System.currentTimeMillis()+"");
    }

    public static ParamsMap getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (ParamsMap.class) {
                if (instance == null) {
                    instance = new ParamsMap();
                }
            }
        }
        return instance;
    }
    public void put(String key, int value) {
        super.put(key, value + "");
    }

    @Override
    public String put(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value))
            return "";
        return super.put(key, value);
    }
}
