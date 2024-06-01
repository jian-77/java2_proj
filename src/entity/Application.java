package entity;

import java.sql.Timestamp;

public class Application {

    private long aid;
    public User user;
    public Item item;

    private String application_time;
    /**
     * whether returned or not
     */
    private boolean returned;
    private String return_time;
    private int quantity;
    private String manager;


    public void setAid(long aid) {
        this.aid = aid;
    }





    public void setReturned(boolean returned) {
        this.returned = returned;
    }



    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getAid() {
        return aid;
    }





    public boolean isReturned() {
        return returned;
    }


    public int getQuantity() {
        return quantity;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getApplication_time() {
        return application_time;
    }

    public void setApplication_time(String application_time) {
        this.application_time = application_time;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }
    public Application(){
        user=new User();
        item=new Item();
    }
}