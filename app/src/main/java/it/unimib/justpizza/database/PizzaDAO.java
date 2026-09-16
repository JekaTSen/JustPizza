package it.unimib.justpizza.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import it.unimib.justpizza.model.Pizza;

@Dao
public interface PizzaDAO {
    @Query("SELECT * FROM pizzas ORDER BY name ASC")
    LiveData<List<Pizza>> getAllPizzas();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Pizza> pizzas);

    @Query("DELETE FROM pizzas")
    void deleteAll();

}
