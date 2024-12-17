package com.example.qlsinhvien.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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

import com.example.qlsinhvien.Adapter.DanhSachSinhVienAdapter;
import com.example.qlsinhvien.Adapter.DanhSachSinhVienTamThoiAdapter;
import com.example.qlsinhvien.Adapter.LopHanhChinhAdapter;
import com.example.qlsinhvien.Adapter.LopHocPhanAdapter;
import com.example.qlsinhvien.Adapter.NganhAdapter;
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
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class QuanLyDanhSachSinhVienActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    TextView txtThongBao;
    RecyclerView recycleDSSinhVien;
    DanhSachSinhVienAdapter danhSachSinhVienAdapter;
    SearchView searchView;
    FloatingActionButton fbtnThem;
    LopSinhVienManager lopSinhVienManager;
    NganhManager nganhManager;
    UserManager userManager;
    HocKyManager hocKyManager;
    LopHanhChinhManager lopHanhChinhManager;
    SinhVienManager sinhVienManager;
    List<SinhVien> sinhVienList, sinhVienListTemp;
    LopHanhChinhAdapter lopHanhChinhAdapter;
    NganhAdapter nganhAdapter;
    String MSSV, hoTen, CCCD, taiKhoan, matKhau, xacNhan, email, maLopHanhChinh, maNganh;
    Double ngaySinh = 0.0;
    List<User> userListTemp;
    DanhSachSinhVienTamThoiAdapter danhSachSinhVienTamThoiAdapter;
    int userID, idCuoi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Locale locale = new Locale("vi", "VN");
        Locale.setDefault(locale);

        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);

        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_danh_sach_sinh_vien);
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
        recycleDSSinhVien = findViewById(R.id.recycleDSSinhVien);
        txtThongBao = findViewById(R.id.txtThongBao);
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menuquanlylophoc);
        Menu menu = toolbar.getMenu();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.search) {
                    searchView = (SearchView) item.getActionView();
                    SearchManager searchManager = (SearchManager) QuanLyDanhSachSinhVienActivity.this.getSystemService(Context.SEARCH_SERVICE);
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(QuanLyDanhSachSinhVienActivity.this.getComponentName()));

                    assert searchView != null;
                    searchView.setQueryHint("Tìm theo tên hoặc mã sinh viên");

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            danhSachSinhVienAdapter.getFilter().filter(query);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            danhSachSinhVienAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                    return true;
                }
                return false;
            }

        });
        fbtnThem = findViewById(R.id.fbtnThem);
        fbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyDanhSachSinhVienActivity.this);
                View view = LayoutInflater.from(QuanLyDanhSachSinhVienActivity.this).inflate(R.layout.itemthemsinhvien, null);
                builder.setView(view);
                userListTemp = new ArrayList<>();
                sinhVienListTemp = new ArrayList<>();
                userID = (int) userManager.getLastUserID();
                idCuoi = 0;
                Log.d("test1", String.valueOf(userID));
                EditText edtMSSV, edtHoten, edtCCCD, edtNgaySinh, edtUsername, edtPassWord, edtCommitPW, edtEmail;
                Button btnHuy, btnThem, btnLuu;
                ImageButton ibtnBirthDay;
                RecyclerView recycleDSThem;
                Spinner spinnerLopHanhChinh, spinnerNganh;

                edtMSSV = view.findViewById(R.id.edtMSSV);
                edtHoten = view.findViewById(R.id.edtHoten);
                edtCCCD = view.findViewById(R.id.edtCCCD);
                edtNgaySinh = view.findViewById(R.id.edtNgaySinh);
                edtUsername = view.findViewById(R.id.edtUsername);
                edtPassWord = view.findViewById(R.id.edtPassword);
                edtCommitPW = view.findViewById(R.id.edtCommitPW);
                edtEmail = view.findViewById(R.id.edtEmail);
                btnHuy = view.findViewById(R.id.btnHuy);
                btnThem = view.findViewById(R.id.btnThem);
                btnLuu = view.findViewById(R.id.btnLuu);
                ibtnBirthDay = view.findViewById(R.id.ibtnBirthDay);
                recycleDSThem = view.findViewById(R.id.recycleDSThem);
                spinnerLopHanhChinh = view.findViewById(R.id.spinnerLopHanhChinh);
                spinnerNganh = view.findViewById(R.id.spinnerNganh);

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
                ibtnBirthDay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMaterialDatePicker2(edtNgaySinh);
                    }
                });
                lopHanhChinhAdapter = new LopHanhChinhAdapter(QuanLyDanhSachSinhVienActivity.this, R.layout.itemgiangvienselected, lopHanhChinhManager.getAllLopHanhChinh());
                spinnerLopHanhChinh.setAdapter(lopHanhChinhAdapter);
                spinnerLopHanhChinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maLopHanhChinh = lopHanhChinhAdapter.getItem(position).getMaLopHanhChinh();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                nganhAdapter = new NganhAdapter(QuanLyDanhSachSinhVienActivity.this, R.layout.itemgiangvienselected, nganhManager.getAllNganh());
                spinnerNganh.setAdapter(nganhAdapter);
                spinnerNganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maNganh = nganhAdapter.getItem(position).getTenNganh();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MSSV = edtMSSV.getText().toString().trim();
                        hoTen = edtHoten.getText().toString().trim();
                        CCCD = edtCCCD.getText().toString().trim();
                        taiKhoan = edtUsername.getText().toString().trim();
                        matKhau = edtPassWord.getText().toString().trim();
                        xacNhan = edtCommitPW.getText().toString().trim();
                        email = edtEmail.getText().toString().trim();


                        if (MSSV.isEmpty() || hoTen.isEmpty() || CCCD.isEmpty() || ngaySinh == 0.0 ||
                                taiKhoan.isEmpty() || matKhau.isEmpty() || xacNhan.isEmpty() || email.isEmpty() || maNganh == null || maLopHanhChinh == null) {
                            Toast.makeText(QuanLyDanhSachSinhVienActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            Toast.makeText(QuanLyDanhSachSinhVienActivity.this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        if (!matKhau.equals(xacNhan)) {
                            Toast.makeText(QuanLyDanhSachSinhVienActivity.this, "Mật khẩu và xác nhận mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        userListTemp.add(new User(taiKhoan, matKhau, BitmapFactory.decodeResource(getResources(), R.drawable.avatarsample), email, "SinhVien"));
                        sinhVienListTemp.add(new SinhVien(MSSV, hoTen, CCCD, ngaySinh, ++userID, maLopHanhChinh, maNganh));
                        Log.d("test", String.valueOf(sinhVienListTemp.get(sinhVienListTemp.size() - 1).getId()));
                        danhSachSinhVienTamThoiAdapter = new DanhSachSinhVienTamThoiAdapter(QuanLyDanhSachSinhVienActivity.this);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuanLyDanhSachSinhVienActivity.this, RecyclerView.VERTICAL, false);
                        recycleDSThem.setLayoutManager(linearLayoutManager);
                        danhSachSinhVienTamThoiAdapter.setData(sinhVienListTemp,userListTemp);
                        recycleDSThem.setAdapter(danhSachSinhVienTamThoiAdapter);
                        Toast.makeText(QuanLyDanhSachSinhVienActivity.this, "Thêm thành công", Toast.LENGTH_SHORT);
                        danhSachSinhVienTamThoiAdapter.notifyDataSetChanged();
                        idCuoi = sinhVienListTemp.get(sinhVienListTemp.size() - 1).getId();
                        edtMSSV.setText("");
                        edtMSSV.requestFocus();
                        edtHoten.setText("");
                        edtCCCD.setText("");
                        edtNgaySinh.setText("");
                        edtUsername.setText("");
                        edtPassWord.setText("");
                        edtCommitPW.setText("");
                        edtEmail.setText("");

                    }
                });
                btnLuu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sinhVienListTemp.size() > 0) {
                            userID = idCuoi;
                            Log.d("test3", String.valueOf(userID));
                            for (User user : userListTemp) {
                                userManager.addUser(user);
                            }
                            for (SinhVien sinhVien : sinhVienListTemp) {
                                sinhVienManager.addSinhVien(sinhVien);
                                sinhVienList.add(sinhVien);
                            }
                            danhSachSinhVienAdapter.notifyDataSetChanged();

                            View view = LayoutInflater.from(QuanLyDanhSachSinhVienActivity.this).inflate(R.layout.successdialog, null);
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(QuanLyDanhSachSinhVienActivity.this);
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
                            sinhVienListTemp.clear();
                            userListTemp.clear();
                            danhSachSinhVienTamThoiAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(QuanLyDanhSachSinhVienActivity.this, "Bạn chưa thêm sinh viên", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        sinhVienList = sinhVienManager.getAllSinhVien();
        if (sinhVienList == null || sinhVienList.isEmpty()) {
            txtThongBao.setText("Danh sách sinh viên trống");
            return;
        }
        danhSachSinhVienAdapter = new DanhSachSinhVienAdapter(this,QuanLyDanhSachSinhVienActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleDSSinhVien.setLayoutManager(linearLayoutManager);
        danhSachSinhVienAdapter.setData(sinhVienList);
        recycleDSSinhVien.setAdapter(danhSachSinhVienAdapter);
    }

    public void setThongBaoVisibility(boolean isVisible) {
        if (isVisible) {
            txtThongBao.setText("Không tìm thấy sinh viên");
            txtThongBao.setVisibility(View.VISIBLE);
        } else {
            txtThongBao.setVisibility(View.GONE);
        }
    }


    private void showMaterialDatePicker(final EditText edtNgay) {

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, Calendar.JANUARY, 1);
        constraintsBuilder.setStart(startDate.getTimeInMillis());
        constraintsBuilder.setEnd(System.currentTimeMillis());

        // Tạo MaterialDatePicker
        MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày sinh")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .build();

        // Thiết lập Listener để nhận ngày được chọn
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            // Chuyển đổi long value thành ngày
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.setTimeInMillis(selection);

            // Cập nhật EditText với ngày đã chọn
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            edtNgay.setText(dateFormat.format(selectedDate.getTime()));

            // Cập nhật giá trị ngày sinh (nếu cần)
            ngaySinh = Double.parseDouble(String.format(Locale.getDefault(), "%d.%02d%02d", selectedDate.get(Calendar.YEAR),
                    selectedDate.get(Calendar.MONTH) + 1, selectedDate.get(Calendar.DAY_OF_MONTH)));
        });

        // Hiển thị MaterialDatePicker
        materialDatePicker.show(getSupportFragmentManager(), materialDatePicker.toString());
    }
    private void showMaterialDatePicker2(final EditText edtNgay) {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, Calendar.JANUARY, 1);
        constraintsBuilder.setStart(startDate.getTimeInMillis());
        constraintsBuilder.setEnd(System.currentTimeMillis());

        // Tạo MaterialDatePicker
        MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày sinh")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .build();

        // Thiết lập Listener để nhận ngày được chọn
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            // Chuyển đổi long value thành ngày
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.setTimeInMillis(selection);

            // Cập nhật EditText với ngày đã chọn
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            edtNgay.setText(dateFormat.format(selectedDate.getTime()));

            // Chuyển ngày từ long thành double theo định dạng yyyy.MMdd
            Double ngaySinhTam = Double.parseDouble(String.format(Locale.getDefault(), "%d.%02d%02d",
                    selectedDate.get(Calendar.YEAR),
                    selectedDate.get(Calendar.MONTH) + 1,
                    selectedDate.get(Calendar.DAY_OF_MONTH)));

            // Cập nhật giá trị ngày sinh
            ngaySinh = ngaySinhTam;
        });

        // Hiển thị MaterialDatePicker
        materialDatePicker.show(QuanLyDanhSachSinhVienActivity.this.getSupportFragmentManager(), materialDatePicker.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (danhSachSinhVienAdapter != null) {
            danhSachSinhVienAdapter.release();
        }
    }

}