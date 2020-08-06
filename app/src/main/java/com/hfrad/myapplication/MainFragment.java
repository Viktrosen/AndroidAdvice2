package com.hfrad.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=Moscow,RU&appid=";
    private static final String WEATHER_API_KEY = "f08d91ee6d0e41fc37b2b993e4e93faa";

    private EditText city;
    private EditText temperature;
    private EditText pressure;
    private EditText humidity;
    private EditText windSpeed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container,
                false);
        init(rootView);
        initShow(rootView);
        return rootView;
    }

    private void init(View rootView) {
        city = rootView.findViewById(R.id.textCity);
        temperature = rootView.findViewById(R.id.textTemprature);
        pressure = rootView.findViewById(R.id.textPressure);
        humidity = rootView.findViewById(R.id.textHumidity);
        windSpeed = rootView.findViewById(R.id.textWindspeed);

        Button info = rootView.findViewById(R.id.button3);
        info.setOnClickListener(infoListener);

    }

    View.OnClickListener infoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String url = "https://ru.wikipedia.org/wiki/" + city.getText();
            Uri uri = Uri.parse(url);
            Intent browser = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(browser);
        }
    };




    private void initShow(View v){

        try {
                final URL uri;
                if (MainActivity.getCity!=null) {
                    uri = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + MainActivity.getCity + ",RU&appid=" + WEATHER_API_KEY);
                } else {
                    uri = new URL(WEATHER_URL + WEATHER_API_KEY);
                }
                final Handler handler = new Handler(); // Запоминаем основной поток
                new Thread(new Runnable() {
                    public void run() {
                        HttpsURLConnection urlConnection = null;
                        try {
                            urlConnection = (HttpsURLConnection) uri.openConnection();
                            urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
                            urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
                            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
                            String result = getLines(in);
                            // преобразование данных запроса в модель
                            Gson gson = new Gson();
                            final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                            // Возвращаемся к основному потоку
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    displayWeather(weatherRequest);
                                }
                            });
                        } catch (Exception e) {
                            Log.e(TAG, "Fail connection", e);
                            e.printStackTrace();
                        } finally {
                            if (null != urlConnection) {
                                urlConnection.disconnect();
                            }
                        }
                    }
                }).start();
            } catch (MalformedURLException e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                // В билдере указываем заголовок окна (можно указывать как ресурс,
                // так и строку)
                builder.setTitle("ALARM")
                        // Указываем сообщение в окне (также есть вариант со
                        // строковым параметром)
                        .setMessage("Ошибка в получении данных с сервера")
                        // Можно указать и пиктограмму
                        .setIcon(R.mipmap.ic_launcher_round)
                        // Из этого окна нельзя выйти кнопкой Back
                        .setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();

                Log.e(TAG, "Fail URI", e);
                e.printStackTrace();
            }
        }

        private String getLines(BufferedReader in) {
            return in.lines().collect(Collectors.joining("\n"));
        }

        private void displayWeather(WeatherRequest weatherRequest) {
            city.setText(weatherRequest.getName());
            temperature.setText(String.format("%f2", weatherRequest.getMain().getTemp() - 273.15f));
            pressure.setText(String.format("%d", weatherRequest.getMain().getPressure()));
            humidity.setText(String.format("%d", weatherRequest.getMain().getHumidity()));
            windSpeed.setText(String.format("%d", weatherRequest.getWind().getSpeed()));
        }




}