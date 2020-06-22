package com.yunjaena.seller.data.network.interactor;


import com.yunjaena.core.network.ApiCallback;

public interface MoveInteractor {
    void moveRobot(String roomName, final ApiCallback apiCallback);

}
