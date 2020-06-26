package com.yunjaena.deliveryservice.data.network.service;


import com.yunjaena.deliveryservice.data.entity.DefaultResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoveService {
    @GET("/")
    Observable<DefaultResponse> moveRobot(@Query("room") String roomName);
}

