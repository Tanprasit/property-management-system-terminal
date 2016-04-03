package example.tanprasit.com.terminal_app.networks;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import example.tanprasit.com.terminal_app.Constants;
import example.tanprasit.com.terminal_app.R;
import example.tanprasit.com.terminal_app.Tools.TimeHelper;
import example.tanprasit.com.terminal_app.models.Forecast.Forecast;
import example.tanprasit.com.terminal_app.models.Forecast.WeatherInstance;

/**
 * Created by luketanprasit on 14/03/2016.
 */
public class WeatherBroadcastReceiver extends BroadcastReceiver {

    private Context context;
    private String weatherIcon;
    // Add icon name here to change the text colour to black instead of black.
    private List<String> iconAltColourScheme = new ArrayList<>(Arrays.asList("snow", "fog"));

    public WeatherBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String weatherResult = intent.getStringExtra(Constants.EXTRA_WEATHER_KEY);

        final Forecast forecast = new Gson().fromJson(weatherResult, Forecast.class);
        final Activity parentActivity = (Activity) context;

        this.weatherIcon = forecast.getCurrently().getIcon();

        final List<WeatherInstance> days = forecast.getDaily().getData();

        if (null != parentActivity) {
            parentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Fragment weatherFragment = parentActivity.getFragmentManager().findFragmentById(R.id.fragment_weather);

                    // Grab all the views that we need inject with weather information.
                    View weatherView = weatherFragment.getView();

                    // Set background image to reflect the current weather.
                    setWeatherBackgroundImage(weatherView, forecast);

                    setUpCurrentWeather(weatherView, forecast);
                    TimeHelper timeHelper = new TimeHelper();
                    setUpHeaders(weatherView);

                    // Grab weather instance for everyday within the week.
                    final WeatherInstance firstDayInstance = days.get(0);
                    final WeatherInstance secondDayInstance = days.get(1);
                    final WeatherInstance thirdDayInstance = days.get(2);
                    final WeatherInstance fourthDayInstance = days.get(3);
                    final WeatherInstance fifthDayInstance = days.get(4);
                    final WeatherInstance sixthDayInstance = days.get(5);
                    final WeatherInstance seventhDayInstance = days.get(6);

                    setupFirstDayForecast(weatherView, firstDayInstance, timeHelper);
                    setupSecondDayForecast(weatherView, secondDayInstance, timeHelper);
                    setupThirdDayForecast(weatherView, thirdDayInstance, timeHelper);
                    setupFourthDayForecast(weatherView, fourthDayInstance, timeHelper);
                    setupFifthDayForecast(weatherView, fifthDayInstance, timeHelper);
                    setupSixthDayForecast(weatherView, sixthDayInstance, timeHelper);
                    setupSeventhDayForecast(weatherView, seventhDayInstance, timeHelper);
                }
            });
        }
    }

    private void setUpHeaders(View weatherView) {

        if (this.iconAltColourScheme.contains(this.weatherIcon)) {
            TextView maxView = (TextView) (weatherView != null
                    ? weatherView.findViewById(R.id.fragment_temp_max)
                    : null);

            TextView minView = (TextView) (weatherView != null
                    ? weatherView.findViewById(R.id.fragment_temp_min)
                    : null);

            if (maxView != null) {
                maxView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }

            if (minView != null) {
                minView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

    }

    private void setupSeventhDayForecast(View weatherView, WeatherInstance weatherInstance, TimeHelper timeHelper) {
        // Grab views for day 7.
        TextView dayView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_7_day)
                : null);

        TextView maxTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_7_temp_high)
                : null);

        TextView minTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_7_temp_min)
                : null);

        // Inject data to day 7.
        timeHelper.setCurrentTimeFromLong(weatherInstance.getTime());

        String dayOfWeek = timeHelper.getDateTime().dayOfWeek().getAsText();
        if (dayView != null) {
            dayView.setText(dayOfWeek);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                dayView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (maxTempView != null) {
            String maxTemp = String.valueOf((int) weatherInstance.getTemperatureMax()) + (char) 0x00B0;;
            maxTempView.setText(maxTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                maxTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (minTempView != null) {
            String minTemp = String.valueOf((int) weatherInstance.getTemperatureMin()) + (char) 0x00B0;
            minTempView.setText(minTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                minTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }
    }

    private void setupSixthDayForecast(View weatherView, WeatherInstance weatherInstance, TimeHelper timeHelper) {
        // Grab views for day 6.
        TextView dayView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_6_day)
                : null);

        TextView maxTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_6_temp_high)
                : null);

        TextView minTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_6_temp_min)
                : null);

        // Inject data to day 6. Must convert time to milliseconds.
        timeHelper.setCurrentTimeFromLong(weatherInstance.getTime() * 1000);

        String dayOfWeek = timeHelper.getDateTime().dayOfWeek().getAsText();
        if (dayView != null) {
            dayView.setText(dayOfWeek);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                dayView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (maxTempView != null) {
            String maxTemp = String.valueOf((int) weatherInstance.getTemperatureMax()) + (char) 0x00B0;;
            maxTempView.setText(maxTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                maxTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (minTempView != null) {
            String minTemp = String.valueOf((int) weatherInstance.getTemperatureMin()) + (char) 0x00B0;
            minTempView.setText(minTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                minTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            };
        }
    }

    private void setupFifthDayForecast(View weatherView, WeatherInstance weatherInstance, TimeHelper timeHelper) {
        // Grab views for day 5.
        TextView dayView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_5_day)
                : null);

        TextView maxTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_5_temp_high)
                : null);

        TextView minTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_5_temp_min)
                : null);

        // Inject data to day 5. Must convert time to milliseconds.
        timeHelper.setCurrentTimeFromLong(weatherInstance.getTime() * 1000);

        String dayOfWeek = timeHelper.getDateTime().dayOfWeek().getAsText();
        if (dayView != null) {
            dayView.setText(dayOfWeek);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                dayView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (maxTempView != null) {
            String maxTemp = String.valueOf((int) weatherInstance.getTemperatureMax()) + (char) 0x00B0;;
            maxTempView.setText(maxTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                maxTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (minTempView != null) {
            String minTemp = String.valueOf((int) weatherInstance.getTemperatureMin()) + (char) 0x00B0;
            minTempView.setText(minTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                minTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }
    }

    private void setupFourthDayForecast(View weatherView, WeatherInstance weatherInstance, TimeHelper timeHelper) {
        // Grab views for day 4.
        TextView dayView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_4_day)
                : null);

        TextView maxTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_4_temp_high)
                : null);

        TextView minTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_4_temp_min)
                : null);

        // Inject data to day 4. Must convert time to milliseconds.
        timeHelper.setCurrentTimeFromLong(weatherInstance.getTime() * 1000);

        String dayOfWeek = timeHelper.getDateTime().dayOfWeek().getAsText();
        if (dayView != null) {
            dayView.setText(dayOfWeek);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                dayView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (maxTempView != null) {
            String maxTemp = String.valueOf((int) weatherInstance.getTemperatureMax()) + (char) 0x00B0;;
            maxTempView.setText(maxTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                maxTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (minTempView != null) {
            String minTemp = String.valueOf((int) weatherInstance.getTemperatureMin()) + (char) 0x00B0;
            minTempView.setText(minTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                minTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }
    }

    private void setupThirdDayForecast(View weatherView, WeatherInstance weatherInstance, TimeHelper timeHelper) {
        // Grab views for day 3.
        TextView dayView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_3_day)
                : null);

        TextView maxTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_3_temp_high)
                : null);

        TextView minTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_3_temp_min)
                : null);

        // Inject data to day 3. Must convert time to milliseconds.
        timeHelper.setCurrentTimeFromLong(weatherInstance.getTime() * 1000);

        String dayOfWeek = timeHelper.getDateTime().dayOfWeek().getAsText();
        if (dayView != null) {
            dayView.setText(dayOfWeek);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                dayView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (maxTempView != null) {
            String maxTemp = String.valueOf((int) weatherInstance.getTemperatureMax()) + (char) 0x00B0;;
            maxTempView.setText(maxTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                maxTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (minTempView != null) {
            String minTemp = String.valueOf((int) weatherInstance.getTemperatureMin()) + (char) 0x00B0;
            minTempView.setText(minTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                minTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }
    }

    private void setupSecondDayForecast(View weatherView, WeatherInstance weatherInstance, TimeHelper timeHelper) {
        // Grab views for day 2.
        TextView dayView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_2_day)
                : null);

        TextView maxTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_2_temp_high)
                : null);

        TextView minTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_2_temp_min)
                : null);

        // Inject data to day 2. Must convert time to milliseconds.
        timeHelper.setCurrentTimeFromLong(weatherInstance.getTime() * 1000);

        String dayOfWeek = timeHelper.getDateTime().dayOfWeek().getAsText();
        if (dayView != null) {
            dayView.setText(dayOfWeek);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                dayView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (maxTempView != null) {
            String maxTemp = String.valueOf((int) weatherInstance.getTemperatureMax()) + (char) 0x00B0;;
            maxTempView.setText(maxTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                maxTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (minTempView != null) {
            String minTemp = String.valueOf((int) weatherInstance.getTemperatureMin()) + (char) 0x00B0;
            minTempView.setText(minTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                minTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }

        }
    }

    private void setupFirstDayForecast(View weatherView, WeatherInstance weatherInstance, TimeHelper timeHelper) {
        // Grab views for day 1.
        TextView dayView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_1_day)
                : null);

        TextView maxTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_1_temp_high)
                : null);

        TextView minTempView = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_day_1_temp_min)
                : null);

        // Inject data to day 1. Must convert time to milliseconds.
        timeHelper.setCurrentTimeFromLong(weatherInstance.getTime() * 1000);

        String dayOfWeek = timeHelper.getDateTime().dayOfWeek().getAsText();
        if (dayView != null) {
            dayView.setText(dayOfWeek);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                dayView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (maxTempView != null) {
            String maxTemp = String.valueOf((int) weatherInstance.getTemperatureMax()) + (char) 0x00B0;;
            maxTempView.setText(maxTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                maxTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (minTempView != null) {
            String minTemp = String.valueOf((int) weatherInstance.getTemperatureMin()) + (char) 0x00B0;
            minTempView.setText(minTemp);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                minTempView.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }

        }
    }

    private void setUpCurrentWeather(View weatherView, Forecast forecast) {
        // Grab all views from fragment.
        TextView weatherStatus = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_weather_status)
                : null);

        TextView weatherTemp = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_weather_temperature)
                : null);

        TextView weatherPrecip = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_weather_percip)
                : null);

        TextView weatherHumidity = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_weather_humidity)
                : null);

        TextView weatherWind = (TextView) (weatherView != null
                ? weatherView.findViewById(R.id.fragment_weather_wind)
                : null);

        // Display current weather summary.
        if (null != weatherStatus) {
            weatherStatus.setText(forecast.getCurrently().getSummary());
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                weatherStatus.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (null != weatherTemp) {
            String tempText = String.valueOf((int) forecast.getCurrently().getTemperature()) + (char) 0x00B0;
            weatherTemp.setText(tempText);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                weatherTemp.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (null != weatherPrecip) {
            String precipText = "Precipitation: " + String.valueOf((int) forecast.getCurrently().getPrecipProbability()) + "%";
            weatherPrecip.setText(precipText);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                weatherPrecip.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (null != weatherHumidity) {
            String humidityText = "Humidity: " + String.valueOf((int) forecast.getCurrently().getHumidity()) + "%";
            weatherHumidity.setText(humidityText);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                weatherHumidity.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }

        if (null != weatherWind) {
            String windText = "Wind: " + String.valueOf(forecast.getCurrently().getWindSpeed()) + " mph";
            weatherWind.setText(windText);
            if (this.iconAltColourScheme.contains(this.weatherIcon)) {
                weatherWind.setTextColor(this.context.getResources().getColor(R.color.altTextColor));
            }
        }
    }

    private void setWeatherBackgroundImage(View weatherView, Forecast forecast) {
        if (weatherView != null) {

            ImageView imageView = (ImageView) weatherView.findViewById(R.id.fragment_weather_bg);

            switch (this.weatherIcon){
                case "clear_day":
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.clear_day, null));
                    break;
                case "clear_night":
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.clear_night, null));
                    break;
                case "snow":
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.snow, null));
                    break;
                case "sleet":
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.sleet, null));
                    break;
                case "rain":
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.rain, null));
                    break;
                case "wind":
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.wind, null));
                    break;
                case "fog":
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.fog, null));
                    break;
                case "cloudy":
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.cloudy, null));
                    break;
                case "partly_cloudy_day":
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.partly_cloudy_day, null));
                    break;
                case "partly_cloudy_night":
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.partly_cloudy_night, null));
                    break;
                default:
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.clear_day, null));
                    break;
            }
        }

    }
}