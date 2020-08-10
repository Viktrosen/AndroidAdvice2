package com.hfrad.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {City.class}, version = 1)
public abstract class CityDatabase extends RoomDatabase {
    public abstract CitiesDao getCityDao();
}
