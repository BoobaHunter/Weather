package com.example.weather2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class About extends AppCompatActivity {
    //ссылка на приложение
    MyApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //отодбражаем кнопку назад
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // получаем ссылку на приложение
        mApp=(MyApplication)getApplicationContext();

    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
}