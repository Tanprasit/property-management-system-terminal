package example.tanprasit.com.terminal_app.models.Forecast;

/**
 * Created by luketanprasit on 29/03/2016.
 */
public class Forecast {

    private double latitude;
    private double longitude;
    private String timezone;
    private double offset;
    private WeatherInstance currently;
    private Minutely minutely;
    private Hourly hourly;
    private Daily daily;

    public Forecast(double latitude, double longitude, String timezone, double offset, WeatherInstance weatherInstance, Minutely minutely, Hourly hourly, Daily daily) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
        this.offset = offset;
        this.currently = weatherInstance;
        this.minutely = minutely;
        this.hourly = hourly;
        this.daily = daily;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public Minutely getMinutely() {
        return minutely;
    }

    public void setMinutely(Minutely minutely) {
        this.minutely = minutely;
    }

    public Hourly getHourly() {
        return hourly;
    }

    public void setHourly(Hourly hourly) {
        this.hourly = hourly;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public WeatherInstance getCurrently() {
        return currently;
    }

    public void setCurrently(WeatherInstance currently) {
        this.currently = currently;
    }
}
