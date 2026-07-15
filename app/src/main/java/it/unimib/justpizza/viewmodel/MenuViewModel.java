//package it.unimib.justpizza.viewmodel;
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//import java.util.List;
//
//import it.unimib.justpizza.model.Pizza;
//
//public class MenuViewModel extends AndroidViewModel {
//
//    private final PizzaViewModel pizzaViewModel;
//
//    public MenuViewModel(@NonNull Application application) {
//        super(application);
//        pizzaViewModel = new PizzaViewModel(application);
//    }
//
//    public LiveData<List<Pizza>> getAllPizzas() {
//        return pizzaViewModel.getAllPizzas();
//    }
//
//
//}