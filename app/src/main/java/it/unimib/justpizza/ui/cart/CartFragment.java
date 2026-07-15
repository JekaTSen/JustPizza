package it.unimib.justpizza.ui.cart;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import it.unimib.justpizza.R;
import it.unimib.justpizza.model.Order;
import it.unimib.justpizza.ui.cart.adapter.CartAdapter;
import it.unimib.justpizza.viewmodel.CartViewModel;
import it.unimib.justpizza.viewmodel.OrdersViewModel;

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView totalTextView;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_cart);
        totalTextView = view.findViewById(R.id.cart_total);

        Button btnClear = view.findViewById(R.id.btn_clear_cart);
        btnClear.setOnClickListener(v -> cartViewModel.clearCart());

        Button btnProceed = view.findViewById(R.id.btn_proceed);
        btnProceed.setOnClickListener(v -> {
            double total = cartViewModel.getTotalPrice().getValue() != null ?
                    cartViewModel.getTotalPrice().getValue() : 0.0;

            if (total > 0) {
                String summary = "Ordine di " + total + " €"; // puoi migliorare

                Order order = new Order("user1", total, summary); // userId temporaneo

                // Salva ordine
                OrdersViewModel orderViewModel = new ViewModelProvider(requireActivity()).get(OrdersViewModel.class);
                orderViewModel.insertOrder(order);

                // Svuota carrello
                cartViewModel.clearCart();

                Snackbar.make(requireView(), "Pagamento effettuato! Ordine salvato.", Snackbar.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Il carrello è vuoto", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        cartAdapter = new CartAdapter();
        cartAdapter.setOnCartItemClickListener(item -> {
            cartViewModel.removeFromCart(item);
        });

        recyclerView.setAdapter(cartAdapter);

        cartViewModel.getAllCartItems().observe(getViewLifecycleOwner(), items -> {
            cartAdapter.submitList(items);
        });

        cartViewModel.getTotalPrice().observe(getViewLifecycleOwner(), total -> {
            totalTextView.setText(String.format("Totale: € %.2f", total));
        });

        return view;
    }


}