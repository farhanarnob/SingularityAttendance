package com.farhanarnob.singularityattendance.network.retrofit;

import com.google.gson.annotations.SerializedName;

public class RestError {
    @SerializedName("success")
    public boolean success;
    @SerializedName("message")
    public String message;
    public RestError(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
