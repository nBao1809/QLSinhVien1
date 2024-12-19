package com.example.qlsinhvien.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.qlsinhvien.Fragments.AccountFragment;
import com.example.qlsinhvien.Fragments.HomeFragment;
import com.example.qlsinhvien.Fragments.StatisticFragment;
import com.example.qlsinhvien.Fragments.UtilityFragment;
import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.UserManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class InteractActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SharedPreferences userRefs;
    UserManager userManager;
    int idc = R.id.home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_interact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(InteractActivity.this)
                        .setTitle("Xác nhận").setIcon(R.drawable.checkicon)
                        .setMessage("Bạn có muốn đăng xuất không?")
                        .setPositiveButton("Có", (dialog, which) -> finish())
                        .setNegativeButton("Không", null)
                        .show();
            }
        });
        userManager = new UserManager(this);
        Intent intent=getIntent();
        User currentUser = userManager.getUserByID(intent.getIntExtra("ID",-1));
        replaceFragment(new HomeFragment());
        bottomNavigationView = findViewById(R.id.bottomBar);
        if(currentUser.getRole().equals("gv")||currentUser.getRole().equals("sv")){
            bottomNavigationView.getMenu().removeItem(R.id.utility);
            bottomNavigationView.getMenu().removeItem(R.id.stat);
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home && id != idc) {
                    replaceFragment(new HomeFragment().newInstance(currentUser.getID()));


                } else if (id == R.id.utility && id != idc) {
                    replaceFragment(new UtilityFragment().newInstance(currentUser.getID()));

                } else if (id == R.id.stat && id != idc) {
                    replaceFragment(new StatisticFragment().newInstance(currentUser.getID()));

                } else if (id == R.id.account && id != idc) {
                    replaceFragment(new AccountFragment().newInstance(currentUser.getID()));

                }
                idc = id;
                return true;
            }
        });

    }


    public void replaceFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }
}