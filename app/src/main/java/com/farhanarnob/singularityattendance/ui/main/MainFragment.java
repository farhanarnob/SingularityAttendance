package com.farhanarnob.singularityattendance.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.farhanarnob.singularityattendance.R;
import com.farhanarnob.singularityattendance.network.repository.StoreListGetApiHandler;
import com.farhanarnob.singularityattendance.network.retrofit.APIClientResponse;
import com.farhanarnob.singularityattendance.network.room.DataModel;
import com.farhanarnob.singularityattendance.network.room.DataRoomDatabase;
import com.farhanarnob.singularityattendance.utility.PreferenceDB;

import java.util.List;

public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static MainFragment newInstance() {
        return new MainFragment();
    }
    public static final String DOWNLOAD = "download";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialRecyclerview();
        downloadStoreList();

    }

    private void initialRecyclerview() {
        recyclerView = getView().findViewById(R.id.rv_seller_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    public void   downloadStoreList(){
        new DownloadTask(getContext()).execute();
    }
    private class DownloadTask extends AsyncTask<Void,Void,Boolean> {

        private Context mContext;

        // doInBackground methods runs on a worker thread
        DownloadTask(Context context){
            this.mContext = context;
        }
        @Override
        protected Boolean doInBackground(Void... objs) {
            StoreListGetApiHandler.getInstance().callAPI(mContext, new APIClientResponse() {
                @Override
                public void failureOnApiCall(String msg, Object sender) {

                }

                @Override
                public void failureOnNetworkConnection(String msg, Object sender) {

                }

                @Override
                public void successOnApiCall(String msg, Object sender) {
                    PreferenceDB.getInstance(mContext).putBoolean(DOWNLOAD,true);
                }
            });
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            LiveData<List<DataModel>> storeList = DataRoomDatabase.getDatabase(mContext)
                    .storeDao().getAllStores();
            mAdapter = new StoreListAdapter(storeList.getValue());
            recyclerView.setAdapter(mAdapter);
        }

    }

}