package example.tanprasit.com.terminal_app.models.Forecast;

/**
 * Created by luketanprasit on 29/03/2016.
 */
public class WeatherInstance {

    private long time;
    private String summary;
    private String icon;
    private double precipProbability;
    private double temperature;
    private double apparentTemperature;
    private double humidity;
    private double windSpeed;
    private double temperatureMax;
    private double temperatureMin;

    public WeatherInstance(long time, String summary, String icon, double precipProbability,double temperature, double apparentTemperature, double humidity, double windSpeed, double temperatureMax, double temperatureMin) {
        this.time = time;
        this.summary = summary;
        this.icon = icon;
        this.precipProbability = precipProbability;
        this.temperature = temperature;
        this.apparentTemperature = apparentTemperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getPrecipProbability() {
        return precipProbability * 100;
    }

    public void setPrecipProbability(double precipProbability) {
        this.precipProbability = precipProbability;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public double getHumidity() {
        return humidity * 100;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }
}
