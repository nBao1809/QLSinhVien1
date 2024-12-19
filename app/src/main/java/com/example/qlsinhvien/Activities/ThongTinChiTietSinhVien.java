package com.example.qlsinhvien.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.LopHanhChinhManager;
import com.example.qlsinhvien.dao.LopHocPhanManager;
import com.example.qlsinhvien.dao.LopSinhVienManager;
import com.example.qlsinhvien.dao.NganhManager;
import com.example.qlsinhvien.dao.SinhVienManager;
import com.google.android.material.appbar.MaterialToolbar;


public class ThongTinChiTietSinhVien extends AppCompatActivity {
    TextView txtTenSinhVien, txtMSSV, txtNgaySinh, txtCCCD, txtLopHanhChinh, txtNganh;
    MaterialToolbar toolbar;
    NganhManager nganhManager;
    SinhVienManager sinhVienManager;
    LopHanhChinhManager lopHanhChinhManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_tin_chi_tiet_sinh_vien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sinhVienManager = new SinhVienManager(this);
        lopHanhChinhManager = new LopHanhChinhManager(this);
        nganhManager = new NganhManager(this);
        txtCCCD = findViewById(R.id.txtCCCD);
        txtTenSinhVien = findViewById(R.id.txtTenSinhVien);
        txtMSSV = findViewById(R.id.txtMSSV);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        txtLopHanhChinh = findViewById(R.id.txtLopHanhChinh);
        txtNganh = findViewById(R.id.txtNganh);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        SinhVien sinhVien = (SinhVien) bundle.get("sinhVien");
        String formattedDay = dateFormat(sinhVien.getNgaySinh());
        txtNgaySinh.setText(formattedDay);
        txtCCCD.setText(sinhVien.getCccd());
        txtTenSinhVien.setText(sinhVien.getHoTen());
        txtMSSV.setText(sinhVien.getMssv());
        Log.d("test8", String.valueOf(lopHanhChinhManager.getLopHanhChinh(sinhVien.getMaLopHanhChinh()).getTenLopHanhChinh()));
        txtLopHanhChinh.setText(lopHanhChinhManager.getLopHanhChinh(sinhVien.getMaLopHanhChinh()).getTenLopHanhChinh());
        txtNganh.setText(nganhManager.getNganh(sinhVien.getMaNganh()).getTenNganh());
        Log.d("test23",nganhManager.getNganh(sinhVien.getMaNganh()).getTenNganh());
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