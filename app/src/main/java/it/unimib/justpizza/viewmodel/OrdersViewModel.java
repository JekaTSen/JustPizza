package it.unimib.justpizza.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import it.unimib.justpizza.model.Order;
import it.unimib.justpizza.repository.OrderRepository;

public class OrdersViewModel extends AndroidViewModel {
    private final OrderRepository repository;

    public OrdersViewModel(@NonNull Application application) {
        super(application);
        repository = new OrderRepository(application);
    }

    public LiveData<List<Order>> getAllOrders() {
        return repository.getAllOrders();
    }

    public void insertOrder(Order order) {
        repository.insertOrder(order);
    }

    public void refreshOrders(String userId) {
        repository.refreshOrders(userId);
    }
}