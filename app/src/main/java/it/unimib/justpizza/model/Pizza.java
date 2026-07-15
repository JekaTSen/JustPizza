package it.unimib.justpizza.model;


import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "pizzas")
public class Pizza extends MenuItem {
    private String type; // normale, calzone, teglia_famiglia
    private String ingredients;

    public Pizza() {}

    @Ignore
    public Pizza(String id, String name, double price, String imageURL, String type,
                 String ingredients, String description) {
        super(id, name, price, imageURL, description);
        this.type = type;
        this.ingredients = ingredients;
    }

    //getters & setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

}
