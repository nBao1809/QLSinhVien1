package com.example.qlsinhvien.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlsinhvien.Adapter.SinhVienAdapter;
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
    List<SinhVien> sinhVienList;
    User currentUser;
    SharedPreferences userRefs;

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
        assert lopHocPhan != null;
        txtLop.setText(lopHocPhan.getTenLop());
        mssvList = lopSinhVienManager.getMSSVfromMalop(lopHocPhan.getMaLop());
        if (mssvList == null || mssvList.isEmpty()) {
            txtThongBao.setText("Danh sách sinh viên trống");
            return;
        }
        sinhVienList = sinhVienManager.getSinhVienByMSSVList(mssvList);
        if (sinhVienList == null || sinhVienList.isEmpty()) {
            txtThongBao.setText("Danh sách sinh viên trống");
            return;
        }
        Boolean bool = Boolean.TRUE;
        recycleLopSinhVien = findViewById(R.id.recycleSinhVien);
        sinhVienAdapter = new SinhVienAdapter(this, lopHocPhan);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleLopSinhVien.setLayoutManager(linearLayoutManager);
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

    public void setThongBaoVisibility(boolean isVisible) {
        if (isVisible) {
            txtThongBao.setText("Không tìm thấy sinh viên");
            txtThongBao.setVisibility(View.VISIBLE);
        } else {
            txtThongBao.setVisibility(View.GONE);
        }
    }
}
