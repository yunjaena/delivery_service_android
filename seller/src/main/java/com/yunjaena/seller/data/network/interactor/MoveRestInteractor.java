package com.yunjaena.seller.data.network.interactor;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.yunjaena.core.network.ApiCallback;
import com.yunjaena.core.network.RetrofitManager;
import com.yunjaena.seller.data.entity.DefaultResponse;
import com.yunjaena.seller.data.entity.Order;
import com.yunjaena.seller.data.network.service.MoveService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class MoveRestInteractor implements MoveInteractor {

    private final String TAG = "MoveRestInteractor";


    @Override
    public void moveRobot(String roomName, ApiCallback apiCallback) {
        RetrofitManager.getInstance().getRetrofit().create(MoveService.class).moveRobot(roomName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DefaultResponse>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onNext(DefaultResponse response) {
                        if (response != null && response.isSuccess) {
                            apiCallback.onSuccess(response);
                        } else {
                            apiCallback.onFailure(new Throwable("fail to move"));
                        }
                    }


                    @Override
                    public void onError(Throwable throwable) {
                        if (throwable instanceof HttpException) {
                            Log.d(TAG, ((HttpException) throwable).code() + " ");
                        }
                        apiCallback.onFailure(throwable);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public Observable<List<Order>> loadAllOrder() {
        return Observable.create(subscriber -> {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            List<Order> orderList = new ArrayList<>();
            Query query = reference.child("order_list");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot issue : dataSnapshot.getChildren()) {
                            Order order = issue.getValue(Order.class);
                            if (order != null)
                                orderList.add(order);
                        }
                        subscriber.onNext(orderList);
                        subscriber.onComplete();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    subscriber.onError(new Throwable("canceled get report"));
                }
            });
        });
    }

    @Override
    public Observable<Boolean> updateOrder(Order order) {
        return Observable.create(subscriber -> {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            Map<String, Object> childUpdates = new HashMap<>();
            Map<String, Object> postValues = null;
            postValues = order.toMap();
            childUpdates.put("/order_list/" + order.getRoomNumber(), postValues);
            Task<Void> task = databaseReference.updateChildren(childUpdates);
            task.addOnFailureListener(e -> subscriber.onError(e)).addOnSuccessListener(aVoid -> {
                subscriber.onNext(true);
                subscriber.onComplete();
            });
        });
    }

    @Override
    public void setDatabaseUpdateListener(final ApiCallback apiCallback) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateOrderProcess(dataSnapshot, apiCallback);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateOrderProcess(dataSnapshot, apiCallback);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateOrderProcess(DataSnapshot dataSnapshot, ApiCallback apiCallback) {
        List<Order> orderList = new ArrayList<>();
        for (DataSnapshot issue : dataSnapshot.getChildren()) {
            Order order = issue.getValue(Order.class);
            if (order != null)
                orderList.add(order);
        }
        if (apiCallback != null)
            apiCallback.onSuccess(orderList);
    }
}