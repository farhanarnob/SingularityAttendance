package com.farhanarnob.singularityattendance.network.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface StoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DataModel storeInfo);

    @Query("SELECT * from DataModel ORDER BY id ASC")
    LiveData<List<DataModel>> getAllStores();

    @Query("DELETE FROM DataModel")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DataModel> resultModel);
}
