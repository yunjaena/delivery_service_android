package com.yunjaena.deliveryservice.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaultResponse {
    @SerializedName("response")
    @Expose
    public boolean isSuccess;
}
