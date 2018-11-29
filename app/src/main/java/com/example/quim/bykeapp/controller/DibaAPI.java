package com.example.quim.bykeapp.controller;

import com.example.quim.bykeapp.entity.Cities;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DibaAPI {

    @GET("/api/dataset/municipis/format/json/pag-ini/{pagIni}/pag-fi/{pagFi}")
    Call<Cities> cities(@Path("pagIni") String pagIni, @Path("pagFi") String pagFi);

}
