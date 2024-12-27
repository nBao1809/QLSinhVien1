package com.example.qlsinhvien.Activities;

import static com.example.qlsinhvien.dao.UserManager.generateOTP;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.UserManager;
import com.google.android.material.appbar.MaterialToolbar;

public class ForgetPasswordActivity extends AppCompatActivity {
    User user;
    UserManager userManager;
    private EditText edtUsername, edtOTP, edtNewPassword, edtConfirmPassword;
    private Button btnSendOTP, btnVerifyOTP, btnChangePassword;
    private TextView tvOTPStatus, tvCountdown;

    private CountDownTimer countDownTimer;
    private SharedPreferences otpPrefs;
    private SharedPreferences.Editor otpEditor;
    private String otp;
    private long remainingTime;
    LinearLayout isSendedOTP, newPass, cfPass;
MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        otpPrefs = getSharedPreferences("OTP", MODE_PRIVATE); // Lưu trữ thời gian OTP
        otpEditor = otpPrefs.edit();
        userManager = new UserManager(this);

        edtUsername = findViewById(R.id.edtUsername);
        edtOTP = findViewById(R.id.edtOTP);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSendOTP = findViewById(R.id.btnSendOTP);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        tvOTPStatus = findViewById(R.id.tvOTPStatus);
        tvCountdown = findViewById(R.id.tvCountdown);
        isSendedOTP = findViewById(R.id.isSendOTP);
        newPass = findViewById(R.id.newPass);
        cfPass = findViewById(R.id.cfPass);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        // Khi nhấn nút gửi OTP
        btnSendOTP.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            if (username.isEmpty()) {
                Toast.makeText(ForgetPasswordActivity.this, "Vui lòng nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                return;
            }
            user = userManager.getUserByUserName(username);
            if (user == null) {
                Toast.makeText(ForgetPasswordActivity.this, "Username không tồn tại", Toast.LENGTH_SHORT).show();
                return;
            }
            otp = generateOTP();  // Tạo OTP mới
            String email = user.getEmail();
            // Gửi email với OTP
            userManager.sendEmail(email, otp);
            tvOTPStatus.setText("Gửi OTP đến mail: " + email);
            tvOTPStatus.setVisibility(View.VISIBLE);

            // Hiển thị ô nhập mã OTP và nút xác thực
            isSendedOTP.setVisibility(View.VISIBLE);
            btnVerifyOTP.setVisibility(View.VISIBLE);
            tvCountdown.setVisibility(View.VISIBLE);

            // Lưu thời gian gửi OTP vào SharedPreferences
            otpEditor.putString("otp", otp);
            otpEditor.putLong("otp_time", System.currentTimeMillis() + 300000);
            otpEditor.apply();

            // Cập nhật nút gửi OTP thành "Gửi lại OTP"
            btnSendOTP.setText("Gửi lại OTP");
            btnSendOTP.setEnabled(false);

            // Bắt đầu đếm ngược
            startCountdown(300000);
        });

        // Khi nhấn nút xác thực OTP
        btnVerifyOTP.setOnClickListener(v -> {
            String enteredOTP = edtOTP.getText().toString().trim();
            if (enteredOTP.isEmpty()) {
                Toast.makeText(ForgetPasswordActivity.this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
                return;
            }

            if (enteredOTP.equals(otp)) {
                // OTP đúng, hiển thị các ô nhập mật khẩu mới
                newPass.setVisibility(View.VISIBLE);
                cfPass.setVisibility(View.VISIBLE);
                btnChangePassword.setVisibility(View.VISIBLE);
                countDownTimer.cancel();
                tvCountdown.setVisibility(View.GONE);
            } else {
                Toast.makeText(ForgetPasswordActivity.this, "Mã OTP không đúng", Toast.LENGTH_SHORT).show();
            }
        });

        // Khi nhấn nút thay đổi mật khẩu
        btnChangePassword.setOnClickListener(v -> {
            String newPassword = edtNewPassword.getText().toString().trim();
            String confirmPassword = edtConfirmPassword.getText().toString().trim();

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(ForgetPasswordActivity.this, "Vui lòng nhập đầy đủ mật khẩu mới và xác nhận", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(ForgetPasswordActivity.this, "Mật khẩu và xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            user.setPassword(newPassword);
            userManager.updateUser(user);
            Toast.makeText(ForgetPasswordActivity.this, "Mật khẩu đã được thay đổi thành công", Toast.LENGTH_SHORT).show();
        });
    }

    private void startCountdown(long timeLeft) {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                // Sau khi hết thời gian, cho phép người dùng gửi lại OTP
                btnSendOTP.setEnabled(true); // Kích hoạt lại nút gửi OTP
                btnSendOTP.setText("Gửi lại OTP"); // Thay đổi tên nút thành "Gửi lại OTP"
                remainingTime = 0;  // Đặt lại remainingTime sau khi hết thời gian
            }
        }.start();
    }

    // Cập nhật thời gian đếm ngược
    private void updateCountdownText() {
        int minutes = (int) (remainingTime / 1000) / 60;
        int seconds = (int) (remainingTime / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        tvCountdown.setText("Thời gian còn lại: " + timeLeftFormatted);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Lưu lại OTP và thời gian gửi OTP khi Activity bị tạm dừng
        otpEditor.putString("otp", otp);
        otpEditor.putLong("otp_time", System.currentTimeMillis() + remainingTime);  // Lưu thời gian gửi
        otpEditor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Khi Activity được khôi phục, kiểm tra thời gian còn lại của OTP
        String otp = otpPrefs.getString("otp", "");
        long otpTime = otpPrefs.getLong("otp_time", 0);
        remainingTime = otpTime - System.currentTimeMillis();

        if (remainingTime > 0) {
            btnSendOTP.setEnabled(false);  // Vô hiệu hóa nút gửi OTP nếu thời gian chưa hết
            startCountdown(remainingTime);  // Bắt đầu đếm ngược từ thời gian còn lại
            tvCountdown.setVisibility(View.VISIBLE);
        } else {
            btnSendOTP.setEnabled(true);  // Cho phép gửi lại OTP nếu hết thời gian
            btnSendOTP.setText("Gửi OTP");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Dừng đếm ngược khi Activity bị dừng
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
