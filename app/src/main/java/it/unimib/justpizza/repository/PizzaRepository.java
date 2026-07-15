package it.unimib.justpizza.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.justpizza.database.AppDatabase;
import it.unimib.justpizza.database.PizzaDAO;
import it.unimib.justpizza.model.Pizza;

public class PizzaRepository {

    private final PizzaDAO pizzaDao;
    private final ExecutorService executorService;

    public PizzaRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.pizzaDao = db.pizzaDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Pizza>> getAllPizzas() {
        return pizzaDao.getAllPizzas();
    }

    public void insertAllPizzas(List<Pizza> pizzas){
        executorService.execute(() -> pizzaDao.insertAll(pizzas));
    }

    public void deleteAllPizzas() {
        executorService.execute(pizzaDao::deleteAll);
    }

}
