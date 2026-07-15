package it.unimib.justpizza.model;

import androidx.annotation.NonNull;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//classe genitore astratta che rappresenta un prodotto che è presente nel menu.
// Può essere pizza, drink or side (patatine fritte, etc).
public abstract class MenuItem {
    @PrimaryKey
    @NonNull
    private String id;
    protected String name;
    protected double price;
    protected String imageUrl;
    protected String description;

    public MenuItem(){}

    @Ignore //In questo modo Room ignora questo costruttore.
    public MenuItem(String id, String name, double price, String imageURL, String description){
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageURL;
        this.description = description;
    }


    //Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
