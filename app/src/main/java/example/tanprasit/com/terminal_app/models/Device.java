package example.tanprasit.com.terminal_app.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by luketanprasit on 08/03/2016.
 */
public class Device {
    private int id;
    private String model;
    private String manufacturer;
    private String serialNumber;
    private String product;
    private String sdkVersion;
    private ArrayList<Notification> notificationsList;

    public Device(int id, String model, String manufacturer, String serialNumber, String product, String sdkVersion, ArrayList<Notification> notificationsList) {
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.serialNumber = serialNumber;
        this.product = product;
        this.sdkVersion = sdkVersion;
        this.notificationsList = notificationsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public ArrayList<Notification> getNotificationsList() {
        return notificationsList;
    }

    public void setNotificationsList(ArrayList<Notification> notificationsList) {
        this.notificationsList = notificationsList;
    }
}
