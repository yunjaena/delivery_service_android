package com.yunjaena.seller.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.yunjaena.core.activity.ActivityBase;
import com.yunjaena.seller.R;
import com.yunjaena.seller.ui.main.MainActivity;

public class SplashActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 2000);
    }
}
