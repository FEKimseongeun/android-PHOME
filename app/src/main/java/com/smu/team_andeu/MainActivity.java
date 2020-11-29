package com.smu.team_andeu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smu.team_andeu.data.Routine;
import com.smu.team_andeu.model.Dexer;
import com.smu.team_andeu.data.RoutineWithDexers;
import com.smu.team_andeu.model.Exer;
import com.smu.team_andeu.nav.DExerFragment;
import com.smu.team_andeu.nav.DRoutineFragment;


public class MainActivity extends AppCompatActivity {

    //private AppBarConfiguration appBarConfiguration;
    BottomNavigationView bottomNav;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(
                this, R.id.nav_host_fragment
        );

        // 앱바가 필요하면 이걸 쓰면 된다.
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
//             //   .setOpenableLayout()
//                .build();
//        NavigationUI.setupWithNavController(
//                toolbar, navController, appBarConfiguration
//        );
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

//        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
//            if(destination.getId() == R.id.home_dest){
//                toolbar.setVisibility(View.VISIBLE);
//                bottomNav.setVisibility(View.VISIBLE);
//            }
//        });

        bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    // 루틴 아이디를 전달함으로서 새로운 운동을 루틴에 추가할시에 RoutineId를 알 수 있도록 한다.
    public void showAddExer(Routine routine){
        Bundle bundle = DRoutineFragment.getBundleWithId(routine.getRoutineId());
        navController.navigate(R.id.action_detail_routine_dest_to_add_exer_dest, bundle);
    }

    // for Exer List
    public void showExer(Exer exer) {
        Bundle bundle = DExerFragment.getBundleWithId(exer.getExerId());
        navController.navigate(R.id.add_exer_dest_to_detail_exer_dest, bundle);
    }

    public void showExerWithDexer(Dexer dexer) {
        Bundle bundle = DExerFragment.getBundleWithDexerId(dexer.getExerId());
        navController.navigate(R.id.action_detail_routine_dest_to_detail_exer_dest, bundle);
    }

    // for Routine List
    public void showRoutine(RoutineWithDexers routineWithDexers) {
        Bundle bundle = DRoutineFragment.getBundleWithId(routineWithDexers.getRoutine().getRoutineId());
        navController.navigate(R.id.action_routine_dest_to_detail_routine_dest, bundle);
    }


    @Override
    public void onBackPressed() {
        int i = navController.getCurrentDestination().getId();
        if (i == R.id.routine_dest || i == R.id.setting_dest || i == R.id.training_dest) {
            navController.popBackStack(R.id.myPage_dest, true);
            navController.navigate(R.id.myPage_dest);
        } else if (i == R.id.myPage_dest) {
            new AlertDialog.Builder(this)
                    .setTitle(getApplication().getPackageName())
                    .setMessage("Phomer를 종료하시겠습니까?")
                    .setPositiveButton("종료", (dialog, which) -> finish())
                    .setNegativeButton("아니요", ((dialog, which) -> navController.popBackStack(R.id.myPage_dest, true)))
                    .show();
        } else if (!navController.popBackStack()) {
                new AlertDialog.Builder(this)
                        .setTitle(getApplication().getPackageName())
                        .setMessage("Phomer를 종료하시겠습니까?")
                        .setPositiveButton("종료", (dialog, which) -> finish())
                        .setNegativeButton("아니요", null)
                        .show();
        }
    }
}