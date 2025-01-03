package com.example.qlsinhvien.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlsinhvien.R;

public class SplashActivity extends AppCompatActivity {
Handler handler = new Handler();
Runnable runnable ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.enter_left_to_right,R.anim.exit_right_to_left);
                finish();
            };
            };
        handler.postDelayed(runnable,2070);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
