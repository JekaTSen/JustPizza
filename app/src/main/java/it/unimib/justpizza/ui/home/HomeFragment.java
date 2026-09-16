package it.unimib.justpizza.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;

import it.unimib.justpizza.R;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton btnGoMenu = view.findViewById(R.id.btn_go_menu);
        btnGoMenu.setOnClickListener(v -> {
            com.google.android.material.bottomnavigation.BottomNavigationView navView =
                    requireActivity().findViewById(R.id.nav_view);
            navView.setSelectedItemId(R.id.navigation_menu);
        });
    }
}