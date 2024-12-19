package com.example.qlsinhvien.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlsinhvien.Models.GiangVien;
import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.GiangVienManager;
import com.google.android.material.appbar.MaterialToolbar;

public class ThongTinChiTietGiangVienActivity extends AppCompatActivity {
    TextView txtMGV, txtHoTen, txtCCCD, txtKhoa,txtNgaySinh ;
    MaterialToolbar toolbar;
    GiangVienManager giangVienManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_tin_chi_tiet_giang_vien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtMGV=findViewById(R.id.txtMGV);
        txtCCCD=findViewById(R.id.txtCCCD);
        txtHoTen=findViewById(R.id.txtHoTen);
        txtKhoa=findViewById(R.id.txtKhoa);
        txtNgaySinh=findViewById(R.id.txtNgaySinh);
        giangVienManager = new GiangVienManager(this);
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
        GiangVien giangVien = (GiangVien) bundle.get("giangVien");
        String formattedDay= dateFormat(giangVien.getNgaySinh());
        txtNgaySinh.setText(formattedDay);
        txtKhoa.setText(giangVien.getKhoa());
        txtCCCD.setText(giangVien.getCccd());
        txtMGV.setText(giangVien.getMaGiangVien());
        txtHoTen.setText(giangVien.getHoTen());

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