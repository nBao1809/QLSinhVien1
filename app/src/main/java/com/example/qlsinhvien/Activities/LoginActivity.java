package com.example.qlsinhvien.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlsinhvien.Models.HocKy;
import com.example.qlsinhvien.Models.LopSinhVien;
import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.dao.HocKyManager;
import com.example.qlsinhvien.dao.LopSinhVienManager;
import com.example.qlsinhvien.dao.SinhVienManager;
import com.example.qlsinhvien.dao.UserManager;
import com.example.qlsinhvien.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edtLogin, edtPassword;
    UserManager userManager;
    Button btLogin;
    TextView forgetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userManager = new UserManager(this);
        edtPassword = findViewById(R.id.edtPassword);

        edtLogin = findViewById(R.id.edtLogin);

        btLogin = findViewById(R.id.btLogin);
        forgetPass=findViewById(R.id.forget);
        forgetPass.setOnClickListener(v -> {
            Intent intent= new Intent(this,ForgetPasswordActivity.class);
            startActivity(intent);
        });
        SinhVienManager sinhVienManager=new SinhVienManager(this);
        HocKyManager hocKyManager=new HocKyManager(this);
        LopSinhVienManager lopSinhVienManager=new LopSinhVienManager(this);
        edtLogin.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                edtPassword.requestFocus();
                return true;
            }
            return false;
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtPasswordString = edtPassword.getText().toString().trim();
                String edtLoginString = edtLogin.getText().toString().trim();
                if (edtLoginString.isEmpty() && edtPasswordString.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tài khoản và mật khẩu để đăng nhập", Toast.LENGTH_SHORT).show();
                    edtLogin.requestFocus();
                } else if (!edtLoginString.isEmpty() && edtPasswordString.isEmpty()) {
                    edtPassword.requestFocus();
                } else if (edtLoginString.isEmpty() && !edtPasswordString.isEmpty()) {
                    edtLogin.requestFocus();
                } else {
                    userManager.addUser(new User(1,"superadmin", "1",
                            BitmapFactory.decodeResource(getResources(), R.drawable.avatarsample),
                            "superadmin@ou.edu.vn", "superadmin"));
                    User isUserValid = userManager.checkLogin(edtLoginString,
                            edtPasswordString);
                    if (isUserValid != null) {
                        Intent myIntent = new Intent(LoginActivity.this, InteractActivity.class);
                        myIntent.putExtra("ID",isUserValid.getID());
                        startActivity(myIntent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}