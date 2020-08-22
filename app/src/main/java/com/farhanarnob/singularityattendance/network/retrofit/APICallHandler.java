package com.farhanarnob.singularityattendance.network.retrofit;

import android.content.Context;

public abstract class APICallHandler<T> {
    protected APIClientResponse callback;
    protected Context context;

    public void callAPI(Context context, final APIClientResponse callback) {
        this.context = context;
        this.callback = callback;
    }
}
