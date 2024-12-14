package com.example.qlsinhvien.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.UserManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlsinhvien.Models.User;

public class SingleUserActivity extends AppCompatActivity {
    private User user;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        userManager = new UserManager(this);
        user = userManager.getUserByID(intent.getIntExtra("userID", -1));

    }
}