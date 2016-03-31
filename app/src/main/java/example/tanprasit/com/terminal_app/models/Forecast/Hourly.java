package example.tanprasit.com.terminal_app.models.Forecast;

import java.util.List;

/**
 * Created by luketanprasit on 29/03/2016.
 */
public class Hourly {
    private String summary;
    private String icon;
    private List<WeatherInstance> data;

    public Hourly(String summary, String icon, List<WeatherInstance> data) {
        this.summary = summary;
        this.icon = icon;
        this.data = data;
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

    public List<WeatherInstance> getData() {
        return data;
    }

    public void setData(List<WeatherInstance> data) {
        this.data = data;
    }
}
