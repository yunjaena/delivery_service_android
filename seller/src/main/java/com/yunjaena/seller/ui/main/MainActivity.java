package com.yunjaena.seller.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yunjaena.core.activity.ActivityBase;
import com.yunjaena.core.toast.ToastUtil;
import com.yunjaena.seller.R;
import com.yunjaena.seller.data.entity.Order;
import com.yunjaena.seller.data.network.interactor.MoveRestInteractor;
import com.yunjaena.seller.ui.main.adapater.MainOrderAdapter;
import com.yunjaena.seller.ui.main.presenter.MainContract;
import com.yunjaena.seller.ui.main.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActivityBase implements View.OnClickListener, MainContract.View, MainOrderAdapter.OrderRecyclerViewClickListener {
    private ImageView roomOneStatusImageView;
    private ImageView roomTwoStatusImageView;
    private ImageView roomThreeStatusImageView;
    private ImageView roomFourStatusImageView;
    private ImageView roomFiveStatusImageView;
    private ImageView roomSixStatusImageView;
    private Button roomOneMoveButton;
    private Button roomTwoMoveButton;
    private Button roomThreeMoveButton;
    private Button roomFourMoveButton;
    private Button roomFiveMoveButton;
    private Button roomSixMoveButton;
    private Button homeMoveButton;
    private RecyclerView orderRecyclerView;
    private MainOrderAdapter mainOrderAdapter;
    private LinearLayoutManager linearLayoutManager;

    private MainPresenter mainPresenter;
    private List<Order> orderList;
    private List<Order> allOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        orderList = new ArrayList<>();
        allOrderList = new ArrayList<>();
        mainPresenter = new MainPresenter(this, new MoveRestInteractor());
        roomOneStatusImageView = findViewById(R.id.room1_status_image_view);
        roomTwoStatusImageView = findViewById(R.id.room2_status_image_view);
        roomThreeStatusImageView = findViewById(R.id.room3_status_image_view);
        roomFourStatusImageView = findViewById(R.id.room4_status_image_view);
        roomFiveStatusImageView = findViewById(R.id.room5_status_image_view);
        roomSixStatusImageView = findViewById(R.id.room6_status_image_view);
        roomOneMoveButton = findViewById(R.id.room1_move_button);
        roomTwoMoveButton = findViewById(R.id.room2_move_button);
        roomThreeMoveButton = findViewById(R.id.room3_move_button);
        roomFourMoveButton = findViewById(R.id.room4_move_button);
        roomFiveMoveButton = findViewById(R.id.room5_move_button);
        roomSixMoveButton = findViewById(R.id.room6_move_button);
        homeMoveButton = findViewById(R.id.home_move_button);
        orderRecyclerView = findViewById(R.id.order_recycler_view);
        initRecyclerView();
        bindEvent();
        mainPresenter.setOrderListener();
    }

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(this);
        mainOrderAdapter = new MainOrderAdapter(orderList);
        mainOrderAdapter.setOrderRecyclerViewClickListener(this);
        orderRecyclerView.setLayoutManager(linearLayoutManager);
        orderRecyclerView.setAdapter(mainOrderAdapter);
    }

    private void bindEvent() {
        roomOneMoveButton.setOnClickListener(this);
        roomTwoMoveButton.setOnClickListener(this);
        roomThreeMoveButton.setOnClickListener(this);
        roomFourMoveButton.setOnClickListener(this);
        roomFiveMoveButton.setOnClickListener(this);
        roomSixMoveButton.setOnClickListener(this);
        homeMoveButton.setOnClickListener(this);
    }

    private void updateUI() {
        orderList.clear();
        for (int i = 0; i < allOrderList.size(); i++) {
            if (allOrderList.get(i).getDeliveryStatus() != 0) {
                orderList.add(allOrderList.get(i));
                setStatusColor(allOrderList.get(i).getRoomNumber(), true);
            } else {
                setStatusColor(allOrderList.get(i).getRoomNumber(), false);
            }
        }
        mainOrderAdapter.notifyDataSetChanged();
    }

    private void setStatusColor(int roomNumber, boolean isOrdered) {
        ImageView imageView = null;
        int imageId = (isOrdered) ? R.drawable.ic_circle_green : R.drawable.ic_circle_red;
        switch (roomNumber) {
            case 1:
                imageView = roomOneStatusImageView;
                break;
            case 2:
                imageView = roomTwoStatusImageView;
                break;
            case 3:
                imageView = roomThreeStatusImageView;
                break;
            case 4:
                imageView = roomFourStatusImageView;
                break;
            case 5:
                imageView = roomFiveStatusImageView;
                break;
            case 6:
                imageView = roomSixStatusImageView;
                break;
        }
        if (imageView != null)
            imageView.setBackground(getResources().getDrawable(imageId));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.room1_move_button:
                mainPresenter.moveRobot("1");
                break;
            case R.id.room2_move_button:
                mainPresenter.moveRobot("2");
                break;
            case R.id.room3_move_button:
                mainPresenter.moveRobot("3");
                break;
            case R.id.room4_move_button:
                mainPresenter.moveRobot("4");
                break;
            case R.id.room5_move_button:
                mainPresenter.moveRobot("5");
                break;
            case R.id.room6_move_button:
                mainPresenter.moveRobot("6");
                break;
            case R.id.home_move_button:
                mainPresenter.moveRobot("0");
                break;
        }
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
        if (hasNewOrder(orderList)) {
            ToastUtil.getInstance().makeShort("새로운 주문!!!");
        }

        if (isDeliveryEnd(orderList)) {
            ToastUtil.getInstance().makeShort("배달이 완료되었습니다.");
        }

        allOrderList.clear();
        allOrderList.addAll(orderList);
        updateUI();
    }

    public boolean hasNewOrder(List<Order> orderList) {
        if (allOrderList.size() == 0)
            return false;

        for (int a = 0; a < allOrderList.size(); a++) {
            for (int b = 0; b < orderList.size(); b++) {
                Order currentOrder = allOrderList.get(a);
                Order newOrder = orderList.get(b);
                if (currentOrder.getRoomNumber() == newOrder.getRoomNumber() && currentOrder.getDeliveryStatus() == 0 && newOrder.getDeliveryStatus() != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDeliveryEnd(List<Order> orderList) {
        if (allOrderList.size() == 0)
            return false;

        for (int a = 0; a < allOrderList.size(); a++) {
            for (int b = 0; b < orderList.size(); b++) {
                Order currentOrder = allOrderList.get(a);
                Order newOrder = orderList.get(b);
                if (currentOrder.getRoomNumber() == newOrder.getRoomNumber() && currentOrder.getDeliveryStatus() != 0 && newOrder.getDeliveryStatus() == 0) {
                    return true;
                }
            }
        }
        return false;
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

    @Override
    public void onClickOrderButton(int position, boolean isReceipt) {
        Order order = orderList.get(position);
        if(isReceipt)
            order.setDeliveryStatus(2);
        else
            order.setDeliveryStatus(3);

        mainPresenter.updateOrder(order);
    }
}
