package com.farhanarnob.singularityattendance.network.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {DataModel.class}, version = 1)
public abstract class DataRoomDatabase extends RoomDatabase {
    public abstract StoreDao storeDao();

    private static DataRoomDatabase INSTANCE;


    public static DataRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DataRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataRoomDatabase.class, "store_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
