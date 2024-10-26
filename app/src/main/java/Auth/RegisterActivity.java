package Auth;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlsinhvien.R;

import Auth.ui.PassUI;

public class RegisterActivity extends AppCompatActivity {
EditText edtUsername,edtPass,edtConfirmPass;
ImageView ivTogglePassword,ivToggleConFirmPassword;
TextView txtReturn;
Button btnRegister;
DbUser db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DbUser(this);

        edtUsername=findViewById(R.id.edtUsername);
        edtPass=findViewById(R.id.edtPassword);
        edtConfirmPass=findViewById(R.id.edtConfirmPassword);
        txtReturn =findViewById(R.id.txtReturn);
        txtReturn.setOnClickListener(v -> finish());
        btnRegister=findViewById(R.id.btnRegister);
        ivTogglePassword=findViewById(R.id.ivTogglePassword);
        ivToggleConFirmPassword=findViewById(R.id.ivToggleConfirmPassword);
        PassUI PassUI =new PassUI(edtPass,ivTogglePassword);
        PassUI.setupUI();
        Auth.ui.PassUI ConfirmPassUI =new PassUI(edtPass,ivToggleConFirmPassword);
        ConfirmPassUI.setupUI();
btnRegister.setOnClickListener(v -> {
    String username = edtUsername.getText().toString();
    String password = edtPass.getText().toString();
    String confirmPassword = edtConfirmPass.getText().toString();
User user=new User(username,password);
    if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
        Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
    } else {
        if (password.equals(confirmPassword)) {
            boolean userExists = db.checkUserExists(user);
            if (!userExists) {
                boolean insert = db.insertUser(user);
                if (insert) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
        }


        }
    });
    }
}
