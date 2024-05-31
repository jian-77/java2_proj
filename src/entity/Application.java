package entity;

import java.sql.Timestamp;

public class Application {
    private long aid;
    private long account;
    private long id;
    /**
     * The applicant's name
     */
    private String a_name;
    /**
     * The material's name
     */
    private String m_name;
    private boolean returnable;
    /**
     * The material's type
     */
    private String m_type;
    private Timestamp application_time;
    /**
     * whether returned or not
     */
    private boolean returned;
    private Timestamp return_time;
    private int quantity;


    public void setAid(long aid) {
        this.aid = aid;
    }

    public void setAccount(long account) {
        this.account = account;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public void setReturnable(boolean returnable) {
        this.returnable = returnable;
    }

    public void setM_type(String m_type) {
        this.m_type = m_type;
    }

    public void setApplication_time(Timestamp application_time) {
        this.application_time = application_time;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public void setReturn_time(Timestamp return_time) {
        this.return_time = return_time;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getAid() {
        return aid;
    }

    public long getAccount() {
        return account;
    }

    public long getId() {
        return id;
    }

    public String getA_name() {
        return a_name;
    }

    public String getM_name() {
        return m_name;
    }

    public boolean isReturnable() {
        return returnable;
    }

    public String getM_type() {
        return m_type;
    }

    public Timestamp getApplication_time() {
        return application_time;
    }

    public boolean isReturned() {
        return returned;
    }

    public Timestamp getReturn_time() {
        return return_time;
    }

    public int getQuantity() {
        return quantity;
    }

}