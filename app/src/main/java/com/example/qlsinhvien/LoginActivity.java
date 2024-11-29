package com.example.qlsinhvien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edtLogin,edtPassword;
    Button btLogin;
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
String admin="admin";
String password="123456";
        edtPassword=findViewById(R.id.edtPassword);

        edtLogin=findViewById(R.id.edtLogin);

        btLogin=findViewById(R.id.btLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtPasswordString=edtPassword.getText().toString();
                String edtLoginString=edtLogin.getText().toString();
if(edtLoginString.isEmpty() && edtPasswordString.isEmpty()){
    Toast.makeText(LoginActivity.this,"Vui lòng nhập tài khoản và mật khẩu để đăng nhập", Toast.LENGTH_SHORT).show();
    edtLogin.requestFocus();
}
else if(!edtLoginString.isEmpty() && edtPasswordString.isEmpty()){
    edtPassword.requestFocus();
}
else if(edtLoginString.isEmpty() && !edtPasswordString.isEmpty()){
    edtLogin.requestFocus();
}else{
    if(edtLoginString.equals(admin)  && edtPasswordString.equals(password)) {
        Intent myIntent = new Intent(LoginActivity.this,InteractActivity.class);
        startActivity(myIntent);
    }else{
        Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
    }
}
            }
        });
    }
}