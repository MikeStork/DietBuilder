package app.model;

public class Meal {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    Integer id;
    String name;
    String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    Integer amount;
    Integer product_id;

    public Meal(Integer id, String name, String type, Integer amount, Integer product_id) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.product_id = product_id;
    }


}
