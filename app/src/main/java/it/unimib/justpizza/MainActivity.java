package it.unimib.justpizza;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.unimib.justpizza.databinding.ActivityMainBinding;
import it.unimib.justpizza.utils.DataSetDB;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Carica i dati di esempio (solo la prima volta o per test)
        if (savedInstanceState == null) {   // solo al primo avvio
            DataSetDB.seedData(this);
        }

        BottomNavigationView navView = binding.navView;

        //Material Design - Bottom Navigation
/*        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_menu,
                R.id.navigation_cart,
                R.id.navigation_orders,
                R.id.navigation_profile)
                .build();*/

        NavController navController = Navigation.findNavController(
                this,
                R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }



}