package com.example.qlsinhvien.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.UserManager;
import com.google.android.material.appbar.MaterialToolbar;

public class SettingActivity extends AppCompatActivity {
    Button buttonsignout, buttonpasschange, buttonexit;
    MaterialToolbar toolbar;
    SharedPreferences userRefs;
    User currentUser;
    UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar = findViewById(R.id.toolbarsetting);
        toolbar.inflateMenu(R.menu.menusetting);
        userManager = new UserManager(this);
        userRefs = this.getSharedPreferences("currentUser", MODE_PRIVATE);
        currentUser = userManager.getUserByID(userRefs.getInt("ID", -1));
        Menu menu = toolbar.getMenu();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.notification) {
                    Toast.makeText(SettingActivity.this, "Chưa có chức năng", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonpasschange = findViewById(R.id.buttonpasschange);
        buttonpasschange.setOnClickListener(v -> {
            Intent myinIntent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
            startActivity(myinIntent);
        });
        buttonexit = findViewById(R.id.buttonexit);
        buttonexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("Xác nhận!")
                        .setIcon(R.drawable.checkicon)
                        .setMessage("Bạn có chắc muốn thoát ứng dụng hay không")
                        .setPositiveButton("Có", (dialog, which) -> finishAffinity())
                        .setNegativeButton("Không", null)
                        .show();
            }
        });
        buttonsignout = findViewById(R.id.buttonsignout);
        buttonsignout.setOnClickListener(v -> {
            Intent myinIntent = new Intent(SettingActivity.this, LoginActivity.class);
            myinIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myinIntent);
            finish();
        });
    }
}