package com.example.qlsinhvien.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.UserManager;
import com.google.android.material.textfield.TextInputEditText;


public class ChangePasswordActivity extends AppCompatActivity {
    TextInputEditText oldPass, newPass, confirmPass;
    Button btnDoi,btnHuy;
    UserManager userManager;
    SharedPreferences userRefs;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            userManager = new UserManager(this);
            Intent intent =getIntent();
            currentUser = userManager.getUserByID(intent.getIntExtra("ID",-1));
            oldPass = findViewById(R.id.edtOldPass);
            newPass = findViewById(R.id.edtNewPass);
            confirmPass = findViewById(R.id.edtConfirmPass);
            btnHuy=findViewById(R.id.btnHuy);
            btnHuy.setOnClickListener(v1 -> {
                finish();
            });
            btnDoi = findViewById(R.id.btnDoi);
            btnDoi.setOnClickListener(v1 -> {
                String oldPassString = oldPass.getText().toString().trim();
                String newPassString = newPass.getText().toString().trim();
                String confirmPassString = confirmPass.getText().toString().trim();
                if (oldPassString.isEmpty() && newPassString.isEmpty() && confirmPassString.isEmpty()) {
                    // Nếu tất cả các trường đều trống
                    Toast.makeText(ChangePasswordActivity.this, "Vui lòng nhập đủ các thông tin", Toast.LENGTH_SHORT).show();
                    oldPass.requestFocus(); // Đặt focus vào Mật khẩu cũ
                } else if (oldPassString.isEmpty()) {
                    // Nếu Mật khẩu cũ trống
                    Toast.makeText(ChangePasswordActivity.this, "Vui lòng nhập mật khẩu cũ", Toast.LENGTH_SHORT).show();
                    oldPass.requestFocus(); // Đặt focus vào Mật khẩu cũ
                } else if (newPassString.isEmpty()) {
                    // Nếu Mật khẩu mới trống
                    Toast.makeText(ChangePasswordActivity.this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                    newPass.requestFocus(); // Đặt focus vào Mật khẩu mới
                } else if (confirmPassString.isEmpty()) {
                    // Nếu Xác nhận mật khẩu mới trống
                    Toast.makeText(ChangePasswordActivity.this, "Vui lòng nhập xác nhận mật khẩu mới", Toast.LENGTH_SHORT).show();
                    confirmPass.requestFocus(); // Đặt focus vào Xác nhận mật khẩu mới
                } else if (!newPassString.equals(confirmPassString)) {
                    // Nếu Mật khẩu mới và Xác nhận mật khẩu mới không khớp
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới và xác nhận mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                    confirmPass.requestFocus(); // Đặt focus vào Xác nhận mật khẩu mới
                } else {
                    User user = userManager.getUserByID(userRefs.getInt("ID", -1));
                    boolean isChange = userManager.changePassword(newPassString, user);
                    if (isChange) {
                        Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công",
                                Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            });


            return insets;
        });
    }

    ;
}