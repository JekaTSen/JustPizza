package it.unimib.justpizza.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.justpizza.model.Pizza;
import it.unimib.justpizza.repository.PizzaRepository;

public class PizzaViewModel extends AndroidViewModel {
    private final PizzaRepository repository;

    public PizzaViewModel(@NonNull Application application) {
        super(application);
        repository = new PizzaRepository(application);
    }

    public LiveData<List<Pizza>> getAllPizzas() {
        return repository.getAllPizzas();
    }

    public void insertAllPizzas(List<Pizza> pizzas) {
        repository.insertAllPizzas(pizzas);
    }


}
