package com.yunjaena.deliveryservice.ui.main.presenter;

import com.yunjaena.core.network.ApiCallback;
import com.yunjaena.deliveryservice.R;
import com.yunjaena.deliveryservice.data.entity.Order;
import com.yunjaena.deliveryservice.data.network.interactor.MoveInteractor;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {
    private MainContract.View mainView;
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


    public void setOrderListener() {
        moveInteractor.setDatabaseUpdateListener(updateListenerApiCallback);
    }

    public void updateOrder(Order order) {
        mainView.showProgress(R.string.progress);
        Disposable reportDisposable = moveInteractor.updateOrder(order).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isSuccess -> {
                    if (isSuccess) {
                        if (order.getDeliveryStatus() == 1)
                            mainView.showMessage(R.string.finish_order);
                        else
                            mainView.showMessage(R.string.thanks_for_using);
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
