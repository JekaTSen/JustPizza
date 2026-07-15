package it.unimib.justpizza.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "orders")
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String userId;
    private double totalPrice;
    private String itemsSummary;   // "Margherita x1, Diavola x2"
    private Date orderDate;

    public Order() {}

    @Ignore
    public Order(String userId, double totalPrice, String itemsSummary) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.itemsSummary = itemsSummary;
        this.orderDate = new Date();
    }

    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getItemsSummary() { return itemsSummary; }
    public void setItemsSummary(String itemsSummary) { this.itemsSummary = itemsSummary; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
}