package example.tanprasit.com.terminal_app.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import example.tanprasit.com.terminal_app.Constants;
import example.tanprasit.com.terminal_app.networks.URLBuilder;
import example.tanprasit.com.terminal_app.networks.VollyCallback;
import example.tanprasit.com.terminal_app.networks.VollySingleton;

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

        getWeatherData(url, new VollyCallback() {
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

    private void getWeatherData(String url, final VollyCallback vollyCallback) {
        //        Do work here
        RequestQueue requestQueue = VollySingleton.getInstance(getApplicationContext()).getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                vollyCallback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new RuntimeException(error.getMessage());
            }
        });

        requestQueue.add(stringRequest);
    }
}
