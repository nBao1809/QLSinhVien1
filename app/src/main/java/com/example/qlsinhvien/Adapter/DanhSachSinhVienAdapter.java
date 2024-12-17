package com.example.qlsinhvien.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.qlsinhvien.Activities.DanhSachLopSinhVienActivity;
import com.example.qlsinhvien.Activities.DiemSinhVien;
import com.example.qlsinhvien.Activities.QuanLyDanhSachSinhVienActivity;
import com.example.qlsinhvien.Activities.QuanLyDiemvaInforSinhVien;
import com.example.qlsinhvien.Activities.QuanLySinhVienActivity;
import com.example.qlsinhvien.Models.LopHanhChinh;
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.Models.SinhVien;

import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.LopHanhChinhManager;
import com.example.qlsinhvien.dao.LopSinhVienManager;
import com.example.qlsinhvien.dao.NganhManager;
import com.example.qlsinhvien.dao.SinhVienManager;
import com.example.qlsinhvien.dao.UserManager;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DanhSachSinhVienAdapter extends RecyclerView.Adapter<DanhSachSinhVienAdapter.DanhSachSinhVienViewHolder> implements Filterable {
    private UserManager userManager;
    private LopHanhChinhManager lopHanhChinhManager;
    private SinhVienManager sinhVienManager;
    private LopSinhVienManager lopSinhVienManager;
    private Context context;
    private List<SinhVien> sinhVienList, sinhVienListOld;
    NganhManager nganhManager;
    LopHanhChinhAdapter lopHanhChinhAdapter;
    NganhAdapter nganhAdapter;
    String MSSV, hoTen, CCCD, maLopHanhChinh, maNganh;
    Double ngaySinh = 0.0;
    int ID;
    QuanLyDanhSachSinhVienActivity quanLyDanhSachSinhVienActivity;


    public DanhSachSinhVienAdapter(Context context, QuanLyDanhSachSinhVienActivity quanLyDanhSachSinhVienActivity) {
        this.context = context;
        this.quanLyDanhSachSinhVienActivity = quanLyDanhSachSinhVienActivity;
        userManager = new UserManager(context);
        sinhVienManager = new SinhVienManager(context);
        lopSinhVienManager = new LopSinhVienManager(context);
        lopHanhChinhManager = new LopHanhChinhManager(context);
        nganhManager = new NganhManager(context);
    }


    public void setData(List<SinhVien> sinhVienList) {
        this.sinhVienList = sinhVienList;
        this.sinhVienListOld = sinhVienList;
        notifyDataSetChanged();
    }

    public String dateFormat(double ngaySinhDouble) {
        int nam = (int) ngaySinhDouble;
        int thangNgay = (int) ((ngaySinhDouble - nam) * 10000);
        int thang = thangNgay / 100;
        int ngay = thangNgay % 100;
        String formattedDate = String.format("%02d/%02d/%04d", ngay, thang, nam);
        return formattedDate;
    }

    @NonNull
    @Override
    public DanhSachSinhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemquanlydanhsachsinhvien, parent, false);


        return new DanhSachSinhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachSinhVienViewHolder holder, int position) {
        SinhVien sinhVien = sinhVienList.get(position);
        if (sinhVien == null)
            return;
        holder.txtTen.setText(sinhVien.getHoTen());
        holder.txtMSSV.setText(sinhVien.getMssv());
        holder.txtLopHanhChinh.setText(lopHanhChinhManager.getLopHanhChinh(sinhVien.getMaLopHanhChinh()).getTenLopHanhChinh());
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToLopSinhVien(sinhVien);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDelete(sinhVien);
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEdit(sinhVien);
            }
        });

    }

    public void onClickEdit(SinhVien sinhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.itemchinhsuasinhvien, null);
        builder.setView(view);

        EditText edtMSSV, edtHoten, edtCCCD, edtNgaySinh;
        Button btnHuy, btnLuu;
        ImageButton ibtnBirthDay;
        Spinner spinnerLopHanhChinh, spinnerNganh;
        edtMSSV = view.findViewById(R.id.edtMSSV);
        edtHoten = view.findViewById(R.id.edtHoten);
        edtCCCD = view.findViewById(R.id.edtCCCD);
        edtNgaySinh = view.findViewById(R.id.edtNgaySinh);
        btnHuy = view.findViewById(R.id.btnHuy);
        btnLuu = view.findViewById(R.id.btnLuu);
        ibtnBirthDay = view.findViewById(R.id.ibtnBirthDay);
        spinnerLopHanhChinh = view.findViewById(R.id.spinnerLopHanhChinh);
        spinnerNganh = view.findViewById(R.id.spinnerNganh);

        edtMSSV.setText(sinhVien.getMssv());
        edtMSSV.setBackgroundColor(Color.TRANSPARENT);
        edtHoten.setText(sinhVien.getHoTen());
        edtCCCD.setText(sinhVien.getCccd());
        ngaySinh = sinhVien.getNgaySinh();
        String sNgaySinh = dateFormat(sinhVien.getNgaySinh());
        edtNgaySinh.setText(sNgaySinh);

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
                showDatePickerDialogForBirthDate(edtNgaySinh);
            }
        });
        lopHanhChinhAdapter = new LopHanhChinhAdapter(context, R.layout.itemgiangvienselected, lopHanhChinhManager.getAllLopHanhChinh());
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
        nganhAdapter = new NganhAdapter(context, R.layout.itemgiangvienselected, nganhManager.getAllNganh());
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
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = sinhVien.getId();
                MSSV = sinhVien.getMssv();
                hoTen = edtHoten.getText().toString().trim();
                CCCD = edtCCCD.getText().toString().trim();
                if (MSSV.isEmpty() || hoTen.isEmpty() || CCCD.isEmpty() || ID == 0 || maLopHanhChinh == null || maNganh == null) {
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    SinhVien sinhVienUpdate = new SinhVien(MSSV, hoTen, CCCD, ngaySinh, ID, maLopHanhChinh, maNganh);
                    int ketqua = sinhVienManager.updateSinhVien(sinhVienUpdate);
                    if (ketqua > 0) {
                        for (int i = 0; i < sinhVienList.size(); i++) {
                            if (sinhVienList.get(i).equals(MSSV)) {
                                sinhVienList.set(i, sinhVienUpdate);
                                break;
                            }
                        }

                        notifyDataSetChanged();
                        View view = LayoutInflater.from(context).inflate(R.layout.successdialog, null);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setView(view);
                        Button successDone = view.findViewById(R.id.successDone);
                        TextView sucessBelow = view.findViewById(R.id.successBelow);
                        AlertDialog alertDialog = builder1.create();
                        if (alertDialog.getWindow() != null) {
                            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                            alertDialog.getWindow().setGravity(Gravity.CENTER);
                        }
                        alertDialog.show();
                        sucessBelow.setText("Bạn đã chỉnh sửa thành công");
                        successDone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                    } else {
                        View view = LayoutInflater.from(context).inflate(R.layout.faildialog, null);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setView(view);
                        Button failDone = view.findViewById(R.id.failDone);
                        TextView failBelow = view.findViewById(R.id.failBelow);
                        AlertDialog alertDialog = builder1.create();
                        if (alertDialog.getWindow() != null) {
                            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                            alertDialog.getWindow().setGravity(Gravity.CENTER);
                        }
                        alertDialog.show();
                        failBelow.setText("Chỉnh sửa thất bại");
                        failDone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                    }
                }

            }
        });
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

            // Tạo giá trị Double với phần thập phân
            int year = selectedDate.get(Calendar.YEAR);
            int month = selectedDate.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
            int day = selectedDate.get(Calendar.DAY_OF_MONTH);
            ngaySinh = year + (month / 100.0) + (day / 10000.0); // Ví dụ: 2023.1212
        });
        // Hiển thị MaterialDatePicker
        materialDatePicker.show(quanLyDanhSachSinhVienActivity.getSupportFragmentManager(), materialDatePicker.toString());
    }

    private void showDatePickerDialogForBirthDate(final EditText edtNgaySinh) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                edtNgaySinh.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);

                        // Cập nhật ngày cho EditText
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        edtNgaySinh.setText(dateFormat.format(selectedDate.getTime()));

                        // Chuyển ngày sinh thành kiểu Double (nếu cần)
                        Double ngaySinh = Double.parseDouble(String.format(Locale.getDefault(), "%d.%02d%02d", year, month + 1, dayOfMonth));
                        // Lưu lại ngày sinh
                        // Bạn có thể lưu ngày sinh này vào biến tương ứng trong lớp của bạn nếu cần
                        // ví dụ: sinhVien.setNgaySinh(ngaySinh);
                    }
                },
                year, month, day
        );

        // Thiết lập ngày tối thiểu cho DatePicker (ngày hiện tại)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);  // Để tránh chọn ngày trong tương lai

        // Nếu đã có ngày trong EditText, cập nhật DatePicker với ngày hiện tại trong EditText
        String currentDate = edtNgaySinh.getText().toString();
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

    public void onClickDelete(SinhVien sinhvien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa sinh viên " + sinhvien.getHoTen() + " ?");
        builder.setMessage("Bạn có chắc muốn xóa sinh viên " + sinhvien.getHoTen() + " không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int ketquaSV = sinhVienManager.deleteSinhVien(sinhvien.getMssv());
                int ketquaUser = userManager.deleteUser(userManager.getUserByID(sinhvien.getId()).getID());
                sinhVienList.remove(sinhvien);
                if (sinhVienList.isEmpty()) {
                    if (quanLyDanhSachSinhVienActivity != null) {
                        quanLyDanhSachSinhVienActivity.setThongBaoVisibility(true);
                    }
                }
                notifyDataSetChanged();
                if (ketquaSV > 0 && ketquaUser > 0) {
                    View view = LayoutInflater.from(context).inflate(R.layout.successdialog, null);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setView(view);
                    Button successDone = view.findViewById(R.id.successDone);
                    TextView successBelow = view.findViewById(R.id.successBelow);
                    AlertDialog alertDialog = builder1.create();
                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                        alertDialog.getWindow().setGravity(Gravity.CENTER);
                    }
                    alertDialog.show();
                    successBelow.setText("Xóa sinh viên thành công!!!");
                    successDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                } else {
                    View view = LayoutInflater.from(context).inflate(R.layout.faildialog, null);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setView(view);
                    Button failDone = view.findViewById(R.id.failDone);
                    TextView failBelow = view.findViewById(R.id.failBelow);
                    AlertDialog alertDialog = builder1.create();
                    if (alertDialog.getWindow() != null) {
                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.custome_success);
                        alertDialog.getWindow().setGravity(Gravity.CENTER);
                    }
                    alertDialog.show();
                    failBelow.setText("Xóa sinh viên thất bại");
                    failDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    private void onClickGoToLopSinhVien(SinhVien sinhVien) {

        Intent intent = new Intent(context, QuanLyDiemvaInforSinhVien.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("sinhVien", sinhVien);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (sinhVienList != null) {
            Log.d("test5", String.valueOf(sinhVienList.size()));
            return sinhVienList.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = StringUtility.removeMark(constraint.toString().toLowerCase());
                if (searchString.isEmpty()) {
                    sinhVienList = sinhVienListOld;
                } else {
                    List<SinhVien> list = new ArrayList<>();
                    for (SinhVien sinhVien : sinhVienListOld) {
                        String mssv =
                                StringUtility.removeMark(sinhVien.getMssv().toLowerCase());
                        String tenSinhVien =
                                StringUtility.removeMark(sinhVien.getHoTen().toLowerCase());
                        if (mssv.startsWith(searchString) || tenSinhVien.contains(searchString) || tenSinhVien.startsWith(searchString)) {
                            list.add(sinhVien);
                        }

                    }
                    sinhVienList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = sinhVienList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sinhVienList = (List<SinhVien>) results.values;
                if (sinhVienList == null || sinhVienList.isEmpty()) {
                    // Hiển thị thông báo không tìm thấy sinh viên
                    if (context instanceof QuanLyDanhSachSinhVienActivity) {
                        ((QuanLyDanhSachSinhVienActivity) context).setThongBaoVisibility(true);
                    }
                } else {
                    // Ẩn thông báo
                    if (context instanceof QuanLyDanhSachSinhVienActivity) {
                        ((QuanLyDanhSachSinhVienActivity) context).setThongBaoVisibility(false);
                    }
                }
                notifyDataSetChanged();
            }
        };
    }

    public class DanhSachSinhVienViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtMSSV, txtLopHanhChinh;
        CardView itemLayout;
        ImageButton btnEdit, btnDelete;

        public DanhSachSinhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtMSSV = itemView.findViewById(R.id.txtMSSV);
            txtLopHanhChinh = itemView.findViewById(R.id.txtLopHanhChinh);
            itemLayout = itemView.findViewById(R.id.layoutItem);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }

    public void release() {
        context = null;

    }
}
