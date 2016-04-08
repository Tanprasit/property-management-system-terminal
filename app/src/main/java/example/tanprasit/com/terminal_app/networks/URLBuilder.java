package example.tanprasit.com.terminal_app.networks;

import example.tanprasit.com.terminal_app.Constants;

/**
 * Created by luketanprasit on 07/03/2016.
 */
public class URLBuilder {

//    private String baseURL= "ec2-54-229-185-207.eu-west-1.compute.amazonaws.com";
    public static String baseURL = "192.168.0.7:8000";
    private String requestURL;

    public URLBuilder() {
    }

    private void addRelativePath(int apiVersion, String apiName) {
        this.requestURL = "http://" + baseURL + "/api/v" + apiVersion + "/" + apiName;
    }

    public String getNotificationUrl(int notificationId) {
        this.addRelativePath(1, "notifications" + "/" + notificationId);
        return this.requestURL;
    }

    public String getDeviceRegisterUrl() {
        this.addRelativePath(1, "register/device");
        return this.requestURL;
    }

    public String getDeviceUrl(int id) {
        this.addRelativePath(1, "devices" + "/" + id);
        return  this.requestURL;
    }

    public String getWeatherUrl(double latitude, double longitude) {
        return  "https://api.forecast.io/forecast/"
                + Constants.WEATHER_API_KEY + "/"
                + latitude + "," + longitude;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }
}
