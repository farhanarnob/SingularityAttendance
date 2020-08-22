package com.farhanarnob.singularityattendance.network.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.farhanarnob.singularityattendance.R;
import com.farhanarnob.singularityattendance.network.retrofit.APICallHandler;
import com.farhanarnob.singularityattendance.network.retrofit.APIClientResponse;
import com.farhanarnob.singularityattendance.network.retrofit.APIHandler;
import com.farhanarnob.singularityattendance.network.retrofit.error_handler.ApiErrorHandler;
import com.farhanarnob.singularityattendance.network.retrofit.error_handler.RetrofitErrorResponse;
import com.farhanarnob.singularityattendance.network.room.DataModel;
import com.farhanarnob.singularityattendance.network.room.DataRoomDatabase;
import com.farhanarnob.singularityattendance.network.room.StoreDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;


public class StoreListGetApiHandler extends APICallHandler<Long> {
    public static final String STORE_LIST_REPORT_URL = "/api/stores?type=number&default=1";
    public static final String DATA = "data";
    private static volatile APICallHandler<Long> singleton;
    private StoreListGetAPI storeListGetAPI;
    private APIClientResponse callback;
    private boolean successFlag;

    private StoreListGetApiHandler() {
        storeListGetAPI = new APIHandler<StoreListGetAPI>().getApiInterface(StoreListGetAPI.class);
    }

    public static StoreListGetApiHandler getInstance() {
        if (singleton == null)
            singleton = new StoreListGetApiHandler();
        return (StoreListGetApiHandler) singleton;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        return new CloneNotSupportedException();
    }

    @Override
    public void callAPI(final Context context, final APIClientResponse callback) {
        super.callAPI(context,callback);
        this.callback = callback;
        this.context = context;

        storeListGetAPI.getStoreList(STORE_LIST_REPORT_URL).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        success(response.body());
                    }
                } else {
                    ApiErrorHandler.handleError(context, response, retrofitErrorResponse);
                }
            }
            private void success(final JsonObject response) {
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    if (jsonObject.getJSONArray(DATA)!=null) {
                        final JSONArray storeListArray = jsonObject.getJSONArray(DATA);
                        Type userListType = new TypeToken<ArrayList<DataModel>>(){}.getType();
                        new AsyncTask<Void,Void,Boolean>(){

                            @Override
                            protected Boolean doInBackground(Void... voids) {
                                StoreDao storeDao = DataRoomDatabase.getDatabase(context)
                                        .storeDao();
                                for (int i = 0; i < storeListArray.length(); i++){
                                    try {
                                        DataModel dataModel = new DataModel();
                                        dataModel.setId((storeListArray
                                                .getJSONObject(i)).getInt("id"));
                                        dataModel.setName((storeListArray
                                                .getJSONObject(i)).getString("name"));
                                        dataModel.setAddress((storeListArray
                                                .getJSONObject(i)).getString("address"));

                                        storeDao.insert(dataModel);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        databaseFailure(e);
                                    }

                                }
                                return true;
                            }

                        }.execute();
                        if (successFlag) successCallBack();
                    } else {
                        failureCallBack(context.getString(R.string.api_failure_txt) + "\t" + StoreListGetApiHandler.class.getSimpleName());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    databaseFailure(e);
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ApiErrorHandler.handleError(context, t, retrofitErrorResponse);
            }
        });
    }

    private RetrofitErrorResponse retrofitErrorResponse = new RetrofitErrorResponse() {
        @Override
        public void errorMessage(String errorMessage) {
            failureCallBack(errorMessage);
        }

        @Override
        public void tSyncError() {

        }

        @Override
        public void invalidTokenError() {

        }
    };

    private void failureCallBack(String errorMessage) {
        if (callback != null) {
            try {
                callback.failureOnApiCall(errorMessage, this);
            } catch (Exception e) {
                callback.failureOnApiCall(errorMessage, this.getClass().getSimpleName());
            }
        }
    }

    private void successCallBack() {
        callback.successOnApiCall("Success " + StoreListGetApiHandler.class.getSimpleName(), singleton);
    }

    private void databaseFailure(Exception error) {
        successFlag = false;
        error.printStackTrace();
    }



    private interface StoreListGetAPI {
        @GET
        Call<JsonObject> getStoreList(@Url String url);
    }
}
