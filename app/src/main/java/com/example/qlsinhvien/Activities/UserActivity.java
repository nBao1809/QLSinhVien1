package com.example.qlsinhvien.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.example.qlsinhvien.Adapter.UserAdapter;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.UserManager;
import com.google.android.material.appbar.MaterialToolbar;

public class UserActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    SearchView searchView;
    RecyclerView recycleUserAdmin, recycleUserMod, recycleUserGV, recycleUserSV;
    UserAdapter adminAdapter, modAdapter, gvAdapter, svAdapter;
    UserManager userManager;
    LinearLayout layoutAdmin, layoutMod, layoutGV, layoutSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar = findViewById(R.id.toolbaruser);
        userManager = new UserManager(this);
        adminAdapter = new UserAdapter();
        modAdapter = new UserAdapter();
        gvAdapter = new UserAdapter();
        svAdapter = new UserAdapter();
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        LinearLayoutManager linearLayoutAdmin = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutMod = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutGV = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);

        LinearLayoutManager linearLayoutSV = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);

        recycleUserAdmin = findViewById(R.id.recycleUserAdmin);
        recycleUserAdmin.setLayoutManager(linearLayoutAdmin);
        adminAdapter.setData(userManager.getUsersByRole("admin"));
        recycleUserAdmin.setAdapter(adminAdapter);

        recycleUserMod = findViewById(R.id.recycleUserMod);
        recycleUserMod.setLayoutManager(linearLayoutMod);
        modAdapter.setData(userManager.getUsersByRole("mod"));
        recycleUserMod.setAdapter(modAdapter);

        recycleUserGV = findViewById(R.id.recycleUserGiangVien);
        recycleUserGV.setLayoutManager(linearLayoutGV);
        gvAdapter.setData(userManager.getUsersByRole("gv"));
        recycleUserGV.setAdapter(gvAdapter);

        recycleUserSV = findViewById(R.id.recycleUserSinhVien);
        recycleUserSV.setLayoutManager(linearLayoutSV);
        svAdapter.setData(userManager.getUsersByRole("sv"));
        recycleUserSV.setAdapter(svAdapter);

        layoutAdmin = findViewById(R.id.Admin);
        layoutMod = findViewById(R.id.Mod);
        layoutGV = findViewById(R.id.Giangvien);
        layoutSV = findViewById(R.id.SinhVien);

        layoutAdmin.setOnClickListener(v -> {
            ImageView image = findViewById(R.id.admin);
            if (recycleUserAdmin.getVisibility() == View.VISIBLE) {
                recycleUserAdmin.setVisibility(View.GONE);
                image.setBackgroundResource(R.drawable.chevron_down);
            } else {
                recycleUserAdmin.setVisibility(View.VISIBLE);
                image.setBackgroundResource(R.drawable.chevron_left);
            }
        });
        layoutMod.setOnClickListener(v -> {
            ImageView image = findViewById(R.id.mod);
            if (recycleUserMod.getVisibility() == View.VISIBLE) {
                recycleUserMod.setVisibility(View.GONE);
                image.setBackgroundResource(R.drawable.chevron_down);
            } else {
                recycleUserMod.setVisibility(View.VISIBLE);
                image.setBackgroundResource(R.drawable.chevron_left);
            }
        });
        layoutGV.setOnClickListener(v -> {
            ImageView image = findViewById(R.id.giangvien);
            if (recycleUserGV.getVisibility() == View.VISIBLE) {
                recycleUserGV.setVisibility(View.GONE);
                image.setBackgroundResource(R.drawable.chevron_down);
            } else {
                recycleUserGV.setVisibility(View.VISIBLE);
                image.setBackgroundResource(R.drawable.chevron_left);
            }
        });
        layoutSV.setOnClickListener(v -> {
            ImageView image = findViewById(R.id.sinhvien);
            if (recycleUserSV.getVisibility() == View.VISIBLE) {
                recycleUserSV.setVisibility(View.GONE);
                image.setBackgroundResource(R.drawable.chevron_down);
            } else {
                recycleUserSV.setVisibility(View.VISIBLE);
                image.setBackgroundResource(R.drawable.chevron_left);
            }
        });

        toolbar.inflateMenu(R.menu.menutoolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.search) {
                    searchView = (SearchView) item.getActionView();
                    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

                    if (searchView != null) {
                        searchView.setQueryHint("Tìm tên tài khoản");
                    }

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            adminAdapter.getFilter().filter(query);
                            modAdapter.getFilter().filter(query);
                            gvAdapter.getFilter().filter(query);
                            svAdapter.getFilter().filter(query);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            adminAdapter.getFilter().filter(newText);
                            modAdapter.getFilter().filter(newText);
                            gvAdapter.getFilter().filter(newText);
                            svAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                    return true;
                }
                if (id == R.id.notification) {
                    Toast.makeText(UserActivity.this, "Chưa có chức năng", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }

        });
    }
}