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
import com.example.qlsinhvien.Activities.QuanLyGiangVienActivity;
import com.example.qlsinhvien.Activities.QuanLySinhVienActivity;
import com.example.qlsinhvien.Activities.ThongTinChiTietGiangVienActivity;
import com.example.qlsinhvien.Activities.ThongTinChiTietSinhVien;
import com.example.qlsinhvien.Models.GiangVien;
import com.example.qlsinhvien.Models.LopHanhChinh;
import com.example.qlsinhvien.Models.LopHocPhan;
import com.example.qlsinhvien.Models.SinhVien;

import com.example.qlsinhvien.Models.User;
import com.example.qlsinhvien.R;
import com.example.qlsinhvien.StringUtility;
import com.example.qlsinhvien.dao.GiangVienManager;
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

public class DanhSachGiangVienAdapter extends RecyclerView.Adapter<DanhSachGiangVienAdapter.DanhSachGiangVienViewHolder> implements Filterable {
    private UserManager userManager;
    private LopHanhChinhManager lopHanhChinhManager;
    private GiangVienManager giangVienManager;
    private LopSinhVienManager lopSinhVienManager;
    private Context context;
    private List<GiangVien> giangVienList, giangVienListOld;
    NganhManager nganhManager;
    LopHanhChinhAdapter lopHanhChinhAdapter;
    NganhAdapter nganhAdapter;
    String MGV, hoTen, CCCD, khoa;
    Double ngaySinh = 0.0;
    int ID;
    QuanLyGiangVienActivity quanLyGiangVienActivity;


    public DanhSachGiangVienAdapter(Context context, QuanLyGiangVienActivity quanLyGiangVienActivity) {
        this.context = context;
        this.quanLyGiangVienActivity = quanLyGiangVienActivity;
        userManager = new UserManager(context);
        giangVienManager = new GiangVienManager(context);
        lopSinhVienManager = new LopSinhVienManager(context);
        lopHanhChinhManager = new LopHanhChinhManager(context);
        nganhManager = new NganhManager(context);
    }


    public void setData(List<GiangVien> giangVienList) {
        this.giangVienList = giangVienList;
        this.giangVienListOld = giangVienList;
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
    public DanhSachGiangVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemquanlygiangvien, parent, false);


