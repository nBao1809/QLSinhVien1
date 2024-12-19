package com.example.qlsinhvien.Activities;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsinhvien.Adapter.GiangVienAdapter;
import com.example.qlsinhvien.Adapter.LopHanhChinhAdapter;
import com.example.qlsinhvien.Adapter.SinhVienAdapter;
import com.example.qlsinhvien.Adapter.SinhVienbyMaLopHCAdapter;
import com.example.qlsinhvien.Models.LoaiDiem;
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.Models.LopSinhVien;
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

public class QuanLySinhVienActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    RecyclerView recycleLopSinhVien;

    SearchView searchView;
    SinhVienAdapter sinhVienAdapter;
    LopSinhVienManager lopSinhVienManager;
    NganhManager nganhManager;
    UserManager userManager;
    HocKyManager hocKyManager;
    LopHanhChinhManager lopHanhChinhManager;
    SinhVienManager sinhVienManager;
    TextView txtLop, txtThongBao;
    List<String> mssvList;
    List<SinhVien> sinhVienList, sinhVienListSpinner;
    User currentUser;
    SharedPreferences userRefs;
    FloatingActionButton fbtnThem;
    LopHanhChinhAdapter lopHanhChinhAdapter;
    SinhVienbyMaLopHCAdapter sinhVienbyMaLopHCAdapter;
    String maLopHanhChinh, maSinhVien, maHocKy;
    String lopSinhVienID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_sinh_vien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lopSinhVienManager = new LopSinhVienManager(this);
        sinhVienManager = new SinhVienManager(this);
        nganhManager = new NganhManager(this);
        userManager = new UserManager(this);
        hocKyManager = new HocKyManager(this);
        lopHanhChinhManager = new LopHanhChinhManager(this);
        userRefs = getSharedPreferences("currentUser", MODE_PRIVATE);
        currentUser = userManager.getUserByID(userRefs.getInt("ID", -1));
        txtLop = findViewById(R.id.txtLop);
        txtThongBao = findViewById(R.id.txtThongBao);
        fbtnThem = findViewById(R.id.fbtnThem);
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menutoolbar);
        Menu menu = toolbar.getMenu();
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
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.search) {
                    searchView = (SearchView) item.getActionView();
                    SearchManager searchManager = (SearchManager) QuanLySinhVienActivity.this.getSystemService(Context.SEARCH_SERVICE);
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(QuanLySinhVienActivity.this.getComponentName()));

                    assert searchView != null;
                    searchView.setQueryHint("Tìm theo tên hoặc mã sinh viên");

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            sinhVienAdapter.getFilter().filter(query);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            sinhVienAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                    return true;
                }
                return false;
            }

        });

        LopHocPhan lopHocPhan = (LopHocPhan) bundle.get("lopHocPhan");

        txtLop.setText(lopHocPhan.getTenLop());
        Boolean bool = Boolean.TRUE;
        recycleLopSinhVien = findViewById(R.id.recycleSinhVien);
        sinhVienAdapter = new SinhVienAdapter(this, lopHocPhan);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleLopSinhVien.setLayoutManager(linearLayoutManager);
        mssvList = new ArrayList<>();
        sinhVienList = new ArrayList<>();
        sinhVienListSpinner = new ArrayList<>();
        fbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLySinhVienActivity.this);
                View view = LayoutInflater.from(QuanLySinhVienActivity.this).inflate(R.layout.itemthemsinhvienlop, null);
                builder.setView(view);
                Spinner spinnerLopHanhChinh, spinnerSinhVien;
                Button btnHuy, btnThem;

                spinnerLopHanhChinh = view.findViewById(R.id.spinnerLopHanhChinh);
                spinnerSinhVien = view.findViewById(R.id.spinnerSinhVien);
                btnHuy = view.findViewById(R.id.btnHuy);
                btnThem = view.findViewById(R.id.btnThem);

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

                lopHanhChinhAdapter = new LopHanhChinhAdapter(QuanLySinhVienActivity.this, R.layout.itemgiangvienselected, lopHanhChinhManager.getAllLopHanhChinh());
                spinnerLopHanhChinh.setAdapter(lopHanhChinhAdapter);
                spinnerLopHanhChinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maLopHanhChinh = lopHanhChinhAdapter.getItem(position).getMaLopHanhChinh();
                        sinhVienListSpinner = sinhVienManager.getSinhVienByMaLopHanhChinh(maLopHanhChinh);
                        if (sinhVienListSpinner == null || sinhVienListSpinner.isEmpty()) {
                            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(
                                    QuanLySinhVienActivity.this,
                                    android.R.layout.simple_spinner_item,
                                    Collections.singletonList("Không có sinh viên thuộc mã hành chính này")
                            );
                            emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerSinhVien.setAdapter(emptyAdapter);
                            spinnerSinhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            List<SinhVien> updatedList = new ArrayList<>();
                            for (SinhVien sinhVien : sinhVienListSpinner) {
                                if (!mssvList.contains(sinhVien.getMssv())) {
                                    updatedList.add(sinhVien);
                                }
                            }
                            if (updatedList.isEmpty()) {

                                ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(QuanLySinhVienActivity.this,
                                        android.R.layout.simple_spinner_item, Collections.singletonList("Không có sinh viên thuộc mã hành chính này"));
                                emptyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerSinhVien.setAdapter(emptyAdapter);
                            } else {

                                sinhVienListSpinner = updatedList;
                                sinhVienbyMaLopHCAdapter = new SinhVienbyMaLopHCAdapter(
                                        QuanLySinhVienActivity.this,
                                        R.layout.itemsinhvienselected,
                                        sinhVienListSpinner
                                );
                                spinnerSinhVien.setAdapter(sinhVienbyMaLopHCAdapter);
                                spinnerSinhVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        maSinhVien = sinhVienbyMaLopHCAdapter.getItem(position).getMssv();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lopSinhVienID = lopSinhVienManager.getNextLopSinhVienID();
                        maHocKy = getMaHocKy(lopHocPhan.getNgayBatDau());
                        if(mssvList.contains(maSinhVien)){
                            Toast.makeText(QuanLySinhVienActivity.this,"Sinh viên này đã có trong danh sách",Toast.LENGTH_SHORT).show();
                        }
                        int ketqua = (int) lopSinhVienManager.addLopSinhVien(new LopSinhVien(lopSinhVienID, lopHocPhan.getMaLop(), maSinhVien, maHocKy));

                        if (ketqua > 0) {
                            // Thêm sinh viên vào danh sách
                            mssvList.add(maSinhVien);
                            sinhVienList.add(sinhVienManager.getSinhVien(maSinhVien));
                            txtThongBao.setText("");
                            sinhVienAdapter.notifyDataSetChanged();
                            if (sinhVienbyMaLopHCAdapter != null) {
                                sinhVienListSpinner.removeIf(sinhVien -> sinhVien.getMssv().equals(maSinhVien));
                                sinhVienbyMaLopHCAdapter.notifyDataSetChanged();
                                spinnerSinhVien.setAdapter(sinhVienbyMaLopHCAdapter);
                            }

                            // Hiển thị dialog thành công
                            View view = LayoutInflater.from(QuanLySinhVienActivity.this).inflate(R.layout.successdialog, null);
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(QuanLySinhVienActivity.this);
                            builder1.setView(view);
                            Button successDone = view.findViewById(R.id.successDone);
                            TextView successBelow = view.findViewById(R.id.successBelow);
                            AlertDialog alertDialog = builder1.create();
                            if (alertDialog.getWindow() != null) {
                                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                                alertDialog.getWindow().setGravity(Gravity.CENTER);
                            }
                            alertDialog.show();
                            successBelow.setText("Thêm thành công!!!");
                            successDone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });
                        } else {
                            // Hiển thị dialog thất bại
                            View view = LayoutInflater.from(QuanLySinhVienActivity.this).inflate(R.layout.faildialog, null);
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(QuanLySinhVienActivity.this);
                            builder1.setView(view);
                            Button failDone = view.findViewById(R.id.failDone);
                            TextView failBelow = view.findViewById(R.id.failBelow);
                            AlertDialog alertDialog = builder1.create();
                            if (alertDialog.getWindow() != null) {
                                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                                alertDialog.getWindow().setGravity(Gravity.CENTER);
                            }
                            alertDialog.show();
                            failBelow.setText("Thêm thất bại!!");
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

        if (lopSinhVienManager.getMSSVfromMalop(lopHocPhan.getMaLop()) != null && sinhVienManager.getSinhVienByMSSVList(mssvList) != null) {
            mssvList = lopSinhVienManager.getMSSVfromMalop(lopHocPhan.getMaLop());
            sinhVienList = sinhVienManager.getSinhVienByMSSVList(mssvList);
            txtThongBao.setText("");
        } else {
            txtThongBao.setText("Chưa có sinh viên trong lớp");
        }

        sinhVienAdapter.setData(sinhVienList, bool);
        recycleLopSinhVien.setAdapter(sinhVienAdapter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sinhVienAdapter != null) {
            sinhVienAdapter.release();
        }
    }

    public String getMaHocKy(double ngayBatDau) {

        int nam = (int) ngayBatDau;
        double phanThapPhan = ngayBatDau - nam;


        int thang = (int) (phanThapPhan * 100);
        int ngay = (int) ((phanThapPhan * 10000) % 100);


        if (thang >= 9 && thang <= 12) {
            return "HK1";
        } else if (thang >= 1 && thang <= 5) {
            return "HK2";
        } else if (thang >= 6 && thang <= 8) {
            return "HK3";
        }
        return "Unknown";
    }

    public void setThongBaoVisibility(boolean isVisible) {
        if (isVisible) {
            txtThongBao.setText("Không tìm thấy sinh viên");
            txtThongBao.setVisibility(View.VISIBLE);
        } else {
            txtThongBao.setVisibility(View.GONE);
        }
    }

}
