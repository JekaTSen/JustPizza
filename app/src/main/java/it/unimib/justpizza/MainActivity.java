package it.unimib.justpizza;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.unimib.justpizza.databinding.ActivityMainBinding;
import it.unimib.justpizza.utils.SeedMenu;
import it.unimib.justpizza.viewmodel.PizzaViewModel;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

 /*       ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, 0);
            binding.navView.setPadding(0, 0, 0, bars.bottom);
            return insets;
        });*/
        //SeedMenu.uploadAll();
        PizzaViewModel pizzaViewModel = new ViewModelProvider(this).get(PizzaViewModel.class);
        pizzaViewModel.refreshMenu();

        BottomNavigationView navView = binding.navView;
        NavController navController = Navigation.findNavController(
                this,
                R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController);
    }
}