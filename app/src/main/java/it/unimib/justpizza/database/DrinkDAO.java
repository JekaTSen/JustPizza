package it.unimib.justpizza.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import it.unimib.justpizza.model.Drink;

@Dao
public interface DrinkDAO {

    @Query("SELECT * FROM drinks")
    LiveData<List<Drink>> getAllDrinks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Drink> drinks);

    @Query("DELETE FROM drinks")
    void deleteAll();
}
