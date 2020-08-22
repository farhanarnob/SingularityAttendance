package com.farhanarnob.singularityattendance.network.retrofit.error_handler;

public interface RetrofitErrorResponse {
    void errorMessage(String errorMessage);
    void tSyncError();
    void invalidTokenError();
}
