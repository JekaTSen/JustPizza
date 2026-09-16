package it.unimib.justpizza.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.justpizza.database.AppDatabase;
import it.unimib.justpizza.database.OrderDAO;
import it.unimib.justpizza.datasource.OrderRemoteDataSource;
import it.unimib.justpizza.model.Order;

public class OrderRepository {
    private final OrderDAO orderDao;
    private final ExecutorService executorService;
    private final OrderRemoteDataSource remoteDataSource;

    public OrderRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.orderDao = db.orderDao();
        this.executorService = Executors.newSingleThreadExecutor();
        this.remoteDataSource = new OrderRemoteDataSource();
    }

    public LiveData<List<Order>> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public void insertOrder(Order order) {
        executorService.execute(() -> orderDao.insert(order));
        remoteDataSource.uploadOrder(order, null);
    }

    public void deleteAllOrders() {
        executorService.execute(orderDao::deleteAll);
    }

    public void refreshOrders(String userId) {
        remoteDataSource.fetchOrders(userId, new OrderRemoteDataSource.ListCallback() {
            @Override
            public void onSuccess(List<Order> orders) {
                if (orders == null) return;
                executorService.execute(() -> {
                    orderDao.deleteAll();
                    for (Order order : orders) {
                        orderDao.insert(order);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                // offline: restano gli ordini in Room
            }
        });
    }


}
