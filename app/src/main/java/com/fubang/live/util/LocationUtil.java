package com.fubang.live.util;

import android.content.Context;

import com.fubang.live.AppConstant;
import com.fubang.live.entities.UserInfoEntity;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.socks.library.KLog;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jacky on 2017/5/4.
 */
public class LocationUtil {
    public static void uploadLatLng(Context context, String lat, String lng) {
        if (lat.startsWith("0") || lng.startsWith("0")) {
            KLog.e("经纬度为0，无效");
            return;
        }
        String url = AppConstant.BASE_URL + AppConstant.MSG_UPLOAD_LATLON;
        OkGo.post(url)//
                .tag(context)//
                .params("nuserid", StartUtil.getUserId(context))
                .params("lat", lat)
                .params("lng", lng)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        KLog.e("上传经纬度成功");
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();
                    }
                });

    }
}
