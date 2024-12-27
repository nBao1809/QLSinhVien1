package com.example.qlsinhvien.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.example.qlsinhvien.dao.SinhVienManager;
import com.example.qlsinhvien.dao.UserManager;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DanhSachSinhVienTamThoiAdapter extends RecyclerView.Adapter<DanhSachSinhVienTamThoiAdapter.DanhSachSinhVienViewHolder> implements Filterable {
    private UserManager userManager;
    private LopHanhChinhManager lopHanhChinhManager;
    private SinhVienManager sinhVienManager;
    private LopSinhVienManager lopSinhVienManager;
    private Context context;
    private List<SinhVien> sinhVienList,sinhVienListOld;
    private List<User> userList;


    public DanhSachSinhVienTamThoiAdapter(Context context) {
        this.context = context;
        userManager = new UserManager(context);
        sinhVienManager = new SinhVienManager(context);
        lopSinhVienManager = new LopSinhVienManager(context);
        lopHanhChinhManager = new LopHanhChinhManager(context);
    }


    public void setData(List<SinhVien> sinhVienList,List<User> userList) {
        this.sinhVienList = sinhVienList;
        this.sinhVienListOld=sinhVienList;
        this.userList = userList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdanhsachsinhvientemp, parent, false);


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

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positionTam = holder.getAdapterPosition();
                if (positionTam != RecyclerView.NO_POSITION) {
                    int idBiXoa = sinhVienList.get(positionTam).getId();
                    sinhVienList.remove(positionTam);
                    userList.remove(positionTam);
                    updateSinhVienList(idBiXoa);
                    ((QuanLyDanhSachSinhVienActivity)context).minusUserID();
                    notifyItemRemoved(positionTam);
                }
            }
        });


    }

    public void updateSinhVienList(int idBiXoa) {
        for (int i = 0; i < sinhVienList.size(); i++) {
            if (idBiXoa + 1 == sinhVienList.get(i).getId()) {
                SinhVien sinhVien = sinhVienList.get(i);
                sinhVien.setId(idBiXoa);
                idBiXoa = sinhVienList.get(i).getId();
            }
            Log.d("test", String.valueOf(sinhVienList.get(i).getId()));
        }
    }




    @Override
    public int getItemCount() {
        if (sinhVienList != null)
            return sinhVienList.size();
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
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }

    public void release() {
        context = null;

    }
}
