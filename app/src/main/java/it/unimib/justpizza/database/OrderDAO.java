package it.unimib.justpizza.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import it.unimib.justpizza.model.Order;

@Dao
public interface OrderDAO {

    @Query("SELECT * FROM orders ORDER BY orderDate DESC")
    LiveData<List<Order>> getAllOrders();

    @Insert
    void insert(Order order);

    @Query("DELETE FROM orders")
    void deleteAll();
}