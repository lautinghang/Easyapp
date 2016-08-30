package com.androidapptutorial.andy.easyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidapptutorial.andy.easyapp.tools.DialogHelper;
import com.androidapptutorial.andy.easyapp.tools.LoadingView;
import com.androidapptutorial.andy.easyapp.volley.NetworkSetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        Response.ErrorListener {

    private Button clickBtn;
    private EditText txtCity;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickBtn = (Button) this.findViewById(R.id.button);
        clickBtn.setOnClickListener(this);

        txtCity = (EditText) this.findViewById(R.id.editText);

        txtResult = (TextView) this.findViewById(R.id.txtResult);
    }

    @Override
    public void onClick(View view) {

        if(view == clickBtn)
        {
            // Show alert message when button click
            Toast.makeText(this, "You want to search : "+txtCity.getText(), Toast.LENGTH_SHORT).show();
            // Send API - http://openweathermap.org/
            getWeatherData();
        }
    }

    public void getWeatherData() {
        // Test with hard code URL first
        // String url = "http://api.openweathermap.org/data/2.5/weather?q=London&APPID=053b881a29df9400f39c285f84b629f5";
        //
        String url = NetworkSetting.getWeatherByCityID(txtCity.getText().toString());

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,

            new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonObject) {
                    // Print Log of Response
                    Log.i("Volley",jsonObject.toString());
                    // Expect Response
                    // {"coord":{"lon":114.1,"lat":22.37},
                    // "weather":[{"id":801,"main":"Clouds","description":"few clouds","icon":"02d"}],"base":"stations","main":{"temp":302.35,"pressure":1007,"humidity":62,"temp_min":300.93,"temp_max":305.37},"visibility":10000,"wind":{"speed":4.6,"deg":20},"clouds":{"all":20},"dt":1472446814,"sys":{"type":1,"id":7904,"message":0.013,"country":"HK","sunrise":1472421931,"sunset":1472467382},"id":1818209,"name":"Tsuen Wan","cod":200}

                    // Show result on the page first
                    // txtResult.setText(jsonObject.toString());

                    try {

                        // IF Response Success
                        if(NetworkSetting.RESPONSE_SUCCESS == jsonObject.getInt("cod"))
                        {
                            //
                            int cityID = jsonObject.getInt("id");
                            String cityName = jsonObject.getString("name");
                            //
                            JSONObject coordinate = jsonObject.getJSONObject("coord");

                            Double lat = coordinate.getDouble("lat");
                            Double lon = coordinate.getDouble("lon");
                            // Get JSON Array [ ]
                            JSONArray weatherArray = jsonObject.getJSONArray("weather");
                            // Get First Weather
                            JSONObject weather = weatherArray.getJSONObject(0);
                            //
                            String weatherMain = weather.getString("main");
                            String weatherIcon = weather.getString("icon");
                            // Get JSON Object { }

                            // Get Data Type (String, Int, Double, Long, Boolean

                            txtResult.setText("Lat : "+lat.toString()
                                    + "/n Lng : "+lon.toString());


                            Intent Intent1 = new Intent();
                            Intent1.setClass(MainActivity.this, ResultActivity.class);

                            Bundle Bundle1 = new Bundle();
                            Bundle1.putInt("cityID", cityID);
                            Bundle1.putString("cityName", cityName);
                            Bundle1.putDouble("lat", lat);
                            Bundle1.putDouble("lon", lon);
                            Bundle1.putString("weatherMain", weatherMain);
                            Bundle1.putString("weatherIcon", weatherIcon);
                            Intent1.putExtras(Bundle1);

                            startActivity(Intent1);
                            overridePendingTransition(R.anim.in_from_right,
                                    R.anim.out_to_left);

                        }
                        else
                        {
                            LoadingView.hideLoading();
                            DialogHelper.showDialog(MainActivity.this, getString(R.string.network_error), getString(R.string.yes), false);
                        }

                    } catch (JSONException e) {
                        LoadingView.hideLoading();
                        DialogHelper.showDialog(MainActivity.this, getString(R.string.network_error), getString(R.string.yes), false);
                    }

                    LoadingView.hideLoading();

                }}, this);

        LoadingView.showLoading(this);

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkSetting.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsObjRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // TODO Auto-generated method stub
        LoadingView.hideLoading();
        Log.e("VolleyError",error.getLocalizedMessage()+":"+error.getMessage()+":");
        error.printStackTrace();
        DialogHelper.showDialog(this, getString(R.string.network_error), getString(R.string.yes), false);
        return;
    }
}
