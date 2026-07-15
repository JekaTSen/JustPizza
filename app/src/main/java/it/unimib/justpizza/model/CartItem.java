package it.unimib.justpizza.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartItem {

    @PrimaryKey(autoGenerate = true)
    private int cartId;
    private String itemId;
    private String itemType; //pizza, drink or Side

    private String name;
    private double price;
    private int quantity;
    private double totalPrice;


    public CartItem(){}

    @Ignore
    public CartItem(MenuItem item, int quantity) {
        this.itemId = item.getId();
        this.itemType = item.getClass().getSimpleName();
        this.name = item.getName();
        this.price = item.getPrice();
        this.quantity = quantity;
        this.totalPrice = item.getPrice() * quantity;
    }

    //getters and setters
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

