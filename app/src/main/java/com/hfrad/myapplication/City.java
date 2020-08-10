package com.hfrad.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(indices = {@Index(value = {"city_name"})})
public class City {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "city_name")
    public String cityName;

}
