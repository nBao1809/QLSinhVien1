package com.example.qlsinhvien.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import com.example.qlsinhvien.Adapter.GiangVienAdapter;
import com.example.qlsinhvien.Adapter.LopHocPhanAdapter;
import com.example.qlsinhvien.Adapter.MonHocAdapter;
import com.example.qlsinhvien.Models.GiangVien;
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.DatabaseHelper;
import com.example.qlsinhvien.dao.GiangVienManager;
import com.example.qlsinhvien.dao.LopHocPhanManager;
import com.example.qlsinhvien.dao.MonHocManager;
import com.example.qlsinhvien.dao.NganhManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class QuanLyLopHocActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    TextView txtThongBao;
    RecyclerView recycleHocPhan;
    LopHocPhanAdapter lopHocPhanAdapter;
    SearchView searchView;
    FloatingActionButton fbtnThem;
    LopHocPhanManager lopHocPhanManager;
    MonHocManager monHocManager;
    GiangVienManager giangVienManager;
    NganhManager nganhManager;
    Double ngayBatDau = 0.0;
    Double ngayKetthuc = 0.0;
    String maMonHoc, maGiangVien, maLop, tenLop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale locale = new Locale("vi", "VN");
        Locale.setDefault(locale);

        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);

        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_lop_hoc);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtThongBao = findViewById(R.id.txtThongBao);
        recycleHocPhan = findViewById(R.id.recycleHocPhan);

        lopHocPhanManager = new LopHocPhanManager(this);
        giangVienManager = new GiangVienManager(this);
        monHocManager = new MonHocManager(this);
        nganhManager = new NganhManager(this);
        fbtnThem = findViewById(R.id.fbtnThem);
        toolbar = findViewById(R.id.toolbar);
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
                    SearchManager searchManager = (SearchManager) QuanLyLopHocActivity.this.getSystemService(Context.SEARCH_SERVICE);
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(QuanLyLopHocActivity.this.getComponentName()));

                    assert searchView != null;
                    searchView.setQueryHint("Tìm theo tên hoặc môn học");

                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            lopHocPhanAdapter.getFilter().filter(query);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            lopHocPhanAdapter.getFilter().filter(newText);
                            return false;
                        }
                    });
                    return true;
                }
                return false;
            }
        });
        Boolean bool = Boolean.TRUE;
        lopHocPhanAdapter = new LopHocPhanAdapter(this, QuanLyLopHocActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycleHocPhan.setLayoutManager(linearLayoutManager);
        List<LopHocPhan> lopHocPhan = lopHocPhanManager.getAllLopHocPhan();
        if (lopHocPhan == null || lopHocPhan.isEmpty()) {


        }
        lopHocPhanAdapter.setData(lopHocPhan, bool);
        recycleHocPhan.setAdapter(lopHocPhanAdapter);
        fbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuanLyLopHocActivity.this);
                View view = LayoutInflater.from(QuanLyLopHocActivity.this).inflate(R.layout.themlophocphan, null);
                builder.setView(view);
                EditText edtMaLop, edtTenLop, edtNgayBatDau, edtNgayKetThuc;
                ImageButton ibtnStartday, ibtnEndDay;
                Spinner spinnerGiangVien, spinnerMonHoc;
                Button btnHuy, btnThem;
                edtNgayBatDau = view.findViewById(R.id.edtNgayBatDau);
                edtNgayKetThuc = view.findViewById(R.id.edtNgayKetThuc);
                edtMaLop = view.findViewById(R.id.edtMaLop);
                edtTenLop = view.findViewById(R.id.edtTenLop);
                ibtnStartday = view.findViewById(R.id.ibtnStartday);
                ibtnEndDay = view.findViewById(R.id.ibtnEndDay);
                spinnerGiangVien = view.findViewById(R.id.spinnerGiangVien);
                spinnerMonHoc = view.findViewById(R.id.spinnerMonHoc);
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



                ibtnStartday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showDatePickerDialog(edtNgayBatDau, true);

                    }
                });
                ibtnEndDay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog(edtNgayKetThuc, false);

                    }
                });
                GiangVienAdapter adapter = new GiangVienAdapter(QuanLyLopHocActivity.this, R.layout.itemgiangvienselected, giangVienManager.getAllGiangVien());
                spinnerGiangVien.setAdapter(adapter);
                spinnerGiangVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maGiangVien = adapter.getItem(position).getMaGiangVien();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                MonHocAdapter monHocAdapter = new MonHocAdapter(QuanLyLopHocActivity.this, R.layout.itemgiangvienselected, monHocManager.getAllMonHoc());
                spinnerMonHoc.setAdapter(monHocAdapter);
                spinnerMonHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maMonHoc = monHocAdapter.getItem(position).getMaMonHoc();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        maLop = edtMaLop.getText().toString().trim();
                        tenLop = edtTenLop.getText().toString().trim();
                        if (maLop.isEmpty() || tenLop.isEmpty() || ngayBatDau == 0.0 || ngayKetthuc == 0.0 || maMonHoc == null || maGiangVien == null) {
                            Toast.makeText(QuanLyLopHocActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            lopHocPhanManager.addLopHocPhan(new LopHocPhan(maLop, tenLop, ngayBatDau, ngayKetthuc, maMonHoc, maGiangVien));
                            lopHocPhan.add(new LopHocPhan(maLop, tenLop, ngayBatDau, ngayKetthuc, maMonHoc, maGiangVien));
                            lopHocPhanAdapter.notifyDataSetChanged();
                            Toast.makeText(QuanLyLopHocActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            edtMaLop.setText("");
                            edtTenLop.setText("");
                            edtNgayBatDau.setText("");
                            edtNgayKetThuc.setText("");
                            edtMaLop.requestFocus();
                        }
                    }
                });
            }
        });
    }

    private void showDatePickerDialog(final EditText edtNgay, final Boolean isStartDay) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                edtNgay.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);

                        // Cập nhật ngày cho EditText
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        edtNgay.setText(dateFormat.format(selectedDate.getTime()));

                        if (isStartDay) {
                            // Nếu là ngày bắt đầu, lưu ngày bắt đầu
                            ngayBatDau = Double.parseDouble(String.format(Locale.getDefault(), "%d.%02d%02d", year, month + 1, dayOfMonth));
                        } else {
                            if (ngayBatDau == 0.0) {
                                Toast.makeText(QuanLyLopHocActivity.this, "Bạn phải chọn ngày bắt đầu trước", Toast.LENGTH_SHORT).show();
                                edtNgay.setText("");
                            } else {
                                Double ngayTam = Double.parseDouble(String.format(Locale.getDefault(), "%d.%02d%02d", year, month + 1, dayOfMonth));
                                if (ngayTam < ngayBatDau) {
                                    Toast.makeText(QuanLyLopHocActivity.this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", Toast.LENGTH_SHORT).show();
                                    edtNgay.setText("");
                                    edtNgay.setError("");
                                } else {
                                    edtNgay.setError(null); // Xóa lỗi khi ngày hợp lệ
                                    ngayKetthuc = Double.parseDouble(String.format(Locale.getDefault(), "%d.%02d%02d", year, month + 1, dayOfMonth));
                                }
                            }
                        }
                    }
                },
                year, month, day
        );

        // Thiết lập ngày tối thiểu cho DatePicker (ngày hiện tại)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        // Nếu đã có ngày trong EditText, cập nhật DatePicker với ngày hiện tại trong EditText
        String currentDate = edtNgay.getText().toString();
        if (!currentDate.isEmpty()) {
            try {
                String[] parts = currentDate.split("/");
                int existingDay = Integer.parseInt(parts[0]);
                int existingMonth = Integer.parseInt(parts[1]) - 1;  // Chuyển đổi tháng về chỉ số từ 0
                int existingYear = Integer.parseInt(parts[2]);
                datePickerDialog.updateDate(existingYear, existingMonth, existingDay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        datePickerDialog.show();
    }


    public void setThongBaoVisibility(boolean isVisible) {
        if (isVisible) {
            txtThongBao.setText("Không tìm thấy lớp học phần");
            txtThongBao.setVisibility(View.VISIBLE);
        } else {
            txtThongBao.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (lopHocPhanAdapter != null) {
            lopHocPhanAdapter.release();
        }
    }

}
