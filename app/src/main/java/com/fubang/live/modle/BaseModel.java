package com.fubang.live.modle;


import com.fubang.live.AppConstant;
import com.fubang.live.api.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dell on 2016/4/5.
 */
public class BaseModel {
    public ApiService service;
    public BaseModel(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }
}
