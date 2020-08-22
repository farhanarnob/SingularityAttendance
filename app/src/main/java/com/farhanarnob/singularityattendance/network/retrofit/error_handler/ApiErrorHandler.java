package com.farhanarnob.singularityattendance.network.retrofit.error_handler;
import android.content.Context;
import android.content.Intent;
import com.farhanarnob.singularityattendance.MainActivity;
import com.farhanarnob.singularityattendance.R;
import com.farhanarnob.singularityattendance.network.retrofit.RestError;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import retrofit2.Response;

public class ApiErrorHandler {
    public static final String TSYNC_ERROR = "tsync_id";
    private static final String INVALID_TOKEN = "Invalid token";
    private static final String AUTH_NOT_PROVIDED = "Authentication credentials were not provided";

    public static void handleError(Context context, Throwable throwable, RetrofitErrorResponse response) {
        String errorMessage = context.getString(R.string.retrofit_request_fail);
        try {
            if (throwable != null) {
                errorMessage = errorMessage + " " + throwable.getLocalizedMessage().trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response != null) response.errorMessage(errorMessage);
    }

    public static void handleError(Context context, Response<?> response, RetrofitErrorResponse errorResponse) {
        String errorMessage = context.getString(R.string.api_handler_error);
        if (response.errorBody() != null) try {
            JSONObject jsonObject = new JSONObject(response.errorBody().string());
            RestError restError = new RestError(jsonObject.getBoolean("success"), jsonObject.getString("message"));
            errorMessage = restError.message;
            switch (response.code()) {
                case 400:
                    if (restError.message.contains(TSYNC_ERROR)) {
                        if (errorResponse != null) errorResponse.tSyncError();
                    }
                    break;
                case 500:
                    if (restError.message.contains(INVALID_TOKEN)) {
                        context.startActivity(new Intent(context, MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        if (errorResponse != null) errorResponse.invalidTokenError();
                    }

                    break;
                case 401:
                    if (restError.message.equalsIgnoreCase(AUTH_NOT_PROVIDED)) {
                        context.startActivity(new Intent(context, MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
            }
        } catch (JsonSyntaxException | JsonIOException | IOException | JSONException e) {
            e.printStackTrace();
        }
        if (errorResponse != null) errorResponse.errorMessage(errorMessage);

    }

}
