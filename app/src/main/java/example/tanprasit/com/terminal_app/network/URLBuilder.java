package example.tanprasit.com.terminal_app.network;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by luketanprasit on 07/03/2016.
 */
public class URLBuilder {

    private String baseURL = "192.168.0.7:8000";
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

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }
}
