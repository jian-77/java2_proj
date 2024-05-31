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
}