package app.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String type;
    private String name;
    private Float carbs;
    private Float fats;
    private Float proteins;

    public Recipe(String type, String name) {
        this.type = type;
        this.name = name;
    }
    public void CalculateNutrients(){
        fats = 0.0f;
        carbs = 0.0f;
        proteins = 0.0f;
        for (Item item:
             items) {
            fats+=item.getFats();
            carbs+= item.getCarbs();
            proteins+= item.getProteins();
        }
    }
    private List<Item> items = new ArrayList<>();
    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Float getCarbs() {
        return carbs;
    }

    public Float getFats() {
        return fats;
    }

    public Float getProteins() {
        return proteins;
    }
}
