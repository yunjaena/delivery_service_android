package com.yunjaena.seller.ui.main;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        orderList = new ArrayList<>();
        mainPresenter = new MainPresenter(this, new MoveRestInteractor());
        orderList.add(new Order(1,0));
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

    }

    private void initRecyclerView(){
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
    public void showRoomOrderStatus(int roomNumber, boolean isOrdered) {
        ToastUtil.getInstance().makeShort(roomNumber + " : " + isOrdered);
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
        ToastUtil.getInstance().makeShort(position + " : " + isReceipt);
    }
}
