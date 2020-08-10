package com.hfrad.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.hfrad.myapplication.City;

@Dao
public interface CitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCity(City city);

    @Update
    void updateCity(City city);

    @Delete
    void deleteCity(City city);

    @Query("DELETE FROM city WHERE id = :id")
    void deteleCityById(long id);

    @Query("SELECT * FROM city")
    List<City> getAllCities();

    @Query("SELECT * FROM city WHERE id = :id")
    City getCityById(long id);

    @Query("SELECT COUNT() FROM city")
    long getCountCity();
}
