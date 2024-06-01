package entity;

import java.sql.Timestamp;

public class Application {
    private long id;
    private User user;
    private Item item;

    private String application_time;

    /**
     * whether returned or not
     */
    private boolean returned;
    private String return_time;
    private int quantity;
    private String manager;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getApplication_time() {
        return application_time;
    }

    public void setApplication_time(String application_time) {
        this.application_time = application_time;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}