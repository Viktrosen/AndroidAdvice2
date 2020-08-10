package com.hfrad.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class SettingFragment extends Fragment {
    List<String> cityNames = new ArrayList<>();
    CitiesDao educationDao = App
            .getInstance()
            .getCityDao();
    CitySource citySource = new CitySource(educationDao);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_setting, container,
                false);
        Button backButton = rootView.findViewById(R.id.button2);
        final Spinner spinner = rootView.findViewById(R.id.spinner2);
        final EditText editText = rootView.findViewById(R.id.enterCity);



        List<City> cityList = new ArrayList<>(citySource.getCities());


        List<String> cityNames = new ArrayList<>();

        if (!cityList.isEmpty()){
        for (int i = 0; i < cityList.size(); i++) {
            cityNames.add(cityList.get(i).cityName);
        }}else {cityNames.addAll(Arrays.asList(getResources().getStringArray(R.array.cities)));}



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, cityNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                try {
                    if (!(editText.getText().toString()).equals("") && (editText.getText() != null)){
                        Field field =  MainActivity.class.getDeclaredField("getCity");
                        field.set(this,(String)editText.getText().toString());
                        if (!cityNames.contains(editText.getText().toString())){
                            City city = new City();
                            city.cityName = editText.getText().toString();
                            citySource.addCity(city);
                        }

                    } else{
                        Field field =  MainActivity.class.getDeclaredField("getCity");
                        field.set(this,(String)spinner.getSelectedItem());}
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                Fragment fragment = new MainFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
            }
        });
        return rootView;
    }
}