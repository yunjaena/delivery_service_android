package com.yunjaena.seller.data.network.service;

import com.yunjaena.seller.data.entity.DefaultResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoveService {
    @GET("/")
    Observable<DefaultResponse> moveRobot(@Query("room") String roomName);
}

