package com.yunjaena.seller.ui.main.presenter;

import com.yunjaena.core.network.ApiCallback;
import com.yunjaena.seller.R;
import com.yunjaena.seller.data.network.interactor.MoveInteractor;

public class MainPresenter {
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
    private MainContract.View mainView;
    private MoveInteractor moveInteractor;

    public MainPresenter(MainContract.View mainView, MoveInteractor moveInteractor) {
        this.mainView = mainView;
        this.moveInteractor = moveInteractor;
        mainView.setPresenter(this);
    }

    public void moveRobot(String roomNumber) {
        mainView.showProgress(R.string.progress);
        roomNumber = (roomNumber.equals("0")) ? "home" : roomNumber;
        moveInteractor.moveRobot(roomNumber, moveApiCallback);
    }

    public void destroyView() {

    }
}
