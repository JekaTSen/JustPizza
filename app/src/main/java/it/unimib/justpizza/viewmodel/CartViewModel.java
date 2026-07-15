package it.unimib.justpizza.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import it.unimib.justpizza.model.CartItem;
import it.unimib.justpizza.repository.CartRepository;

public class CartViewModel extends AndroidViewModel {
    private final CartRepository repository;
    private final LiveData<List<CartItem>> allCartItems;


    public CartViewModel(@NonNull Application application) {
        super(application);
        repository = new CartRepository(application);
        allCartItems = repository.getAllCartItems();
    }

    public LiveData<List<CartItem>> getAllCartItems() {
        return allCartItems;
    }

    public void addToCart(CartItem cartItem) {
        repository.insertCartItem(cartItem);
    }

    public void removeFromCart(CartItem cartItem){
        repository.deleteCartItem(cartItem);
    }

    public void clearCart() {
        repository.deleteAllCartItems();
    }

    public LiveData<Double> getTotalPrice() {
        return Transformations.map(getAllCartItems(), items -> {
            double total = 0;
            for (CartItem item : items) {
                total += item.getTotalPrice();
            }
            return total;
        });
    }



}
