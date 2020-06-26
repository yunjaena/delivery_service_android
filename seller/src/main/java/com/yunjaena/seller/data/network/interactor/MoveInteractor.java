package com.yunjaena.seller.data.network.interactor;


import com.yunjaena.core.network.ApiCallback;
import com.yunjaena.seller.data.entity.Order;

import java.util.List;

import io.reactivex.Observable;

public interface MoveInteractor {
    void moveRobot(String roomName, final ApiCallback apiCallback);

    Observable<List<Order>> loadAllOrder();

    Observable<Boolean> updateOrder(Order order);

    void setDatabaseUpdateListener(final ApiCallback apiCallback);


}
