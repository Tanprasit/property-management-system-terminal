package example.tanprasit.com.terminal_app.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import example.tanprasit.com.terminal_app.Constants;
import example.tanprasit.com.terminal_app.R;
import example.tanprasit.com.terminal_app.models.Device;
import example.tanprasit.com.terminal_app.networks.URLBuilder;
import example.tanprasit.com.terminal_app.networks.Volley.RequestTask;
import example.tanprasit.com.terminal_app.services.GPSTracker;

public class MainActivity extends AppCompatActivity {

    private final Gson gson = new Gson();
    private final Context context = this;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLoadingAnimation(true);

        //Check if device info is exists in storage.
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.DEVICE_DETAILS, Context.MODE_PRIVATE);

        //Device object as a string.
        String deviceString = sharedPreferences.getString(Constants.DEVICE_OBJECT, null);

        // If not register the device to server
        if (null == deviceString) {
            registerDeviceDetails();
        } else {
            updateDeviceObject();
        }
    }

    private void setLoadingAnimation(boolean b) {
        this.spinner = (ProgressBar) findViewById(R.id.loadingBar);
        if (b) {
            this.spinner.setVisibility(View.VISIBLE);
        } else {
            this.spinner.setVisibility(View.GONE);
        }
    }

    private void updateDeviceObject() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.DEVICE_DETAILS, Context.MODE_PRIVATE);
        String deviceString = sharedPreferences.getString(Constants.DEVICE_OBJECT, null);

        String url = "";
        URLBuilder urlBuilder;

        if (null != deviceString) {
            Device device = gson.fromJson(deviceString, Device.class);
            urlBuilder = new URLBuilder();
            url = urlBuilder.getDeviceUrl(device.getId());
        } else {
            // Intent to web activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        new RequestTask(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.DEVICE_DETAILS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear();
                editor.putString(Constants.DEVICE_OBJECT, response);
                editor.apply();

                // Intent to web activity
                Intent intent = new Intent(context, WebActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Update device error", String.valueOf(error.networkResponse.statusCode));
                reloadActivity();
            }
        }, getApplicationContext()).sendGetRequest(url);
    }

    private void registerDeviceDetails() {
        URLBuilder urlBuilder = new URLBuilder();
        String url = urlBuilder.getDeviceRegisterUrl();

        new RequestTask(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.DEVICE_DETAILS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear();
                editor.putString(Constants.DEVICE_OBJECT, response);
                editor.apply();

                // Intent to web activity
                Intent intent = new Intent(context, WebActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Register error code", String.valueOf(error.networkResponse.statusCode));
                reloadActivity();
            }
        }, getApplicationContext()).sendPostRequest(url, generateDeviceParams());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void reloadActivity() {
        setLoadingAnimation(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private Map<String, String> generateDeviceParams() {
        Map<String, String> params = new HashMap<>();
        params.put("model", Build.MODEL);
        params.put("manufacturer", Build.MANUFACTURER);
        params.put("sdk_version", String.valueOf(Build.VERSION.SDK_INT));
        params.put("product", Build.PRODUCT);
        params.put("serial_number", Build.SERIAL);

        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
        final String latitude = String.valueOf(gpsTracker.getLatitude());
        final String longitude = String.valueOf(gpsTracker.getLongitude());

        if (!latitude.equals(String.valueOf(0d)) && !longitude.equals(String.valueOf(0d))) {
            params.put("latitude", latitude);
            params.put("longitude", longitude);
        }
        return params;
    }
}
