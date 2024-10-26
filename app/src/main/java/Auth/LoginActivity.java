package Auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qlsinhvien.MainActivity;
import com.example.qlsinhvien.R;

import Auth.ui.PassUI;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    ImageView ivTogglePassword;
    Button btnLogin;
    DbUser db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        TextView txtDangKi =findViewById(R.id.txtRegister);

        txtDangKi.setOnClickListener(v -> {
            Intent intent= new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });
        db = new DbUser(this);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        ivTogglePassword=findViewById(R.id.ivTogglePassword);
        PassUI showPass =new PassUI(edtPassword,ivTogglePassword);
        showPass.setupUI();
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
User user = new User(username,password);
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                boolean isValid = db.checkUserLogin(user);
                if (isValid) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    // Điều hướng sang màn hình khác sau khi đăng nhập thành công
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        })
    ;}
}




