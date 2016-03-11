package example.tanprasit.com.terminal_app.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import example.tanprasit.com.terminal_app.Constants;
import example.tanprasit.com.terminal_app.R;
import example.tanprasit.com.terminal_app.models.Device;
import example.tanprasit.com.terminal_app.network.URLBuilder;
import example.tanprasit.com.terminal_app.network.VollySingleton;

public class MainActivity extends AppCompatActivity {

    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if device info is exists in storage.
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.DEVICE_DETAILS, Context.MODE_PRIVATE);

        //Device object as a string.
        String deviceString = sharedPreferences.getString(Constants.DEVICE_OBJECT, null);

        // If not register the device to server
        if (null == deviceString) {
            registerDeviceDetails();
            reloadActivity();
        } else {
            updateDeviceObject();
            // Intent to web activity
            Toast.makeText(this, "Loading notifications...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, WebActivity.class);
            startActivity(intent);
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


        // Instantiate the RequestQueue.
        RequestQueue queue = VollySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.DEVICE_DETAILS, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.clear();
                        editor.putString(Constants.DEVICE_OBJECT, response);
                        editor.apply();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error status code ", error.getMessage());
                Toast.makeText(getApplicationContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("model", Build.MODEL);
                params.put("manufacturer", Build.MANUFACTURER);
                params.put("sdk_version", String.valueOf(Build.VERSION.SDK_INT));
                params.put("product", Build.PRODUCT);
                params.put("serial_number", Build.SERIAL);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void reloadActivity() {
        Toast.makeText(getApplicationContext(), "Finished registering current device, reloading...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void registerDeviceDetails() {
        URLBuilder urlBuilder = new URLBuilder();
        String url = urlBuilder.getDeviceRegisterUrl();

        // Instantiate the RequestQueue.
        RequestQueue queue = VollySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.DEVICE_DETAILS, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.clear();
                        editor.putString(Constants.DEVICE_OBJECT, response);
                        editor.apply();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error status code ", error.getMessage());
                Toast.makeText(getApplicationContext(), "Something is wrong", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("model", Build.MODEL);
                params.put("manufacturer", Build.MANUFACTURER);
                params.put("sdk_version", String.valueOf(Build.VERSION.SDK_INT));
                params.put("product", Build.PRODUCT);
                params.put("serial_number", Build.SERIAL);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
