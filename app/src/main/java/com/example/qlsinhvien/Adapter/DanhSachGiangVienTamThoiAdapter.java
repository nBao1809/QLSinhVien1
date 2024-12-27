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
import com.example.qlsinhvien.Activities.QuanLyGiangVienActivity;
import com.example.qlsinhvien.Activities.QuanLySinhVienActivity;
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

public class DanhSachGiangVienTamThoiAdapter extends RecyclerView.Adapter<DanhSachGiangVienTamThoiAdapter.DanhSachGiangVienTamThoiViewHolder> implements Filterable {
    private UserManager userManager;

    private GiangVienManager giangVienManager;
    private Context context;
    private List<GiangVien> giangVienList, giangVienListOld;
    private List<User> userList;
    int userID;


    public DanhSachGiangVienTamThoiAdapter(Context context) {
        this.context = context;
        userManager = new UserManager(context);
        giangVienManager = new GiangVienManager(context);

    }


    public void setData(List<GiangVien> giangVienList, List<User> userList) {
        this.giangVienList = giangVienList;
        this.giangVienListOld = giangVienList;
        this.userList = userList;
        this.userID=userID;
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
    public DanhSachGiangVienTamThoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemthemgiangvientemp, parent, false);


        return new DanhSachGiangVienTamThoiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachGiangVienTamThoiViewHolder holder, int position) {
        GiangVien giangVien = giangVienList.get(position);
        if (giangVien == null)
            return;

        holder.txtTen.setText(giangVien.getHoTen());
        holder.txtKhoa.setText(giangVien.getKhoa());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positionTam = holder.getAdapterPosition();
                if (positionTam != RecyclerView.NO_POSITION) {
                    int idBiXoa = giangVienList.get(positionTam).getId();
                    giangVienList.remove(positionTam);
                    userList.remove(positionTam);
                    updateGiangVienList(idBiXoa);
                    ((QuanLyGiangVienActivity) context).minusUserID();
                    notifyDataSetChanged();
                }
            }
        });


    }

    public void updateGiangVienList(int idBiXoa) {
        for (int i = 0; i < giangVienList.size(); i++) {
            if (idBiXoa + 1 == giangVienList.get(i).getId()) {
                GiangVien giangVien = giangVienList.get(i);
                giangVien.setId(idBiXoa);
                idBiXoa = giangVienList.get(i).getId();
            }
        }

    }


    @Override
    public int getItemCount() {
        if (giangVienList != null)
            return giangVienList.size();
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
                        String mssv =
                                StringUtility.removeMark(giangVien.getMaGiangVien().toLowerCase());
                        String tenSinhVien =
                                StringUtility.removeMark(giangVien.getHoTen().toLowerCase());
                        if (mssv.startsWith(searchString) || tenSinhVien.contains(searchString) || tenSinhVien.startsWith(searchString)) {
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

    public class DanhSachGiangVienTamThoiViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtKhoa;
        CardView itemLayout;
        ImageButton btnDelete;

        public DanhSachGiangVienTamThoiViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtKhoa = itemView.findViewById(R.id.txtKhoa);
            itemLayout = itemView.findViewById(R.id.layoutItem);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }

    public void release() {
        context = null;

    }
}
