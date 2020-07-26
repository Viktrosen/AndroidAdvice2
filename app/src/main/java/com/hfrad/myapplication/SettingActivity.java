package com.hfrad.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_setting);
        Button backButton = findViewById(R.id.button2);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Хотите покинуть настройки?", Snackbar.LENGTH_LONG).setAction("Да", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int temperature = (int) (Math.random() * 20);
                        Spinner spinner = findViewById(R.id.spinner2);
                        TextInputEditText cityInput = findViewById(R.id.enterCity);

                        String city = "";
                        if (String.valueOf(cityInput.getText()).equals("")) {
                            city = String.valueOf(spinner.getSelectedItem());
                        } else {
                            city = String.valueOf(cityInput.getText());
                        }
                        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                        intent.putExtra("City", city);
                        startActivity(intent);
                        finish();
                    }
                }).show();
            }
        });



    }
}
