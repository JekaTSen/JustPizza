package it.unimib.justpizza.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.justpizza.R;
import it.unimib.justpizza.model.Order;
import it.unimib.justpizza.repository.AuthRepository;
import it.unimib.justpizza.ui.orders.adapter.OrderAdapter;
import it.unimib.justpizza.viewmodel.OrdersViewModel;

public class OrdersFragment extends Fragment {

    private OrdersViewModel ordersViewModel;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private TextView emptyText;

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        ordersViewModel = new ViewModelProvider(requireActivity()).get(OrdersViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view_orders);
        emptyText = view.findViewById(R.id.tv_orders_empty);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderAdapter = new OrderAdapter();
        recyclerView.setAdapter(orderAdapter);

        String uid = "guest";
        if (new AuthRepository().getCurrentUser() != null) {
            uid = new AuthRepository().getCurrentUser().getUid();
        }
        final String currentUid = uid;

        ordersViewModel.getAllOrders().observe(getViewLifecycleOwner(), orders -> {
            List<Order> mine = new ArrayList<>();
            if (orders != null) {
                for (Order order : orders) {
                    if (currentUid.equals(order.getUserId())) {
                        mine.add(order);
                    }
                }
            }
            orderAdapter.submitList(mine);
            emptyText.setVisibility(mine.isEmpty() ? View.VISIBLE : View.GONE);
        });

        return view;
    }
}