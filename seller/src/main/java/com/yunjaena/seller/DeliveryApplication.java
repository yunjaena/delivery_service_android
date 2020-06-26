package com.yunjaena.seller;

import android.app.Application;
import android.content.Context;

import com.yunjaena.core.notification.NotificationManager;
import com.yunjaena.core.toast.ToastUtil;

public class DeliveryApplication extends Application {
    private Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public void init() {
        applicationContext = this;
        ToastUtil.getInstance().init(applicationContext);
        NotificationManager.createChannel(applicationContext);
    }
}
