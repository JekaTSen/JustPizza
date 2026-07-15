package it.unimib.justpizza.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.justpizza.database.AppDatabase;
import it.unimib.justpizza.database.CartDAO;
import it.unimib.justpizza.model.CartItem;

public class CartRepository {

    private final CartDAO cartDao;
    private final ExecutorService executorService;

    public CartRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.cartDao = db.cartDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<CartItem>> getAllCartItems() {
        return cartDao.getAllCartItems();
    }

    public void insertCartItem(CartItem cartItem) {
        executorService.execute(() -> cartDao.insert(cartItem));
    }

    public void deleteCartItem(CartItem cartItem) {
        executorService.execute(() -> cartDao.delete(cartItem));
    }

    public void deleteAllCartItems() {
        executorService.execute(cartDao::deleteAll);
    }


}
