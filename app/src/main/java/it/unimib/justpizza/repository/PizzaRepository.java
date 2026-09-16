package it.unimib.justpizza.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.justpizza.database.AppDatabase;
import it.unimib.justpizza.database.DrinkDAO;
import it.unimib.justpizza.database.PizzaDAO;
import it.unimib.justpizza.database.SideDAO;
import it.unimib.justpizza.datasource.MenuRemoteDataSource;
import it.unimib.justpizza.model.Drink;
import it.unimib.justpizza.model.Pizza;
import it.unimib.justpizza.model.Side;

public class PizzaRepository {

    private final PizzaDAO pizzaDao;
    private final DrinkDAO drinkDao;
    private final SideDAO sideDao;
    private final ExecutorService executorService;
    private final MenuRemoteDataSource remoteDataSource;

    public PizzaRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.pizzaDao = db.pizzaDao();
        this.drinkDao = db.drinkDao();
        this.sideDao = db.sideDao();
        this.executorService = Executors.newSingleThreadExecutor();
        this.remoteDataSource = new MenuRemoteDataSource();
    }

    public LiveData<List<Pizza>> getAllPizzas() {
        return pizzaDao.getAllPizzas();
    }

    public LiveData<List<Drink>> getAllDrinks() {
        return drinkDao.getAllDrinks();
    }

    public LiveData<List<Side>> getAllSides() {
        return sideDao.getAllSides();
    }

    public void insertAllPizzas(List<Pizza> pizzas) {
        executorService.execute(() -> pizzaDao.insertAll(pizzas));
    }

    public void refreshMenu() {
        remoteDataSource.fetchMenu(new MenuRemoteDataSource.Callback() {
            @Override
            public void onSuccess(List<Pizza> pizzas, List<Drink> drinks, List<Side> sides) {
                executorService.execute(() -> {
                    if (pizzas != null) {
                        pizzaDao.deleteAll();
                        pizzaDao.insertAll(pizzas);
                    }
                    if (drinks != null) {
                        drinkDao.deleteAll();
                        drinkDao.insertAll(drinks);
                    }
                    if (sides != null) {
                        sideDao.deleteAll();
                        sideDao.insertAll(sides);
                    }
                });
            }

            @Override
            public void onError(Exception e) { }
        });
    }
}