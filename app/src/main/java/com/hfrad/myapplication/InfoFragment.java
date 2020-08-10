package com.hfrad.myapplication;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.Objects;

public class InfoFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final WebView page = Objects.requireNonNull(getActivity()).findViewById(R.id.page);

        OkHttpRequester requester;
        requester = new OkHttpRequester(new OkHttpRequester.OnResponseCompleted() {
            // Вызывается по завершении закачки страницы
            @Override
            public void onCompleted(String content) {
                page.loadData(content, "text/html; charset=utf-8", "utf-8");
            }
        });
        // Запускаем запрос
        requester.run("https://https://ru.wikipedia.org/wiki/Пермь"); // Загружаем страницу

        return inflater.inflate(R.layout.fragment_info, container, false);

    }

}
