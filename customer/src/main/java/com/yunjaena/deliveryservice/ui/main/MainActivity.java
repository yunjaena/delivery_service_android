package com.yunjaena.deliveryservice.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.yunjaena.core.activity.ActivityBase;
import com.yunjaena.core.notification.NotificationManager;
import com.yunjaena.core.toast.ToastUtil;
import com.yunjaena.deliveryservice.R;
import com.yunjaena.deliveryservice.data.entity.Order;
import com.yunjaena.deliveryservice.data.network.interactor.MoveRestInteractor;
import com.yunjaena.deliveryservice.ui.main.presenter.MainContract;
import com.yunjaena.deliveryservice.ui.main.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActivityBase implements MainContract.View {
    public static int NOTIFICATION_CHANNEL_ID = 100;
    private Button deliveryStatusButton;
    private TextView deliveryStatusTextView;
    private Spinner roomNumberSpinner;
    private int currentRoomNumber;
    private List<Order> orderList;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        orderList = new ArrayList<>();
        mainPresenter = new MainPresenter(this, new MoveRestInteractor());
        currentRoomNumber = 1;
        deliveryStatusButton = findViewById(R.id.delivery_status_button);
        deliveryStatusTextView = findViewById(R.id.delivery_status_text_view);
        roomNumberSpinner = findViewById(R.id.room_number_spinner);
        deliveryStatusButton.setOnClickListener(v -> {
            Order order = getCurrentRoomOrder(currentRoomNumber);
            if (order == null) return;
            if (order.getDeliveryStatus() == 0) {
                order.setDeliveryStatus(1);
                mainPresenter.updateOrder(order);
            } else {
                order.setDeliveryStatus(0);
                mainPresenter.updateOrder(order);
            }
        });
        setSpinner();
        mainPresenter.setOrderListener();
    }

    public Order getCurrentRoomOrder(int roomNumber) {
        for (Order order : orderList) {
            if (order.getRoomNumber() == roomNumber)
                return order;
        }

        return null;
    }

    public void setSpinner() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.room_number));
        spinnerAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        roomNumberSpinner.setAdapter(spinnerAdapter);
        roomNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentRoomNumber = position + 1;
                updateUI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void updateUI() {
        if (orderList.size() == 0)
            return;

        Order order = getCurrentRoomOrder(currentRoomNumber);
        setDeliveryStatusTextView(order.getDeliveryStatus());
        showDeliveryStatusNotification(order.getDeliveryStatus());
        setDeliveryStatusButton(order.getDeliveryStatus());

    }


    private void setDeliveryStatusTextView(int status) {
        String statusText = "";
        switch (status) {
            case 0:
                statusText = getResources().getString(R.string.order_wait);
                break;
            case 1:
                statusText = getResources().getString(R.string.receipt_wait);
                break;
            case 2:
                statusText = getResources().getString(R.string.delivery_wait);
                break;
            case 3:
                statusText = getResources().getString(R.string.delivery_now);
                break;
        }

        deliveryStatusTextView.setText(statusText);

    }

    private void showDeliveryStatusNotification(int status) {
        String statusText = "";
        switch (status) {
            case 0:
                statusText = getResources().getString(R.string.order_wait);
                break;
            case 1:
                statusText = getResources().getString(R.string.receipt_wait);
                break;
            case 2:
                statusText = getResources().getString(R.string.delivery_wait);
                break;
            case 3:
                statusText = getResources().getString(R.string.delivery_now);
                break;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager.sendNotification(this, NOTIFICATION_CHANNEL_ID, NotificationManager.Channel.MESSAGE, getResources().getString(R.string.app_name), statusText);
        } else {
            NotificationManager.sendNotification(this, NOTIFICATION_CHANNEL_ID, getResources().getString(R.string.app_name), statusText);
        }
    }


    private void setDeliveryStatusButton(int status) {
        String statusText = "";
        switch (status) {
            case 0:
                statusText = getResources().getString(R.string.order);
                break;
            case 1:
            case 2:
                statusText = getResources().getString(R.string.order_cancel);
                break;
            default:
                statusText = getResources().getString(R.string.delivery_finish);
                break;
        }

        deliveryStatusButton.setText(statusText);

    }


    @Override
    public void showProgress(String message) {
        showProgressDialog(message);
    }

    @Override
    public void showProgress(int message) {
        showProgressDialog(message);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void showMessage(String message) {
        ToastUtil.getInstance().makeShort(message);
    }

    @Override
    public void showMessage(int message) {
        ToastUtil.getInstance().makeShort(message);
    }

    @Override
    public void showRoomOrderStatus(List<Order> orderList) {
        this.orderList.clear();
        this.orderList.addAll(orderList);
        updateUI();
    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        this.mainPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.destroyView();
    }
}
