package it.unimib.justpizza.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import it.unimib.justpizza.model.CartItem;

@Dao
public interface CartDAO {

    @Query("SELECT * FROM cart_items")
    LiveData<List<CartItem>> getAllCartItems();

    @Insert
    void insert(CartItem cartItem);

    @Delete
    void delete(CartItem cartItem);

    @Query("DELETE FROM cart_items")
    void deleteAll();

}
