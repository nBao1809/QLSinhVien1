package com.example.qlsinhvien.Activities;

import android.app.AlertDialog;
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
        userManager = new UserManager(this);
        userRefs = getSharedPreferences("currentUser", MODE_PRIVATE);
        User currentUser = userManager.getUserByID(userRefs.getInt("ID", -1));
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
        userRefs = this.getSharedPreferences("currentUser", MODE_PRIVATE);

        replaceFragment(new HomeFragment());
        bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            int isDisplay = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home && id != idc) {
                    replaceFragment(new HomeFragment());


                } else if (id == R.id.utility && id != idc) {
                    replaceFragment(new UtilityFragment());

                } else if (id == R.id.stat && id != idc) {
                    replaceFragment(new StatisticFragment());

                } else if (id == R.id.account && id != idc) {
                    replaceFragment(new AccountFragment().newInstance(userRefs.getInt("ID", -1)));

                }
                idc =id;
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