package com.farhanarnob.singularityattendance.network.room;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "DataModel")
public class DataModel {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    @NonNull
    String name;
    @ColumnInfo(name = "address")
    @SerializedName("address")
    @NonNull
    String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", name = "+name+", address = "+address+"]";
    }
}
