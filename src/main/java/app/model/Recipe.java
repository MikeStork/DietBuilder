package app.model;

import java.util.ArrayList;
import java.util.HashMap;
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

    public Recipe(String type, String name) {
        this.type = type;
        this.name = name;
    }

    private List<Item> items = new ArrayList<>();
    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
