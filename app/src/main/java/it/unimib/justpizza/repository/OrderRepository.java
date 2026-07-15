package it.unimib.justpizza.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.justpizza.database.AppDatabase;
import it.unimib.justpizza.database.OrderDAO;
import it.unimib.justpizza.model.Order;

public class OrderRepository {
    private final OrderDAO orderDao;
    private final ExecutorService executorService;

    public OrderRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.orderDao = db.orderDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Order>> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public void insertOrder(Order order) {
        executorService.execute(() -> orderDao.insert(order));
    }

    public void deleteAllOrders() {
        executorService.execute(orderDao::deleteAll);
    }
}
