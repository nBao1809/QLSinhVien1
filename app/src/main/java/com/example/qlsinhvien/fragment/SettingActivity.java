package com.example.qlsinhvien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlsinhvien.LoginActivity;
import com.example.qlsinhvien.R;

public class SettingActivity extends AppCompatActivity {
    Button buttonsignout, buttonpasschange;
    Toolbar toolbar;

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

        buttonpasschange=findViewById(R.id.buttonpasschange);
        buttonsignout = findViewById(R.id.buttonsignout);
        buttonsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myinIntent = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(myinIntent);
                finish();
            }

        });
    }
}