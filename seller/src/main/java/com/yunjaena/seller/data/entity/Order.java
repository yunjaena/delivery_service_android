package com.yunjaena.seller.data.entity;

public class Order {
    private int roomNumber;
    private int deliveryStatus;

    public Order() {
    }

    public Order(int roomNumber, int deliveryStatus) {
        this.roomNumber = roomNumber;
        this.deliveryStatus = deliveryStatus;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
