package com.example.qlsinhvien.Activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qlsinhvien.Adapter.LopHanhChinhAdapter;
import com.example.qlsinhvien.Adapter.SinhVienAdapter;
import com.example.qlsinhvien.Adapter.SinhVienbyMaLopHCAdapter;
import com.example.qlsinhvien.Models.CustomThongKe;
import com.example.qlsinhvien.Models.Diem;
import com.example.qlsinhvien.Models.SinhVien;
import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.DiemManager;
import com.example.qlsinhvien.dao.HocKyManager;
import com.example.qlsinhvien.dao.LoaiDiemManager;
import com.example.qlsinhvien.dao.LopHanhChinhManager;
import com.example.qlsinhvien.dao.LopHocPhanManager;
import com.example.qlsinhvien.dao.LopSinhVienManager;
import com.example.qlsinhvien.dao.MonHocManager;
import com.example.qlsinhvien.dao.NganhManager;
import com.example.qlsinhvien.dao.SinhVienManager;
import com.example.qlsinhvien.dao.UserManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AverageScoreActivity extends AppCompatActivity {
    Spinner spinnerLopHanhChinh, spinnerSinhVien;
    SinhVienAdapter sinhVienAdapter;
    LopSinhVienManager lopSinhVienManager;
    NganhManager nganhManager;
    UserManager userManager;
    HocKyManager hocKyManager;
    LopHanhChinhManager lopHanhChinhManager;
    SinhVienManager sinhVienManager;
    LoaiDiemManager loaiDiemManager;
    TextView txtLop, txtThongBao;
    List<String> maLopList, maLopSinhVienList;
    List<Diem> diem;
    List<SinhVien> sinhVienList, sinhVienListSpinner;
    User currentUser;
    SharedPreferences userRefs;
    FloatingActionButton fbtnThem;
    LopHanhChinhAdapter lopHanhChinhAdapter;
    MonHocManager monHocManager;
    SinhVienbyMaLopHCAdapter sinhVienbyMaLopHCAdapter;
    String maLopHanhChinh, maSinhVien;
    Button btnXem;

    MaterialToolbar toolbar;
    LopHocPhanManager lopHocPhanManager;

    DiemManager diemManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_average_score);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtThongBao = findViewById(R.id.txtThongBao);
        BarChart barChart = findViewById(R.id.barChart);
        btnXem = findViewById(R.id.btnXem);
        sinhVienManager = new SinhVienManager(AverageScoreActivity.this);
        lopHocPhanManager = new LopHocPhanManager(AverageScoreActivity.this);
        monHocManager = new MonHocManager(AverageScoreActivity.this);
        lopHanhChinhManager = new LopHanhChinhManager(AverageScoreActivity.this);
        lopSinhVienManager = new LopSinhVienManager(AverageScoreActivity.this);
        loaiDiemManager = new LoaiDiemManager(AverageScoreActivity.this);
        diemManager = new DiemManager(AverageScoreActivity.this);
        spinnerLopHanhChinh = findViewById(R.id.spinnerLop);
        spinnerSinhVien = findViewById(R.id.spinnerSinhVien);
        lopHanhChinhAdapter = new LopHanhChinhAdapter(AverageScoreActivity.this, R.layout.itemgiangvienselected, lopHanhChinhManager.getAllLopHanhChinh());
        spinnerLopHanhChinh.setAdapter(lopHanhChinhAdapter);
        spinnerLopHanhChinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLopHanhChinh = lopHanhChinhAdapter.getItem(position).getMaLopHanhChinh();
                sinhVienListSpinner = sinhVienManager.getSinhVienByMaLopHanhChinh(maLopHanhChinh);

                if (sinhVienListSpinner == null || sinhVienListSpinner.isEmpty()) {
                    setEmptyAdapter();
                } else {
                    sinhVienbyMaLopHCAdapter = new SinhVienbyMaLopHCAdapter(
                            AverageScoreActivity.this,
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
                            maSinhVien = null;
                        }
                    });


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerSinhVien.setAdapter(null);
                maSinhVien = null;
            }

            private void setEmptyAdapter() {
                ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(
                        AverageScoreActivity.this,
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
                maSinhVien = null;
            }
        });
        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maSinhVien == null) {
                    Toast.makeText(AverageScoreActivity.this, "Sinh viên không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Double> diemTrungBinh = new ArrayList<>();
                List<String> maMonHoc = new ArrayList<>();
                barChart.setVisibility(View.VISIBLE);
                barChart.clear();

                maLopList = lopSinhVienManager.getMaLopFromMSSV(maSinhVien);

                if (maLopList == null || maLopList.isEmpty()) {
                    txtThongBao.setText("Sinh viên này chưa học môn học nào!!!");
                    return;
                }

                for (String malop : maLopList) {
                    maLopSinhVienList = new ArrayList<>();
                    String maLopSinhVien = lopSinhVienManager.getMaLopSinhVienfromMalopMSSV(malop, maSinhVien);
                    if (maLopSinhVien == null) {
                        txtThongBao.setText("Sinh viên này chưa học môn học nào!!!");
                        return;
                    }
                    maLopSinhVienList.add(maLopSinhVien);

                    for (String maLSV : maLopSinhVienList) {
                        diem = diemManager.getDiemfromMalopsinhvien(maLSV);

                        if (diem == null || diem.isEmpty()) {
                            txtThongBao.setText("Chưa có điểm môn học");
                            continue;
                        }

                        double tong = 0.0;
                        for (Diem d : diem) {
                            tong += d.getDiemSo() * loaiDiemManager.getLoaiDiem(d.getMaLoaiDiem()).getTrongSo();
                        }

                        String formattedDiemTrungBinh = String.format("%.1f", tong);
                        diemTrungBinh.add(Double.parseDouble(formattedDiemTrungBinh));
                        maMonHoc.add(monHocManager.getMonHoc(lopHocPhanManager.getLopHocPhan(malop).getMaMonHoc()).getMaMonHoc());
                    }
                }
                ArrayList<BarEntry> averageScore = new ArrayList<>();
                for (int i = 0; i < diemTrungBinh.size(); i++) {
                    float diem = diemTrungBinh.get(i).floatValue();
                    averageScore.add(new BarEntry(i, diem));
                }

                BarDataSet dataSet = new BarDataSet(averageScore, "Môn học");
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                dataSet.setValueTextColor(Color.BLACK);
                dataSet.setValueTextSize(16f);
                dataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == Math.floor(value)) {
                            return String.format("%d", (int) value);
                        } else {
                            return String.format("%.1f", value);
                        }
                    }
                });

                BarData barData = new BarData(dataSet);

                barChart.setFitBars(true);
                barChart.setData(barData);
                barChart.getDescription().setText("Điểm theo hệ 10 ");
                barChart.animateY(1000);

                barChart.getXAxis().setTextSize(16f);
                barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(maMonHoc));
                barChart.getXAxis().setGranularity(1f);

                CustomThongKe ct = new CustomThongKe(AverageScoreActivity.this, R.layout.customthongke, maMonHoc, diemTrungBinh);
                barChart.setMarker(ct);


                YAxis leftYAxis = barChart.getAxisLeft();
                leftYAxis.setGranularity(2f);
                leftYAxis.setAxisMinimum(0f);
                leftYAxis.setAxisMaximum(10f);
                leftYAxis.setLabelCount(6, false);
                leftYAxis.setEnabled(true);
                barChart.getAxisRight().setEnabled(false);


                barChart.invalidate();
                txtThongBao.setText("");
            }
        });

    }
}