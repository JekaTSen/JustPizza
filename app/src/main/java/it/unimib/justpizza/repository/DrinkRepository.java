package it.unimib.justpizza.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.justpizza.database.AppDatabase;
import it.unimib.justpizza.database.DrinkDAO;
import it.unimib.justpizza.model.Drink;

public class DrinkRepository {

    private final DrinkDAO drinkDao;
    private final ExecutorService executorService;

    public DrinkRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.drinkDao = db.drinkDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Drink>> getAllDrinks() {
        return drinkDao.getAllDrinks();
    }

    public void insertAllDrinks(List<Drink> drinks) {
        executorService.execute(() -> drinkDao.insertAll(drinks));
    }

    public void deleteAllDrinks() {
        executorService.execute(drinkDao::deleteAll);
    }
}