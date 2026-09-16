package it.unimib.justpizza.ui.cart;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.unimib.justpizza.R;
import it.unimib.justpizza.datasource.DeliveryRemoteDataSource;
import it.unimib.justpizza.datasource.NominatimResult;
import it.unimib.justpizza.model.Order;
import it.unimib.justpizza.repository.AuthRepository;
import it.unimib.justpizza.ui.cart.adapter.CartAdapter;
import it.unimib.justpizza.utils.NetworkUtils;
import it.unimib.justpizza.viewmodel.CartViewModel;
import it.unimib.justpizza.viewmodel.OrdersViewModel;

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;
    private OrdersViewModel ordersViewModel;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView totalTextView;
    private MaterialAutoCompleteTextView etAddress;
    private TextInputLayout layoutAddress;

    private final DeliveryRemoteDataSource deliverySource = new DeliveryRemoteDataSource();
    private final Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;
    private List<NominatimResult> lastSuggestions = new ArrayList<>();
    private boolean fromSelection;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        ordersViewModel = new ViewModelProvider(requireActivity()).get(OrdersViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view_cart);
        totalTextView = view.findViewById(R.id.cart_total);
        etAddress = view.findViewById(R.id.et_delivery_address);
        layoutAddress = view.findViewById(R.id.layout_address);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cartAdapter = new CartAdapter();
        cartAdapter.setOnCartItemClickListener(item -> cartViewModel.removeFromCart(item));
        recyclerView.setAdapter(cartAdapter);

        cartViewModel.getAllCartItems().observe(getViewLifecycleOwner(), items ->
                cartAdapter.submitList(items));

        cartViewModel.getTotalPrice().observe(getViewLifecycleOwner(), total ->
                totalTextView.setText(String.format(Locale.ITALY, "Totale: € %.2f", total)));

        setupAddressSuggestions();

        Button btnClear = view.findViewById(R.id.btn_clear_cart);
        btnClear.setOnClickListener(v -> cartViewModel.clearCart());

        Button btnProceed = view.findViewById(R.id.btn_proceed);
        btnProceed.setOnClickListener(v -> attemptPayment());

        return view;
    }

    private void setupAddressSuggestions() {
        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (fromSelection) {
                    return;
                }
                searchHandler.removeCallbacks(searchRunnable);
                String q = s.toString().trim();
                if (q.length() < 3) {
                    return;
                }
                searchRunnable = () -> deliverySource.searchSuggestions(q,
                        new DeliveryRemoteDataSource.SuggestionsCallback() {
                            @Override
                            public void onResults(List<NominatimResult> results) {
                                if (!isAdded()) {
                                    return;
                                }
                                lastSuggestions = results;
                                List<String> labels = new ArrayList<>();
                                for (NominatimResult r : results) {
                                    labels.add(String.format(Locale.ITALY,
                                            "%s  (%.1f km)", r.displayName, r.km));
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                        requireContext(),
                                        android.R.layout.simple_dropdown_item_1line,
                                        labels);
                                etAddress.setAdapter(adapter);
                                if (!labels.isEmpty() && etAddress.hasFocus()) {
                                    etAddress.showDropDown();
                                }
                            }

                            @Override
                            public void onError(String message) {}
                        });
                searchHandler.postDelayed(searchRunnable, 500);
            }
        });

        etAddress.setOnItemClickListener((parent, v, position, id) -> {
            if (position < 0 || position >= lastSuggestions.size()) {
                return;
            }
            NominatimResult chosen = lastSuggestions.get(position);
            fromSelection = true;
            etAddress.setText(chosen.displayName);
            fromSelection = false;
            layoutAddress.setHelperText(String.format(Locale.ITALY,
                    "%.1f km dalla pizzeria", chosen.km));
        });
    }

    private void attemptPayment() {
        if (!NetworkUtils.isOnline(requireContext())) {
            Snackbar.make(requireView(),
                    "Serve connessione per completare il pagamento",
                    Snackbar.LENGTH_LONG).show();
            return;
        }

        Double totalValue = cartViewModel.getTotalPrice().getValue();
        double total = totalValue != null ? totalValue : 0.0;
        if (total <= 0) {
            Toast.makeText(getContext(), "Il carrello è vuoto", Toast.LENGTH_SHORT).show();
            return;
        }

        String address = etAddress.getText() != null
                ? etAddress.getText().toString().trim() : "";
        if (address.isEmpty()) {
            Snackbar.make(requireView(), "Inserisci l'indirizzo di consegna",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        deliverySource.checkAddress(address,
                new DeliveryRemoteDataSource.DeliveryCallback() {
                    @Override
                    public void onInRange(double km, String resolvedAddress) {
                        if (!isAdded()) return;
                        Snackbar.make(requireView(),
                                String.format(Locale.ITALY, "Indirizzo giusto (%.1f km)", km),
                                Snackbar.LENGTH_LONG).show();

                        String uid = "guest";
                        if (new AuthRepository().getCurrentUser() != null) {
                            uid = new AuthRepository().getCurrentUser().getUid();
                        }
                        String summary = String.format(Locale.ITALY, "Ordine di € %.2f", total);
                        ordersViewModel.insertOrder(new Order(uid, total, summary));
                        cartViewModel.clearCart();
                    }

                    @Override
                    public void onOutOfRange(double km) {
                        if (!isAdded()) return;
                        Snackbar.make(requireView(),
                                String.format(Locale.ITALY,
                                        "Indirizzo troppo lontano (%.1f km, max 5 km)", km),
                                Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String message) {
                        if (!isAdded()) return;
                        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show();
                    }
                });
    }
}