package com.hfrad.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Хотите покинуть настройки?", Snackbar.LENGTH_LONG).setAction("Да", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int temperature = (int) (Math.random() * 20);
                        Spinner spinner = rootView.findViewById(R.id.button2);
                        TextInputEditText cityInput = rootView.findViewById(R.id.enterCity);

                        String city = "";
                        if (String.valueOf(cityInput.getText()).equals("")) {
                            city = String.valueOf(spinner.getSelectedItem());
                        } else {
                            city = String.valueOf(cityInput.getText());
                        }

                       Intent intent = new Intent("com.hfrad.myapplication.MainFragment");
                        intent.putExtra("City", city);
                        startActivity(intent);
                    }
                }).show();
            }
        });


        return rootView;
    }
}