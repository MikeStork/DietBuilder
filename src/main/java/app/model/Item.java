package app.model;

public class Item extends Product{

    public Item(String name, Integer weight, Integer amount, Float carbs, Float fats, Float proteins) {
        this.name = name;
        this.weight = weight;
        this.amount = amount;
        this.carbs = carbs;
        this.fats = fats;
        this.proteins = proteins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    private String name;
    private Integer weight;
    private Integer amount;
    private Float carbs;
    private Float fats;
    private Float proteins;
}
