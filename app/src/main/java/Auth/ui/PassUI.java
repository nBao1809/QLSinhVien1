package Auth.ui;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlsinhvien.R;

public class PassUI extends AppCompatActivity {

    private EditText edtPassword;
    private ImageView ivTogglePassword;
    private boolean isPasswordVisible = false;
    public PassUI(EditText edtPassword, ImageView img) {
        this.edtPassword= edtPassword;
        this.ivTogglePassword=img;
    };
     public void setupUI(){

         ivTogglePassword.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 togglePasswordVisibility();
             }
         });
     }




    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Ẩn mật khẩu
            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ivTogglePassword.setImageResource(R.drawable.visibility_off_24px);
        } else {
            // Hiện mật khẩu
            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ivTogglePassword.setImageResource(R.drawable.visibility_24px);
        }
        // Đặt lại con trỏ ở cuối
        edtPassword.setSelection(edtPassword.length());
        isPasswordVisible = !isPasswordVisible;
    }
}

