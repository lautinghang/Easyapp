package com.androidapptutorial.andy.easyapp.volley;

public class NetworkSetting {

	public final static String Domain = "http://api.openweathermap.org/data/2.5/weather";
	public final static String API_KEY = "053b881a29df9400f39c285f84b629f5";
	
	public final static int MY_SOCKET_TIMEOUT_MS = 5000;

	public final static int RESPONSE_SUCCESS = 200;

	private static String getWeatherWithAPIKEY()
	{
		return Domain+"?APPID="+API_KEY;
	}

	public static String getWeatherByCityID(String cityID)
	{
		return getWeatherWithAPIKEY()+"&q="+cityID;
	}
}
