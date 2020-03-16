package com.example.irrigationmanegmentapp;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class WeatherAPIManager {

    private String url;
    private WeatherComponentsSetters weatherComponentsSetters;
    private Context mContext;
    private JsonObjectRequest jsonObjectRequest;

    public WeatherAPIManager(Context context, WeatherComponentsSetters _WeatherComponentsSetters, double lat, double lon) {
        mContext = context;
        this.weatherComponentsSetters = _WeatherComponentsSetters;
        url = "https://api.weatherbit.io/v2.0/current?&lat=" + lat + "&lon=" + lon + "&key=d2f8963526fb4e59bf78053c52a458c5";

        //Start an HTTP request to get the json
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, rListener, eListener);
        Volley.newRequestQueue(mContext).add(jsonObjectRequest);
    }

    /**
     * Get the data out of the response from the Weather API
     */
    Response.Listener<JSONObject> rListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray weatherParameters = response.getJSONArray("data");
                JSONObject data = weatherParameters.getJSONObject(0);
                JSONObject weather = data.getJSONObject("weather");

                int temperature = data.getInt("temp");
                int weatherCode = Integer.parseInt(weather.getString("code"));

                weatherComponentsSetters.tempSetter(temperature);
                weatherComponentsSetters.dayDescriptionSetter(weatherCode);
                weatherComponentsSetters.imageViewSetter(weatherCode);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    /**
     * Error
     */
    Response.ErrorListener eListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    };


}