        return new DanhSachGiangVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachGiangVienViewHolder holder, int position) {
        GiangVien giangVien = giangVienList.get(position);
        if (giangVien == null)
            return;
        holder.txtTen.setText(giangVien.getHoTen());
        holder.txtMGV.setText(giangVien.getMaGiangVien());
        holder.txtKhoa.setText(giangVien.getKhoa());
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToLopGiangVien(giangVien);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDelete(giangVien);
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEdit(giangVien);
            }
        });

    }

    public void onClickEdit(GiangVien giangVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.itemchinhsuagiangvien, null);
        builder.setView(view);

        EditText edtMGV, edtHoten, edtCCCD, edtNgaySinh, edtKhoa;
        Button btnHuy, btnLuu;
        ImageButton ibtnBirthDay;
        edtMGV = view.findViewById(R.id.edtMGV);
        edtHoten = view.findViewById(R.id.edtHoten);
        edtCCCD = view.findViewById(R.id.edtCCCD);
        edtNgaySinh = view.findViewById(R.id.edtNgaySinh);
        edtKhoa=view.findViewById(R.id.edtKhoa);
        btnHuy = view.findViewById(R.id.btnHuy);
        btnLuu = view.findViewById(R.id.btnLuu);
        ibtnBirthDay = view.findViewById(R.id.ibtnBirthDay);


        edtMGV.setText(giangVien.getMaGiangVien());
        edtMGV.setBackgroundColor(Color.TRANSPARENT);
        edtHoten.setText(giangVien.getHoTen());
        edtCCCD.setText(giangVien.getCccd());
        edtKhoa.setText(giangVien.getKhoa());
        ngaySinh = giangVien.getNgaySinh();
        String sNgaySinh = dateFormat(giangVien.getNgaySinh());
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
                showMaterialDatePicker(edtNgaySinh);
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = giangVien.getId();
                MGV = giangVien.getMaGiangVien();
                hoTen = edtHoten.getText().toString().trim();
                CCCD = edtCCCD.getText().toString().trim();
                khoa=edtKhoa.getText().toString().trim();
                if (MGV.isEmpty() || hoTen.isEmpty() || CCCD.isEmpty() || ID == 0||khoa.isEmpty() ) {
                    Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    GiangVien giangVienUpdate = new GiangVien(MGV, hoTen, CCCD, ngaySinh, khoa, ID);
                    int ketqua = giangVienManager.updateGiangVien(giangVienUpdate);
                    if (ketqua > 0) {
                        for (int i = 0; i < giangVienList.size(); i++) {
                            if (giangVienList.get(i).getMaGiangVien().equals(MGV)) {
                                giangVienList.set(i, giangVienUpdate);
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


        MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày sinh")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .build();

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            Calendar selectedDate = Calendar.getInstance();
            selectedDate.setTimeInMillis(selection);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            edtNgay.setText(dateFormat.format(selectedDate.getTime()));

            // Tạo giá trị Double với phần thập phân
            int year = selectedDate.get(Calendar.YEAR);
            int month = selectedDate.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
            int day = selectedDate.get(Calendar.DAY_OF_MONTH);
            ngaySinh = year + (month / 100.0) + (day / 10000.0); // Ví dụ: 2023.1212
        });
        // Hiển thị MaterialDatePicker
        materialDatePicker.show(quanLyGiangVienActivity.getSupportFragmentManager(), materialDatePicker.toString());
    }


    public void onClickDelete(GiangVien giangVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa giảng viên " + giangVien.getHoTen() + " ?");
        builder.setMessage("Bạn có chắc muốn xóa sinh viên " + giangVien.getHoTen() + " không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int ketquaGV = giangVienManager.deleteGiangVien(giangVien.getMaGiangVien());
                int ketquaUser = userManager.deleteUser(userManager.getUserByID(giangVien.getId()).getID());

                if (ketquaGV > 0 && ketquaUser > 0) {
                    giangVienList.remove(giangVien);
                    if (giangVienList.isEmpty()) {
                        if (quanLyGiangVienActivity != null) {
                            quanLyGiangVienActivity.setThongBaoVisibility(true);
                        }
                    }
                    notifyDataSetChanged();
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
                    successBelow.setText("Xóa giảng viên thành công!!!");
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
                    failBelow.setText("Xóa giảng viên thất bại");
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


    private void onClickGoToLopGiangVien(GiangVien giangVien) {

        Intent intent = new Intent(context, ThongTinChiTietGiangVienActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("giangVien", giangVien);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (giangVienList != null) {
            Log.d("test5", String.valueOf(giangVienList.size()));
            return giangVienList.size();
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
                    giangVienList = giangVienListOld;
                } else {
                    List<GiangVien> list = new ArrayList<>();
                    for (GiangVien giangVien : giangVienListOld) {
                        String mgv =
                                StringUtility.removeMark(giangVien.getMaGiangVien().toLowerCase());
                        String tenGiangVien =
                                StringUtility.removeMark(giangVien.getHoTen().toLowerCase());
                        if (mgv.startsWith(searchString) || tenGiangVien.contains(searchString) || tenGiangVien.startsWith(searchString)) {
                            list.add(giangVien);
                        }

                    }
                    giangVienList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = giangVienList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                giangVienList = (List<GiangVien>) results.values;
                if (giangVienList == null || giangVienList.isEmpty()) {
                    // Hiển thị thông báo không tìm thấy sinh viên
                    if (context instanceof QuanLyGiangVienActivity) {
                        ((QuanLyGiangVienActivity) context).setThongBaoVisibility(true);
                    }
                } else {
                    // Ẩn thông báo
                    if (context instanceof QuanLyGiangVienActivity) {
                        ((QuanLyGiangVienActivity) context).setThongBaoVisibility(false);
                    }
                }
                notifyDataSetChanged();
            }
        };
    }

    public class DanhSachGiangVienViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtMGV, txtKhoa;
        CardView itemLayout;
        ImageButton btnEdit, btnDelete;

        public DanhSachGiangVienViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtMGV = itemView.findViewById(R.id.txtMGV);
            txtKhoa = itemView.findViewById(R.id.txtKhoa);
            itemLayout = itemView.findViewById(R.id.layoutItem);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }

    public void release() {
        context = null;

    }
}
