package com.example.weather2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity implements WeatherDelegate{


    MyApplication mApp;



    ImageView mIcon;
    TextView mTemperature;
    TextView mFeelsLike;
    TextView mMinTemp;
    TextView mMaxTemp;
    TextView mPressure;
    TextView mHumidity;
    TextView mVisibilit;
    TextView mWindSpeed;
    TextView mWindDeg;
    TextView mClouds;
    TextView mSunSer;
    TextView mSunRise;
    TextView mDescript;
    Button mUpdate;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mApp=(MyApplication)getApplicationContext();


        mTemperature=findViewById(R.id.temperatura);
        mFeelsLike=findViewById(R.id.FeelsLike);
        mMinTemp=findViewById(R.id.minTemp);
        mMaxTemp=findViewById(R.id.maxTemp);
        mPressure=findViewById(R.id.pressure);
        mHumidity=findViewById(R.id.humidity);
        mVisibilit=findViewById(R.id.visibilit);
        mWindSpeed=findViewById(R.id.windSpeed);
        mWindDeg=findViewById(R.id.windDeg);
        mClouds=findViewById(R.id.clouds);
        mSunSer=findViewById(R.id.sunSer);
        mSunRise=findViewById(R.id.sunRise);


        mIcon=findViewById(R.id.icon);
        mUpdate=findViewById(R.id.update);


        mApp.getWeather().setDelegate(this);


        View.OnClickListener updateListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdatePress(v);
            }
        };
        mUpdate.setOnClickListener(updateListener);

    }

    private void onUpdatePress(View view)
    {
        mApp.getWeather().update();
    }


    @Override
    public void onUpdate()
    {
        mTemperature.setText(" Температура: " + mApp.getWeather().currentTemperature+" ℃");
        mFeelsLike.setText("Ощущается как: "+mApp.getWeather().feelsLike+" ℃");
        mMinTemp.setText("Мин. темп.: "+mApp.getWeather().minTemp+" ℃");
        mMaxTemp.setText("Макс. темп.: "+mApp.getWeather().maxTemp+" ℃");
        mPressure.setText("Давление:  "+mApp.getWeather().pressure);
        mHumidity.setText("Влажность:  "+mApp.getWeather().humidity);
        mVisibilit.setText("Видимость:  "+mApp.getWeather().visibility);
        mWindSpeed.setText("Скорость ветра:  "+mApp.getWeather().windSpeed);
        mWindDeg.setText("Направление:  "+mApp.getWeather().windDeg);
        mClouds.setText("Облака:  "+mApp.getWeather().clouds);
        mSunSer.setText("Закат:  "+mApp.getWeather().sunSer);
        mSunRise.setText("Рассвет:  "+mApp.getWeather().sunRise);

        Picasso.with(this).load(mApp.getWeather().icon).into(mIcon);
    }



    @Override
    public void onError()
    {
        Toast.makeText(this,"Connect error!",Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {

            case R.id.cities:
                cities();
                return true;


            case R.id.about:
                about();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void cities()
    {

        Intent intent = new Intent(getApplicationContext(),Cities.class);
        startActivity(intent);
    }


    private void about()
    {
        Intent intent = new Intent(getApplicationContext(),About.class);
        startActivity(intent);
    }


}