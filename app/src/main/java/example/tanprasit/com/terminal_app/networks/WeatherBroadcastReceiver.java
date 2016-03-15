package example.tanprasit.com.terminal_app.networks;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import example.tanprasit.com.terminal_app.Constants;
import example.tanprasit.com.terminal_app.R;

/**
 * Created by luketanprasit on 14/03/2016.
 */
public class WeatherBroadcastReceiver extends BroadcastReceiver {
    private String weatherResult;
    private TextView weatherStatus;
    private TextView weather;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.weatherResult = intent.getStringExtra(Constants.EXTRA_WEATHER_KEY);

        final Activity parentActivity = (Activity) context;

        if (null != parentActivity) {
            parentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Fragment weatherFragment = parentActivity.getFragmentManager().findFragmentById(R.id.fragment_weather);

                    View weatherView = weatherFragment.getView();
                    weatherStatus = (TextView) (weatherView != null
                            ? weatherView.findViewById(R.id.fragment_weather_status)
                            : null);

                    if (null != weatherStatus) {
                        weatherStatus.setText(R.string.finished);
                    }

                    weather = (TextView) (weatherView != null
                            ? weatherView.findViewById(R.id.fragment_weather_view)
                            : null);

                    if (null != weather) {
                        weather.setText(weatherResult);
                    }
                }
            });
        }
    }
}
