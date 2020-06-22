package com.yunjaena.deliveryservice.data.entity;

import androidx.annotation.Keep;

import com.google.firebase.database.Exclude;
import com.yunjaena.deliveryservice.util.DateUtil;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Keep
public class Order implements Serializable {
    private int roomNumber;
    private int deliveryStatus;
    private String recentDeliveryDate;

    public Order() {
    }

    public Order(Builder builder) {
        this.roomNumber = builder.roomNumber;
        this.deliveryStatus = builder.deliveryStatus;
        this.recentDeliveryDate = builder.recentDeliveryDate;
    }


    public String getRecentDeliveryDate() {
        return recentDeliveryDate;
    }

    public void setRecentDeliveryDate(String recentDeliveryDate) {
        this.recentDeliveryDate = recentDeliveryDate;
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



    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("roomNumber", roomNumber);
        result.put("deliveryStatus", deliveryStatus);
        result.put("recentDeliveryDate", recentDeliveryDate);
        return result;

    }
    public static class Builder {
        private int roomNumber = 0;
        private int deliveryStatus = 0;
        private String recentDeliveryDate = DateUtil.getCurrentDateWithUnderBar();

        public Builder roomNumber(int roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public Builder deliveryStatus(int deliveryStatus) {
            this.deliveryStatus = deliveryStatus;
            return this;
        }

        public Builder recentDeliveryDate(String recentDeliveryDate) {
            this.recentDeliveryDate = recentDeliveryDate;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
