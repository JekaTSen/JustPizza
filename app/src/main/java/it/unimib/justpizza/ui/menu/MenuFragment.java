package it.unimib.justpizza.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import it.unimib.justpizza.R;
import it.unimib.justpizza.model.CartItem;
import it.unimib.justpizza.model.Drink;
import it.unimib.justpizza.model.MenuItem;
import it.unimib.justpizza.model.Pizza;
import it.unimib.justpizza.model.Side;
import it.unimib.justpizza.ui.menu.adapter.MenuItemAdapter;
import it.unimib.justpizza.viewmodel.CartViewModel;
import it.unimib.justpizza.viewmodel.PizzaViewModel;

public class MenuFragment extends Fragment {

    private int selectedTab = 0;
    private List<Pizza> pizzas = new ArrayList<>();
    private List<Drink> drinks = new ArrayList<>();
    private List<Side> sides = new ArrayList<>();
    private MenuItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_pizzas);
        TabLayout tabLayout = view.findViewById(R.id.tab_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MenuItemAdapter();
        recyclerView.setAdapter(adapter);

        CartViewModel cartViewModel =
                new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        adapter.setOnItemClickListener(item -> {
            cartViewModel.addToCart(new CartItem(item, 1));
            Snackbar.make(requireView(),
                    item.getName() + " aggiunta al carrello",
                    Snackbar.LENGTH_SHORT).show();
        });

        PizzaViewModel pizzaViewModel =
                new ViewModelProvider(this).get(PizzaViewModel.class);

        pizzaViewModel.getAllPizzas().observe(getViewLifecycleOwner(), list -> {
            pizzas = list != null ? list : new ArrayList<>();
            showCurrentTab();
        });
        pizzaViewModel.getAllDrinks().observe(getViewLifecycleOwner(), list -> {
            drinks = list != null ? list : new ArrayList<>();
            showCurrentTab();
        });
        pizzaViewModel.getAllSides().observe(getViewLifecycleOwner(), list -> {
            sides = list != null ? list : new ArrayList<>();
            showCurrentTab();
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = tab.getPosition();
                showCurrentTab();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void showCurrentTab() {
        List<? extends MenuItem> data;
        if (selectedTab == 1) {
            data = drinks;
        } else if (selectedTab == 2) {
            data = sides;
        } else {
            data = pizzas;
        }
        adapter.submitList(new ArrayList<>(data));
    }
}