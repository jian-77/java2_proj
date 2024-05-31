package entity;

public class Item {

    private long id;

    private String name;

    private int quantity;

    private String type;

    private boolean returnable;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public boolean isReturnable() {
        return returnable;
    }
}
