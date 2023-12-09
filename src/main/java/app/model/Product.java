package app.model;

public class Product {
    Integer id;
    String name;
    Float carbs;
    Float fats;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCarbs() {
        return carbs;
    }

    public void setCarbs(Float carbs) {
        this.carbs = carbs;
    }

    public Float getFats() {
        return fats;
    }

    public void setFats(Float fats) {
        this.fats = fats;
    }

    public Float getProteins() {
        return proteins;
    }

    public void setProteins(Float proteins) {
        this.proteins = proteins;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    Float proteins;
    Boolean active;
    public Product(){

    }

    public Product(Integer id, String name, Float carbs, Float fats, Float proteins, Boolean active) {
        this.id = id;
        this.name = name;
        this.carbs = carbs;
        this.fats = fats;
        this.proteins = proteins;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
