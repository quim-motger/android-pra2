package com.example.quim.bykeapp.service;

import com.example.quim.bykeapp.controller.DibaAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    public static DibaAPI getDibaAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DibaService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DibaAPI dibaAPI = retrofit.create(DibaAPI.class);
        return dibaAPI;
    }
}
