package com.example.qlsinhvien.Activities;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.example.qlsinhvien.Adapter.DanhSachUserTamThoiAdapter;
import com.example.qlsinhvien.Adapter.RoleAdapter;
import com.example.qlsinhvien.Adapter.UserAdapter;
import com.example.qlsinhvien.Models.Role;
import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.RoleManager;
import com.example.qlsinhvien.dao.UserManager;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class QuanLyUserActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    SearchView searchView;
    RecyclerView recycleUserAdmin, recycleUserMod, recycleUserGV, recycleUserSV;
    UserAdapter adminAdapter, modAdapter, gvAdapter, svAdapter;
    UserManager userManager;
    LinearLayout layoutAdmin, layoutMod, layoutGV, layoutSV;

    User currentUser;
    DanhSachUserTamThoiAdapter danhSachUserTamThoiAdapter;
    ImageButton fbtnThem;
    List<User> userAdminList, userModList, userGVList, userSVList, userListTemp;
    RoleAdapter roleAdapter;
    RoleManager roleManager;
    int userID, idCuoi;
    String role, taiKhoan, matKhau, xacNhan, email;

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
        userManager = new UserManager(this);
        Intent intent = getIntent();
        roleManager = new RoleManager(this);
        currentUser = userManager.getUserByID(intent.getIntExtra("ID", -1));
        toolbar = findViewById(R.id.toolbaruser);
        toolbar.inflateMenu(R.menu.menuuser);

        adminAdapter = new UserAdapter(this, currentUser);
        modAdapter = new UserAdapter(this, currentUser);
        gvAdapter = new UserAdapter(this, currentUser);
        svAdapter = new UserAdapter(this, currentUser);
        danhSachUserTamThoiAdapter = new DanhSachUserTamThoiAdapter(this);
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
        userAdminList = userManager.getUsersByRole("admin");
        adminAdapter.setData(userAdminList);
        recycleUserAdmin.setAdapter(adminAdapter);

        recycleUserMod = findViewById(R.id.recycleUserMod);
        recycleUserMod.setLayoutManager(linearLayoutMod);
        userModList = userManager.getUsersByRole("mod");
        modAdapter.setData(userModList);
        recycleUserMod.setAdapter(modAdapter);

        recycleUserGV = findViewById(R.id.recycleUserGiangVien);
        recycleUserGV.setLayoutManager(linearLayoutGV);
        userGVList = userManager.getUsersByRole("gv");
        gvAdapter.setData(userGVList);
        recycleUserGV.setAdapter(gvAdapter);

        recycleUserSV = findViewById(R.id.recycleUserSinhVien);
        recycleUserSV.setLayoutManager(linearLayoutSV);
        userSVList = userManager.getUsersByRole("sv");
        svAdapter.setData(userSVList);
        recycleUserSV.setAdapter(svAdapter);

        layoutAdmin = findViewById(R.id.Admin);
        layoutMod = findViewById(R.id.Mod);
        layoutGV = findViewById(R.id.Giangvien);
        layoutSV = findViewById(R.id.SinhVien);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                    Toast.makeText(QuanLyUserActivity.this, "Chưa có chức năng", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }

        });
        fbtnThem = findViewById(R.id.fbtnThem);

        fbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyUserActivity.this);
                View view = LayoutInflater.from(QuanLyUserActivity.this).inflate(R.layout.itemthemuser, null);
                builder.setView(view);
                userListTemp = new ArrayList<>();
                userID = (int) userManager.getLastUserID();
                idCuoi = 0;
                Log.d("test1", String.valueOf(userID));
                EditText edtUsername, edtPassWord, edtCommitPW, edtEmail;
                Button btnHuy, btnThem, btnLuu;
                RecyclerView recycleDSThem;
                Spinner spinnerRole;

                edtUsername = view.findViewById(R.id.edtUsername);
                edtPassWord = view.findViewById(R.id.edtPassword);
                edtCommitPW = view.findViewById(R.id.edtCommitPW);
                edtEmail = view.findViewById(R.id.edtEmail);
                btnHuy = view.findViewById(R.id.btnHuy);
                btnThem = view.findViewById(R.id.btnThem);
                btnLuu = view.findViewById(R.id.btnLuu);
                recycleDSThem = view.findViewById(R.id.recycleDSThem);
                spinnerRole = view.findViewById(R.id.spinnerRole);

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

                List<Role> roleList = roleManager.getAllRole();

                roleList.removeIf(role -> role.getMaRole().equals("sv") || role.getMaRole().equals("gv") || role.getMaRole().equals("superadmin"));


                roleAdapter = new RoleAdapter(QuanLyUserActivity.this,
                        R.layout.itemgiangvienselected, roleList
                );
                spinnerRole.setAdapter(roleAdapter);
                spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        role = roleAdapter.getItem(position).getMaRole();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        taiKhoan = edtUsername.getText().toString().trim();
                        matKhau = edtPassWord.getText().toString().trim();
                        xacNhan = edtCommitPW.getText().toString().trim();
                        email = edtEmail.getText().toString().trim();


                        if (taiKhoan.isEmpty() || matKhau.isEmpty() || xacNhan.isEmpty() || email.isEmpty() || role == null) {
                            Toast.makeText(QuanLyUserActivity.this, "Vui lòng nhập đầy đủ thông tin!",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }


                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            Toast.makeText(QuanLyUserActivity.this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        if (!matKhau.equals(xacNhan)) {
                            Toast.makeText(QuanLyUserActivity.this, "Mật khẩu và xác nhận mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        userListTemp.add(new User(taiKhoan, matKhau,
                                BitmapFactory.decodeResource(getResources(),
                                        R.drawable.avatarsample), email, role));
                        danhSachUserTamThoiAdapter =
                                new DanhSachUserTamThoiAdapter(QuanLyUserActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuanLyUserActivity.this, RecyclerView.VERTICAL, false);
                        recycleDSThem.setLayoutManager(linearLayoutManager);
                        danhSachUserTamThoiAdapter.setData(userListTemp);
                        recycleDSThem.setAdapter(danhSachUserTamThoiAdapter);
                        Toast.makeText(QuanLyUserActivity.this, "Thêm thành công", Toast.LENGTH_SHORT);
                        danhSachUserTamThoiAdapter.notifyDataSetChanged();
                        idCuoi = userListTemp.get(userListTemp.size() - 1).getID();
                        edtUsername.setText("");
                        edtPassWord.setText("");
                        edtCommitPW.setText("");
                        edtEmail.setText("");

                    }
                });
                btnLuu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (userListTemp.size() > 0) {
                            userID = idCuoi;
                            Log.d("test3", String.valueOf(userID));
                            for (User user : userListTemp) {
                                int idInserted = (int) userManager.addUser(user);
                                User user1 = userManager.getUserByID(idInserted);
                                if (user1.getRole().equals("admin")) {

                                    userAdminList.add(user1);
                                } else if (user1.getRole().equals("mod")) {
                                    userModList.add(user1);
                                }
                            }
                            adminAdapter.notifyDataSetChanged();
                            modAdapter.notifyDataSetChanged();
                            gvAdapter.notifyDataSetChanged();
                            svAdapter.notifyDataSetChanged();

                            View view =
                                    LayoutInflater.from(QuanLyUserActivity.this).inflate(R.layout.successdialog, null);
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(QuanLyUserActivity.this);
                            builder1.setView(view);
                            Button successDone = view.findViewById(R.id.successDone);
                            TextView sucessBelow = view.findViewById(R.id.successBelow);
                            AlertDialog alertDialog = builder1.create();
                            if (alertDialog.getWindow() != null) {
                                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                                alertDialog.getWindow().setGravity(Gravity.CENTER);
                            }
                            alertDialog.show();
                            sucessBelow.setText("Lưu danh sách sinh viên thành công");
                            successDone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });
                            userListTemp.clear();
                            danhSachUserTamThoiAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(QuanLyUserActivity.this, "Bạn chưa thêm sinh viên", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });

    }

}