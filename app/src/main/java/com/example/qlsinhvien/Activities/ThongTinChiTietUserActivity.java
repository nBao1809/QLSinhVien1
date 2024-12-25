package com.example.qlsinhvien.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qlsinhvien.Models.GiangVien;
import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.GiangVienManager;
import com.example.qlsinhvien.dao.SinhVienManager;
import com.example.qlsinhvien.dao.UserManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlsinhvien.Models.User;
import com.google.android.material.appbar.MaterialToolbar;

public class ThongTinChiTietUserActivity extends AppCompatActivity {
    private User user;
    private SinhVien sinhVien;
    private GiangVien giangVien;
    private UserManager userManager;
    private GiangVienManager giangVienManager;
    private SinhVienManager sinhVienManager;
    MaterialToolbar toolbar;
    TextView txtID, txtUsername, txtRole, txtEmail, txtTenSinhVien, txtMSSV, txtNgaySinh, txtCCCD, txtLopHanhChinh, txtNganh, txtGiangVien, txtMaGiangVien, txtNgaySinhGv, txtCCCDGv, txtKhoa;
    LinearLayout layoutGiangVien, layoutSinhVien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thongtinchitietuser);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();
        userManager = new UserManager(this);
        giangVienManager = new GiangVienManager(this);
        sinhVienManager = new SinhVienManager(this);
        user = userManager.getUserByID(intent.getIntExtra("userID", -1));
        layoutGiangVien = findViewById(R.id.isGiangVien);
        layoutSinhVien = findViewById(R.id.isSinhVien);
        txtID = findViewById(R.id.txtID);
        txtUsername = findViewById(R.id.txtTenTaiKhoan);
        txtEmail = findViewById(R.id.txtEmail);
        txtRole = findViewById(R.id.txtRole);
        txtID.setText(String.valueOf(user.getID()));
        txtUsername.setText(user.getUsername());
        txtEmail.setText(user.getEmail());
        txtRole.setText(user.getRole());
        if (user.getRole().equals("gv")) {
            layoutGiangVien.setVisibility(View.VISIBLE);
            giangVien = giangVienManager.getGiangVienFromUser(user.getID());
            txtGiangVien = findViewById(R.id.txtGiangVien);
            txtMaGiangVien = findViewById(R.id.txtMaGiangVien);
            txtNgaySinhGv = findViewById(R.id.txtNgaySinhGv);
            txtCCCDGv = findViewById(R.id.txtCCCDGv);
            txtKhoa = findViewById(R.id.txtKhoa);
            txtGiangVien.setText(giangVien.getHoTen());
            txtMaGiangVien.setText(giangVien.getMaGiangVien());
            txtNgaySinhGv.setText(dateFormat( giangVien.getNgaySinh()));
            txtCCCDGv.setText(giangVien.getCccd());
            txtKhoa.setText(giangVien.getKhoa());

        } else if (user.getRole().equals("sv")) {
            layoutSinhVien.setVisibility(View.VISIBLE);
            sinhVien = sinhVienManager.getSinhVienFromUser(user.getID());
            txtTenSinhVien = findViewById(R.id.txtTenSinhVien);
            txtMSSV = findViewById(R.id.txtMSSV);
            txtNgaySinh = findViewById(R.id.txtNgaySinh);
            txtCCCD = findViewById(R.id.txtCCCD);
            txtLopHanhChinh = findViewById(R.id.txtLopHanhChinh);
            txtNganh = findViewById(R.id.txtNganh);
            txtTenSinhVien.setText(sinhVien.getHoTen());
            txtMSSV.setText(sinhVien.getMssv());
            txtNgaySinh.setText(sinhVien.getMssv());
            txtCCCD.setText(sinhVien.getCccd());
            txtLopHanhChinh.setText(sinhVien.getMaLopHanhChinh());
            txtNganh.setText(sinhVien.getMaNganh());
        }

    }
    public String dateFormat(double ngaySinhDouble) {
        int nam = (int) ngaySinhDouble;
        int thangNgay = (int) ((ngaySinhDouble - nam) * 10000);
        int thang = thangNgay / 100;
        int ngay = thangNgay % 100;
        String formattedDate = String.format("%02d/%02d/%04d", ngay, thang, nam);
        return formattedDate;
    }
}