package example.tanprasit.com.terminal_app.models;

/**
 * Created by luketanprasit on 08/03/2016.
 */
public class Notification {
    private int id;
    private String title;
    private String notes;
    private String data;

    public Notification(int id, String title, String notes, String data) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
