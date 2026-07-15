package it.unimib.justpizza.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import it.unimib.justpizza.model.Pizza;
import it.unimib.justpizza.repository.PizzaRepository;

public class DataSetDB {

    public static void seedData(Context context) {
        PizzaRepository repository = new PizzaRepository(context);

/*        List<Pizza> pizze = new ArrayList<>();

        pizze.add(new Pizza("p001", "Margherita", 6.50, "https://picsum.photos/id/237/300/200", "normale", "Pomodoro, Mozzarella, Basilico", "La classica"));
        pizze.add(new Pizza("p002", "Diavola", 8.00, "https://picsum.photos/id/201/300/200", "normale", "Pomodoro, Mozzarella, Salame Piccante", "Piccante"));
        pizze.add(new Pizza("p003", "Calzone", 9.00, "https://picsum.photos/id/133/300/200", "calzone", "Pomodoro, Mozzarella, Prosciutto", "Ripieno"));
        pizze.add(new Pizza("p004", "Teglia Famiglia", 18.00, "https://picsum.photos/id/180/300/200", "teglia_famiglia", "Pomodoro, Mozzarella, Verdure", "Per 4 persone"));

        repository.insertAllPizzas(pizze);
    }*/


        // Controlla se ci sono già dati
        repository.getAllPizzas().observeForever(pizzas -> {
            if (pizzas == null || pizzas.isEmpty()) {
                List<Pizza> pizze = new ArrayList<>();

                pizze.add(new Pizza("p001", "Margherita", 6.50, "https://picsum.photos/id/237/300/200", "normale", "Pomodoro, Mozzarella, Basilico", "La classica"));
                pizze.add(new Pizza("p002", "Diavola", 8.00, "https://picsum.photos/id/201/300/200", "normale", "Pomodoro, Mozzarella, Salame Piccante", "Piccante"));
                pizze.add(new Pizza("p003", "Calzone", 9.00, "https://picsum.photos/id/133/300/200", "calzone", "Pomodoro, Mozzarella, Prosciutto", "Ripieno"));
                pizze.add(new Pizza("p004", "Teglia Famiglia", 18.00, "https://picsum.photos/id/180/300/200", "teglia_famiglia", "Pomodoro, Mozzarella, Verdure", "Per 4 persone"));

                repository.insertAllPizzas(pizze);
            }
        });
}
    }
