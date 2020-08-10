package com.hfrad.myapplication;
import java.util.List;

import com.hfrad.myapplication.CitiesDao;
import com.hfrad.myapplication.City;

public class CitySource {
    private final CitiesDao citiesDao;

    // Буфер с данными: сюда будем подкачивать данные из БД
    private List<City> cities;

    public CitySource(CitiesDao citiesDao){
        this.citiesDao = citiesDao;
    }

    // Получить всех студентов
    public List<City> getCities(){
        // Если объекты еще не загружены, загружаем их.
        // Это сделано для того, чтобы не делать запросы к БД каждый раз
        if (cities == null){
            LoadCities();
        }
        return cities;
    }

    // Загружаем студентов в буфер
    public void LoadCities(){
        cities = citiesDao.getAllCities();
    }

    // Получаем количество записей
    public long getCountCity(){
        return citiesDao.getCountCity();
    }

    // Добавляем студента
    public void addCity(City city){
        citiesDao.insertCity(city);
        // После изменения БД надо повторно прочесть данные из буфера
        LoadCities();
    }

    // Заменяем студента
    public void updateCity(City city){
        citiesDao.updateCity(city);
        LoadCities();
    }

    // Удаляем студента из базы
    public void removeCity(long id){
        citiesDao.deteleCityById(id);
        LoadCities();
    }

}
