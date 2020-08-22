package com.farhanarnob.singularityattendance.network.room;

import android.os.AsyncTask;

import com.farhanarnob.singularityattendance.network.room.DataRoomDatabase;
import com.farhanarnob.singularityattendance.network.room.StoreDao;

public class PopulateDBAsync extends AsyncTask<Void, Void, Void> {

    private final StoreDao mDao;

    PopulateDBAsync(DataRoomDatabase db) {
        mDao = db.storeDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        mDao.deleteAll();
        return null;
    }
}