package com.example.qlsinhvien.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsinhvien.Adapter.GiangVienAdapter;
import com.example.qlsinhvien.Adapter.LoaiDiemAdapter;
import com.example.qlsinhvien.Adapter.LopSinhVienAdapter;
import com.example.qlsinhvien.Models.Diem;
import com.example.qlsinhvien.Models.LoaiDiem;
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.DiemManager;
import com.example.qlsinhvien.dao.HocKyManager;
import com.example.qlsinhvien.dao.LoaiDiemManager;
import com.example.qlsinhvien.dao.LopHanhChinhManager;
import com.example.qlsinhvien.dao.LopHocPhanManager;
import com.example.qlsinhvien.dao.LopSinhVienManager;
import com.example.qlsinhvien.dao.NganhManager;
import com.example.qlsinhvien.dao.SinhVienManager;
import com.example.qlsinhvien.dao.UserManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuanLyDiemvaInforSinhVien extends AppCompatActivity {
    TextView txtTenSinhVien, txtMSSV, txtNgaySinh, txtCCCD, txtLopHanhChinh, txtNganh, txtTenLop, txtMalop, txtHocky, txtThongBao;
    RecyclerView recycleDiemSinhVien;
    LopSinhVienAdapter lopSinhVienAdapter;
    UserManager userManager;
    SinhVienManager sinhVienManager;
    LopHanhChinhManager lopHanhChinhManager;
    LopHocPhanManager lopHocPhanManager;
    LopSinhVienManager lopSinhVienManager;
    DiemManager diemManager;
    LoaiDiemManager loaiDiemManager;
    HocKyManager hocKyManager;
    NganhManager nganhManager;
    MaterialToolbar toolbar;
    List<Diem> listDiem, listDiemTemp;
    List<LoaiDiem> loaiDiemList;
    SharedPreferences userRefs;
    ImageButton ibtnThem;
    User currentUser;
    String maLoaiDiem, maLoaiDiemtemp, tenLoaiDiem, diemSoString;
    Double trongSo = 0.0;
    Double diemSo = 0.0;
    Double trongSoTemp;

    LoaiDiemAdapter loaiDiemAdapter;

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
        userManager = new UserManager(this);
        txtCCCD = findViewById(R.id.txtCCCD);
        txtMalop = findViewById(R.id.txtMalop);
        ibtnThem = findViewById(R.id.ibtnThem);
        txtTenSinhVien = findViewById(R.id.txtTenSinhVien);
        txtMSSV = findViewById(R.id.txtMSSV);
        txtHocky = findViewById(R.id.txtHocky);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        txtLopHanhChinh = findViewById(R.id.txtLopHanhChinh);
        txtNganh = findViewById(R.id.txtNganh);
        txtTenLop = findViewById(R.id.txtTenLop);
        recycleDiemSinhVien = findViewById(R.id.recycleDiemSinhVien);
        toolbar = findViewById(R.id.toolbar);
        userRefs = this.getSharedPreferences("currentUser", MODE_PRIVATE);
        currentUser = userManager.getUserByID(userRefs.getInt("ID", -1));
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
        if (lopHocPhan != null) {
            String maLop = lopHocPhan.getMaLop();
            // Tiếp tục xử lý
        } else {
            Log.e("ERROR", "Đối tượng LopHocPhan là null");
        }
        assert lopHocPhan != null;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleDiemSinhVien.setLayoutManager(linearLayoutManager);
        listDiem = new ArrayList<>();
        ibtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyDiemvaInforSinhVien.this);
                View view = LayoutInflater.from(QuanLyDiemvaInforSinhVien.this).inflate(R.layout.itemthemdiem, null);
                builder.setView(view);

                EditText edtDiemSo;
                Spinner spinnerLoaiDiemSo;
                Button btnThemLoaiDiem, btnHuy, btnThem;

                edtDiemSo = view.findViewById(R.id.edtDiemSo);
                spinnerLoaiDiemSo = view.findViewById(R.id.spinnerLoaiDiemSo);
                btnThemLoaiDiem = view.findViewById(R.id.btnThemLoaiDiem);
                btnThem = view.findViewById(R.id.btnThem);
                btnHuy = view.findViewById(R.id.btnHuy);

                AlertDialog alertDialog = builder.create();
                if (alertDialog.getWindow() != null) {
                    alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_themlophocphan);
                }
                alertDialog.show();

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                loaiDiemList = loaiDiemManager.getAllLoaiDiem();
                if (loaiDiemList == null || loaiDiemList.isEmpty()) {

                    ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(
                            QuanLyDiemvaInforSinhVien.this,
                            android.R.layout.simple_spinner_item,
                            Collections.singletonList("Danh sách loại điểm trống ")
                    );
                    emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerLoaiDiemSo.setAdapter(emptyAdapter);
                    spinnerLoaiDiemSo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    loaiDiemAdapter = new LoaiDiemAdapter(QuanLyDiemvaInforSinhVien.this, R.layout.itemsinhvienselected, loaiDiemList);
                    spinnerLoaiDiemSo.setAdapter(loaiDiemAdapter);
                    spinnerLoaiDiemSo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            maLoaiDiem = loaiDiemAdapter.getItem(position).getMaLoaiDiem();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        trongSoTemp = 0.0;
                        diemSoString = edtDiemSo.getText().toString().trim();
                        if (diemSoString.isEmpty() || maLoaiDiem.isEmpty()) {
                            Toast.makeText(QuanLyDiemvaInforSinhVien.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        diemSo = Double.valueOf(diemSoString);
                        if (diemSo > 10 || diemSo < 0) {
                            Toast.makeText(QuanLyDiemvaInforSinhVien.this, "Điểm phải dưới 10 và lớn hơn 0", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (diemSo == 0.0 || maLoaiDiem.isEmpty()) {
                            Toast.makeText(QuanLyDiemvaInforSinhVien.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String malopsinhvien = lopSinhVienManager.getMaLopSinhVienfromMalopMSSV(lopHocPhan.getMaLop(), sinhVien.getMssv());
                        listDiemTemp = new ArrayList<>();
                        if (diemManager.getDiemfromMalopsinhvien(malopsinhvien) != null) {
                            listDiemTemp = diemManager.getDiemfromMalopsinhvien(malopsinhvien);
                        }
                        for (Diem diem : listDiemTemp) {
                            Double trongSo = loaiDiemManager.getLoaiDiem(diem.getMaLoaiDiem()).getTrongSo();
                            trongSoTemp += trongSo;
                        }
                        if ((loaiDiemManager.getLoaiDiem(maLoaiDiem).getTrongSo() + trongSoTemp) > 1) {
                            Toast.makeText(QuanLyDiemvaInforSinhVien.this, "Trọng số điểm của sinh viên đã vượt mức 1 vui lòng chọn loại điểm khác hoặc thêm mới loại điểm", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String maLopSinhVien = lopSinhVienManager.getMaLopSinhVienfromMalopMSSV(lopHocPhan.getMaLop(), sinhVien.getMssv());
                        int ketqua = (int) diemManager.addDiem(new Diem(diemSo, maLoaiDiem, maLopSinhVien));
                        if (ketqua > 0) {
                            listDiem.add(new Diem(diemSo, maLoaiDiem, maLopSinhVien));
                            txtThongBao.setText("");
                            lopSinhVienAdapter.notifyDataSetChanged();
                            View view = LayoutInflater.from(QuanLyDiemvaInforSinhVien.this).inflate(R.layout.successdialog, null);
                            android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(QuanLyDiemvaInforSinhVien.this);
                            builder1.setView(view);
                            Button successDone = view.findViewById(R.id.successDone);
                            TextView successBelow = view.findViewById(R.id.successBelow);
                            android.app.AlertDialog alertDialog = builder1.create();
                            if (alertDialog.getWindow() != null) {
                                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                                alertDialog.getWindow().setGravity(Gravity.CENTER);
                            }
                            alertDialog.show();
                            successBelow.setText("Thêm điểm thành công!!!");
                            successDone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });
                        } else {
                            View view = LayoutInflater.from(QuanLyDiemvaInforSinhVien.this).inflate(R.layout.faildialog, null);
                            android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(QuanLyDiemvaInforSinhVien.this);
                            builder1.setView(view);
                            Button failDone = view.findViewById(R.id.failDone);
                            TextView failBelow = view.findViewById(R.id.failBelow);
                            android.app.AlertDialog alertDialog = builder1.create();
                            if (alertDialog.getWindow() != null) {
                                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                                alertDialog.getWindow().setGravity(Gravity.CENTER);
                            }
                            alertDialog.show();
                            failBelow.setText("Thêm điểm thất bại!!");
                            failDone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });
                        }
                    }
                });
                btnThemLoaiDiem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(QuanLyDiemvaInforSinhVien.this);
                        View view = LayoutInflater.from(QuanLyDiemvaInforSinhVien.this).inflate(R.layout.itemthemloaidiem, null);
                        builder1.setView(view);

                        EditText edtLoaiDiemSo, edtTrongSo;
                        Button btnHuy, btnThem;

                        edtLoaiDiemSo = view.findViewById(R.id.edtLoaiDiemSo);
                        edtTrongSo = view.findViewById(R.id.edtTrongSo);
                        btnHuy = view.findViewById(R.id.btnHuy);
                        btnThem = view.findViewById(R.id.btnThem);

                        AlertDialog alertDialog1 = builder1.create();
                        if (alertDialog1.getWindow() != null) {
                            alertDialog1.getWindow().setBackgroundDrawableResource(R.drawable.custom_themlophocphan);
                        }
                        alertDialog1.show();

                        btnHuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog1.dismiss();
                            }
                        });
                        btnThem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tenLoaiDiem = edtLoaiDiemSo.getText().toString().trim();
                                trongSo = Double.valueOf(edtTrongSo.getText().toString().trim());
                                maLoaiDiemtemp = loaiDiemManager.getNextLoaiDiemID();
                                if (tenLoaiDiem.isEmpty() || trongSo == 0.0) {
                                    Toast.makeText(QuanLyDiemvaInforSinhVien.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (trongSo > 1 || trongSo < 0) {
                                    Toast.makeText(QuanLyDiemvaInforSinhVien.this, "Trọng số phải bé hơn ", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                int ketqua = (int) loaiDiemManager.addLoaiDiem(new LoaiDiem(maLoaiDiemtemp, tenLoaiDiem, trongSo));
                                if (ketqua > 0) {
                                    loaiDiemList.add(loaiDiemManager.getLoaiDiem(maLoaiDiemtemp));
                                    txtThongBao.setText("");
                                    loaiDiemAdapter.notifyDataSetChanged();
                                    View view = LayoutInflater.from(QuanLyDiemvaInforSinhVien.this).inflate(R.layout.successdialog, null);
                                    android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(QuanLyDiemvaInforSinhVien.this);
                                    builder1.setView(view);
                                    Button successDone = view.findViewById(R.id.successDone);
                                    TextView successBelow = view.findViewById(R.id.successBelow);
                                    android.app.AlertDialog alertDialog = builder1.create();
                                    if (alertDialog.getWindow() != null) {
                                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                                        alertDialog.getWindow().setGravity(Gravity.CENTER);
                                    }
                                    alertDialog.show();
                                    successBelow.setText("Thêm điểm thành công!!!");
                                    successDone.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alertDialog.dismiss();
                                        }
                                    });

                                } else {
                                    View view = LayoutInflater.from(QuanLyDiemvaInforSinhVien.this).inflate(R.layout.faildialog, null);
                                    android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(QuanLyDiemvaInforSinhVien.this);
                                    builder1.setView(view);
                                    Button failDone = view.findViewById(R.id.failDone);
                                    TextView failBelow = view.findViewById(R.id.failBelow);
                                    android.app.AlertDialog alertDialog = builder1.create();
                                    if (alertDialog.getWindow() != null) {
                                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                                        alertDialog.getWindow().setGravity(Gravity.CENTER);
                                    }
                                    alertDialog.show();
                                    failBelow.setText("Thêm điểm thất bại!!");
                                    failDone.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alertDialog.dismiss();
                                        }
                                    });
                                }
                            }
                        });

                    }
                });
            }
        });
        if (diemManager.getDiemfromMalopsinhvien(lopSinhVienManager.getMaLopSinhVienfromMalopMSSV(lopHocPhan.getMaLop(), sinhVien.getMssv())) != null) {
            listDiem = diemManager.getDiemfromMalopsinhvien(lopSinhVienManager.getMaLopSinhVienfromMalopMSSV(lopHocPhan.getMaLop(), sinhVien.getMssv()));
            txtThongBao.setText("");
        } else {
            txtThongBao.setText("Giáo viên chưa nhập điểm");
        }
        lopSinhVienAdapter.setData(listDiem);
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

    public boolean isLoaiDiemExists(String maLoaiDiem) {
        for (LoaiDiem loaiDiem : loaiDiemList) {
            if (loaiDiem.getMaLoaiDiem().equals(maLoaiDiem)) {
                return true;
            }
        }
        return false;
    }
    public void setThongBaoVisibility(boolean isVisible) {
        if (isVisible) {
            txtThongBao.setText("Giáo viên chưa nhập điểm");
            txtThongBao.setVisibility(View.VISIBLE);
        } else {
            txtThongBao.setVisibility(View.GONE);
        }
    }
}
