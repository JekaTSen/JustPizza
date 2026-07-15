package it.unimib.justpizza.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import it.unimib.justpizza.model.Side;

@Dao
public interface SideDAO {
    @Query("SELECT * FROM sides ORDER BY name ASC")
    LiveData<List<Side>> getAllSides();

    @Insert
    void insertALL(List<Side> sides);

    @Query("DELETE FROM sides")
    void deleteAll();
}
