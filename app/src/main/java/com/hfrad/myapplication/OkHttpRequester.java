package com.hfrad.myapplication;

import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


// Здесь будем вызывать запросы
public class OkHttpRequester {
    private OnResponseCompleted listener;   // Интерфейс с обратным вызовом

    public OkHttpRequester(OnResponseCompleted listener) {
        this.listener = listener;
    }

    // Синхронный запрос страницы
    public void run(String url) {
        OkHttpClient client = new OkHttpClient();        // Клиент
        Request.Builder builder = new Request.Builder(); // Создаём строителя
        builder.url(url);                                // Указываем адрес
        // сервера
        Request request = builder.build();               // Строим запрос

        Call call = client.newCall(request);             // Ставим запрос
        // в очередь
        call.enqueue(new Callback() {
            // Этот хендлер нужен для синхронизации потоков: если его
            // сконструировать, он запомнит поток и в дальнейшем будет с ним
            // синхронизироваться
            final Handler handler = new Handler();

            // Срабатывает по приходе ответа от сервера
            public void onResponse(Call call, Response response)
                    throws IOException {
                final String answer = response.body().string();
                // Синхронизируем поток с потоком UI
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onCompleted(answer); // Вызываем метод
                        // обратного вызова
                    }
                });
            }

            // При сбое
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    // Интерфейс обратного вызова; метод onCompleted вызывается по окончании
    // загрузки страницы
    public interface OnResponseCompleted {
        void onCompleted(String content);
    }
}

