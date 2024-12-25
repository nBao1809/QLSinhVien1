package com.example.qlsinhvien.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import com.example.qlsinhvien.Adapter.LopHocPhanAdapter;
import com.example.qlsinhvien.Adapter.SinhVienAdapter;
import com.example.qlsinhvien.Models.HocKy;
import com.example.qlsinhvien.Models.LopHanhChinh;
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.Models.LopSinhVien;
import com.example.qlsinhvien.Models.Nganh;
import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.GiangVienManager;
import com.example.qlsinhvien.dao.HocKyManager;
import com.example.qlsinhvien.dao.LopHanhChinhManager;
import com.example.qlsinhvien.dao.LopHocPhanManager;
import com.example.qlsinhvien.dao.LopSinhVienManager;
import com.example.qlsinhvien.dao.MonHocManager;
import com.example.qlsinhvien.dao.NganhManager;
import com.example.qlsinhvien.dao.SinhVienManager;
import com.example.qlsinhvien.dao.UserManager;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class DanhSachLopSinhVienActivity extends AppCompatActivity {
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
    List<SinhVien> sinhVienList;
    User currentUser;
    SharedPreferences userRefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_sach_lop_sinh_vien);
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
        userRefs = this.getSharedPreferences("currentUser", MODE_PRIVATE);
        currentUser = userManager.getUserByID(userRefs.getInt("ID", -1));
//        lopHanhChinhManager.addLopHanhChinh(new LopHanhChinh("1","CS2202"));
//        nganhManager.addNganh(new Nganh("1","Khoa hoc may tinh"));
//        sinhVienManager.addSinhVien(new SinhVien("223","Tester","225101",2004.0102,2,"1","1"));
//        hocKyManager.addHocKy(new HocKy("1","2","2024"));
//        lopSinhVienManager.addLopSinhVien(new LopSinhVien("1","LOP1","223","1"));

        txtLop = findViewById(R.id.txtLop);
        txtThongBao = findViewById(R.id.txtThongBao);

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

        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.search) {
                    searchView = (SearchView) item.getActionView();
                    SearchManager searchManager = (SearchManager) DanhSachLopSinhVienActivity.this.getSystemService(Context.SEARCH_SERVICE);
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(DanhSachLopSinhVienActivity.this.getComponentName()));

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
                if (id == R.id.notification) {
                    Toast.makeText(DanhSachLopSinhVienActivity.this, "Chưa có chức năng", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }

        });


        LopHocPhan lopHocPhan = (LopHocPhan) bundle.get("lopHocPhan");
        assert lopHocPhan != null;
        txtLop.setText(lopHocPhan.getTenLop());
        mssvList = lopSinhVienManager.getMSSVfromMalop(lopHocPhan.getMaLop());
        if (mssvList == null || mssvList.isEmpty()) {
            txtThongBao.setText("Danh sách sinh viên trống");

        }
        sinhVienList = sinhVienManager.getSinhVienByMSSVList(mssvList);
        if (sinhVienList == null || sinhVienList.isEmpty()) {
            txtThongBao.setText("Danh sách sinh viên trống");

            recycleLopSinhVien = findViewById(R.id.recycleSinhVien);
            sinhVienAdapter = new SinhVienAdapter(this, lopHocPhan);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            recycleLopSinhVien.setLayoutManager(linearLayoutManager);
            Boolean bool = Boolean.FALSE;
            sinhVienAdapter.setData(sinhVienList, bool);
            recycleLopSinhVien.setAdapter(sinhVienAdapter);


        }



    } public void setThongBaoVisibility( boolean isVisible){
        if (isVisible) {
            txtThongBao.setText("Không tìm thấy sinh viên");
            txtThongBao.setVisibility(View.VISIBLE);
        } else {
            txtThongBao.setVisibility(View.GONE);
        }
    }
}