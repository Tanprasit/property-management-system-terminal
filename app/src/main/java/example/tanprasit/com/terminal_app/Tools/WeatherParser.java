package example.tanprasit.com.terminal_app.Tools;

/**
 * Created by luketanprasit on 29/03/2016.
 */
public class WeatherParser {
    private String response;

    public WeatherParser(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
