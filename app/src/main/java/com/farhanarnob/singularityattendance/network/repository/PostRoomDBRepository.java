package com.farhanarnob.singularityattendance.network.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.farhanarnob.singularityattendance.network.room.DataModel;
import com.farhanarnob.singularityattendance.network.room.DataRoomDatabase;
import com.farhanarnob.singularityattendance.network.room.StoreDao;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PostRoomDBRepository {
    private StoreDao storeDao;
    LiveData<List<DataModel>> mAllStore;

    public PostRoomDBRepository(Application application){
        DataRoomDatabase db = DataRoomDatabase.getDatabase(application);
        storeDao = db.storeDao();
        mAllStore = storeDao.getAllStores();
    }

    public LiveData<List<DataModel>> getAllPosts() {
        return mAllStore;
    }
    public void insertPosts (List<DataModel> resultModel) {
        if(resultModel!=null) {
            new insertAsyncTask(storeDao).execute(resultModel);
        }
    }

    private static class insertAsyncTask extends AsyncTask<List<DataModel>, Void, Void> {

        private StoreDao mAsyncTaskDao;

        insertAsyncTask(StoreDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<DataModel>... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }
}
