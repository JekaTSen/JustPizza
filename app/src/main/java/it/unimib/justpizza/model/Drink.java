package it.unimib.justpizza.model;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "drinks")
public class Drink extends MenuItem {
//    private String size;

    public Drink() {}

    @Ignore
    public Drink(String id, String name, double price, String imageURL, String description) {
        super(id, name, price, imageURL, description);
    }

}
