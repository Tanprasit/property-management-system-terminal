package example.tanprasit.com.terminal_app.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import example.tanprasit.com.terminal_app.Constants;
import example.tanprasit.com.terminal_app.networks.Volley.VolleyCallback;
import example.tanprasit.com.terminal_app.networks.Volley.VolleySingleton;

/**
 * Created by luketanprasit on 11/03/2016.
 */
public class WeatherService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WeatherService(String name) {
        super(name);
    }

    public WeatherService() {
        super("WeatherService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();

        String url = bundle.getString(Constants.URL);

        if (null == url)
            throw new RuntimeException("Missing url from weather fragment");

        getWeatherData(url, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Intent intentResponse = new Intent();
                intentResponse.setAction(Constants.WEATHER_RESPONSE);
                intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
                intentResponse.putExtra(Constants.EXTRA_WEATHER_KEY, response);
                sendBroadcast(intentResponse);
            }
        });
    }

    private void getWeatherData(String url, final VolleyCallback volleyCallback) {
        //        Do work here
        RequestQueue requestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                volleyCallback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("WeatherService code", String.valueOf(error.networkResponse.statusCode));
            }
        });

        requestQueue.add(stringRequest);
    }
}
