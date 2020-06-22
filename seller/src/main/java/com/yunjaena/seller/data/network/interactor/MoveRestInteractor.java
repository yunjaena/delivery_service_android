package com.yunjaena.seller.data.network.interactor;

import android.util.Log;

import com.yunjaena.core.network.ApiCallback;
import com.yunjaena.core.network.RetrofitManager;
import com.yunjaena.seller.data.entity.DefaultResponse;
import com.yunjaena.seller.data.network.service.MoveService;


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
}
