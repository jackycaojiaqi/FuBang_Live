package com.fubang.live.api;



import com.fubang.live.entities.RoomEntity;
import com.fubang.live.entities.RtmpUrlEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by dell on 2016/4/5.
 */
public interface ApiService {

    @GET("/index.php/app/roomlist?")
    Call<RoomEntity> getRoomEntity(@QueryMap Map<String, String> map);

    @GET("/rtmp_pub.php?")
    Call<RtmpUrlEntity> getRtmpUrlEntity(@QueryMap Map<String, String> map);
}
