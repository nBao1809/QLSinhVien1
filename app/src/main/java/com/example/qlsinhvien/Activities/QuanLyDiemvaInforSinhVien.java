package com.example.qlsinhvien.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsinhvien.Adapter.LopSinhVienAdapter;
import com.example.qlsinhvien.Models.Diem;
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.DiemManager;
import com.example.qlsinhvien.dao.HocKyManager;
import com.example.qlsinhvien.dao.LoaiDiemManager;
import com.example.qlsinhvien.dao.LopHanhChinhManager;
import com.example.qlsinhvien.dao.LopHocPhanManager;
import com.example.qlsinhvien.dao.LopSinhVienManager;
import com.example.qlsinhvien.dao.NganhManager;
import com.example.qlsinhvien.dao.SinhVienManager;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class QuanLyDiemvaInforSinhVien extends AppCompatActivity {
    TextView txtTenSinhVien, txtMSSV, txtNgaySinh, txtCCCD, txtLopHanhChinh, txtNganh, txtTenLop, txtMalop, txtHocky, txtThongBao;
    RecyclerView recycleDiemSinhVien;
    LopSinhVienAdapter lopSinhVienAdapter;
    SinhVienManager sinhVienManager;
    LopHanhChinhManager lopHanhChinhManager;
    LopHocPhanManager lopHocPhanManager;
    LopSinhVienManager lopSinhVienManager;
    DiemManager diemManager;
    LoaiDiemManager loaiDiemManager;
    HocKyManager hocKyManager;
    NganhManager nganhManager;
    MaterialToolbar toolbar;
    List<Diem> listDiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_diemva_infor_sinh_vien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sinhVienManager = new SinhVienManager(this);
        lopSinhVienManager = new LopSinhVienManager(this);
        lopHanhChinhManager = new LopHanhChinhManager(this);
        nganhManager = new NganhManager(this);
        lopHocPhanManager = new LopHocPhanManager(this);
        hocKyManager = new HocKyManager(this);
        diemManager = new DiemManager(this);
        loaiDiemManager = new LoaiDiemManager(this);
        txtCCCD = findViewById(R.id.txtCCCD);
        txtMalop = findViewById(R.id.txtMalop);
        txtTenSinhVien = findViewById(R.id.txtTenSinhVien);
        txtMSSV = findViewById(R.id.txtMSSV);
        txtHocky = findViewById(R.id.txtHocky);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        txtLopHanhChinh = findViewById(R.id.txtLopHanhChinh);
        txtNganh = findViewById(R.id.txtNganh);
        txtTenLop = findViewById(R.id.txtTenLop);
        recycleDiemSinhVien = findViewById(R.id.recycleDiemSinhVien);
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
        LopHocPhan lopHocPhan = (LopHocPhan) bundle.get("lopHocPhan");
        txtTenSinhVien.setText(sinhVien.getHoTen());
        txtMSSV.setText(sinhVien.getMssv());
        String tenHocKy = hocKyManager.getHocKy(lopSinhVienManager.getMaHocKyfromMalop(lopHocPhan.getMaLop())).getTenHocKy();
        txtHocky.setText(tenHocKy);
        String formattedDate = dateFormat(sinhVien.getNgaySinh());
        txtNgaySinh.setText(formattedDate);
        txtCCCD.setText(sinhVien.getCccd());
        txtLopHanhChinh.setText(lopHanhChinhManager.getLopHanhChinh(sinhVien.getMaLopHanhChinh()).getTenLopHanhChinh());
        txtNganh.setText(nganhManager.getNganh(sinhVien.getMaNganh()).getTenNganh());
        txtTenLop.setText(lopHocPhan.getTenLop());
        txtMalop.setText(lopHocPhan.getMaLop());
        txtThongBao = findViewById(R.id.txtThongBao);
        lopSinhVienAdapter = new LopSinhVienAdapter(this);
        listDiem = diemManager.getDiemfromMalopsinhvien(lopSinhVienManager.getMaLopSinhVienfromMalopMSSV(lopHocPhan.getMaLop(), sinhVien.getMssv()));
        if (listDiem == null) {
            txtThongBao.setText("Giáo viên chưa nhập điểm");
            txtThongBao.setVisibility(View.VISIBLE);
            return;
        }
        txtThongBao.setVisibility(View.GONE);
        lopSinhVienAdapter.setData(listDiem);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleDiemSinhVien.setLayoutManager(linearLayoutManager);
        recycleDiemSinhVien.setAdapter(lopSinhVienAdapter);


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