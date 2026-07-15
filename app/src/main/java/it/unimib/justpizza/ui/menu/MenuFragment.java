package it.unimib.justpizza.ui.menu;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import it.unimib.justpizza.R;
import it.unimib.justpizza.model.CartItem;
import it.unimib.justpizza.ui.menu.adapter.PizzaAdapter;
import it.unimib.justpizza.viewmodel.CartViewModel;
import it.unimib.justpizza.viewmodel.PizzaViewModel;

public class MenuFragment extends Fragment {

//    private MenuViewModel mViewModel;
    private PizzaViewModel pizzaViewModel;
    private RecyclerView recyclerView;
    private PizzaAdapter pizzaAdapter;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_pizzas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        pizzaViewModel = new ViewModelProvider(this).get(PizzaViewModel.class);

        pizzaAdapter = new PizzaAdapter();
        recyclerView.setAdapter(pizzaAdapter);

        pizzaAdapter.setOnPizzaClickListener(pizza -> {
            CartItem cartItem = new CartItem(pizza, 1);

            CartViewModel cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
            cartViewModel.addToCart(cartItem);

//            Toast.makeText(requireContext(), pizza.getName() + " aggiunta al carrello", Toast.LENGTH_SHORT).show();
            Snackbar.make(requireView(), pizza.getName() + " aggiunta al carrello", Snackbar.LENGTH_SHORT)
                    .setAction("Vai al carrello", v -> {
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                        navController.popBackStack();
                        navController.navigate(R.id.navigation_cart);
                    })
                    .show();
        });

        pizzaViewModel.getAllPizzas().observe(getViewLifecycleOwner(), pizzas -> {
            pizzaAdapter.submitList(pizzas);
        });

        return view;
    }







}