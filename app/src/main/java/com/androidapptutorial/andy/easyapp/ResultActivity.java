package com.androidapptutorial.andy.easyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.androidapptutorial.andy.easyapp.image.ImageCacheManager;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle Bundle1 = this.getIntent().getExtras();
        int cityID = Bundle1.getInt("cityID");
        String cityName = Bundle1.getString("cityName");
        Double lat = Bundle1.getDouble("lat");
        Double lon = Bundle1.getDouble("lon");
        String weatherMain = Bundle1.getString("weatherMain");
        String weatherIcon = Bundle1.getString("weatherIcon");

        TextView txtCityId = (TextView) this.findViewById(R.id.cityId);
        TextView txtCityName = (TextView) this.findViewById(R.id.cityName);

        txtCityId.setText(String.valueOf(cityID));
        txtCityName.setText(cityName);

        // ICON Url - http://openweathermap.org/img/w/10d.png
        ImageLoader mImageLoader = ImageCacheManager.getInstance().getImageLoader();
        NetworkImageView imgWeatherIcon = (NetworkImageView) this.findViewById(R.id.weatherIcon);
        imgWeatherIcon.setDefaultImageResId(R.mipmap.ic_launcher);
        imgWeatherIcon.setErrorImageResId(R.mipmap.ic_launcher);
        imgWeatherIcon.setImageUrl("http://openweathermap.org/img/w/"+weatherIcon+".png", mImageLoader);

    }
}
