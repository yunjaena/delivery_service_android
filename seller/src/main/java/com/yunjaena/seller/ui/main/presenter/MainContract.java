package com.yunjaena.seller.ui.main.presenter;

import androidx.annotation.StringRes;

import com.yunjaena.core.contract.BaseView;

public class MainContract {
    public interface View extends BaseView<MainPresenter> {
        void showProgress(String message);

        void showProgress(@StringRes int message);

        void hideProgress();

        void showMessage(String message);

        void showMessage(@StringRes int message);

        void showRoomOrderStatus(int roomNumber, boolean isOrdered);
    }
}
