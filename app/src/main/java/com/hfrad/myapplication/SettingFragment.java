package com.hfrad.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_setting, container,
                false);
        Button backButton = rootView.findViewById(R.id.button2);
        final Spinner spinner = rootView.findViewById(R.id.spinner2);
        final EditText cityInput = (EditText)getActivity().findViewById(R.id.enterCity);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        int temperature = (int) (Math.random() * 20);
                        String city = "";

                try {
                    Field field =  MainActivity.class.getDeclaredField("getCity");
                    field.set(this,(String)spinner.getSelectedItem());
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }






                Fragment fragment = new MainFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();


            }
        });
        return rootView;
    }
}