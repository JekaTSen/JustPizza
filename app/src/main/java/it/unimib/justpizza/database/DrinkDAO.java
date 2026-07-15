package it.unimib.justpizza.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import it.unimib.justpizza.model.Drink;

@Dao
public interface DrinkDAO {
    @Query("SELECT * FROM drinks ORDER BY name ASC")
    LiveData<List<Drink>> getAllDrinks();

    @Insert
    void insertALL(List<Drink> drinks);

    @Query("DELETE FROM drinks")
    void deleteAll();
}
