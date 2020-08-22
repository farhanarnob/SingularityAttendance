package com.farhanarnob.singularityattendance;

import android.app.Application;

import com.farhanarnob.singularityattendance.network.room.DataRoomDatabase;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataRoomDatabase.getDatabase(this);

    }
}
