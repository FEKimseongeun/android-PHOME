package com.smu.team_andeu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.smu.team_andeu.data.RoutineWithDexers;
import com.smu.team_andeu.model.Exer;
import com.smu.team_andeu.nav.DExerFragment;
import com.smu.team_andeu.nav.DRoutineFragment;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    BottomNavigationView bottomNav;
    NavController navController;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_app_bar);
        setSupportActionBar(toolbar);

        navController = Navigation.findNavController(
                this, R.id.nav_host_fragment
        );

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
             //   .setOpenableLayout()
                .build();

        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration
        );
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav,navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(destination.getId() == R.id.home_dest){
                toolbar.setVisibility(View.VISIBLE);
                bottomNav.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        boolean ret = super.onCreateOptionsMenu(menu);
        // NavigationView navigationView = findViewById(R.id.nav_view)
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    // for Exer List
    public void showExer(Exer exer) {
        Bundle bundle = DExerFragment.getBundleWithId(exer.getExerId());
        navController.navigate(R.id.action_DRoutineFragment_to_DExerFragment, bundle);
    }

    // for Routine List
    public void showRoutine(RoutineWithDexers routineWithDexers) {
        Bundle bundle = DRoutineFragment.getBundleWithId(routineWithDexers.getRoutine().getRoutineId());
        navController.navigate(R.id.action_routine_dest_to_detail_routine_dest, bundle);
    }

}