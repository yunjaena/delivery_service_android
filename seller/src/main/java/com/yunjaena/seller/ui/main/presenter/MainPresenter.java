package com.yunjaena.seller.ui.main.presenter;

import com.yunjaena.core.network.ApiCallback;
import com.yunjaena.seller.R;
import com.yunjaena.seller.data.entity.Order;
import com.yunjaena.seller.data.network.interactor.MoveInteractor;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {
    private MainContract.View mainView;
    final ApiCallback moveApiCallback = new ApiCallback() {
        @Override
        public void onSuccess(Object object) {
            mainView.showMessage(R.string.success_moving);
            mainView.hideProgress();
        }

        @Override
        public void onFailure(Throwable throwable) {
            mainView.showMessage(R.string.failed_moving);
            mainView.hideProgress();
        }
    };
    final ApiCallback updateListenerApiCallback = new ApiCallback() {
        @Override
        public void onSuccess(Object object) {
            List<Order> orderList = (List<Order>) object;
            if (orderList != null && mainView != null)
                mainView.showRoomOrderStatus(orderList);
        }

        @Override
        public void onFailure(Throwable throwable) {

        }
    };
    private MoveInteractor moveInteractor;
    private CompositeDisposable disposable;

    public MainPresenter(MainContract.View mainView, MoveInteractor moveInteractor) {
        this.mainView = mainView;
        this.moveInteractor = moveInteractor;
        disposable = new CompositeDisposable();
    }

    public void moveRobot(String roomNumber) {
        mainView.showProgress(R.string.progress);
        roomNumber = (roomNumber.equals("0")) ? "home" : roomNumber;
        moveInteractor.moveRobot(roomNumber, moveApiCallback);
    }

    public void setOrderListener() {
        moveInteractor.setDatabaseUpdateListener(updateListenerApiCallback);
    }

    public void loadOrder() {
        mainView.showProgress(R.string.loading);
        Disposable reportDisposable = moveInteractor.loadAllOrder().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderList -> {
                            mainView.showRoomOrderStatus(orderList);
                            mainView.hideProgress();
                        }
                        , error -> {
                            mainView.showMessage(R.string.load_failed);
                            mainView.hideProgress();
                        });


        disposable.add(reportDisposable);
    }

    public void updateOrder(Order order) {
        mainView.showProgress(R.string.progress);
        Disposable reportDisposable = moveInteractor.updateOrder(order).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isSuccess -> {
                    if (isSuccess) {
                        mainView.showMessage(R.string.send_to_customer);
                        mainView.hideProgress();
                    } else {
                        mainView.showMessage(R.string.failed);
                        mainView.hideProgress();
                    }
                }, throwable -> {
                    mainView.showMessage(R.string.failed);
                    mainView.hideProgress();
                });


        disposable.add(reportDisposable);

    }


    public void destroyView() {
        mainView = null;
        moveInteractor = null;
        disposable.clear();
    }
}
