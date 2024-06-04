package entity;

import java.sql.Timestamp;

public class Application {
    private long aid;
    private User user;
    private Item item;

    private String application_time;

    /**
     * whether returned or not
     */
    private boolean returned;
    private String return_time;
    private int applied_quantity;
    private int status;  //0 表示 未审核，1表示审核通过，2表示未通过
    private User manager;
    public boolean isDelete;
    public Application(){
        user=new User();
        manager=new User();
        item=new Item();
        isDelete=false;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long id) {
        this.aid = id;
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

    public int getApplied_quantity() {
        return applied_quantity;
    }

    public void setApplied_quantity(int applied_quantity) {
        this.applied_quantity = applied_quantity;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}